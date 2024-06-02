package Controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.UserDAO;
import Model.User;
import util.Hash.HashUtil;

/**
 * Servlet implementation class SignIn
 */
@WebServlet("/dang-nhap")
public class SignIn extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignIn() {
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
		// Kiểm tra tài khoản đã tồn tại hay chưa
		String email = HashUtil.hashWithSHA256(request.getParameter("typeEmail"));
		String password = HashUtil.hashWithSHA256(request.getParameter("typePassword"));
		String url = "/html/LoginPage.jsp";

		UserDAO userDAO = new UserDAO();
		User user = new User(null, email, password);
		User userSelected = userDAO.selectByEmail(user);
		// Nếu chưa tồn tại thì quay về trang đăng nhập
		if (userSelected == null) {
			request.setAttribute("error", "Email không tồn tại");
		} else {
			// Kiểm tra mật khẩu
			if (password.equals(userSelected.getPassword()))
				url = "/html/MainPage.html";
			else {
				request.setAttribute("error", "Mật khẩu không chính xác");
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
