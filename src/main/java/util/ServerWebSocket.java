package util;

import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

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
	public void sendMessage(String message, Session session) {
		String name = (String) session.getUserProperties().get("username");
		// Đầu tiên sẽ lưu người dùng gửi tin nhắn vào session của họ
		if (name == null) {
			session.getUserProperties().put("username", message);
		}
		// Nếu user đã tồn tại thì sẽ gửi nội dung user nhập vào cho tất cả các user
		// trong danh sách
		else {
			try {
				for (Session session2 : listUser) {
					String name2 = (String) session2.getUserProperties().get("username");
					if (!name2.equals(name))
						session2.getBasicRemote().sendText(message);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	// Nếu user thoát ra khỏi chtr thì danh sách sẽ xóa user đó ra khỏi danh sách
	@OnClose
	public void removeUser(Session session) {
		listUser.remove(session);
	}

	@OnError
	public void showError(Throwable throwable) {
		throwable.printStackTrace();
	}
}
