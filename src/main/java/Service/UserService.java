package Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {
	public HttpServletRequest signUp(HttpServletRequest request, HttpServletResponse response);

	public Object signIn(HttpServletRequest request, HttpServletResponse response);

	public HttpServletRequest emailConfirm(HttpServletRequest request, HttpServletResponse response);
}
