<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<%
	String url = request.getParameter("url");
String idUrl = request.getParameter("idUrl");
String currenUserId = request.getParameter("currenUserId");
%>
<body>
<%
				if (currenUserId.equals(idUrl)) {
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
						<a href="<%=url%>/Friend?method=add&userId=<%=idUrl%>" class="add_story"> <i class="fas fa-user-friends"></i>
							Thêm bạn bè
						</a> <a href="<%=url%>/jsp/InfoChange.jsp" class="setting"> <i
							class="fab fa-facebook-messenger"></i> Nhắn tin
						</a>
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