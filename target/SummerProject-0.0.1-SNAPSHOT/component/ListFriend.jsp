<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="Model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%
	User user = (User) session.getAttribute("user");
%>
<body>
	<div class="friend">
		<h4>Người liên hệ</h4>
		<ul>
		<%
			List<User> friends = new ArrayList<>();
			for(User friend : friends)
			{
				%>
					<li><img src="<%=url%>/img/friend2.jpg"/><span
					class="name-friend"><%=friend.getUserInformation().getFullName()%></span></li>
				<%
			}
		%>
		</ul>
	</div>
</body>
</html>