package Service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.FriendRequest2;
import Model.Post;
import Model.User;

public interface UserService {
	public HttpServletRequest signUp(HttpServletRequest request, HttpServletResponse response);

	public Object signIn(HttpServletRequest request, HttpServletResponse response);

	public HttpServletRequest emailConfirm(HttpServletRequest request, HttpServletResponse response);

	public List<Post> getAllPost(String userId);

	public List<User> getAllFriend(String userId);

	public List<FriendRequest2> getFriendRequests(String userId);

	public User selectUserByUserId(String userId);
}
