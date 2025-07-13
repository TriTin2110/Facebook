<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

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
boolean isSentRequest = Boolean.parseBoolean(request.getParameter("isSentRequest"));
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
		<%
			if(!isSentRequest)
			{
				%>
					<a class="btn btn-primary" href="<%=url%>/Friend?method=proccess-adding-friend&userId=<%=idUrl%>">Thêm bạn bè</a>	
				<%
			}
			else{
				%>
					<a class="btn btn-secondary" href="<%=url%>/Friend">Hủy lời mời</a>
				<%
			}
		%>
			
			<a href="<%=url%>/jsp/InfoChange.jsp" class="setting"> <i
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
