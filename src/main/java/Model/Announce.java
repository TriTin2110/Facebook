package Model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Announce {
	@Id
	private String id;
	private String message;
	private String userNameRequested;
	private String userAvatarRequested;
	private String typeOfAnnouce;
	private boolean checked;
	private long date;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "annouce_for_sender_id")
	private Announce annouce;

	public Announce() {
	}

	public Announce(String id) {
		super();
		this.id = id;
	}

	public Announce(String id, String message, boolean checked, long date) {
		this.id = id;
		this.message = message;
		this.checked = checked;
		this.date = date;
	}

	public Announce(String id, String message, User user, boolean checked, long date) {
		super();
		this.id = id;
		this.message = message;
		this.user = user;
		this.checked = checked;
		this.date = date;
	}

	public Announce(String id, String message, String userNameRequested, String userAvatarRequested, User user,
			boolean checked, long date, String typeOfAnnouce) {
		super();
		this.id = id;
		this.message = message;
		this.userNameRequested = userNameRequested;
		this.userAvatarRequested = userAvatarRequested;
		this.user = user;
		this.checked = checked;
		this.date = date;
		this.typeOfAnnouce = typeOfAnnouce;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public String getUserNameRequested() {
		return userNameRequested;
	}

	public void setUserNameRequested(String userNameRequested) {
		this.userNameRequested = userNameRequested;
	}

	public String getUserAvatarRequested() {
		return userAvatarRequested;
	}

	public void setUserAvatarRequested(String userAvatarRequested) {
		this.userAvatarRequested = userAvatarRequested;
	}

	@Override
	public String toString() {
		return "Announce [id=" + id + ", message=" + message + ", user=" + user + ", checked=" + checked + ", date="
				+ new Date(date) + "]";
	}

	public String getTypeOfAnnouce() {
		return typeOfAnnouce;
	}

	public void setTypeOfAnnouce(String typeOfAnnouce) {
		this.typeOfAnnouce = typeOfAnnouce;
	}

	public Announce getAnnouce() {
		return annouce;
	}

	public void setAnnouce(Announce annouce) {
		this.annouce = annouce;
	}

}
