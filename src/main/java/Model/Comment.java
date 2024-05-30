package Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Comment {
	@Id
	private String commentId;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	private String commentContent;
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;

	public Comment() {
	}

	public Comment(String commentId, User user, String commentContent, Post post) {
		super();
		this.commentId = commentId;
		this.user = user;
		this.commentContent = commentContent;
		this.post = post;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

}
