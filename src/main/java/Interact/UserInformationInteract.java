package Interact;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import Model.User;
import Model.UserInformation;

public class UserInformationInteract {
	private HttpServletRequest request;

	public UserInformationInteract(HttpServletRequest request) {
		this.request = request;
	}

	public UserInformation createUserInformation(User user) {
		String lastName = request.getParameter("ho");
		String firstName = request.getParameter("ten");
		String fullName = firstName + " " + lastName;
		int dOB = Integer.parseInt(request.getParameter("ngaysinh"));
		int mOB = Integer.parseInt(request.getParameter("thangsinh"));
		int yOB = Integer.parseInt(request.getParameter("namsinh"));
		String genderStr = request.getParameter("gioitinh");
		boolean gender = (genderStr.equals("Nam") ? true : false);
		Calendar cal = Calendar.getInstance();
		cal.set(yOB, mOB - 1, dOB);

		UserInformation userInformation = new UserInformation(user.getUserId(), user, fullName, gender, cal);
		return userInformation;
	}

}
