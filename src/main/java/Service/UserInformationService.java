package Service;

import javax.servlet.http.HttpServletRequest;

public interface UserInformationService {
	public boolean updateGeneralInformation(HttpServletRequest request);

	public boolean updateDOB(HttpServletRequest request);
}
