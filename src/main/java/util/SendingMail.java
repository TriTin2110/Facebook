package util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendingMail {
	static final String from = "minhtrietpro8@gmail.com";
	static final String password = "itrjdkdydkllbdgr";

	public static void sendMail(String email, int ran, String fullName) {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true"); // Cho phép đăng nhập vào tài khoản để gửi mail
		properties.put("mail.smtp.starttls.enable", "true"); // Cho phép mã hóa TLS

		Authenticator authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication(from, password);
			}
		};

		// Tạo phiên làm việc mới
		Session session = Session.getInstance(properties, authenticator);

		MimeMessage mimeMessage = new MimeMessage(session);

		String html = "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "<meta charset=\"UTF-8\">\r\n"
				+ "<title>Insert title here</title>\r\n"
				+ "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH\" crossorigin=\"anonymous\">\r\n"
				+ "</head>\r\n" + "<body>\r\n" + "	<div class=\"container\">\r\n" + "		Xin chào " + fullName
				+ "! Cảm ơn bạn đã sử dụng BodyBook của chúng tôi\r\n" + "		<p>Mã xác minh của bạn là: <b>" + ran
				+ "</b></p>\r\n" + "	</div>\r\n" + "</body>\r\n" + "</html>";
		try {
			// Đặt header
			mimeMessage.addHeader("Content-type", "text/html; charset=UTF-8");
			// Đặt người gửi
			mimeMessage.setFrom(from);
			// Đặt người nhận
			mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			// Đặt tiêu đề
			mimeMessage.setSubject("Thử nghiệm");
			// Đặt ngày
			mimeMessage.setSentDate(new Date());
			// Đặt nội dung
			mimeMessage.setContent(html, "text/html; charset=UTF-8");

			// Tiến hành gửi mail
			Transport.send(mimeMessage);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
