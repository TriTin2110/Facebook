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
<link rel="stylesheet" href="<%=url%>/css/MainPage.css" />
<link rel="stylesheet" href="<%=url%>/css/responsive.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" />
<link rel="stylesheet" type="text/css"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous" />
</head>
<%
	User user = (User) session.getAttribute("user");
%>
<body>
<div class="nav">
			<div class="logo-search">
				<div class="logo">
					<a href="">B</a>
				</div>
				<div class="search">
					<input type="text" name="search"
						placeholder="Tìm kiếm trên BodyBook" /> <i
						class="fa-solid fa-magnifying-glass"></i>
				</div>
			</div>
			<div class="nav-list">
				<ul>
					<li><a href="<%=url%>/jsp/MainPage.jsp"><i class="fa-solid fa-house"></i></a></li>
					<li><a href=""><i class="fa-solid fa-user-group"></i></a></li>
					<li><a href=""><i class="fa-solid fa-tv"></i></a></li>
					<li><a href=""><i class="fa-solid fa-shop"></i></a></li>
					<li><a href=""><i class="fa-solid fa-gamepad"></i></a></li>
				</ul>
			</div>
			<div class="notify-mess">
				<ul class="nm_list">
					<li class="nm_item"><a href="#" class="nm_link"><i
							class="nm_icon fa-solid fa-ellipsis"></i></a></li>
					<li class="nm_item"><a href="Messenger.jsp" class="nm_link"><i
							class="nm_icon fa-solid fa-comments"></i></a></li>
					<li class="nm_item"><a href="#" class="nm_link"><i
							class="nm_icon fa-solid fa-bell"></i></a></li>
					<li class="nm_item"><a href="#" class="nm_link"> <img
							src="<%=url%>/img/avt.jpg" alt="" id="open-profile" />
							<div class="acc_more">
								<ul>
									<li><a href="<%=url%>/jsp/Profile.jsp"> <img src="<%=url%>/img/avt.jpg" alt="">
											<h3><%=user.getUserInformation().getFullName()%></h3>
									</a></li>
									<li><a href="<%=url%>/jsp/InfoChange.jsp"> <i
											class="fa-solid fa-gear"></i>
											<h4>Cài đặt & quyền riêng tư</h4>
									</a></li>
									<li><a href="#"> <i
											class="fa-solid fa-circle-question"></i>
											<h4>Trợ giúp & hỗ trợ</h4>
									</a></li>
									<li><a href="#"> <i class="fa-solid fa-moon"></i>
											<h4>Màn hình & trợ năng</h4>
									</a></li>
									<li><a href="#"> <i class="fa-solid fa-exclamation"></i>
											<h4>Đóng góp ý kiến</h4>
									</a></li>
									<li><a href="<%=url%>/jsp/LoginPage.jsp"> <i
											class="fa-solid fa-right-from-bracket"></i>
											<h4>Đăng xuất</h4>
									</a></li>
								</ul>
							</div>
					</a></li>
				</ul>
			</div>
		</div>
</body>
</html>