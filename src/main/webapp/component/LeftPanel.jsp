<%@page import="Model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath();
%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%
String avatar = request.getParameter("avatar");
String fullName = request.getParameter("fullName");
%>
<body>
	<div class="section-left hide-on-mobile-tablet">
		<div class="left-top">
			<ul>
				<li><a href="<%=url%>/jsp/Profile.jsp"><img
						src="<%=url%>/img/<%=avatar%>" alt="" />
						<h5><%=fullName%></h5> </a></li>
				<li><a href="#"><i class="fa-solid fa-user-group"></i>
						<h5>Bạn bè</h5> </a></li>
				<li><a href="#"><i class="fa-solid fa-heart"></i>
						<h5>Kỷ niệm</h5> </a></li>
				<li><a href="#"><i class="fa-solid fa-bookmark"></i>
						<h5>Đã lưu</h5> </a></li>
				<li><a href="#"><i class="fa-solid fa-people-group"></i>
						<h5>Nhóm</h5> </a></li>
				<li><a href="#"><i class="fa-solid fa-video"></i>
						<h5>Video</h5> </a></li>
				<li><a href="#"><i class="fa-solid fa-caret-down"></i>
						<h5>Xem thêm</h5> </a></li>
			</ul>
		</div>
		<div class="left-bot">
			<h4>Lối tắt của bạn</h4>
			<ul>
				<li><a href="#"> <img src="<%=url%>/img/group1.jpg" alt="" />
						<h5>Đảo mèo</h5>
				</a></li>
				<li><a href="#"> <img src="<%=url%>/img/group2.jpg" alt="" />
						<h5>Đảo chó</h5>
				</a></li>
				<li><a href="#"> <img src="<%=url%>/img/group3.jpg" alt="" />
						<h5>Cộng đồng lập trình</h5>
				</a></li>
				<li><a href="#"> <img src="<%=url%>/img/group4.jpg" alt="" />
						<h5>Sinh viên 2k3</h5>
				</a></li>
				<li><a href="#"> <img src="<%=url%>/img/group5.jpg" alt="" />
						<h5>Cộng đồng sinh viên IUH</h5>
				</a></li>
				<li><a href="#"><i class="fa-solid fa-caret-down"></i>
						<h5>Xem thêm</h5> </a></li>
			</ul>
		</div>
	</div>
</body>
</html>