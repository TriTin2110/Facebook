package Model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Groupz")
public class Group {
	@Id
	private String groupId;
	private String groupName;
	@ManyToMany
	@JoinTable(name = "Group_User", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "group_id") })
	private List<User> listMember;
	private Integer groupPermission;

	public Group() {
	}

	public Group(String groupId, String groupName, List<User> listMember, Integer groupPermission) {
		this.groupId = groupId;
		this.groupName = groupName;
		this.listMember = listMember;
		this.groupPermission = groupPermission;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<User> getListMember() {
		return listMember;
	}

	public void setListMember(List<User> listMember) {
		this.listMember = listMember;
	}

	public Integer getGroupPermission() {
		return groupPermission;
	}

	public void setGroupPermission(Integer groupPermission) {
		this.groupPermission = groupPermission;
	}

}
