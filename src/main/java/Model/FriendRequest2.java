package Model;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

//@Entity
//@Table(name = "friend_request")
public class FriendRequest2 extends Announce {
	@ManyToOne
	@JoinColumn(name = "from_user")
	private User fromUser;

	public FriendRequest2() {
	}

	public FriendRequest2(User fromUser, User toUser, String message) {
		super(fromUser.getUserId() + toUser.getUserId(), message, toUser);
		this.fromUser = fromUser;
	}

	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}

}
