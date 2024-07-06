package Model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class User {
	@Id
	private String userId;
	private String email;
	@OneToOne(mappedBy = "user")
	private UserInformation userInformation;
	private String password;
	private Integer friendQuantity;
	private boolean identifyStatus;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User self;
	@OneToMany(mappedBy = "listFriend")
	private List<User> listFriend;
	private String avatar; // Hình ảnh

	@ManyToMany(mappedBy = "listMember")
	private List<Group> listGroup;

	@OneToMany(mappedBy = "userIdPost")
	private List<Post> listPost;

	public User() {
	}

	public User(String email) {
		super();
		this.email = email;
	}

	public User(String userId, String email, String password) {
		this.userId = userId;
		this.email = email;
		this.password = password;
	}

	public User(String userId, String email, UserInformation userInformation, String password, Integer friendQuantity,
			List<User> listFriend, String avatar, List<Group> listGroup, List<Post> postList) {
		this.userId = userId;
		this.email = email;
		this.userInformation = userInformation;
		this.password = password;
		this.friendQuantity = friendQuantity;
		this.listFriend = listFriend;
		this.avatar = avatar;
		this.listGroup = listGroup;
		this.listPost = postList;
	}

	public User(String userId, String email, UserInformation userInformation, String password, Integer friendQuantity,
			User self, List<User> listFriend, String avatar, List<Group> listGroup, List<Post> listPost) {
		this.userId = userId;
		this.email = email;
		this.userInformation = userInformation;
		this.password = password;
		this.friendQuantity = friendQuantity;
		this.self = self;
		this.listFriend = listFriend;
		this.avatar = avatar;
		this.listGroup = listGroup;
		this.listPost = listPost;
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

	public User getSelf() {
		return self;
	}

	public void setSelf(User self) {
		this.self = self;
	}

	public List<Post> getListPost() {
		return listPost;
	}

	public void setListPost(List<Post> listPost) {
		this.listPost = listPost;
	}

}
