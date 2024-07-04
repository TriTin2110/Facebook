import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import DAO.CommentDAO;
import DAO.GroupDAO;
import DAO.PostDAO;
import DAO.UserDAO;
import DAO.UserInformationDAO;
import Model.Comment;
import Model.Group;
import Model.Post;
import Model.User;
import Model.UserInformation;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testInput();
	}

	public static void testCase() {
		int year = new Date(System.currentTimeMillis()).getYear() + 1900;
		System.out.println(year);
	}

	public static void testInput() {
		Group group = new Group("G1", "Group 1", null, 1);
		Post post = new Post(null, "P1", "image.png", "nothing", 2, 3, null);
		Comment comment = new Comment("C1", null, "nothing", post);
		UserInformation userInformation = new UserInformation("U1", null, "ViDu", true,
				new Date(System.currentTimeMillis()), "12345678", "VN", "HCM", "HCM", "Nothing");
		User user = new User("U1", "abc@gmail.com", userInformation, "123", 10, null, "image.png", null, null);
//
		List<Group> listGroups = new ArrayList<Group>();
		List<Comment> listComment = new ArrayList<Comment>();
		List<Post> listPosts = new ArrayList<Post>();
		List<User> list = new ArrayList<User>();

		listGroups.add(group);
		listComment.add(comment);
		listPosts.add(post);
		list.add(user);

		user.setListGroup(listGroups);
		post.setPostCommentList(listComment);
		user.setPostList(listPosts);
		group.setListMember(list);

		post.setUser(user);
		comment.setUser(user);

		GroupDAO groupDAO = new GroupDAO();
		PostDAO postDAO = new PostDAO();
		CommentDAO commentDAO = new CommentDAO();
		UserInformationDAO userInformationDAO = new UserInformationDAO();
		UserDAO userDAO = new UserDAO();
//
		userInformationDAO.add(userInformation);
		userDAO.add(user);
		groupDAO.add(group);
		postDAO.add(post);
		commentDAO.add(comment);

	}

}
