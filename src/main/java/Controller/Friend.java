package Controller;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Cache.UserCache;
import DAO.UserDAO;
import Model.Announce;
import Model.User;
import services.SearchFriendService;

class AnnouceForm {
	private String userName, message, typeOfAnnouce, avatar;
	@SuppressWarnings("unused")
	private boolean isReceiver;

	public AnnouceForm(String userName, String avatar, boolean isReceiver) {
		this.userName = userName;
		this.avatar = avatar;
		this.isReceiver = isReceiver;
		if (isReceiver) {
			this.message = userName + " đã gửi lời mời kết bạn dành cho bạn!";
			this.typeOfAnnouce = "FR";
		} else {
			this.message = "Bạn đã gửi lời mời kết bạn dành cho " + userName + "!";
			this.typeOfAnnouce = "SN";
		}

	}

	public String getUserName() {
		return userName;
	}

	public String getMessage() {
		return message;
	}

	public String getTypeOfAnnouce() {
		return typeOfAnnouce;
	}

	public String getAvatar() {
		return avatar;
	}

}

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
		UserDAO userDAO = new UserDAO();
		UserCache userCache = (UserCache) request.getSession().getAttribute("cache");
		String idReceiver = request.getParameter("userId");
		String url = "/jsp/Profile.jsp?found-user-id=" + idReceiver;

		User receiver = userDAO.selectById(idReceiver);
		User sender = userCache.getCurrentUser();

		AnnouceForm annouceFormReceiver = new AnnouceForm(sender.getUserInformation().getFullName(),
				receiver.getAvatar(), true);
		AnnouceForm annouceFormSender = new AnnouceForm(receiver.getUserInformation().getFullName(), sender.getAvatar(),
				false);

		Announce announceReceiver = createAnnouce(sender.getUserId(), receiver, annouceFormReceiver);
		Announce announceSender = createAnnouce(sender.getUserId(), sender, annouceFormSender);

		announceReceiver.setAnnouce(announceSender);

		saveAnnouce(announceReceiver, receiver);

		System.out.println("Adding Friend");

		List<User> listFriend = userCache.selectFriendsByUserIdCache(sender.getUserId());
		if (listFriend.isEmpty())
			listFriend = new LinkedList<>();
		listFriend.add(receiver);
		sender.setListFriend(listFriend);
		userDAO.update(sender);

		userCache.resetFriendList(sender.getUserId());
		request.getRequestDispatcher(url).forward(request, response);
	}

	private Announce createAnnouce(String idUserSendRequest, User toUser, AnnouceForm form) {
		boolean checked = false;
		long dateReceiveRequest = System.currentTimeMillis();
		Announce announce = new Announce(idUserSendRequest + "-" + dateReceiveRequest, form.getMessage(),
				form.getUserName(), form.getAvatar(), toUser, checked, dateReceiveRequest, form.getTypeOfAnnouce());
		return announce;
	}

	private void saveAnnouce(Announce announceReceiver, User receiver) {
		// TODO Auto-generated method stub
		List<Announce> announces = receiver.getAnnounces();
		announces.add(announceReceiver);
		receiver.setAnnounces(announces);

		userDAO.update(receiver);
	}

	private void acceptingAddFriend(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String idFriend = request.getParameter("friendId");
		UserCache cache = (UserCache) request.getSession().getAttribute("cache");
		User user = cache.getCurrentUser();

		List<User> friends = cache.selectFriendsByUserIdCache(user.getUserId());
		if (friends.isEmpty())
			friends = new LinkedList<User>();
		user.setListFriend(friends);
		userDAO.processAddingFriend(idFriend, user);
		cache.resetFriendList(user.getUserId());
		response.sendRedirect(request.getContextPath());
	}
}
