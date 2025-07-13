package Model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import Enums.TypeAnnouce;

@Entity
public class Announce {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String message;
	private Date date;
	private String type;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "sender_id")
	private User sender;

	public Announce() {
		this.date = new Date(System.currentTimeMillis());
	}

	public Announce(String type, User user) {
		this.type = type;
		this.user = user;
		this.date = new Date(System.currentTimeMillis());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public static String createMessage(String rawMessage, String avatar) {
		StringBuilder message = new StringBuilder();
		message.append("<a href=\"#\"><img src=\"http://localhost:8080/SummerProject/img/");
		message.append(avatar);
		message.append("\" alt=\"\">\r\n" + "<p> ");
		message.append(rawMessage);
		message.append("</p>" + "</a>");

		return message.toString();
	}

	public static Announce createAnnouceObject(String message, User user, String avatar, TypeAnnouce typeAnnouce) {
		Announce announce = null;
		try {
			StringBuilder messageResult = new StringBuilder();
			messageResult.append(message);
			announce = new Announce(typeAnnouce.getMessage(), user);
			announce.setMessage(Announce.createMessage(message, avatar));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return announce;
	}

}
