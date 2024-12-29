package Model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Post {
	@ManyToOne
	@JoinColumn(name = "by_user")
	private User user;
	@Id
	private String postId;
	private String postImage; // Hình ảnh
	private String postContent;
	private Integer postInteract;
	private Integer postShare;
	@OneToMany(mappedBy = "post", fetch = FetchType.EAGER) // Về sau sẽ thay EAGER bằng cách khác (vd: sql)
	private List<Comment> postCommentList;
	private Date createdAt; // Add this field

	public Post() {
		super();
	}

	public Post(User user, String postId, String postImage, String postContent, Integer postInteract, Integer postShare,
			List<Comment> postCommentList) {
		super();
		this.user = user;
		this.postId = postId;
		this.postImage = postImage;
		this.postContent = postContent;
		this.postInteract = postInteract;
		this.postShare = postShare;
		this.postCommentList = postCommentList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getPostImage() {
		return postImage;
	}

	public void setPostImage(String postImage) {
		this.postImage = postImage;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public Integer getPostInteract() {
		return postInteract;
	}

	public void setPostInteract(Integer postInteract) {
		this.postInteract = postInteract;
	}

	public Integer getPostShare() {
		return postShare;
	}

	public void setPostShare(Integer postShare) {
		this.postShare = postShare;
	}

	public List<Comment> getPostCommentList() {
		return postCommentList;
	}

	public void setPostCommentList(List<Comment> postCommentList) {
		this.postCommentList = postCommentList;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
