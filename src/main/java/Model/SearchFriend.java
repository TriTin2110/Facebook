package Model;

public class SearchFriend {
	private String id, fullName, avatar;

	public SearchFriend() {
	}

	public SearchFriend(String id, String fullName, String avatar) {
		this.id = id;
		this.fullName = fullName;
		this.avatar = avatar;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Override
	public String toString() {
		return "SearchFriend [id=" + id + ", fullName=" + fullName + ", avatar=" + avatar + "]";
	}

}
