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
User user = (User) request.getSession().getAttribute("user");
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