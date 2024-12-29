<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%
	String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
	String avatar = request.getParameter("avatar");
	String fullName = request.getParameter("fullname");
%>
<body>
<div class="status">
					<div class="post-input">
						<img src="<%=url%>/img/<%=avatar%>" alt=""/> <span
							type="button" class="btn btn-primary" data-bs-toggle="modal"
							data-bs-target="#myModal"><%=fullName%>
							ơi, bạn đang nghĩ gì thế?</span>
					</div>
					<div class="post-btn">
						<ul>
							<li><i class="fa-solid fa-video"></i> <span>Video
									trực tiếp</span></li>
							<li><i class="fa-solid fa-photo-film"></i> <span>Ảnh/video</span>
							</li>
							<li><i class="fa-regular fa-face-smile"></i> <span>Cảm
									xúc/hoạt động</span></li>
						</ul>
					</div>
				</div>
</body>
</html>