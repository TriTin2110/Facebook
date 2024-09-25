package Controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.UserDAO;
import DAO.UserInformationDAO;
import Model.User;
import Model.UserInformation;
import util.BcryptUtil;
import util.PasswordUtils;
import util.SendingMail;
import util.Hash.HashUtil;

@WebServlet("/Account")
public class Account extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Account() {
		super();
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

	@SuppressWarnings({ "removal", "deprecation" })
	private void singUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String email = request.getParameter("email");
		String password = request.getParameter("matkhau");
		String lastName = request.getParameter("ho");
		String firstName = request.getParameter("ten");

		UserDAO userDAO = new UserDAO();

		// Mã thông tin đăng nhập
		String passwordEncrypted = BcryptUtil.hashPassword(password);
		String emailEncrypted = HashUtil.hashWithSHA256(email);
		String idUserEncrypted = HashUtil.hashWithSHA256(System.currentTimeMillis() + email);

		User user = new User(idUserEncrypted, emailEncrypted, passwordEncrypted);

		if (getAccountByInputedEmail(userDAO, user) != null) {
			request.setAttribute("error", "Tài khoản đã tồn tại");
		} else {
			UserInformation userInformation = createUserInformation(user, idUserEncrypted, request);
			UserInformationDAO userInformationDAO = new UserInformationDAO();

			if (addUserSuccess(user, userDAO)) {
				if (addUserInformationSuccess(userInformation, userInformationDAO)) {
					String urlEmailConfirm = request.getScheme() + "://" + request.getServerName() + ":"
							+ request.getServerPort() + request.getContextPath();
					System.out.println("Đã tạo tài khoản thành công");
					SendingMail.sendMail(email, userInformation.getFullName(), idUserEncrypted, urlEmailConfirm);
				} else {
					request.setAttribute("error", "Tạo tài khoản không thành công");
					userDAO.remove(user);
				}
			} else {
				request.setAttribute("error", "Tạo tài khoản không thành công");
			}

			String[] name = { firstName, lastName };
			gainValueForSignUpForm(request, name, userInformation);
		}

		getServletContext().getRequestDispatcher("/jsp/LoginPage.jsp").forward(request, response);
	}

	private User getAccountByInputedEmail(UserDAO userDAO, User user) {
		return userDAO.selectByEmail(user);
	}

	@SuppressWarnings({ "deprecation", "removal" })
	private UserInformation createUserInformation(User user, String idUserEncrypt, HttpServletRequest request) {
		String lastName = request.getParameter("ho");
		String firstName = request.getParameter("ten");
		String dOB = request.getParameter("ngaysinh");
		String mOB = request.getParameter("thangsinh");
		String yOB = request.getParameter("namsinh");
		String gender = request.getParameter("gioitinh");

		UserInformation userInformation = new UserInformation(idUserEncrypt, user, lastName + " " + firstName,
				new Boolean(gender),
				new Date(Integer.parseInt(yOB) - 1900, Integer.parseInt(mOB) - 1, Integer.parseInt(dOB)), "", "", "",
				"", "");
		return userInformation;
	}

	private boolean addUserSuccess(User user, UserDAO userDAO) {
		return (userDAO.add(user) <= 0) ? false : true;
	}

	private boolean addUserInformationSuccess(UserInformation userInformation, UserInformationDAO userInformationDAO) {
		return (userInformationDAO.add(userInformation) <= 0) ? false : true;
	}

	@SuppressWarnings("deprecation")
	private void gainValueForSignUpForm(HttpServletRequest request, String[] information,
			UserInformation userInformation) {
		request.setAttribute("firstName", information[0]);
		request.setAttribute("lastName", information[1]);
		request.setAttribute("dOB", userInformation.getDateOfBirth().getDay());
		request.setAttribute("mOB", userInformation.getDateOfBirth().getMonth());
		request.setAttribute("yOB", userInformation.getDateOfBirth().getYear());
		request.setAttribute("gender", userInformation.getGender());
	}

	private void signIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String emailInput = request.getParameter("typeEmail");
		String passwordInput = request.getParameter("typePassword");

		String url = "/jsp/LoginPage.jsp";

		UserDAO userDAO = new UserDAO();
		String userEmailInputEncrypted = HashUtil.hashWithSHA256(emailInput);
		User user = getAccountByInputedEmail(userDAO, new User(userEmailInputEncrypted));
		HttpSession session = request.getSession();

		if (user == null) {
			request.setAttribute("error", "Email không tồn tại");
		} else {
			// Kiểm tra mật khẩu
			if (PasswordUtils.checkPassword(passwordInput, user.getPassword())) {
				if (emailIsConfirmed(userEmailInputEncrypted)) {
					url = "/jsp/MainPage.jsp";
					session.setAttribute("user", user);
				} else {
					request.setAttribute("error", "Email chưa được xác thực!");
				}
			} else {
				request.setAttribute("error", "Mật khẩu không chính xác");
			}
		}
		request.getRequestDispatcher(url).forward(request, response);
	}

	private boolean emailIsConfirmed(String email) {
		UserDAO userDAO = new UserDAO();
		User user = new User();
		user.setEmail(email);
		user = userDAO.selectByEmail(user);
		return user.isIdentifyStatus();
	}

	private void emailConfirm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String idUser = request.getParameter("iduser");
		UserDAO userDAO = new UserDAO();
		userDAO.confirmEmail(idUser);
		getServletContext().getRequestDispatcher("/jsp/EmailConfirmSuccess.jsp").forward(request, response);
	}
}
