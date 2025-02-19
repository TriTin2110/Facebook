<%@page import="Cache.UserCache"%>
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
UserCache cache = (UserCache) request.getSession().getAttribute("cache");
User user = cache.getCurrentUser();
List<User> friends = cache.selectFriendsByUserIdCache(user.getUserId());
%>
<body>
	<div class="friend">
		<h4>Người liên hệ</h4>
		<ul>
		<%
		for(User f : friends){
			%>
			<li><img src="<%=url%>/img/<%=f.getAvatar()%>"/><span
			class="name-friend"><%=f.getUserInformation().getFullName()%></span></li>
			<%
		}
		%>
		</ul>
	</div>
</body>
</html>