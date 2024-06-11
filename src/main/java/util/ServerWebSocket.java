package util;

import java.io.IOException;
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
	public void sendMessage(String message, Session session) {
		String name = (String) session.getUserProperties().get("username");
		// Kết nối với user khác khi có yêu cầu
		if (message.contains("connectToUser")) {
			String guestName = (String) session.getUserProperties().get("guestName");
			if (guestName == null) { // Tạo mới guestName cho user gửi yêu cầu
				guestName = message.split("=")[1];
				session.getUserProperties().put("guestName", guestName);
				System.out.println("Guest name của: " + name + " là: " + guestName);
			}
			// khi user yêu cầu đổi guest
			else {
				// Phải lấy đc toàn bộ nội dung của bên guest gửi qua
				guestName = message.split("=")[1];
				session.getUserProperties().put("guestName", guestName);
				System.out.println("Guest name của: " + name + " là: " + guestName);
				String guestText = "";
				for (Session session2 : listUser) {
					if (guestName.equals(session2.getUserProperties().get("username"))) {
						guestText = getPreviousMessage(session, session2, name, guestName);
					}
				}
				if (guestText != null) {
					try {
						// Lấy từng đoạn đc ngăn cách = dấu ";"
						String[] guestTextSplitString = guestText.split(";");
						// Hiển thị nội dung trước đó của 2 user
						for (String text : guestTextSplitString) {
							session.getBasicRemote().sendText(text);
						}

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		} else {
			try {
				// Tạo user nếu họ chưa có trong danh sách
				if (name == null) {
					session.getUserProperties().put("username", message);
					// Hiển thị những người khác tại khung danh sách
					for (Session otherUser : listUser) {
						if (!otherUser.getUserProperties().get("username").equals(message)) {
							// Gửi user mới vào cho các user còn lại
							otherUser.getBasicRemote().sendText(leftSidePage(message));
							// Gửi các user còn lại cho user mới vào
							session.getBasicRemote()
									.sendText(leftSidePage((String) otherUser.getUserProperties().get("username")));
						}

					}

				} else {
					try {
						for (Session session2 : listUser) {
							String name2 = (String) session2.getUserProperties().get("username");
							if (name2.equals(session.getUserProperties().get("guestName"))) {
								String previousMessage = getPreviousMessage(session, session2, name, name2);
								message = (previousMessage == null) ? message : previousMessage + message;
								// Thực hiện lưu tin nhắn của 2 user
								savingMessageBetweenUser(session, session2, name, name2, message);

								// Thực hiện đặt guestName cho user2 guest (nếu chưa có)
								if (session2.getUserProperties().get("guestName") == null) {
									session2.getUserProperties().put("guestName", name);
									// Đặt guest name cho guest

									session2.getBasicRemote().sendText(message);
								}

								// Thực hiện gửi tin nhắn cho guest nếu username của user1 == guestName của user
								// 2
								else if (session2.getUserProperties().get("guestName").equals(name)) {
									System.out.println(message);
									if (message.contains(";"))
										message = message.substring(message.lastIndexOf(";") + 1, message.length());
									session2.getBasicRemote().sendText(message);
								}
							}

						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
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

	public void savingMessageBetweenUser(Session session, Session session2, String user1, String user2,
			String message) {
		if (session2.getUserProperties().get("text" + user2 + user1) != null) {
			session2.getUserProperties().put("text" + user2 + user1, message + ";");
		} else {
			session.getUserProperties().put("text" + user1 + user2, message + ";");
		}
	}

	public String getPreviousMessage(Session session, Session session2, String name, String name2) {
		if (session2.getUserProperties().get("text" + name2 + name) != null) {
			return (String) session2.getUserProperties().get("text" + name2 + name);
		} else {
			return (String) session.getUserProperties().get("text" + name + name2);
		}
	}

	public String leftSidePage(String userName) {
		String result = "<li class=\"p-2 border-bottom\">\r\n"
				+ "                            <button onclick=\"connectToUser('connectToUser=" + userName
				+ "')\" class=\"d-flex justify-content-between\">\r\n"
				+ "                              <div class=\"d-flex flex-row\">\r\n"
				+ "                                <div>\r\n" + "                                  <img\r\n"
				+ "                                    src=\"https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava1-bg.webp\"\r\n"
				+ "                                    alt=\"avatar\" class=\"d-flex align-self-center me-3\" width=\"60\">\r\n"
				+ "                                  <span class=\"badge bg-success badge-dot\"></span>\r\n"
				+ "                                </div>\r\n"
				+ "                                <div class=\"pt-1\">\r\n"
				+ "                                  <p class=\"fw-bold mb-0\">" + userName + "</p>\r\n"
				+ "                                  <p class=\"small text-muted\">Hello, Are you there?</p>\r\n"
				+ "                                </div>\r\n" + "                              </div>\r\n"
				+ "                              <div class=\"pt-1\">\r\n"
				+ "                                <p class=\"small text-muted mb-1\">Just now</p>\r\n"
				+ "                                <span class=\"badge bg-danger rounded-pill float-end\">3</span>\r\n"
				+ "                              </div>\r\n" + "                            </button>\r\n"
				+ "                          </li>";
		return result;
	}
}
