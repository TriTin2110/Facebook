package util.MessageServer;

import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

//guestName là tên của user muốn kết nối
//name là tên của user gửi yêu cầu

@ServerEndpoint(value = "/serverChatting")
public class ServerWebSocket {
	// Set<Session> dùng để lưu trữ user phục vụ cho việc gửi tin nhắn
	private static Set<Session> listUser = new HashSet<Session>();

	// Khi có kết nối gửi đến thì sẽ lưu user vào listUser
	@OnOpen
	public void addUser(Session session) {
		listUser.add(session);
	}

	// Khi có tin nhắn gửi đến server thì server sẽ gửi tin nhắn đó cho tất cả các
	// user trong danh sách
	@OnMessage
	public void sendMessage(String message, Session currentUser) {
		String userName = (String) currentUser.getUserProperties().get("username");
		// Kết nối với user khác khi có yêu cầu
		if (message.contains("connectToUser")) {
			UserInteract.connect(currentUser, message, userName, listUser);
		}
		// khi user ko yêu cầu kết nối với user khác
		else {
			try {
				if (userName == null) {// Tạo user nếu họ chưa có trong danh sách (phần này sẽ tự động thực hiện khi
										// user đăng nhập vào web)
					UserInteract.createUser(currentUser, message, listUser);
				}
				// trường hợp user gửi tin nhắn
				else {
					UserInteract.sendMessageForAnother(currentUser, userName, message);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

	}

	// Nếu user thoát ra khỏi chtr thì danh sách sẽ xóa user đó ra khỏi danh sách
	@OnClose
	public void removeUser(Session currentUser) {
		if (listUser.size() == 1)
			listUser.remove(currentUser);
		else {
			// Khi user đóng thì lưu lại tin nhắn trước đó của user với những user khác
			String guestName = (String) currentUser.getUserProperties().get("guestName");
			if (guestName != null) { // Khi guest name không tồn tại thì việc lưu vào database sẽ bị lỗi
				InteractMessageInDB.savingMessageToDB(currentUser,
						(String) currentUser.getUserProperties().get("username"));
			}
			listUser.remove(currentUser);
			Util.removeUserFromPageOfOther(listUser, currentUser, guestName);
		}
	}

	@OnError
	public void showError(Throwable throwable) {
		throwable.printStackTrace();
	}
}
