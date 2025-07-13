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
import Enums.TypeAnnouce;
import Model.Announce;
import Model.User;
import Service.UserService;
import Service.implement.UserServiceImpl;
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
		if (request.getSession().getAttribute("user") != null) {
			try {
				switch (method) {
				case "search":
					searchFriend(request, response);
					return;
				case "proccess-adding-friend":
					sendFriendRequest(request, response);
					break;
				case "add":
					acceptingAddFriend(request, response);
					break;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} else
			request.getRequestDispatcher(request.getContextPath()).forward(request, response);
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
		System.out.println("Da tim kiem");
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

	private String sendFriendRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User sender = (User) request.getSession().getAttribute("user");
		String idOfReceiverAnnouce = request.getParameter("userId");
		boolean isAnnounceAlreadyExists = false;
		AnnounceDAO announceDAO = new AnnounceDAO();
		List<Announce> senderAnnounces = announceDAO.selectSentAnnouncesByFromUserId(idOfReceiverAnnouce);
		List<User> senderFriends = sender.getListFriend();

		for (Announce announce : senderAnnounces) {
			if (announce.getUser().getUserId().equals(idOfReceiverAnnouce)) {
				isAnnounceAlreadyExists = true;
				break;
			}
		}

		for (User user : senderFriends) {
			if (user.getUserId().equals(idOfReceiverAnnouce)) {
				isAnnounceAlreadyExists = true;
				break;
			}
		}

		if (!isAnnounceAlreadyExists) {
			UserService service = new UserServiceImpl();
			User receiver = service.selectUserByUserId(idOfReceiverAnnouce);
			sender = service.selectUserByUserId(sender.getUserId());
			Announce announceSender = Announce.createAnnouceObject(
					"Bạn đã gửi lời kết bạn cho <strong> " + receiver.getUserInformation().getFullName() + "</strong>",
					sender, receiver.getAvatar(), TypeAnnouce.FRIEND_REQUEST);

			Announce announceReceiver = Announce.createAnnouceObject("Bạn đã nhận được lời kết bạn từ <strong> "
					+ sender.getUserInformation().getFullName() + "</strong>", receiver, sender.getAvatar(),
					TypeAnnouce.FRIEND_RESPONSE);
			announceReceiver.setSender(sender);

			sender.getAnnounces().add(announceSender);
			receiver.getAnnounces().add(announceReceiver);
			userDAO.update(sender);
			userDAO.update(receiver);
			request.getSession().setAttribute("user", sender);
		}
		String url = "/jsp/Profile.jsp?found-user-id=" + idOfReceiverAnnouce;
		response.sendRedirect(request.getContextPath() + url);
		return url;
	}

	private void acceptingAddFriend(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String idFriend = request.getParameter("friendId");
		User user = (User) request.getSession().getAttribute("user");
		userDAO.processAddingFriend(idFriend, user);
		request.getSession().setAttribute("user", user);
		response.sendRedirect(request.getContextPath());
	}
}
