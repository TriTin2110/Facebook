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
import javax.websocket.Session;

import Cache.UserAnnounce;
import DAO.UserDAO;
import Model.Announce;
import Model.User;
import Service.UserService;
import Service.implement.UserServiceImpl;
import WebSocket.Notification;
import services.SearchFriendService;

/**
 * Servlet implementation class SearchFriend
 */
@WebServlet("/Friend")
public class Friend extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session;

	private UserDAO userDAO;

	public Friend() {
		this.userDAO = new UserDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		createUtil(request);

		String method = request.getParameter("method");
		StringBuilder url = new StringBuilder();
		try {
			switch (method) {
			case "search":
				searchFriend(request, response);
				return;
			case "proccess-adding-friend":
				url.append(proccessAddingFriend(request, response));
				break;
//			case "add":
//				acceptingAddFriend(request, response);
//				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		request.getRequestDispatcher(url.toString()).forward(request, response);
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

	private String proccessAddingFriend(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User sender = (User) request.getSession().getAttribute("user");
		UserService service = new UserServiceImpl();
		String idOfReceiverAnnouce = request.getParameter("userId");
		User receiver = service.selectUserByUserId(idOfReceiverAnnouce);
		sender = service.selectUserByUserId(sender.getUserId());

		Announce announce = new Announce(sender, receiver);

		sender.getTo_announces().add(announce);

		userDAO.update(sender);

		// Update Announces in cache for 2 users
		UserAnnounce.insertSentAnnounce(sender.getUserId(), announce);
		UserAnnounce.insertReceivedAnnounce(receiver.getUserId(), announce);

		Session senderSession = Notification.getSession(sender.getUserId());
		Session receiverSession = Notification.getSession(receiver.getUserId());

		// Then we will send this friend request to both user
		if (senderSession != null) {
			Notification.sendMessage(sender.getUserId(), senderSession);
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		if (receiverSession != null) {
			Notification.sendMessage(receiver.getUserId(), receiverSession);
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		String url = "/jsp/Profile.jsp?found-user-id=" + idOfReceiverAnnouce;
		return url;

	}

//	private void acceptingAddFriend(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		// TODO Auto-generated method stub
//		String idFriend = request.getParameter("friendId");
//		User user = (User) request.getSession().getAttribute("user");
//		userDAO.processAddingFriend(idFriend, user);
//		request.getSession().setAttribute("user", user);
//		response.sendRedirect(request.getContextPath());
//	}
}
