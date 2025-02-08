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

	public Account() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		switch (action) {
		case "dang-ky":
			singUp(request, response);
			break;

		case "dang-nhap":
			signIn(request, response);
			break;

		case "xac-thuc-email":
			emailConfirm(request, response);
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void singUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDAO userDAO = new UserDAO();
		UserInteract userInt = new UserInteract(request);
		UserInformationInteract infoInt = new UserInformationInteract(request);

		String email = request.getParameter("email");
		User user = userInt.createUser();

		// Mã hóa thông tin đăng nhập
		user = userInt.encryptPasswordEmailId(user);
		UserInformation userInformation = infoInt.createUserInformation(user);
		user.setUserInformation(userInformation);

		if (userDAO.selectByEmail(user) != null) {
			request.setAttribute("error", "Tài khoản đã tồn tại");
			String[] name = { request.getParameter("ten"), request.getParameter("ho") };
			gainValueForSignUpForm(request, name, userInformation);
		} else {
			if (addUserSuccess(user, userDAO)) {
				String urlEmailConfirm = request.getScheme() + "://" + request.getServerName() + ":"
						+ request.getServerPort() + request.getContextPath();
				request.setAttribute("error", "Tạo tài khoản thành công! Vui lòng kiểm tra email đã đăng ký");
				SendingMail.sendMail(email, userInformation.getFullName(), user.getUserId(), urlEmailConfirm);
			} else {
				request.setAttribute("error", "Tạo tài khoản không thành công");
			}

		}
		getServletContext().getRequestDispatcher("/jsp/LoginPage.jsp").forward(request, response);
	}

	private boolean addUserSuccess(User user, UserDAO userDAO) {
		return (userDAO.add(user) <= 0) ? false : true;
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
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		UserDAO userDAO = new UserDAO();
		UserInteract userInt = new UserInteract(request);

		String emailInput = request.getParameter("typeEmail");
		String passwordInput = request.getParameter("typePassword");
		String url = "/jsp/LoginPage.jsp";
		String userEmailInputEncrypted = userInt.hashEncrypt(emailInput);

		User user = userDAO.selectByEmail(new User(userEmailInputEncrypted));
		HttpSession session = request.getSession();

		if (user == null) {
			request.setAttribute("error", "Email không tồn tại");
		} else {
			userInt.setUser(user);
			// Kiểm tra mật khẩu
			boolean passwordAndEmailCorrected = userInt.checkingAccount(userEmailInputEncrypted, passwordInput);
			if (passwordAndEmailCorrected) {
				url = request.getContextPath();
				session.setAttribute("user", user);
				response.sendRedirect(url);
				return;
			} else
				request.setAttribute("error", "Email chưa được xác thực hoặc Mật khẩu không chính xác!");
		}
		request.getRequestDispatcher(url).forward(request, response);
	}

	private void emailConfirm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String idUser = request.getParameter("iduser");
		UserDAO userDAO = new UserDAO();
		User user = userDAO.confirmEmail(idUser);
		if (user != null) {
			request.getSession().setAttribute("user", user);
			request.getRequestDispatcher("/jsp/EmailConfirmSuccess.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("/jsp/LoginPage.jsp").forward(request, response);
		}

	}
}
