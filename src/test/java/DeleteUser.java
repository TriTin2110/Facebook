import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import DAO.UserDAO;
import Model.User;

public class DeleteUser {
	private static UserDAO userDAO;

	@BeforeAll
	public static void createDAO() {
		userDAO = new UserDAO();
	}

	@Test
	public void deleteAllUser() {
		List<User> users = userDAO.selectAll();
		System.out.println(users.size());
		String message = "Có lỗi xảy ra";
		boolean success = false;
		try {
			for (User user : users) {
				System.out.println(user.getUserInformation().getFullName());
				userDAO.remove(user);
			}
			message = "Xóa thành công!";
			success = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if (!userDAO.selectAll().isEmpty()) {
			message = "Mảng không rỗng!";
			success = false;
		}

		assertTrue(success, message);
	}
}
