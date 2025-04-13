package Service.implement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Cache.UserAnnounce;
import Cache.UserFriend;
import DAO.PostDAO;
import DAO.UserDAO;
import Interact.UserInformationInteract;
import Interact.UserInteract;
import Model.Announce;
import Model.FriendRequest2;
import Model.Post;
import Model.User;
import Model.UserInformation;
import Service.UserService;

public class UserServiceImpl implements UserService {
	private UserDAO userDAO;
	private UserInformationInteract infoInt;

	public UserServiceImpl() {
		userDAO = new UserDAO();
	}

	public UserServiceImpl(HttpServletRequest request) {
		userDAO = new UserDAO();
		infoInt = new UserInformationInteract(request);
	}

	public HttpServletRequest signUp(HttpServletRequest request, HttpServletResponse response) {
		UserInteract userInt = new UserInteract(request);
		UserInformation userInformation = null;
		User user;
		String notice = "Tạo tài khoản thành công! Vui lòng kiểm tra email đã đăng ký";
//		String urlEmailConfirm = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
//				+ request.getContextPath();

		String firstName = request.getParameter("ten");
		String lastName = request.getParameter("ho");
		// Tạo user với email và passowrd
		user = userInt.createUser();
		System.out.println(user.getEmail());
		// Mã hóa email và password cho user đồng thời tạo id cho user
		user = userInt.encryptPasswordEmailId(user);

		userInformation = infoInt.createUserInformation(user);
		user.setUserInformation(userInformation);

		boolean userIsAlreadyExists = userDAO.selectByEmail(user) != null;
		if (userIsAlreadyExists) {
			notice = "Tài khoản đã tồn tại";
			request.setAttribute("error", notice);
			String[] name = { firstName, lastName };
			request = gainValueForSignUpForm(request, name, userInformation);
		} else {
			boolean addUserSuccess = userDAO.add(user) > 0;
			if (addUserSuccess) {
				request.setAttribute("error", notice);
//				Đóng khi testing: SendingMail.sendMail(email, userInformation.getFullName(), user.getUserId(), urlEmailConfirm);
			} else {
				notice = "Tạo tài khoản không thành công";
				request.setAttribute("error", notice);
			}
		}
		return request;
	}

	private HttpServletRequest gainValueForSignUpForm(HttpServletRequest request, String[] name,
			UserInformation userInformation) {
		request.setAttribute("firstName", name[0]);
		request.setAttribute("lastName", name[1]);
		request.setAttribute("dOB", userInformation.getDateOfBirth().get(Calendar.DAY_OF_MONTH));
		request.setAttribute("mOB", userInformation.getDateOfBirth().get(Calendar.MONTH));
		request.setAttribute("yOB", userInformation.getDateOfBirth().get(Calendar.YEAR));
		request.setAttribute("gender", userInformation.getGender());
		return request;
	}

	public Object signIn(HttpServletRequest request, HttpServletResponse response) {
		UserInteract userInt = new UserInteract(request);
		String notice = "Email chưa được xác thực hoặc Mật khẩu không chính xác!";

		String emailInput = request.getParameter("typeEmail");
		String passwordInput = request.getParameter("typePassword");
		String userEmailInputEncrypted = userInt.hashEncrypt(emailInput);
		User user = userDAO.selectByEmail(new User(userEmailInputEncrypted));
		HttpSession session = request.getSession();

		boolean userNotExists = user == null;
		if (userNotExists) {
			notice = "Email không tồn tại";
			request.setAttribute("error", notice);
		} else {
			userInt.setUser(user);
			// Kiểm tra mật khẩu và xác thực email
			boolean passwordAndEmailCorrected = userInt.checkingAccount(passwordInput);
			if (passwordAndEmailCorrected) {
				List<Announce> userAnnounces = UserAnnounce.getAnnounces(user.getUserId());
				List<User> userFriends = UserFriend.getFriends(user.getUserId());
				if (userAnnounces == null)
					userAnnounces = new ArrayList<Announce>();
				if (userFriends == null)
					userFriends = new ArrayList<User>();

				user.setAnnounces(userAnnounces);
				user.setListFriend(userFriends);
				session.setAttribute("user", user);
				return response;
			} else {
				request.setAttribute("error", notice);
			}
		}
		return request;
	}

	public HttpServletRequest emailConfirm(HttpServletRequest request, HttpServletResponse response) {
		String idUser = request.getParameter("iduser");
		User user = userDAO.confirmEmail(idUser);
		if (user != null) {
			request.getSession().setAttribute("user", user);
			request.setAttribute("notice", "Email đã xác thực thành công! Mời bạn đăng nhập lại");
		}
		return request;
	}

	@Override
	public List<Post> getAllPost(String userId) {
		// TODO Auto-generated method stub
		PostDAO postDAO = new PostDAO();
		List<Post> posts = postDAO.selectAllPostByUserId(userId);
		return posts;
	}

	@Override
	public List<User> getAllFriend(String userId) {
		// TODO Auto-generated method stub
		return userDAO.selectFriendsByUserId(userId);
	}

	@Override
	public List<FriendRequest2> getFriendRequests(String userId) {
		// TODO Auto-generated method stub
		return userDAO.selectFriendRequestsByUserId(userId);
	}

	@Override
	public User selectUserByUserId(String userId) {
		// TODO Auto-generated method stub
		return userDAO.selectById(userId);
	}
}
