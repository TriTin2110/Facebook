package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Service.UserInformationService;
import Service.implement.UserInformationServiceImpl;

/**
 * Servlet implementation class ChangeInformation
 */
@WebServlet("/ChangeInformation")
public class ChangeInformation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserInformationService informationService = new UserInformationServiceImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		String action = request.getParameter("update");
		boolean updateSuccess = false;

		switch (action) {
		case "GeneralInformation":
			updateSuccess = informationService.updateGeneralInformation(request);
			break;
		case "DOB":
			updateSuccess = informationService.updateDOB(request);
			break;
		}

		notice(updateSuccess, request, response);
	}

	private void notice(boolean updateSuccess, HttpServletRequest request, HttpServletResponse response) {
		if (updateSuccess)
			request.setAttribute("notice", "Cập nhật thành công");
		else {
			request.setAttribute("notice", "Cập nhật thất bại");
		}

		try {
			request.getRequestDispatcher("/jsp/InfoChange.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
