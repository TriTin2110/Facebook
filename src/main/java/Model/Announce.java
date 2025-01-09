package Model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Announce {
	@Id
	private String id;
	private String data;
	private String userNameRequested;
	private String userAvatarRequested;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	private boolean checked;
	private long date;

	public Announce() {
	}

	public Announce(String id, String data, boolean checked, long date) {
		this.id = id;
		this.data = data;
		this.checked = checked;
		this.date = date;
	}

	public Announce(String id, String data, User user, boolean checked, long date) {
		super();
		this.id = id;
		this.data = data;
		this.user = user;
		this.checked = checked;
		this.date = date;
	}

	public Announce(String id, String data, String userNameRequested, String userAvatarRequested, User user,
			boolean checked, long date) {
		super();
		this.id = id;
		this.data = data;
		this.userNameRequested = userNameRequested;
		this.userAvatarRequested = userAvatarRequested;
		this.user = user;
		this.checked = checked;
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
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
		return "Announce [id=" + id + ", data=" + data + ", user=" + user + ", checked=" + checked + ", date="
				+ new Date(date) + "]";
	}

}
