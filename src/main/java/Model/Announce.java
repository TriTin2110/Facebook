package Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import Enums.TypeAnnouce;

@Entity
//Bảng cha và bảng con sẽ nằm tách biệt và liên kết với nhau thông qua
// khóa ngoại
@Inheritance(strategy = InheritanceType.JOINED)
public class Announce {
	@Id
	private String id;
	private String message;
	private Date date;
	@Column(name = "type_annouce")
	private TypeAnnouce typeAnnouce;
	@ManyToOne
	@JoinColumn(name = "to_user")
	private User toUser;

	public Announce() {
	}

	public Announce(String id) {
		this.id = id;
		this.typeAnnouce = TypeAnnouce.FRIEND_REQUEST;
		this.date = new Date(System.currentTimeMillis());
	}

	public Announce(String id, String message) {
		this.id = id;
		this.message = message;
	}

	public Announce(String id, User toUser) {
		this.id = id;
		this.toUser = toUser;
		this.typeAnnouce = TypeAnnouce.FRIEND_REQUEST;
		this.date = new Date(System.currentTimeMillis());
	}

	public Announce(String id, String message, User toUser) {
		this.id = id;
		this.message = message;
		this.toUser = toUser;
		this.date = new Date(System.currentTimeMillis());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	public TypeAnnouce getTypeAnnouce() {
		return typeAnnouce;
	}

	public void setTypeAnnouce(TypeAnnouce typeAnnouce) {
		this.typeAnnouce = typeAnnouce;
	}

	@Override
	public String toString() {
		return "Announce [id=" + id + ", message=" + message + ", date=" + date + ", typeAnnouce=" + typeAnnouce
				+ ", toUser=" + toUser + "]";
	}

}
