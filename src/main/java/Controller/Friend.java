package Controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.UserDAO;
import Model.Announce;
import Model.User;
import services.SearchFriendService;

/**
 * Servlet implementation class SearchFriend
 */
@WebServlet("/Friend")
public class Friend extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session;

	public Friend() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		createUtil(request);

		String method = request.getParameter("method");

		try {
			switch (method) {
			case "search":
				searchFriend(request, response);
				break;
			case "proccess-adding-friend":
				proccessAddingFriend(request, response);
				break;
			case "add":
				acceptingAddFriend(request, response);
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void createUtil(HttpServletRequest request) {
		session = request.getSession();
	}

	@SuppressWarnings("unchecked")
	private void searchFriend(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String data = request.getParameter("searchedData");
		SearchFriendService search = new SearchFriendService();
		List<String> searched = (List<String>) session.getAttribute("dataSearched");
		String url = request.getContextPath() + "/jsp/SearchFriend.jsp";

		addSearchingDataToSearchedTable(searched, data);

		session.setAttribute("dataSearched", searched);
		session.setAttribute("listSearched", search.getUsersByData(data));

		response.sendRedirect(url);
	}

	private void addSearchingDataToSearchedTable(List<String> searched, String data) {
		if (!searched.contains(data)) {
			searched.add(data);
			Collections.sort(searched, Collections.reverseOrder());
		}
	}

	private void proccessAddingFriend(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String idReceiver = request.getParameter("userId");
		String idSender = request.getParameter("userSentRequestId");
		String url = "/jsp/Profile.jsp?userId=" + idReceiver;

		User receiver = createAnnouceForReceiver(request, idReceiver);
		User sender = createAnnouceForSender(request, idSender);

		UserDAO userDAO = new UserDAO();
		userDAO.update(receiver);
		userDAO.update(sender);

		request.getRequestDispatcher(url).forward(request, response);
	}

	private User createAnnouceForReceiver(HttpServletRequest request, String idReceiver) {
		// Thông báo cho người nhận
		String userNameRequested = request.getParameter("fullName");
		String avatar = request.getParameter("avatar");
		String message = userNameRequested + " đã gửi lời mời kết bạn dành cho bạn!";
		String typeOfAnnouce = "FR";

		User receiver = updateAnnouceForUser(request, message, typeOfAnnouce, idReceiver, avatar);
		return receiver;
	}

	private User createAnnouceForSender(HttpServletRequest request, String idSender) {
		// Thông báo cho người nhận
		String userPageName = request.getParameter("userPageName");
		String message = ". Bạn đã gửi lời mời kết bạn dành cho " + userPageName + "!";
		String typeOfAnnouce = "SN";
		String avatar = request.getParameter("userPageAvatar");

		User sender = updateAnnouceForUser(request, message, typeOfAnnouce, idSender, avatar);
		return sender;
	}

	private User updateAnnouceForUser(HttpServletRequest request, String message, String typeOfAnnouce, String idUser,
			String avatar) {
		UserDAO userDAO = new UserDAO();
		// Thông báo cho người nhận
		String userNameRequested = request.getParameter("fullName");

		boolean checked = false;
		long dateReceiveRequest = System.currentTimeMillis();

		User user = new User();
		user.setUserId(idUser);
		user = userDAO.selectById(user);

		Announce senderAnnouce = createAnnouce(idUser, message, userNameRequested, avatar, typeOfAnnouce, user, checked,
				dateReceiveRequest);
		List<Announce> announces = user.getAnnounces();
		announces.add(senderAnnouce);
		user.setAnnounces(announces);
		return user;
	}

	private Announce createAnnouce(String idUserSendRequest, String content, String nameUserRequested,
			String avatarUserRequested, String typeOfNotice, User toUser, boolean checked, long dateReceiveRequest) {

		Announce announce = new Announce(idUserSendRequest + dateReceiveRequest, content, nameUserRequested,
				avatarUserRequested, toUser, checked, dateReceiveRequest, typeOfNotice);
		return announce;
	}

	private void acceptingAddFriend(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		UserDAO userDAO = new UserDAO();

		String idFriend = request.getParameter("friendId");
		User user = (User) request.getSession().getAttribute("user");

		userDAO.processAddingFriend(idFriend, user);
		user = userDAO.selectById(user);

		session.setAttribute("user", user);
		response.sendRedirect(request.getContextPath());
	}
}
