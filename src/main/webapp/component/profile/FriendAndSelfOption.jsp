<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<style type="text/css">
#adding-friend-button {
	font-size: 15px;
	margin-bottom: 8px;
	padding: 5px 8px 5px 8px;
	margin-right: 5px;
}
</style>
</head>
<%
String url = request.getParameter("url");
String idUrl = request.getParameter("idUrl");
String currenUserId = request.getParameter("currenUserId");
String currentUserName = request.getParameter("currentUserName");
String currentUserAvatar = request.getParameter("currentUserAvatar");
String userPageName = request.getParameter("userPageName");
String userPageAvatar = request.getParameter("userPageAvatar");
%>
<body>
	<%
	if (idUrl.equals("null")) {
	%>
	<div class="profile_setting">
		<div class="setting_btn">
			<a href="#" class="add_story"> <i class="fa-solid fa-plus"></i>
				Thêm vào tin
			</a> <a href="<%=url%>/jsp/InfoChange.jsp" class="setting"> <i
				class="fa-solid fa-pen"></i> Chỉnh sửa trang cá nhân
			</a>
		</div>
		<div class="setting_more">
			<a href="#"><i class="fa-solid fa-chevron-down"></i></a>
		</div>
	</div>

	<%
	} else {
	%>
	<div class="profile_setting">
		<div class="setting_btn">
			<form action="<%=url%>/Friend" method="post">
				<input type="hidden" name="userId" value="<%=idUrl%>"> <input
					type="hidden" name="userSentRequestId" value="<%=currenUserId%>"><input
					type="hidden" name="fullName" value="<%=currentUserName%>">
				<input type="hidden" name="avatar" value="<%=currentUserAvatar%>">
				<input type="hidden" name="userPageName" value="<%=userPageName%>" />
				<input type="hidden" name="userPageAvatar" value="<%=userPageAvatar%>" /> <input
					type="hidden" name="method" value="proccess-adding-friend">
				<button type="submit" class="btn btn-primary"
					id="adding-friend-button">
					<i class="fas fa-user-friends"></i> Thêm bạn bè
				</button>
				<a href="<%=url%>/jsp/InfoChange.jsp" class="setting"> <i
					class="fab fa-facebook-messenger"></i> Nhắn tin
				</a>
			</form>
		</div>
		<div class="setting_more">
			<a href="#"><i class="fa-solid fa-chevron-down"></i></a>
		</div>
	</div>
	<%
	}
	%>
</body>
</html>