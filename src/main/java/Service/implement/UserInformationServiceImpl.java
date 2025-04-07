package Service.implement;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import DAO.UserInformationDAO;
import Model.User;
import Model.UserInformation;
import Service.UserInformationService;

public class UserInformationServiceImpl implements UserInformationService {
	private UserInformationDAO userInformationDAO;

	public boolean updateGeneralInformation(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
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

	public boolean updateDOB(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
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
}
