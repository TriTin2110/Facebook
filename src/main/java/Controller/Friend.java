package Controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Friend() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
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
				addFriend(request, response);
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	@SuppressWarnings("unchecked")
	private void searchFriend(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String data = request.getParameter("searchedData");
		SearchFriendService search = new SearchFriendService();
		List<String> searched = (List<String>) request.getSession().getAttribute("dataSearched");
		if (!searched.contains(data)) {
			searched.add(data);
			Collections.sort(searched, Collections.reverseOrder());
		}
		request.getSession().setAttribute("dataSearched", searched);
		request.getSession().setAttribute("listSearched", search.getListUser(data));
		response.sendRedirect(request.getContextPath() + "/jsp/SearchFriend.jsp");
	}

	private void proccessAddingFriend(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String idFriend = request.getParameter("userId");
		String idUserSendRequest = request.getParameter("userSentRequestId");
		String fullName = request.getParameter("fullName");
		String avatar = request.getParameter("avatar");
		sendAddFriendRequest(idFriend, idUserSendRequest, fullName, avatar);
		request.getRequestDispatcher("/jsp/Profile.jsp?userId=" + idFriend).forward(request, response);
	}

	private void sendAddFriendRequest(String idFriend, String idUserSendRequest, String fullName, String avatar) {
		// TODO Auto-generated method stub
		// Tạo thông báo mới
		AnnounceDAO dao = new AnnounceDAO();
		dao.setUpAnnounce(idFriend, idUserSendRequest, fullName, avatar);
	}

	private void addFriend(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String idFriend = request.getParameter("friendId");
		User user = (User) request.getSession().getAttribute("user");
		UserDAO userDAO = new UserDAO();
		userDAO.processAddingFriend(idFriend, user);
		user = userDAO.selectById(user);
		request.getSession().setAttribute("user", user);
		response.sendRedirect(request.getContextPath());
	}
}
