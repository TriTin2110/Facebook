package WebSocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import Cache.UserAnnounce;
import Model.Announce;

@ServerEndpoint(value = "/NotificationWebSocket")
public class Notification {
	private static Map<String, Session> notifications = new HashMap<String, Session>();
	private static Map<String, List<String>> messages = new HashMap<String, List<String>>();

	// Get Websocket session notification
	public static Session getSession(String userId) {
		Set<String> keySets = notifications.keySet();
		for (String key : keySets) {
			if (userId.equals(key))
				return notifications.get(key);
		}
		return null;
	}

	@OnOpen
	public void setupConnection(Session session) {
		System.out.println("Da mo session");
	}

	@OnMessage
	public static void sendMessage(String userId, Session session) {
		// We will set Key-Value for each session with
		// Key: userId, Value: session
		if (!notifications.containsKey(userId)) {
			notifications.put(userId, session);
		}
		// Get all announce from user
		List<Announce> announces = UserAnnounce.getAnnounces(userId);
		try {
			// If there is no announce will return empty string
			if (announces.isEmpty()) {
				session.getBasicRemote().sendText("");
			} else {
				StringBuilder result = handleAnnounce(userId, announces);
				if (session.isOpen()) {
					try {
						saveMessage(userId, result.toString());
						session.getBasicRemote().sendText(result.toString());
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				} else
					System.out.println("Session is closed");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@OnClose
	public void handlingClosing(Session session) {
		System.out.println("Nguoi dung dong: " + System.currentTimeMillis());
	}

	public static void saveMessage(String userId, String message) {
		List<String> messageList = messages.get(userId);
		if (messageList == null) {
			messageList = new LinkedList<String>();
		}
		messageList.add(message);
		messages.put(userId, messageList);
	}

	public static void sendNotification(String userId, List<Announce> announces) {
		Session session = notifications.get(userId);
		if (session != null) {
			StringBuilder result = handleAnnounce(userId, announces);
			saveMessage(userId, result.toString());
			try {
				session.getBasicRemote().sendText(result.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private static StringBuilder handleAnnounce(String userId, List<Announce> announces) {
		StringBuilder result = new StringBuilder();
		announces.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
		for (Announce announce : announces) {
			result.append(announce.getMessage());
		}
		System.out.println("Nguoi dung da lay thong bao: " + System.currentTimeMillis());
		return result;
	}
}
