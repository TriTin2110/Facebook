package Model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class User {
	@Id
	private String userId;
	private String email;
	private String password;
	private Integer friendQuantity;
	private boolean identifyStatus;
	private String avatar;

	@OneToOne(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private UserInformation userInformation;
	@Lob
	@Column(length = Integer.MAX_VALUE)
	private String listFriendId;

	@ManyToMany(mappedBy = "listMember")
	private List<Group> listGroup;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Post> listPost;

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<Announce> announces;

	public User() {
	}

	public User(String email) {
		this.email = email;
	}

	public User(String email, String password) {
		this.email = email;
		this.password = password;
		this.userId = "";
		this.friendQuantity = 0;
		this.identifyStatus = false;
		this.avatar = "friend2.jpg";
		this.listFriendId = "";
		this.userInformation = new UserInformation();
		this.listGroup = new ArrayList<Group>();
		this.listPost = new ArrayList<Post>();
		this.announces = new ArrayList<Announce>();
	}

	public User(String userId, String avatar, UserInformation userInformation) {
		this.userId = userId;
		this.avatar = avatar;
		this.userInformation = userInformation;
	}

	public User(String userId, String email, String password, Integer friendQuantity, boolean identifyStatus,
			String avatar, UserInformation userInformation, String listFriendId, List<Group> listGroup,
			List<Post> listPost, List<Announce> announces) {
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.friendQuantity = friendQuantity;
		this.identifyStatus = identifyStatus;
		this.avatar = avatar;
		this.userInformation = userInformation;
		this.listFriendId = listFriendId;
		this.listGroup = listGroup;
		this.listPost = listPost;
		this.announces = announces;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserInformation getUserInformation() {
		return userInformation;
	}

	public void setUserInformation(UserInformation userInformation) {
		this.userInformation = userInformation;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getFriendQuantity() {
		return friendQuantity;
	}

	public void setFriendQuantity(Integer friendQuantity) {
		this.friendQuantity = friendQuantity;
	}

	public String getListFriendId() {
		return listFriendId;
	}

	public void setListFriend(String listFriendId) {
		this.listFriendId = listFriendId;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public List<Group> getListGroup() {
		return listGroup;
	}

	public void setListGroup(List<Group> listGroup) {
		this.listGroup = listGroup;
	}

	public List<Post> getPostList() {
		return listPost;
	}

	public void setPostList(List<Post> postList) {
		this.listPost = postList;
	}

	public boolean isIdentifyStatus() {
		return identifyStatus;
	}

	public void setIdentifyStatus(boolean identifyStatus) {
		this.identifyStatus = identifyStatus;
	}

	public List<Post> getListPost() {
		return listPost;
	}

	public void setListPost(List<Post> listPost) {
		this.listPost = listPost;
	}

	public List<Announce> getAnnounces() {
		return announces;
	}

	public void setAnnounces(List<Announce> announces) {
		this.announces = announces;
	}

	public void setListFriendId(String listFriendId) {
		this.listFriendId = listFriendId;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", email=" + email + ", password=" + password + ", friendQuantity="
				+ friendQuantity + ", identifyStatus=" + identifyStatus + ", avatar=" + avatar + "]";
	}
}
