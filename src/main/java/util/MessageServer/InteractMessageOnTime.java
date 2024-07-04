package util.MessageServer;

import java.util.Arrays;

import javax.websocket.Session;

public class InteractMessageOnTime {
	// Lưu tin nhắn của 2 user khi cả hai đang chat vs nhau
	public static void savingMessageBetweenUser(Session session, Session session2, String user1, String user2,
			String message) {
		String[] userNameSorted = { user1, user2 };
		Arrays.sort(userNameSorted);
		if (!session2.isOpen()) {
			session.getUserProperties().put("text" + userNameSorted[0] + userNameSorted[1], message);
		} else {
			session2.getUserProperties().put("text" + userNameSorted[0] + userNameSorted[1], message);
			session.getUserProperties().put("text" + userNameSorted[0] + userNameSorted[1], message);
		}
	}

	// Lấy tin nhắn của 2 user khi cả hai đang online
	public static String getPreviousMessage(Session session, Session session2, String name, String name2) {
		String[] userNameSorted = { name, name2 };
		Arrays.sort(userNameSorted);
		if (!session2.isOpen()) {
			return (String) session.getUserProperties().get("text" + userNameSorted[0] + userNameSorted[1]);
		} else {
			return (String) session2.getUserProperties().get("text" + userNameSorted[0] + userNameSorted[1]);
		}
	}
}
