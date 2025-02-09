package services;

import java.util.ArrayList;
import java.util.List;

import DAO.UserDAO;
import Model.Friend;

public class SearchFriendService {
	public List<Friend> getUsersByData(String dataSearching) {
		List<Friend> users = new ArrayList<Friend>();
		UserDAO dao = new UserDAO();
		users = dao.selectByFullName(dataSearching);
		return users;
	}
}
