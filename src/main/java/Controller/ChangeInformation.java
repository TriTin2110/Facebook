package Controller;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.UserInformationDAO;
import Model.User;
import Model.UserInformation;

/**
 * Servlet implementation class ChangeInformation
 */
@WebServlet("/ChangeInformation")
public class ChangeInformation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserInformationDAO userInformationDAO;
	private HttpSession session;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		createUtil(request);

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

		notice(updateSuccess, request, response);
	}

	public void createUtil(HttpServletRequest request) {
		userInformationDAO = new UserInformationDAO();
		session = request.getSession();
	}

	private boolean updateGeneralInformation(HttpServletRequest request) {
		User user = (User) session.getAttribute("user");
		UserInformation userInformation = user.getUserInformation();
		userInformation = setAttributeForUserInformation(request, userInformation);

		boolean updateSucceed = userInformationDAO.update(userInformation) > 0;
		if (updateSucceed) {
			return true;
		} else {
			return false;
		}
	}

	private UserInformation setAttributeForUserInformation(HttpServletRequest request,
			UserInformation userInformation) {
		String name = request.getParameter("name");
		boolean gender = request.getParameter("gender").equals("Nam");
		String phoneNumber = request.getParameter("phone");
		String homeTown = request.getParameter("home-town");

		userInformation.setFullName(name);
		userInformation.setGender(gender);
		userInformation.setPhoneNumber(phoneNumber);
		userInformation.setHomeTown(homeTown);

		return userInformation;
	}

	private boolean updateDOB(HttpServletRequest request) {
		User user = (User) session.getAttribute("user");
		UserInformation userInformation = user.getUserInformation();
		userInformation = setDOBUserInformation(request, userInformation);

		boolean updateSucceed = userInformationDAO.update(userInformation) > 0;
		if (updateSucceed) {
			return true;
		} else {
			return false;
		}
	}

	public UserInformation setDOBUserInformation(HttpServletRequest request, UserInformation userInformation) {
		String[] dateOfBirth = request.getParameter("dob").split("-");
		int year = Integer.parseInt(dateOfBirth[0]);
		int month = Integer.parseInt(dateOfBirth[1]) - 1;
		int day = Integer.parseInt(dateOfBirth[2]);
		Calendar dob = Calendar.getInstance();
		dob.set(year, month, day);

		userInformation.setDateOfBirth(dob);

		return userInformation;
	}

	private void notice(boolean updateSuccess, HttpServletRequest request, HttpServletResponse response) {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
