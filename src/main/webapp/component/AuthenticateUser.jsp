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
UserDAO dao = new UserDAO();
User user = null;
String id = request.getParameter("userID");
if(id!=null)
{
	user.setUserId(id);
	user = dao.selectById(user);
}
else
{
	user = (User) request.getSession().getAttribute("user");
}
	
request.setCharacterEncoding("UTF-8");
if (user == null) {
		%>
			<jsp:forward page="/jsp/LoginPage.jsp"></jsp:forward>
		<%
}
%>
<body>
</body>
</html>