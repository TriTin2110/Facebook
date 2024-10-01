package util.MessageServer;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import javax.websocket.Session;

public class UserInteract {

	public void connect(Session currentUser, String message, String userName, Set<Session> listUser,
			Map<String, String> map) {
		String guestName = (String) currentUser.getUserProperties().get("guestName");
		String messageInDB = null;
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
			messageInDB = getMessageFromDB(currentUser, userName, guestName, map);
			System.out.println("Guest name của: " + userName + " là: " + guestName);
		}

		// khi user yêu cầu đổi guest (guest yêu cầu đổi khác với guest trước đó)
		else if (guestName != null) {
			String guestNamePrevious = currentUser.getUserProperties().get("guestName").toString();
			// Lưu toàn bộ tin nhắn trước đó của user hiện tại và user trước đó vào db
			String[] userGuestName = { currentUser.getUserProperties().get("username").toString(), guestNamePrevious };
			Arrays.sort(userGuestName);

			// Phải lấy đc toàn bộ nội dung của bên guest gửi qua
			guestName = message.split("=")[1];

			if (!guestNamePrevious.equals(guestName)) {
				InteractMessageInDB.savingMessageToDB(userGuestName[0] + userGuestName[1],
						map.get(userGuestName[0] + userGuestName[1]));
				currentUser.getUserProperties().put("guestName", guestName);
				System.out.println("Guest name của: " + userName + " là: " + guestName);
				for (Session session2 : listUser) {
					if (guestName.equals(session2.getUserProperties().get("username"))) {
						currentUser.getUserProperties().put("guest", session2);
						currentUser.getUserProperties().put("guestName", guestName);
					}
				}
				messageInDB = getMessageFromDB(currentUser, userName, guestName, map);
			} else {
				messageInDB = map.get(userGuestName[0] + userGuestName[1]);
			}

		}
		Util.showMessage(currentUser, userName, messageInDB);
	}

	public String getMessageFromDB(Session currentUser, String userName, String guestName, Map<String, String> map) {
		InteractMessageInDB.getMessengerBetweenUserInDB(currentUser, userName, guestName, map);
		String[] name = { userName, guestName };
		Arrays.sort(name);
		String previousMessage = getPreviousMessage(name, map);
		return previousMessage;

	}

	public void createUser(Session currentUser, String message, Set<Session> listUser) throws Exception {
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

	public void sendMessageForAnother(Session currentUser, String userName, String message, Map<String, String> map) {
		try {
			Session guest = (Session) currentUser.getUserProperties().get("guest");
			String guestName = (String) currentUser.getUserProperties().get("guestName");
			String[] name = { userName, guestName };
			Arrays.sort(name);

			String previousMessage = getPreviousMessage(name, map);

			message = (previousMessage == null) ? userName + ":" + message : previousMessage + userName + ":" + message;
			System.out.println(message + ";");
			map.put(name[0] + name[1], message + ";");
			// Thực hiện lưu tin nhắn của 2 user (kèm tin nhắn của 2 user trong db {nếu có})
			if (guest.isOpen()) {
				Session guestOfGuest = (Session) guest.getUserProperties().get("guest");
				if (guestOfGuest.getUserProperties().get("username").equals(userName)) {
					message = Util.getNewestMessage(message);
					guest.getBasicRemote().sendText(Util.showMessage(message.split(":")[1], false));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public String getPreviousMessage(String[] name, Map<String, String> map) {
		// Lấy tin nhắn trước đó giữa 2 người (nếu có)
		String previousMessage = map.get(name[0] + name[1]);
		return previousMessage;
	}
}
