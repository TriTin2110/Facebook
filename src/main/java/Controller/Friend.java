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

import DAO.AnnounceDAO;
import DAO.UserDAO;
import Model.User;
import services.SearchFriendService;

/**
 * Servlet implementation class SearchFriend
 */
@WebServlet("/Friend")
public class Friend extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session;
	private AnnounceDAO dao;

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
		dao = new AnnounceDAO();
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

		String idFriend = request.getParameter("userId");
		String idUserSendRequest = request.getParameter("userSentRequestId");
		// Thông báo cho người nhận
		String userNameRequested = request.getParameter("fullName");
		String avatar = request.getParameter("avatar");
		// Thông báo cho người gửi
		String userPageName = request.getParameter("userPageName");
		String userPageAvatar = request.getParameter("userPageAvatar");
		String url = "/jsp/Profile.jsp?userId=" + idFriend;

		sendAddFriendRequest(idFriend, idUserSendRequest, userNameRequested, avatar);
		sendAddFriendSelfNotification(idUserSendRequest, idFriend, userNameRequested, userPageName, userPageAvatar);

		request.getRequestDispatcher(url).forward(request, response);
	}

	private void sendAddFriendRequest(String idFriend, String idUserSendRequest, String userNameRequested,
			String avatar) {
		// Tạo thông báo mới
		String message = "đã gửi lời mời kết bạn dành cho bạn!";
		boolean checked = false;
		dao.setUpAnnounce(idFriend, idUserSendRequest, message, userNameRequested, avatar, checked);
	}

	private void sendAddFriendSelfNotification(String idFriend, String idUserSendRequest, String userNameRequested,
			String userPageName, String userPageAvatar) {
		// TODO Auto-generated method stub
		String message = ". Bạn đã gửi lời mời kết bạn dành cho " + userPageName + "!";
		boolean checked = true;
		dao.setUpAnnounce(idFriend, idUserSendRequest, message, userNameRequested, userPageAvatar, checked);
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
