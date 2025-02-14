package Cache;

import java.util.List;
import java.util.stream.Collectors;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import DAO.AnnounceDAO;
import DAO.PostDAO;
import DAO.UserDAO;
import Model.Announce;
import Model.Post;
import Model.User;

public class UserCache {
	private UserDAO dao;
	private LoadingCache<String, List<User>> friends;
	private Cache<String, List<Announce>> annouces;
	private Cache<String, List<Post>> posts;
	private User currentUser;

	public UserCache() {
		dao = new UserDAO();
		friends = Caffeine.newBuilder().build(dao::selectFriendsByUserId);
		annouces = Caffeine.newBuilder().build();
		posts = Caffeine.newBuilder().build();

	}

	public List<User> selectFriendsByUserIdCache(String id) {
		return friends.get(id);
	}

	public List<Announce> selectAnnouncesByUserIdDAO(String id) {
		AnnounceDAO announceDAO = new AnnounceDAO();
		List<Announce> announces = announceDAO.selectAnnoucesByUserId(id).stream().collect(Collectors.toList());
		return announces;
	}

	public List<Announce> selectAnnouncesByUserIdCache(String id) {
		return annouces.get(id, this::selectAnnouncesByUserIdDAO);
	}

	public List<Post> selectPostsByUserIdDAO(String id) {
		PostDAO postDAO = new PostDAO();
		List<Post> posts = postDAO.selectAllPostByUserId(id).stream().collect(Collectors.toList());
		return posts;
	}

	public List<Post> selectPostsByUserIdCache(String id) {
		return posts.get(id, this::selectPostsByUserIdDAO);
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
}
