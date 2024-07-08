package util.MessageServer;

import java.util.Arrays;

import javax.websocket.Session;

import DAO.MessengerDAO;
import Model.Messenger;

public class InteractMessageInDB {
	public static void savingMessageToDB(String key, String map) {
		MessengerDAO messengerDAO = new MessengerDAO();
		if (map != null) {
			// Gọi hàm lưu tin nhắn
			Messenger messenger = new Messenger(key, map);
			messengerDAO.add(messenger);
		}
//			}
	}

	public static String getMessengerBetweenUserInDB(Session currentUser, String userName, String guestName) {
		// Hiển thị tin nhắn của cả 2 từ db (nếu có)
		String[] userNameGuestName = { userName, guestName };
		Arrays.sort(userNameGuestName);
		Messenger messengerInDB = new Messenger();
		messengerInDB.setUserNameGuestName(userNameGuestName[0] + userNameGuestName[1]);

		MessengerDAO messengerDAO = new MessengerDAO();
		messengerInDB = messengerDAO.selectById(messengerInDB); // Tìm kiếm tin nhắn trong db dưa vào tên của
																// currentUser và
		// guest (đã đc sắp xếp)
		// Khi tin nhắn giữa 2 người đã tồn tại trong db
		// Thì gọi hàm savingMessageBetweenUser để lưu tin nhắn trong phiên hiện tại
		// Và gửi tin nhắn trong db cho user gửi yêu cầu
		Session guest = (Session) currentUser.getUserProperties().get("guest");
		String previousMessage = InteractMessageOnTime.getPreviousMessage(currentUser, guest, userName, guestName);
		if (messengerInDB != null) {
			String message = messengerInDB.getMessage();

			if (previousMessage != null) {// Lưu tin nhắn của 2 user kể từ khi kết nối và đã có dữ liệu từ database
				System.out.println("Đã thực hiện");
				InteractMessageOnTime.savingMessageBetweenUser(currentUser, guest, userName, guestName,
						previousMessage);
				return previousMessage;
			} else {
				// Lấy tin nhắn trong db
				InteractMessageOnTime.savingMessageBetweenUser(currentUser, guest, userName, guestName, message);
				return message;
			}
		}
		return previousMessage;
	}
}
