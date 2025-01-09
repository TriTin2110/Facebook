package services;

import java.util.ArrayList;
import java.util.List;

import DAO.UserDAO;
import Model.SearchFriend;

public class SearchFriendService {
	public List<SearchFriend> getListUser(String dataSearching) {
		List<SearchFriend> users = new ArrayList<SearchFriend>();
		UserDAO dao = new UserDAO();
		users = dao.selectByFullName(dataSearching);
		return users;
	}
}
