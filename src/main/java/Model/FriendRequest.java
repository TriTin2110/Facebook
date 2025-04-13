package Model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "friend_request")
public class FriendRequest extends Announce {
	@ManyToOne
	@JoinColumn(name = "friendRequests")
	private User toUser;

	public FriendRequest() {
	}

	public FriendRequest(User fromUser, User toUser) {
		super(fromUser.getUserId());
		this.toUser = toUser;
	}

	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

}
