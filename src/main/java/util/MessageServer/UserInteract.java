package util.MessageServer;

import java.util.Set;

import javax.websocket.Session;

public class UserInteract {
	public static void connect(Session currentUser, String message, String userName, Set<Session> listUser) {
		String guestName = (String) currentUser.getUserProperties().get("guestName");
		String messageInDB = "";
		if (guestName == null) { // Tạo mới guestName cho user gửi yêu cầu (khi user chưa kết nối với ai trước
									// đó)
			guestName = message.split("=")[1];
			for (Session session2 : listUser) {
				if (session2.getUserProperties().get("username").equals(guestName)) {
					Session guestTmp = session2;
					currentUser.getUserProperties().put("guest", guestTmp);
					currentUser.getUserProperties().put("guestName", guestName);
				}
			}
			System.out.println("Guest name của: " + userName + " là: " + guestName);
			messageInDB = InteractMessageInDB.getMessengerBetweenUserInDB(currentUser, userName, guestName);
		}

		// khi user yêu cầu đổi guest (guest yêu cầu đổi khác với guest trước đó)
		else if (guestName != null) {
			// Phải lấy đc toàn bộ nội dung của bên guest gửi qua
			guestName = message.split("=")[1];
			currentUser.getUserProperties().put("guestName", guestName);
			System.out.println("Guest name của: " + userName + " là: " + guestName);
			for (Session session2 : listUser) {
				if (guestName.equals(session2.getUserProperties().get("username"))) {
					currentUser.getUserProperties().put("guest", session2);
					currentUser.getUserProperties().put("guestName", guestName);
					// Tìm tin nhắn của 2 user trong db
					messageInDB = InteractMessageInDB.getMessengerBetweenUserInDB(currentUser, userName, guestName);
				}
			}
		}
		if (messageInDB != null) {
			Util.showMessage(currentUser, userName, messageInDB);
		}
	}

	public static void createUser(Session currentUser, String message, Set<Session> listUser) throws Exception {
		currentUser.getUserProperties().put("username", message);
		// Hiển thị những người khác tại khung danh sách
		for (Session otherUser : listUser) {
			if (!otherUser.getUserProperties().get("username").equals(message)) {
				// Gửi user mới vào cho các user còn lại
				otherUser.getBasicRemote().sendText("user-name: " + message);
				// Gửi các user còn lại cho user mới vào
				currentUser.getBasicRemote().sendText("user-name: " + otherUser.getUserProperties().get("username"));
			}
		}
	}

	public static void sendMessageForAnother(Session currentUser, String userName, String message) {
		try {
			Session guest = (Session) currentUser.getUserProperties().get("guest");
			String guestName = (String) currentUser.getUserProperties().get("guestName");
			// Lấy tin nhắn trước đó giữa 2 người (nếu có)
			String previousMessage = InteractMessageOnTime.getPreviousMessage(currentUser, guest, userName, guestName);

			message = (previousMessage == null) ? userName + ":" + message : previousMessage + userName + ":" + message;
			System.out.println(message);
			// Thực hiện lưu tin nhắn của 2 user (kèm tin nhắn của 2 user trong db {nếu có})
			InteractMessageOnTime.savingMessageBetweenUser(currentUser, guest, userName, guestName, message + ";");

			if (guest.isOpen()) {
				Session guestOfGuest = (Session) guest.getUserProperties().get("guest");
				// Thực hiện đặt guestName cho user2 = user1 (nếu chưa có)
				if (guestOfGuest == null) {
					guest.getUserProperties().put("guest", currentUser);
				}

				// Thực hiện gửi tin nhắn cho guest nếu username của user1 == guestName của user
				// 2 (2 user đã thông nhau)
				else if (guestOfGuest.getUserProperties().get("username").equals(userName)) {
					message = Util.getNewestMessage(message);
					guest.getBasicRemote().sendText(message.split(":")[1]);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
