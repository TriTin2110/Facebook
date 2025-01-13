<%@page import="Model.Friend"%>
<%@page import="java.util.List"%>
<%@page import="Model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
List<Friend> users = (List<Friend>) request.getSession().getAttribute("listSearched");
request.getSession().removeAttribute("listSearched");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="<%=url%>/css/SearchFriend.css" />
<link rel="stylesheet" href="<%=url%>/css/responsive.css" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" />
<title>BodyBook</title>
</head>
<%@include file="../component/AuthenticateUser.jsp"%>
<body>
	<div class="body">
		<jsp:include page="../component/Header.jsp"></jsp:include>
		<jsp:include page="../component/LeftPanel.jsp">
			<jsp:param value="<%=user.getAvatar()%>" name="avatar" />
			<jsp:param value="<%=user.getUserInformation().getFullName()%>"
				name="fullName" />
		</jsp:include>
		<div class="search-friend">
			<div class="search-friend_list">
				<ul>
					<li>
						<h2>Mọi người</h2>
					</li>
					<%
								if (user==null && users.isEmpty()) {
					%>
					<li style="display: flex; justify-content: center;">
						<h2 style="color: gray;">Không có kết quả nào trùng khớp</h2>
					</li>
					<%
								} else {
								for (Friend userInSearchedList : users) {
					%>
					<li>
						<div class="avt_name">
							<img src="<%=url%>/img/<%=userInSearchedList.getAvatar()%>"
								alt="" />
							<h3>
								<a style="text-decoration: none; color: black;"
									href="<%=url%>/jsp/Profile.jsp?userId=<%=userInSearchedList.getId()%>">
									<%=userInSearchedList.getFullName() %></a>
							</h3>
						</div>
						<div class="addfr_btn">
							<a href="#">Thêm bạn bè</a>
						</div>
					</li>
					<%
					}
					}
					%>
				</ul>
			</div>
		</div>
		<jsp:include page="../component/RightPanel.jsp"></jsp:include>
	</div>

	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
</body>
</html>
