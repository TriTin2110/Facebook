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

	public static void sendMail(String email, String fullName, String userId, String url) {
		String css = "/css/emailAnnounce.css";

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
		System.out.println(url + css);
		String html = "<!DOCTYPE html>\r\n" + "<html>\r\n" + "  <head>\r\n" + "    <meta charset=\"UTF-8\" />\r\n"
				+ "    <title>Insert title here</title>\r\n" + "<style type=\"text/css\">\r\n" + "    body {\r\n"
				+ "    font-family: Arial, sans-serif;\r\n" + "    background-color: #f0f2f5;\r\n" + "}\r\n" + "\r\n"
				+ "* {\r\n" + "    margin: 0;\r\n" + "    padding: 0;\r\n" + "    box-sizing: border-box;\r\n" + "}\r\n"
				+ "\r\n" + ".container {\r\n" + "    display: flex;\r\n" + "    justify-content: center;\r\n"
				+ "    align-items: center;\r\n" + "    height: 100vh;\r\n" + "}\r\n" + "\r\n" + ".c-email {\r\n"
				+ "    width: 600px;\r\n" + "    text-align: center;\r\n" + "    border-radius: 50px;\r\n"
				+ "    box-shadow: 0px 1px 3px 1px #ccc;\r\n" + "}\r\n" + "\r\n" + ".header__title {\r\n"
				+ "    border-top-left-radius: 50px;\r\n" + "    border-top-right-radius: 50px;\r\n"
				+ "    color: #fff;\r\n" + "    padding: 12px 8px;\r\n" + "    background-color: #0fd59f;\r\n" + "}\r\n"
				+ "\r\n" + ".content__text-code {\r\n" + "    font-size: 30px;\r\n" + "    margin: 20px 0 40px 0;\r\n"
				+ "}\r\n" + "\r\n" + ".code__text {\r\n" + "    font-size: 30px;\r\n" + "    border-radius: 30px;\r\n"
				+ "    padding: 8px 20px;\r\n" + "    text-decoration: none;\r\n" + "    background-color: #ccc;\r\n"
				+ "}\r\n" + "\r\n" + ".content__text {\r\n" + "    color: #ccc;\r\n" + "    font-size: 20px;\r\n"
				+ "    font-style: italic;\r\n" + "    margin: 30px 0 60px 0;\r\n" + "}\r\n" + "</style>"
				+ "  </head>\r\n" + "  <body style=\"display: flex; justify-content: center;\">\r\n"
				+ "    <div class=\"container\">\r\n" + "      <div class=\"c-email\">\r\n"
				+ "        <div class=\"c-email__header\">\r\n" + "          <h2 class=\"header__title\">\r\n"
				+ "            Xin chào " + fullName + "! Cảm ơn bạn đã sử dụng BodyBook của chúng tôi\r\n"
				+ "          </h2>\r\n" + "        </div>\r\n" + "        <div class=\"content\">\r\n"
				+ "          <p class=\"content__text-code\">Để xác minh tài khoản, bạn hãy truy cập vào đường link dưới đây:</p>\r\n"
				+ "          <div class=\"code\">\r\n" + "            <a class=\"code__text\" href=\"" + url
				+ "/Account?action=xac-thuc-email&iduser=" + userId + "\">Nhấn vào đây để lấy mã xác thực</a>\r\n"
				+ "          </div>\r\n"
				+ "          <p class=\"content__text\">Mã xác thực này chỉ tồn tại trong 10 phút</p>\r\n"
				+ "        </div>\r\n" + "        <div class=\"footer\"></div>\r\n" + "      </div>\r\n"
				+ "    </div>\r\n" + "  </body>\r\n" + "</html>";
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
