package thread;

import java.io.IOException;

import javax.websocket.Session;

public class SendNotification extends Thread {
	private Session session;
	private String messageForSender;

	public SendNotification(Session session, String messageForSender) {
		this.session = session;
		this.messageForSender = messageForSender;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (session != null && session.isOpen()) {
			System.out.println("Da gui thong bao cho sender");
			try {
				session.getBasicRemote().sendText(messageForSender);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
