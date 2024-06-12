package util;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import DAO.MessengerDAO;
import Model.Messenger;

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
				String messageUserGuest = getMessengerBetweenUserInDB(session, name, guestName);
				if (messageUserGuest != null) {
					showMessage(session, name, messageUserGuest);
				}

			}
			// khi user yêu cầu đổi guest (guest yêu đổi phải khác với guest trước đó)
			else if (guestName != null) {
				// Phải lấy đc toàn bộ nội dung của bên guest gửi qua
				guestName = message.split("=")[1];
				session.getUserProperties().put("guestName", guestName);
				System.out.println("Guest name của: " + name + " là: " + guestName);
				String guestText = "";
				for (Session session2 : listUser) {
					if (guestName.equals(session2.getUserProperties().get("username"))) {
						// Tìm tin nhắn của 2 user trong db
						guestText = getMessengerBetweenUserInDB(session, name, guestName);
						// Nếu không tồn tại thì lấy tin nhắn trước đó trong phiên
						if (guestText == null) {
							guestText = getPreviousMessage(session, session2, name, guestName);
						}
					}
				}
				// Nếu đoạn tin nhắn trước đó != null thì in ra
				if (guestText != null) {
					showMessage(session, name, guestText);
				}
			}

		}
		// khi user ko yêu cầu kết nối với user khác
		else {
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

				}
				// trường hợp user gửi tin nhắn
				else {
					try {
						for (Session session2 : listUser) {
							String name2 = (String) session2.getUserProperties().get("username");
							if (name2.equals(session.getUserProperties().get("guestName"))) {
								// Lấy tin nhắn trước đó giữa 2 người (nếu có)
								String previousMessage = getPreviousMessage(session, session2, name, name2);
								message = (previousMessage == null) ? name + ":" + message
										: previousMessage + name + ":" + message;
								// Thực hiện lưu tin nhắn của 2 user
								savingMessageBetweenUser(session, session2, name, name2, message + ";");

								// Thực hiện đặt guestName cho user2 = user1 (nếu chưa có)
								if (session2.getUserProperties().get("guestName") == null) {
									session2.getUserProperties().put("guestName", name);
//									message = getNewestMessage(message);
//									session2.getBasicRemote().sendText(message.split(":")[1]);
								}

								// Thực hiện gửi tin nhắn cho guest nếu username của user1 == guestName của user
								// 2
								else if (session2.getUserProperties().get("guestName").equals(name)) {
//									message = getNewestMessage(message);
//									session2.getBasicRemote().sendText(message.split(":")[1]);
									message = getNewestMessage(message);
									session2.getBasicRemote().sendText(message.split(":")[1]);
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
		// Khi user đóng thì lưu lại tin nhắn trước đó của user với những user khác
		savingMessageToDB(session, (String) session.getUserProperties().get("username"));
		listUser.remove(session);
	}

	public void savingMessageToDB(Session session, String name) {
		// guest của user1 khác username của user2
		MessengerDAO messengerDAO = new MessengerDAO();
		for (Session session2 : listUser) {
			// Lần lượt lấy từng từng cuộc trò truyện của user và những người khác
			// Sau đó lưu cuộc trò chuyện của user và guestName tương ứng vào DB
			String user2Name = (String) session2.getUserProperties().get("username");
			String messageUserGuest = getPreviousMessage(session, session2, name, user2Name);
			if (messageUserGuest != null) {
				String[] userNameGuestName = { name, user2Name };
				Arrays.sort(userNameGuestName);
				// Gọi hàm lưu tin nhắn
				Messenger messenger = new Messenger(userNameGuestName[0] + userNameGuestName[1], messageUserGuest);
				messengerDAO.add(messenger);
			}
		}
	}

	public String getMessengerBetweenUserInDB(Session session, String name, String guestName) {
		// Hiển thị tin nhắn của cả 2 từ db (nếu có)
		String[] userNameGuestName = { name, guestName };
		Arrays.sort(userNameGuestName);
		Messenger messenger = new Messenger();
		messenger.setUserNameGuestName(userNameGuestName[0] + userNameGuestName[1]);

		MessengerDAO messengerDAO = new MessengerDAO();
		messenger = messengerDAO.selectById(messenger);
		// Khi tin nhắn giữa 2 người đã tồn tại trong db
		// Thì gọi hàm savingMessageBetweenUser để lưu tin nhắn trong phiên hiện tại
		// Và gửi tin nhắn trong db cho user gửi yêu cầu
		if (messenger != null) {
			for (Session session2 : listUser) {
				if (guestName.equals(session2.getUserProperties().get("username"))) {
					String message = "";
					String previousMessage = getPreviousMessage(session, session2, name, guestName);
					if (previousMessage != null) {
						System.out.println("Đã thực hiện");
						savingMessageBetweenUser(session, session2, name, guestName, previousMessage);
						return previousMessage;
					} else {
						// Lấy tin nhắn trong db
						message = messenger.getMessage();
						savingMessageBetweenUser(session, session2, name, guestName, message);
						return message;
					}
				}
			}
		}
		return null;

	}

	@OnError
	public void showError(Throwable throwable) {
		throwable.printStackTrace();
	}

	public void savingMessageBetweenUser(Session session, Session session2, String user1, String user2,
			String message) {
		if (session2.getUserProperties().get("text" + user2 + user1) != null) {
			session2.getUserProperties().put("text" + user2 + user1, message);
		} else {
			System.out.println("Đã thực hiện lưu trong phiên");
			System.out.println(message);
			session.getUserProperties().put("text" + user1 + user2, message);
		}
	}

	public String getPreviousMessage(Session session, Session session2, String name, String name2) {
		if (session2.getUserProperties().get("text" + name2 + name) != null) {
			return (String) session2.getUserProperties().get("text" + name2 + name);
		} else {
			return (String) session.getUserProperties().get("text" + name + name2);
		}
	}

	public void showMessage(Session session, String name, String guestText) {
		try {
			// Lấy từng đoạn đc ngăn cách = dấu ";"
			String[] guestTextSplitString = guestText.split(";");
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

	public String getNewestMessage(String message) {
		return message.substring(message.lastIndexOf(";") + 1, message.length());
	}

	// Hiển thị danh sách người dùng bên trái
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

	// Form hiển thị tin nhắn của bản thân
	public String showSelfMessage(String message) {
		return "<div contenteditable=\"false\" class=\"d-flex flex-row justify-content-end\">"
				+ "<p class=\"small p-2 me-3 mb-1 text-white rounded-3 bg-primary\">" + message + "</p></div><br>";
	}

}
