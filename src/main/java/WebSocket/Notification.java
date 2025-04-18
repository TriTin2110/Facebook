package WebSocket;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import Cache.UserAnnounce;
import Model.Announce;
import Model.User;

@ServerEndpoint(value = "/NotificationWebSocket")
public class Notification {
	private static List<Session> notifications = new ArrayList<Session>();

	@OnOpen
	public void setupConnection(Session session) {
		notifications.add(session);
	}

	@OnMessage
	public void sendMessage(String userId, Session session) {
		// Get all announce from user
		System.out.println(userId);
		List<Announce> sentAnnounces = UserAnnounce.getSentAnnounces(userId);
		List<Announce> receivedAnnounces = UserAnnounce.getReceivedAnnounces(userId);
		try {
			// If there is no announce will return empty string
			if (sentAnnounces.isEmpty() && receivedAnnounces.isEmpty())
				session.getBasicRemote().sendText("");
			else {
				StringBuilder result = handleAnnounce(userId, sentAnnounces, receivedAnnounces);
				session.getBasicRemote().sendText(result.toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public StringBuilder handleAnnounce(String userId, List<Announce> sentAnnounces, List<Announce> receivedAnnounces) {
		List<Announce> announces = new ArrayList<Announce>();

		// Add all element from 2 secondary announces to primary announces
		announces.addAll(sentAnnounces);
		announces.addAll(receivedAnnounces);

		// Sort announces
		announces.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));

		StringBuilder result = new StringBuilder();

		User toUser = null;
		String message;
		for (Announce announce : announces) {
			message = createMessage(userId, announce, toUser);
			result.append(message);
		}
		return result;
	}

	private String createMessage(String userId, Announce announce, User toUser) {
		String message;
		// If this announce is received announce
		if (userId.equals(announce.getTo().getUserId())) {
			toUser = announce.getFrom();
			message = "<li><a href=\"#\">" + "<img src=\"http://localhost:8080/SummerProject/img/" + toUser.getAvatar()
					+ "\" alt=\"\">\r\n" + "<p> Bạn đã nhận được lời mời kết bạn từ <b>"
					+ toUser.getUserInformation().getFullName() + "!</b> </p>" + "</a></li>";
		}
		// In case this announce is sent announce
		else {
			toUser = announce.getTo();
			message = "<li><a href=\"#\">" + "<img src=\"http://localhost:8080/SummerProject/img/" + toUser.getAvatar()
					+ "\" alt=\"\">\r\n" + "<p> Bạn đã gửi lời mời kết bạn cho <b>"
					+ toUser.getUserInformation().getFullName() + "!</b> </p>" + "</a></li>";
		}
		return message;
	}

}
