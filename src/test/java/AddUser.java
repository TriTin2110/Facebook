import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import DAO.UserDAO;
import Model.User;
import Model.UserInformation;
import util.PasswordBcrypt;
import util.Hash.HashUtil;

public class AddUser {
	private static UserDAO userDAO;

	@BeforeAll
	public static void setup() {
		userDAO = new UserDAO();
	}

	@Test
	public void addUser() {
		boolean success = false;
		try {
			String passwordEncrypted1 = PasswordBcrypt.hashPassword("123");
			String emailEncrypted1 = HashUtil.hashWithSHA256("tin@gmail.com");
			User user1 = new User(emailEncrypted1, passwordEncrypted1);
			UserInformation userInfo1 = new UserInformation(user1, "Nguyen Tin");
			String idUserEncrypted1 = HashUtil.hashWithSHA256(System.currentTimeMillis() + user1.getEmail());
			user1.setUserId(idUserEncrypted1);
			userInfo1.setUserId(idUserEncrypted1);
			user1.setUserInformation(userInfo1);

			String passwordEncrypted2 = PasswordBcrypt.hashPassword("123");
			String emailEncrypted2 = HashUtil.hashWithSHA256("ntt@gmail.com");
			User user2 = new User(emailEncrypted2, passwordEncrypted2);
			UserInformation userInfo2 = new UserInformation(user2, "Nguyen NTT");
			String idUserEncrypted2 = HashUtil.hashWithSHA256(System.currentTimeMillis() + user1.getEmail());
			user2.setUserId(idUserEncrypted2);
			userInfo2.setUserId(idUserEncrypted2);
			user2.setUserInformation(userInfo2);

			userDAO.add(user1);
			userDAO.add(user2);
			success = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		assertTrue(success);
	}
}
