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
				String[] splitText = text.split(":"); // Chỉ lấy phần nội dung
				if (splitText[0].equals(name)) {// Khi tin nhắn là của mình
					session.getBasicRemote().sendText(showSelfMessage(splitText[1]));
				} else {// Khi tin nhắn là của khách
					session.getBasicRemote().sendText(splitText[1]);
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void removeUserFromPageOfOther(Set<Session> listUser, Session currentUser, String guestName) {
		for (Session otherUser : listUser) { // Xóa thẻ user ra khỏi danh sách
			try {
				if (guestName.equals(otherUser.getUserProperties().get("username"))) {
					// Gọi hàm clear text nếu user đang online đang chat vs user (đã tắt)
					otherUser.getBasicRemote().sendText("clear-text");
					otherUser.getUserProperties().put("guestName", "null");
				}
				otherUser.getBasicRemote().sendText("remove-user:" + currentUser.getUserProperties().get("username"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// Cập nhật tin nhắn
	public static String getNewestMessage(String message) {
		return message.substring(message.lastIndexOf(";") + 1, message.length());
	}

	// Form hiển thị tin nhắn của bản thân
	public static String showSelfMessage(String message) {
		return "<div contenteditable=\"false\" class=\"d-flex flex-row justify-content-end\">"
				+ "<p class=\"small p-2 me-3 mb-1 text-white rounded-3 bg-primary\">" + message + "</p></div><br>";
	}
}
