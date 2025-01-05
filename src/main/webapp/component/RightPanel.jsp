<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath();
%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="section-right hide-on-mobile-tablet">
		<div class="advertise">
			<h4>Được tài trợ</h4>
			<ul>
				<li><a href="https://www.xanhsm.com/" target="blank"> <img
						src="<%=url%>/img/anhqc.jpg" alt="" />
						<div class="des">
							<h5>Đặt Xe Ngay</h5>
							<h6>xanhsm.com</h6>
						</div>
				</a></li>
				<li><a href="https://king52.fun/" target="blank"> <img
						src="<%=url%>/img/anhqc2.jpg" alt="" />
						<div class="des">
							<h5>Đăng ký nhận ưu đãi</h5>
							<h6>king52.fun</h6>
						</div>
				</a></li>
			</ul>
		</div>
		<div class="addfriend">
			<div class="header">
				<h4>Lời mời kết bạn</h4>
				<a href="#">Xem tất cả</a>
			</div>
			<div class="newfriend">
				<img src="<%=url%>/img/j97.jpg" alt="" class="nf-avt" />
				<div class="nf-information">
					<div class="name-acpt">
						<h5>Anh Dắc 97</h5>
						<a href="#">Xác nhận</a>
					</div>
					<div class="time-refuse">
						<h6>32 phút</h6>
						<a href="#">Xóa</a>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="/component/ListFriend.jsp"></jsp:include>
	</div>
</body>
</html>