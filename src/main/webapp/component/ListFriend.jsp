<%@page import="DAO.UserDAO"%>
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
UserDAO userDAO = new UserDAO();
String[] friendIds = user.getListFriendId().split(";");
%>
<body>
	<div class="friend">
		<h4>Người liên hệ</h4>
		<ul>
		<%
			
			for(String friendId: friendIds)
			{
				User friend = new User();
				friend.setUserId(friendId);
				friend = userDAO.selectById(friend);
				%>
					<li><img src="<%=url%>/img/<%=friend.getAvatar()%>"/><span
					class="name-friend"><%=friend.getUserInformation().getFullName()%></span></li>
				<%
			}
		%>
		</ul>
	</div>
</body>
</html>