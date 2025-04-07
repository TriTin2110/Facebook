package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Service.UserService;
import Service.implement.UserServiceImpl;

@WebServlet("/Account")
public class Account extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		userService = new UserServiceImpl(request);
		String action = request.getParameter("action");

		switch (action) {
		case "dang-ky":
			request = userService.signUp(request, response);
			getServletContext().getRequestDispatcher("/jsp/LoginPage.jsp").forward(request, response);
			break;

		case "dang-nhap":
			Object obj = userService.signIn(request, response);
			if (obj instanceof HttpServletRequest)
				request.getRequestDispatcher("/jsp/LoginPage.jsp").forward(request, response);
			else
				response.sendRedirect(request.getContextPath());
			break;

		case "xac-thuc-email":
			request = userService.emailConfirm(request, response);
			request.getRequestDispatcher("/jsp/LoginPage.jsp").forward(request, response);
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
