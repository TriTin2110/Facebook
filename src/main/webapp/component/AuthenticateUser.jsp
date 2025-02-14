<%@page import="Cache.UserCache"%>
<%@page import="DAO.UserDAO"%>
<%@page import="Model.User"%>
<%@page import="util.CheckingLogin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%
UserDAO userDAO = new UserDAO();
UserCache cache = (UserCache) request.getSession().getAttribute("cache");

User user = null;
String id = request.getParameter("userId");
if(cache == null)
{
	%>
	<jsp:forward page="/jsp/LoginPage.jsp"></jsp:forward>
<%	
}
else
{
	user = cache.getCurrentUser();
	//Kiểm tra User để hiển thị trang profile
	//Nếu User profile Id khác với người dùng hiện tại 
	//user = User có profile Id đó
	if(id != null && id!=user.getUserId())
	{
		user.setUserId(id);
		user = userDAO.selectById(user);
	}
}
request.setAttribute("postContent", user);
request.setCharacterEncoding("UTF-8");

%>
<body>
</body>
</html>