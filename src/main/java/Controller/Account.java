package Controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import DAO.UserDAO;
import DAO.UserInformationDAO;
import HibernateUtil.HibernateUtil;
import Model.User;
import Model.UserInformation;
import util.SendingMail;
import util.Hash.HashUtil;

/**
 * Servlet implementation class Account
 */
@WebServlet("/Account")
public class Account extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Account() {
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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		switch (action) {
		case "dang-ky":
			singUp(request, response);
			break;

		case "dang-nhap":
			singIn(request, response);
			break;

		case "xac-thuc-email":
			emailConfirm(request, response);
			break;
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

	private void gainValueForForm(HttpServletRequest request, String[] information) {
		// setAttribute cho các thuộc tính để gán giá trị cho các ô input
		request.setAttribute("firstName", information[0]);
		request.setAttribute("lastName", information[1]);
		request.setAttribute("dOB", information[2]);
		request.setAttribute("mOB", information[3]);
		request.setAttribute("yOB", information[4]);
		request.setAttribute("gender", information[5]);
	}

	private List<String> encrypt(List<String> accountInfos) {
		List<String> result = new ArrayList<String>();
		for (String info : accountInfos) {
			result.add(HashUtil.hashWithSHA256(info));
		}
		return result;
	}

	private boolean emailAlreadyExist(UserDAO userDAO, User user) {
		return userDAO.selectByEmail(user) != null;
	}

	private boolean emailIsConfirmed(String email) {
		UserDAO userDAO = new UserDAO();
		User user = new User();
		user.setEmail(email);
		user = userDAO.selectByEmail(user);
		return user.isIdentifyStatus();
	}

	private void singUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String email = request.getParameter("email");
		String password = request.getParameter("matkhau");
		String lastName = request.getParameter("ho");
		String firstName = request.getParameter("ten");
		String dOB = request.getParameter("ngaysinh");
		String mOB = request.getParameter("thangsinh");
		String yOB = request.getParameter("namsinh");
		String gender = request.getParameter("gioitinh");

//				================================================================================================================
		String[] information = { firstName, lastName, dOB, mOB, yOB, gender };
		gainValueForForm(request, information);
//				================================================================================================================
		// Đã tồn tại email trong db
		// Lưu userInformation
		// Lưu user
		UserDAO userDAO = new UserDAO();
		String idUser = System.currentTimeMillis() + email;

		// Mã hóa
		List<String> infoEncrypted = encrypt(Arrays.asList(email, password, idUser));
		String emailEncrypt = infoEncrypted.get(0);
		String passwordEncrypt = infoEncrypted.get(1);
		String idUserEncrypt = infoEncrypted.get(2); // ID = mã hóa (giờ hiện tại + email)

		User user = new User(idUserEncrypt, emailEncrypt, passwordEncrypt);

		String url = "/html/LoginPage.jsp"; // Đặt url mặc định là trang đăng nhập (trường hợp đăng ký ko thành công)

//				================================================================================================================
		// Trường hợp đã tồn tại user trong db
		// Thông báo không thành công và quay về trang đăng nhập
		if (emailAlreadyExist(userDAO, user)) {
			request.setAttribute("error", "Email đã tồn tại");
		}
		// Trường hợp ngược lại thực hiện việc thêm user mới
		else {

			UserInformation userInformation = new UserInformation(idUserEncrypt, user, lastName + " " + firstName,
					new Boolean(gender),
					new Date(Integer.parseInt(yOB) - 1900, Integer.parseInt(mOB) - 1, Integer.parseInt(dOB)), "", "",
					"", "", "");
			UserInformationDAO userInformationDAO = new UserInformationDAO();
			
			boolean addSuccess = (userDAO.add(user) <= 0) ? false : true;
			if (addSuccess) {
				addSuccess = (userInformationDAO.add(userInformation) <= 0) ? false : true;
				if (addSuccess) {
					String urlEmailConfirm = request.getScheme() + "://" + request.getServerName() + ":"
							+ request.getServerPort() + request.getContextPath();
					System.out.println("Đã tạo tài khoản thành công");
					Random random = new Random();
					int ran = random.nextInt(100000, 999999);
					SendingMail.sendMail(email, ran, firstName + " " + lastName, idUserEncrypt, urlEmailConfirm);
				}
				// Nếu thêm userInformationDAO không thành công thì loại bỏ trường user đã thêm
				// trước
				// đó ra khỏi danh sách
				else {
					request.setAttribute("error", "Tạo tài khoản không thành công");
					userDAO.remove(user);
				}
			}
			// Trường hợp ngược lại thì thông báo lỗi và quay về trang đăng nhập
			else {
				request.setAttribute("error", "Tạo tài khoản không thành công");
			}
		}
		RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(url);
		requestDispatcher.forward(request, response);
	}

	private void singIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// Kiểm tra tài khoản đã tồn tại hay chưa
		List<String> infoEncrypted = encrypt(
				Arrays.asList(request.getParameter("typeEmail"), request.getParameter("typePassword")));
		String email = infoEncrypted.get(0);
		String password = infoEncrypted.get(1);
		String url = "/html/LoginPage.jsp";

		UserDAO userDAO = new UserDAO();
		User user = new User(null, email, password);

		if (!emailAlreadyExist(userDAO, user)) {
			request.setAttribute("error", "Email không tồn tại");
		} else {
			// Kiểm tra mật khẩu
			if (password.equals(user.getPassword())) {
				if (emailIsConfirmed(email)) {
					url = "/html/MainPage.html";
					HttpSession session = request.getSession();
					session.setAttribute("userId", userDAO.selectByEmail(user).getUserId());
				} else {
					request.setAttribute("error", "Email chưa được xác thực!");
				}
			} else {
				request.setAttribute("error", "Mật khẩu không chính xác");
			}
		}

		RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(url);
		requestDispatcher.forward(request, response);
	}

	private void emailConfirm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String idUser = request.getParameter("iduser");

		// Lấy user ra và set trạng thái xác thực = true
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		User user = session.get(User.class, idUser);
		user.setIdentifyStatus(true);

		// Cập nhật lại đối tượng user sau khi đã đặt trạng thái xác thực là true
		Transaction transaction = session.beginTransaction();
		session.update(user);
		transaction.commit();
		session.close();
		sessionFactory.close();
		getServletContext().getRequestDispatcher("/html/EmailConfirmSuccess.html").forward(request, response);

	}
}
