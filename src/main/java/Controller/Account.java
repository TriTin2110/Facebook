package Controller;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.UserDAO;
import Interact.UserInformationInteract;
import Interact.UserInteract;
import Model.User;
import Model.UserInformation;
import util.SendingMail;

@WebServlet("/Account")
public class Account extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	private UserInteract userInt;
	private UserInformationInteract infoInt;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		createUtil(request);

		String action = request.getParameter("action");

		switch (action) {
		case "dang-ky":
			signUp(request, response);
			break;

		case "dang-nhap":
			signIn(request, response);
			break;

		case "xac-thuc-email":
			emailConfirm(request, response);
			break;
		}
	}

	private void createUtil(HttpServletRequest request) {
		userDAO = new UserDAO();
		userInt = new UserInteract(request);
		infoInt = new UserInformationInteract(request);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void signUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserInformation userInformation = null;
		User user = null;

		String email = request.getParameter("email");
		String notice = "Tạo tài khoản thành công! Vui lòng kiểm tra email đã đăng ký";
		String firstName = request.getParameter("ten");
		String lastName = request.getParameter("ho");
		String urlEmailConfirm = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();

		user = userInt.createUser();
		// Mã hóa thông tin đăng nhập
		user = userInt.encryptPasswordEmailId(user);
		userInformation = infoInt.createUserInformation(user);
		user.setUserInformation(userInformation);

		if (userDAO.selectByEmail(user) != null) {
			notice = "Tài khoản đã tồn tại";
			request.setAttribute("error", notice);
			String[] name = { firstName, lastName };
			gainValueForSignUpForm(request, name, userInformation);
		} else {
			boolean addUserSuccess = userDAO.add(user) > 0;
			if (addUserSuccess) {
				request.setAttribute("error", notice);
				SendingMail.sendMail(email, userInformation.getFullName(), user.getUserId(), urlEmailConfirm);
			} else {
				notice = "Tạo tài khoản không thành công";
				request.setAttribute("error", notice);
			}

		}
		getServletContext().getRequestDispatcher("/jsp/LoginPage.jsp").forward(request, response);
	}

	private void gainValueForSignUpForm(HttpServletRequest request, String[] name, UserInformation userInformation) {
		request.setAttribute("firstName", name[0]);
		request.setAttribute("lastName", name[1]);
		request.setAttribute("dOB", userInformation.getDateOfBirth().get(Calendar.DAY_OF_MONTH));
		request.setAttribute("mOB", userInformation.getDateOfBirth().get(Calendar.MONTH));
		request.setAttribute("yOB", userInformation.getDateOfBirth().get(Calendar.YEAR));
		request.setAttribute("gender", userInformation.getGender());
	}

	private void signIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String emailInput = request.getParameter("typeEmail");
		String passwordInput = request.getParameter("typePassword");
		String userEmailInputEncrypted = userInt.hashEncrypt(emailInput);
		String notice = "Email chưa được xác thực hoặc Mật khẩu không chính xác!";
		String url = "/jsp/LoginPage.jsp";
		User user = userDAO.selectByEmail(new User(userEmailInputEncrypted));
		HttpSession session = request.getSession();

		if (user == null) {
			notice = "Email không tồn tại";
			request.setAttribute("error", notice);
		} else {
			userInt.setUser(user);
			// Kiểm tra mật khẩu
			boolean passwordAndEmailCorrected = userInt.checkingAccount(passwordInput);
			if (passwordAndEmailCorrected) {
				url = request.getContextPath();
				session.setAttribute("user", user);
				response.sendRedirect(url);
				return;
			} else {
				request.setAttribute("error", notice);
			}
		}
		request.getRequestDispatcher(url).forward(request, response);
	}

	private void emailConfirm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idUser = request.getParameter("iduser");
		User user = userDAO.confirmEmail(idUser);
		String url = "/jsp/EmailConfirmSuccess.jsp";
		if (user != null) {
			request.getSession().setAttribute("user", user);
			request.getRequestDispatcher(url).forward(request, response);
		} else {
			url = "/jsp/LoginPage.jsp";
			request.getRequestDispatcher(url).forward(request, response);
		}
	}
}
