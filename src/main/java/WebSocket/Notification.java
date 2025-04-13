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
		List<Announce> announces = UserAnnounce.getAnnounces(userId);
		try {
			if (announces.isEmpty())
				session.getBasicRemote().sendText("");
			else {
				StringBuilder result = handleAnnounce(announces);
				session.getBasicRemote().sendText(result.toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public StringBuilder handleAnnounce(List<Announce> announces) {
		StringBuilder result = new StringBuilder();
		for (Announce announce : announces) {
			User toUser = announce.getToUser();
			if (toUser != null) {
				result.append("<li><a href=\"#\">" + "<img src=\"http://localhost:8080/SummerProject/img/"
						+ toUser.getAvatar() + "\" alt=\"\">\r\n"
						+ "                      <p> Bạn đã gửi lời mời kết bạn cho <b>"
						+ toUser.getUserInformation().getFullName() + "</b> </p>" + "</a></li>");
			}
		}
		return result;
	}

}
