package Model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "friend_receive")
public class FriendReceive extends Announce {
	@ManyToOne
	@JoinColumn(name = "friendReceives")
	private User fromUser;

	public FriendReceive() {
	}

	public FriendReceive(User toUser, User fromUser) {
		super(toUser.getUserId());
		this.fromUser = fromUser;
	}

	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}

}
