<%@page import="DAO.UserDAO"%>
<%@page import="Model.UserInformation"%>
<%@page import="Model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="../css/Profile.css" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" />
<title>Trang cá nhân</title>
</head>
<%@include file="../component/AuthenticateUser.jsp"%>
<%
String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath();
UserInformation profileInformation = user.getUserInformation();
User currentUser = (User) request.getSession().getAttribute("user");
String idUrl = request.getParameter("userId") + "";
%>
<body>
	<div class="app">
		<jsp:include page="/component/Header.jsp"></jsp:include>
		<div class="header">
			<div class="cover_photo">
				<img src="../img/anhbia.jpg" alt="" /> <a href="#"
					class="update_cover_photo"> <i class="fa-solid fa-camera"></i>
					Chỉnh sửa ảnh bìa
				</a>
			</div>
			<div class="avt_info">
				<div class="img_info">
					<div class="avt_img">
						<img src="../img/<%=user.getAvatar()%>" alt="" />
					</div>
					<div class="info">
						<h1 class="name"><%=profileInformation.getFullName()%></h1>
						<a href="#" class="number_friend">102 người bạn</a>
					</div>
				</div>

				<%
				if (currentUser.getUserId().equals(idUrl)) {
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
						<a href="#" class="add_story"> <i class="fas fa-user-friends"></i>
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
			</div>
			<div class="avt_list_btn">
				<ul class="avt_list">
					<li class="avt_item"><a href="#" class="avt_link">Bài viết</a>
					</li>
					<li class="avt_item"><a href="#" class="avt_link">Giới
							thiệu</a></li>
					<li class="avt_item"><a href="#" class="avt_link">Bạn bè</a></li>
					<li class="avt_item"><a href="#" class="avt_link">Ảnh</a></li>
					<li class="avt_item"><a href="#" class="avt_link">Video</a></li>
					<li class="avt_item"><a href="#" class="avt_link">Reels</a></li>
					<li class="avt_item"><a href="#" class="avt_link"> Xem
							thêm <i class="fa-solid fa-chevron-down"></i>
					</a></li>
				</ul>
				<div class="setting_more">
					<a href="#"><i class="fa-solid fa-ellipsis"></i></a>
				</div>
			</div>
		</div>
		<div class="body">
			<div class="section_left">
				<div class="introduce">
					<h2>Giới thiệu</h2>
					<a href="#" class="intro_btn">Thêm tiểu sử</a>
					<ul>
						<li><i class="fa-solid fa-house-chimney"></i> <span>Sống
								tại <b><%=profileInformation.getHomeTown()%></b>
						</span></li>
						<li><i class="fa-solid fa-heart"></i> <span>Đang hẹn
								hò với <b>Lisa</b>
						</span></li>
						<li><i class="fa-solid fa-clock"></i> <span>Tham gia
								vào tháng 6 năm 2017</span></li>
					</ul>
					<a href="#" class="intro_btn">Chỉnh sửa chi tiết</a> <a href="#"
						class="intro_btn">Thêm nội dung đáng chú ý</a>
				</div>
				<div class="list_img"></div>
				<div class="list_friend"></div>
			</div>
			<div class="section_right">
				<div class="status">
					<form action="<%=url%>/posting" method="POST"
						enctype="multipart/form-data">
						<textarea name="content" placeholder="Bạn đang nghĩ gì?"></textarea>
						<input type="file" name="image">
						<button type="submit">Đăng bài</button>
					</form>
				</div>
				<div id="postList">
					<!-- Danh sách bài viết sẽ được thêm vào đây -->
				</div>
				<jsp:include page="/component/PostContent.jsp"></jsp:include>
			</div>
		</div>
	</div>

	<!-- Modal -->
	<div class="modal" id="myModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">Tạo bài viết</h4>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>

				<!-- Modal body -->
				<div class="modal-body">
					<form action="posting" method="POST" enctype="multipart/form-data">
						<div class="acc__post">
							<img src="../img/avt.jpg" alt="">
							<div class="acc__name">
								<h4><%=profileInformation.getFullName()%></h4>
								<select name="aithay" id="user_seen">
									<option value="congkhai">Công khai</option>
									<option value="banbe">Bạn bè</option>
									<option value="chiminhtoi">Chỉ mình tôi</option>
								</select>
							</div>
						</div>
						<div class="content__post">
							<textarea name="content" id="content_area"
								placeholder="<%=profileInformation.getFullName()%>, bạn đang nghĩ gì thế?"></textarea>
						</div>
						<div class="upload__post">
							<input type="file" id="fileInput" name="image">
							<button type="submit" id="postButton">Đăng</button>
						</div>
					</form>
				</div>

			</div>
		</div>
	</div>
	</div>
</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
	crossorigin="anonymous"></script>

<script>
$(document).ready(function() {
    $('form').on('submit', function(e) {
        e.preventDefault();
        var formData = new FormData(this);

        $.ajax({
            url: '<%=url%>
	/posting',
						type : 'POST',
						data : formData,
						success : function(response) {
							alert('Bài viết đã được đăng thành công!');
							loadPosts();
							// Reset form sau khi đăng bài thành công
							$('form')[0].reset();
						},
						error : function(xhr, status, error) {
							alert('Có lỗi xảy ra: ' + error);
						},
						cache : false,
						contentType : false,
						processData : false
					});
				});

				// Hàm để escape HTML để ngăn chặn XSS
				function escapeHtml(unsafe) {
					return unsafe.replace(/&/g, "&amp;").replace(/</g, "&lt;")
							.replace(/>/g, "&gt;").replace(/"/g, "&quot;")
							.replace(/'/g, "&#039;");
				}

				loadPosts(); // Load posts when page loads
			});
</script>
<script src="<%=url%>/js/Profile.js" type="text/javascript"></script>
</html>
