package Interact;

import javax.servlet.http.HttpServletRequest;

import Model.User;
import util.PasswordBcrypt;
import util.Hash.HashUtil;

public class UserInteract {
	private HttpServletRequest request;
	private User user;

	public UserInteract(HttpServletRequest request) {
		this.request = request;
	}

	public User createUser() {
		User user = new User();
		String email = request.getParameter("email");
		String password = request.getParameter("matkhau");

		user = new User(email, password);

		return user;
	}

	public User encryptPasswordEmailId(User user) {
		String passwordEncrypted = bcryptEncrypt(user.getPassword());
		String emailEncrypted = hashEncrypt(user.getEmail());
		String idUserEncrypted = HashUtil.hashWithSHA256(System.currentTimeMillis() + user.getEmail());

		user.setUserId(idUserEncrypted);
		user.setEmail(emailEncrypted);
		user.setPassword(passwordEncrypted);

		return user;
	}

	public String bcryptEncrypt(String text) {
		return PasswordBcrypt.hashPassword(text);
	}

	public String hashEncrypt(String text) {
		return HashUtil.hashWithSHA256(text);
	}

	public boolean checkingAccount(String passwordInput) {
		boolean passwordIsCorrected = checkingPassword(passwordInput);
		boolean emailIsCorrected = user.isIdentifyStatus();
		if (!passwordIsCorrected || !emailIsCorrected) {
			return false;

		}
		return true;
	}

	private boolean checkingPassword(String passwordInput) {
		return PasswordBcrypt.checkPassword(passwordInput, user.getPassword());
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
