package Controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.SearchFriendService;

/**
 * Servlet implementation class SearchFriend
 */
@WebServlet("/SearchFriend")
public class SearchFriend extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchFriend() {
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

		String data = request.getParameter("search");
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
