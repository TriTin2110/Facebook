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
User user = (User) request.getSession().getAttribute("user");
String id = request.getParameter("userId");
if(user == null)
{
	%>
	<jsp:forward page="/jsp/LoginPage.jsp"></jsp:forward>
<%	
}
else
{
	//Kiểm tra User để hiển thị trang profile
	//Nếu User profile Id khác với người dùng hiện tại 
	//user = User có profile Id đó
	if(id != null && id!=user.getUserId())
	{
		user = userDAO.selectById(id);
	}
}
request.setAttribute("postContent", user);
request.setCharacterEncoding("UTF-8");

%>
<body>
</body>
</html>