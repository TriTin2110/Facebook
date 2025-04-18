package Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String message;
	private Date date;
	@Column(name = "type_annouce")
	private TypeAnnouce typeAnnouce;

	@ManyToOne
	@JoinColumn(name = "from_user")
	private User from;

	@ManyToOne
	@JoinColumn(name = "to_user")
	private User to;

	public Announce() {
	}

	public Announce(String message) {
		this.message = message;
	}

	public Announce(User from, User to) {
		this.from = from;
		this.to = to;
		this.typeAnnouce = TypeAnnouce.FRIEND_REQUEST;
		this.date = new Date(System.currentTimeMillis());
	}

	public Announce(User from, String message, User to) {
		this.from = from;
		this.message = message;
		this.to = to;
		this.date = new Date(System.currentTimeMillis());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
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

	public TypeAnnouce getTypeAnnouce() {
		return typeAnnouce;
	}

	public void setTypeAnnouce(TypeAnnouce typeAnnouce) {
		this.typeAnnouce = typeAnnouce;
	}

	@Override
	public String toString() {
		return "Announce [id=" + id + ", message=" + message + ", date=" + date + ", typeAnnouce=" + typeAnnouce
				+ ", from=" + from + ", to=" + to + "]";
	}

}
