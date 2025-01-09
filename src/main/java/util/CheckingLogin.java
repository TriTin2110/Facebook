package util;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import Model.User;

public class CheckingLogin {
	public static User checking(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
}
