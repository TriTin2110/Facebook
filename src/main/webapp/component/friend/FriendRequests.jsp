<%@page import="java.sql.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="Model.Announce"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<%
String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath();
List<Announce> announces = (List<Announce>) request.getAttribute("announces");
SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
%>
<body>
	<%
	if (announces != null && !announces.isEmpty()) {
		for (Announce announce : announces) {
	%>
	<div class="newfriend">
		<img src="<%=url + "/img/" + announce.getUserAvatarRequested()%>" alt=""
			class="nf-avt" />
		<div class="nf-information">
			<div class="name-acpt">
				<h5><%=announce.getUserNameRequested()%></h5>
				<a href="#">Xác nhận</a>
			</div>
			<div class="time-refuse">
				<h6><%=df.format(new Date(announce.getDate()))%></h6>
				<a href="#">Xóa</a>
			</div>
		</div>
	</div>
	<%
	}
	}
	%>
</body>
</html>