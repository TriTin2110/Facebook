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
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		String action = request.getParameter("update");
		boolean updateSuccess = false;

		switch (action) {
		case "GeneralInformation":
			updateSuccess = updateGeneralInformation(request);
			break;
		case "DOB":
			updateSuccess = updateDOB(request);
			break;
		}

		if (updateSuccess)
			request.setAttribute("notice", "Cập nhật thành công");
		else {
			request.setAttribute("notice", "Cập nhật thất bại");
		}

		try {
			request.getRequestDispatcher("/jsp/InfoChange.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean updateDOB(HttpServletRequest request) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		Date dob = Date.valueOf(request.getParameter("dob"));
		UserInformation userInformation = new UserInformation();
		UserInformationDAO userInformationDAO = new UserInformationDAO();
		userInformation.setUserId((String) session.getAttribute("userId"));
		userInformation = userInformationDAO.selectById(userInformation);
		userInformation.setDateOfBirth(dob);
		if (userInformationDAO.update(userInformation) == 1) {
			return true;
		} else {
			return false;
		}
	}

	private boolean updateGeneralInformation(HttpServletRequest request) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String name = request.getParameter("name");
		boolean gender = request.getParameter("gender").equals("Nam");
		String phoneNumber = request.getParameter("phone");
		String homeTown = request.getParameter("home-town");
		String userId = (String) session.getAttribute("userId");
		UserInformation userInformation = new UserInformation(userId);
		UserInformationDAO userInformationDAO = new UserInformationDAO();
		userInformation = userInformationDAO.selectById(userInformation);
		userInformation.setFullName(name);
		userInformation.setGender(gender);
		userInformation.setPhoneNumber(phoneNumber);
		userInformation.setHomeTown(homeTown);
		if (userInformationDAO.update(userInformation) == 1) {
			return true;
		} else {
			return false;
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

}
