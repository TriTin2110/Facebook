package util.MessageServer;

import java.io.IOException;
import java.util.Set;

import javax.websocket.Session;

public class Util {
	public static void showMessage(Session session, String name, String messageInDB) {
		try {
			// Lấy từng đoạn đc ngăn cách = dấu ";"
			String[] guestTextSplitString = messageInDB.split(";");
			// Hiển thị nội dung trước đó của 2 user
			for (String text : guestTextSplitString) {
				if (!text.isEmpty()) {
					String[] splitText = text.split(":"); // Chỉ lấy phần nội dung
					if (splitText[0].equals(name)) {// Khi tin nhắn là của mình
						session.getBasicRemote().sendText(showMessage(splitText[1], true));
					} else {
						session.getBasicRemote().sendText(showMessage(splitText[1], false));
					}
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void removeUserFromPageOfOther(Set<Session> listUser, Session currentUser, String userName) {
		for (Session otherUser : listUser) { // Xóa thẻ user ra khỏi danh sách
			try {
				otherUser.getBasicRemote().sendText("remove-user:" + userName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// Form hiển thị tin nhắn của bản thân
	public static String showMessage(String message, boolean selfMessage) {
		String chatAppMessage = (selfMessage) ? "chat-app__message chat-app__message--receiver"
				: "chat-app__message chat-app__message--sender";

		String chatAppMessageText = (selfMessage) ? "chat-app__message-text chat-app__message-text--receiver"
				: "chat-app__message-text chat-app__message-text--sender";

		return "<div class=\"" + chatAppMessage + "\">\r\n" + "					<div\r\n"
				+ "						class=\"" + chatAppMessageText + "\">\r\n" + "						" + message
				+ "</div>\r\n" + "				</div>";
	}
}
