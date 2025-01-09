package Model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_information")
public class UserInformation {
	@Id
	@Column(name = "user_information_id")
	private String userId;
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	private String fullName;
	private Boolean gender;
	private Date dateOfBirth;
	private String phoneNumber;
	private String country;
	private String homeTown;
	private String workingPlace;
	private String selfDescription;

	public UserInformation() {
		super();
	}

	public UserInformation(String userId) {
		this.userId = userId;
	}

	public UserInformation(String userId, String fullName, Boolean gender, String phoneNumber, String homeTown) {
		this.userId = userId;
		this.fullName = fullName;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
		this.homeTown = homeTown;
	}

	public UserInformation(String userId, User user, String fullName, Boolean gender, Date dateOfBirth,
			String phoneNumber, String country, String homeTown, String workingPlace, String selfDescription) {
		super();
		this.userId = userId;
		this.user = user;
		this.fullName = fullName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.phoneNumber = phoneNumber;
		this.country = country;
		this.homeTown = homeTown;
		this.workingPlace = workingPlace;
		this.selfDescription = selfDescription;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Boolean isGender() {
		return gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHomeTown() {
		return homeTown;
	}

	public void setHomeTown(String homeTown) {
		this.homeTown = homeTown;
	}

	public String getWorkingPlace() {
		return workingPlace;
	}

	public void setWorkingPlace(String workingPlace) {
		this.workingPlace = workingPlace;
	}

	public String getSelfDescription() {
		return selfDescription;
	}

	public void setSelfDescription(String selfDescription) {
		this.selfDescription = selfDescription;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getGender() {
		return gender;
	}

}
