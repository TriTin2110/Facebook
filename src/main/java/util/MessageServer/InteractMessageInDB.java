package util.MessageServer;

import java.util.Arrays;
import java.util.Map;

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

	public static void getMessengerBetweenUserInDB(Session currentUser, String userName, String guestName,
			Map<String, String> map) {
		// Hiển thị tin nhắn của cả 2 từ db (nếu có)
		String[] userNameGuestName = { userName, guestName };
		Arrays.sort(userNameGuestName);
		Messenger messengerInDB = new Messenger();
		messengerInDB.setUserNameGuestName(userNameGuestName[0] + userNameGuestName[1]);

		MessengerDAO messengerDAO = new MessengerDAO();
		messengerInDB = messengerDAO.selectById(messengerInDB.getUserNameGuestName()); // Tìm kiếm tin nhắn trong db dưa
																						// vào tên của
		// currentUser và
		// guest (đã đc sắp xếp)
		// Khi tin nhắn giữa 2 người đã tồn tại trong db
		// Lưu tin nhắn DB vào map
		// Và gửi tin nhắn trong db cho user gửi yêu cầu
		String[] name = { userName, guestName };
		Arrays.sort(name);
		if (messengerInDB != null) {
			String message = messengerInDB.getMessage();
			// Lấy tin nhắn trong db khi tin nhắn của 2 user chưa tồn tại trong session
			if (map.get(name[0] + name[1]) == null)
				map.put(name[0] + name[1], message);

		} else {
			if (map.get(name[0] + name[1]) == null)
				map.put(name[0] + name[1], "");
		}

	}
}
