<%@page import="Enums.TypeAnnouce"%>
<%@page import="Model.User"%>
<%@page import="Model.Announce"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath();
User user = (User) request.getSession().getAttribute("user");
List<Announce> announces = user.getAnnounces();
%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<style type="text/css">
table {
	width: 70%;
}

table .btn {
	font-size: 14px;
}

table .btn-primary {
	margin-right: 5px;
}
</style>
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
			<div>
				<%
				for (Announce announce : announces) {
					if(announce.getSender() != null){
				%>
				<div style="margin-bottom: 10px;">
					<table>
						<tr>
							<td rowspan="2"><img src="<%=url + "/img/"+announce.getSender().getAvatar()%>"
								style="border-radius: 50%;" height="40"></td>
							<td><span style="font-weight: bold; font-size: 16px;"><%=announce.getSender().getUserInformation().getFullName()%></span>
							</td>
						</tr>
						<tr>
							<td><a class="btn btn-primary"
								href="<%=url + "/Friend?method=add&&friendId=" + announce.getSender().getUserId()%>">Xác
									nhận</a><a class="btn btn-secondary">Hủy bỏ</a></td>
						</tr>
					</table>
				</div>
				<%
				}
				}
				%>
			</div>
		</div>
		<jsp:include page="/component/ListFriend.jsp"></jsp:include>
	</div>
</body>
</html>