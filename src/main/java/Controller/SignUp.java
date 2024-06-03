package Controller;

import java.io.IOException;
import java.sql.Date;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.UserDAO;
import DAO.UserInformationDAO;
import Model.User;
import Model.UserInformation;
import util.SendingMail;
import util.Hash.HashUtil;

/**
 * Servlet implementation class SignUp
 */
@WebServlet("/dang-ky")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignUp() {
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
		String email = request.getParameter("email");
		String password = request.getParameter("matkhau");
		String lastName = request.getParameter("ho");
		String firstName = request.getParameter("ten");
		String dOB = request.getParameter("ngaysinh");
		String mOB = request.getParameter("thangsinh");
		String yOB = request.getParameter("namsinh");
		String gender = request.getParameter("gioitinh");

//		================================================================================================================
		// setAttribute cho các thuộc tính để gán giá trị cho các ô input
		request.setAttribute("firstName", firstName);
		request.setAttribute("lastName", lastName);
		request.setAttribute("dOB", dOB);
		request.setAttribute("mOB", mOB);
		request.setAttribute("yOB", yOB);
		request.setAttribute("gender", gender);
//		================================================================================================================
		// Đã tồn tại email trong db
		// Lưu userInformation
		// Lưu user
		String encryptEmail = HashUtil.hashWithSHA256(email);
		UserDAO userDAO = new UserDAO();
		String idUser = System.currentTimeMillis() + email;
		// ID = mã hóa (giờ hiện tại + email)
		User user = new User(HashUtil.hashWithSHA256(idUser), encryptEmail, HashUtil.hashWithSHA256(password));
		String url = "/html/LoginPage.jsp";
//		================================================================================================================
		// Trường hợp đã tồn tại user trong db
		// Thông báo không thành công và quay về trang đăng nhập
		if (userDAO.selectByEmail(user) != null) {
			request.setAttribute("error", "Email đã tồn tại");
		}
		// Trường hợp ngược lại thực hiện việc thêm user mới
		else {

			UserInformation userInformation = new UserInformation(idUser, user, lastName + " " + firstName,
					new Boolean(gender),
					new Date(Integer.parseInt(yOB) + 1900, Integer.parseInt(mOB) + 1, Integer.parseInt(dOB)), "", "",
					"", "", "");
			UserInformationDAO userInformationDAO = new UserInformationDAO();
			// Nếu thêm user thành công thì tiếp tục thêm userInformation
			if (userDAO.add(user) == 1) {
				if (userInformationDAO.add(userInformation) == 1) {
					System.out.println("Đã tạo tài khoản thành công");
					Random random = new Random();
					int ran = random.nextInt(100000, 999999);
					SendingMail.sendMail(email, ran, firstName + " " + lastName);
				}
				// Nếu thêm userInformationDAO không thành công thì loại bỏ user đã thêm trước
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
