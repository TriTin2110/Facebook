package Model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class User {
	@Id
	private String userId;
	private String email;
	private String password;
	private boolean identifyStatus;
	private String avatar;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private UserInformation userInformation;

	@ManyToMany
	@JoinTable(name = "user_friend", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "friend_id"))
	private List<User> listFriend;

	@ManyToMany(mappedBy = "listMember")
	private List<Group> listGroup;

	@OneToMany(mappedBy = "user")
	private List<Post> listPost;

	@OneToMany(mappedBy = "from", cascade = CascadeType.ALL)
	private List<Announce> from_announces;

	@OneToMany(mappedBy = "to", cascade = CascadeType.ALL)
	private List<Announce> to_announces;

	public User() {
		this.listFriend = new ArrayList<User>();
		this.userInformation = new UserInformation();
		this.listGroup = new ArrayList<Group>();
		this.listPost = new ArrayList<Post>();
		this.from_announces = new ArrayList<Announce>();
		this.to_announces = new ArrayList<Announce>();
	}

	public User(String email) {
		this.email = email;
	}

	public User(String email, String password) {
		this.email = email;
		this.password = password;
		this.userId = "";
		this.identifyStatus = true;
		this.avatar = "friend2.jpg";
		this.listFriend = new ArrayList<User>();
		this.userInformation = new UserInformation();
		this.listGroup = new ArrayList<Group>();
		this.listPost = new ArrayList<Post>();
		this.from_announces = new ArrayList<Announce>();
		this.to_announces = new ArrayList<Announce>();
	}

	public User(String userId, String avatar, UserInformation userInformation) {
		this.userId = userId;
		this.avatar = avatar;
		this.userInformation = userInformation;
	}

	public User(String userId, String email, String password, boolean identifyStatus, String avatar,
			UserInformation userInformation, List<User> listFriendId, List<Group> listGroup, List<Post> listPost,
			List<Announce> from_announces, List<Announce> to_announces) {
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.identifyStatus = identifyStatus;
		this.avatar = avatar;
		this.userInformation = userInformation;
		this.listFriend = listFriendId;
		this.listGroup = listGroup;
		this.listPost = listPost;
		this.from_announces = new ArrayList<Announce>();
		this.to_announces = new ArrayList<Announce>();
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

	public List<User> getListFriend() {
		return listFriend;
	}

	public void setListFriend(List<User> listFriend) {
		this.listFriend = listFriend;
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

	public List<Announce> getFrom_announces() {
		return from_announces;
	}

	public void setFrom_announces(List<Announce> from_announces) {
		this.from_announces = from_announces;
	}

	public List<Announce> getTo_announces() {
		return to_announces;
	}

	public void setTo_announces(List<Announce> to_announces) {
		this.to_announces = to_announces;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", email=" + email + ", password=" + password + ", identifyStatus="
				+ identifyStatus + ", avatar=" + avatar + "]";
	}
}
