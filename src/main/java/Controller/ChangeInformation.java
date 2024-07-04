package Controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.UserInformationDAO;
import Model.UserInformation;

/**
 * Servlet implementation class ChangeInformation
 */
@WebServlet("/ChangeInformation")
public class ChangeInformation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangeInformation() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
		String lastName = request.getParameter("ho");
		String firstName = request.getParameter("ten");

		String fullName = firstName + " " + lastName;
		String phoneNumber = request.getParameter("sdt");
		Date dob = Date.valueOf(request.getParameter("date"));
		boolean gender = (request.getParameter("gioitinh").equals("Nam")) ? true : false;
		String userId = (String) session.getAttribute("userId");

		UserInformation userInformation = new UserInformation(userId, fullName, gender, dob, phoneNumber);

		UserInformationDAO userInformationDAO = new UserInformationDAO();
		userInformationDAO.update(userInformation);
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
