package Cache;

import java.util.List;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import DAO.UserDAO;
import Model.User;

public class UserFriend {
	private static UserDAO userDAO = new UserDAO();
	private static LoadingCache<String, List<User>> users = Caffeine.newBuilder()
			.build(key -> userDAO.selectFriendsByUserId(key));

	public static List<User> getFriends(String userId) {
		return users.getIfPresent(userId);
	}

	public static void updateFriends(String userId) {
		users.refresh(userId);
	}
}
