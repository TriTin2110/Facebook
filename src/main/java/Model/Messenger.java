package Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Messenger {
	@Id
	private String userNameGuestName;
	private String message;

	public Messenger() {
		super();
	}

	public Messenger(String userNameGuestName, String message) {
		super();
		this.userNameGuestName = userNameGuestName;
		this.message = message;
	}

	public String getUserNameGuestName() {
		return userNameGuestName;
	}

	public void setUserNameGuestName(String userNameGuestName) {
		this.userNameGuestName = userNameGuestName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
