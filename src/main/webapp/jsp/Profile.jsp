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
<%
String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath();
Object userId = session.getAttribute("userId");
Object profileID = request.getParameter("profileId");
if (userId == null) {
	response.sendRedirect(url+"/jsp/LoginPage.jsp");
} else {
	UserDAO userDAO = new UserDAO();
	User profile = new User();
	profile.setUserId(userId.toString());
	profile = userDAO.selectById(profile);
	if (profile == null) {
		response.sendRedirect(url+"/jsp/LoginPage.jsp");
	} else {
		UserInformation profileInformation = profile.getUserInformation();
%>
<body>
	<div class="app">
		<div class="nav">
			<div class="logo-search">
				<div class="logo">
					<a href="../html/MainPage.html">B</a>
				</div>
				<div class="search">
					<input type="text" name="search"
						placeholder="Tìm kiếm trên BodyBook" /> <i
						class="fa-solid fa-magnifying-glass"></i>
				</div>
			</div>
			<div class="nav-list">
				<ul>
					<li><a href=""><i class="fa-solid fa-house"></i></a></li>
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
							src="../img/avt.jpg" alt="" id="open-profile" />
							<div class="acc_more">
								<ul>
									<li><a href="<%=url%>/jsp/Profile.jsp"> <img
											src="../img/avt.jpg" alt="" />
											<h3><%=profileInformation.getFullName()%></h3>
									</a></li>
									<li><a href="../html/infoChange.html"> <i
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
									<li><a href="../html/LoginPage.jsp"> <i
											class="fa-solid fa-right-from-bracket"></i>
											<h4>Đăng xuất</h4>
									</a></li>
								</ul>
							</div>
					</a></li>
				</ul>
			</div>
		</div>
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
						<img src="../img/avt_profile.jpg" alt="" />
					</div>
					<div class="info">
						<h1 class="name"><%=profileInformation.getFullName() %></h1>
						<a href="#" class="number_friend">102 người bạn</a>
					</div>
				</div>
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
								tại <b><%=profileInformation.getHomeTown() %></b>
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
					<div class="post-input">
						<img src="../img/avt.jpg" alt="" /> <span type="button"
							class="btn btn-primary" data-bs-toggle="modal"
							data-bs-target="#myModal"><%=profileInformation.getFullName()%> ơi, bạn
							đang nghĩ gì thế?</span>
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
				<div class="post_content">
					<div class="header_post">
						<img src="../img/avt.jpg" alt="">
						<div class="name-acc_post">
							<h4><%=profileInformation.getFullName() %></h4>
							<span>3 phút</span>
						</div>
					</div>
					<div class="caption_post">
						<span>Hello World</span>
					</div>
					<div class="content_post">
						<img src="../img/post.jpg" alt="">
					</div>
					<div class="interact_post">
						<div class="number_react">
							<i class="fa-solid fa-thumbs-up color_like"></i> <i
								class="fa-solid fa-heart color_heart"></i> <a href="#"> <span>20</span>
							</a>
						</div>
						<div class="cmt-share">
							<a href="#"> <span>13</span> <i
								class="fa-solid fa-comment color_cmt"></i> <span>12</span> <i
								class="fa-solid fa-share color_cmt"></i>
							</a>
						</div>
					</div>
					<div class="interact_btn">
						<ul>
							<li>
								<div class="button-react">
									<i class="fa-solid fa-thumbs-up icon_like"></i>
									<button class="like-button">Thích</button>
									<div class="emotions">
										<span> <i
											class="fa-solid fa-heart-pulse color_heart emotion"
											data-emotion="Rung động"></i>
										</span> <span> <i
											class="fa-solid fa-face-flushed color_face emotion"
											data-emotion="Rìa lí man"></i>
										</span> <spanhá">
                        <i class="fa-solid fa-face-grin-tongue-squint color_face emotion" data-emotion="Há há"></i>
                      </span>
                      <span ">
                        <i class="fa-solid fa-face-sad-cry color_face emotion" data-emotion="Suyyy"></i>
                      </span>
                      <span>
                        <i class="fa-solid fa-face-angry color_heart emotion" data-emotion="Quạo á"></i>
                      </span>
                  </div>
								</div>
							</li>
							<li>
								<div class="button-react">
									<i class="fa-solid fa-comment"></i>
									<button class="cmt-button">Bình luận</button>
								</div>
							</li>
							<li>
								<div class="button-react">
									<i class="fa-solid fa-paper-plane"></i>
									<button class="send-button">Gửi</button>
								</div>
							</li>
							<li>
								<div class="button-react">
									<i class="fa-solid fa-share"></i>
									<button class="share-button">Chia sẻ</button>
								</div>
							</li>
						</ul>
					</div>
				</div>
				<div class="post_content">
					<div class="header_post">
						<img src="../img/avt.jpg" alt="">
						<div class="name-acc_post">
							<h4><%=profileInformation.getFullName() %></h4>
							<span>2 giờ</span>
						</div>
					</div>
					<div class="caption_post">
						<span>My Love</span>
					</div>
					<div class="content_post">
						<img src="../img/post2.jpg" alt="">
					</div>
					<div class="interact_post">
						<div class="number_react">
							<i class="fa-solid fa-thumbs-up color_like"></i> <i
								class="fa-solid fa-heart color_heart"></i> <a href="#"> <span>12</span>
							</a>
						</div>
						<div class="cmt-share">
							<a href="#"> <span>26</span> <i
								class="fa-solid fa-comment color_cmt"></i> <span>3</span> <i
								class="fa-solid fa-share color_cmt"></i>
							</a>
						</div>
					</div>
					<div class="interact_btn">
						<ul>
							<li>
								<div class="button-react">
									<i class="fa-solid fa-thumbs-up icon_like"></i>
									<button class="like-button">Thích</button>
									<div class="emotions">
										<span> <i
											class="fa-solid fa-heart-pulse color_heart emotion emotion"
											data-emotion="Rung động"></i>
										</span> <span> <i
											class="fa-solid fa-face-flushed color_face emotion"
											data-emotion="Rìa lí man"></i>
										</span> <span> <i
											class="fa-solid fa-face-grin-tongue-squint color_face emotion"
											data-emotion="Há há"></i>
										</span> <span> <i
											class="fa-solid fa-face-sad-cry color_face emotion"
											data-emotion="Suyyy"></i>
										</span> <span> <i
											class="fa-solid fa-face-angry color_heart emotion"
											data-emotion="Quạo á"></i>
										</span>
									</div>
								</div>
							</li>
							<li>
								<div class="button-react">
									<i class="fa-solid fa-comment"></i>
									<button class="cmt-button">Bình luận</button>
								</div>
							</li>
							<li>
								<div class="button-react">
									<i class="fa-solid fa-paper-plane"></i>
									<button class="send-button">Gửi</button>
								</div>
							</li>
							<li>
								<div class="button-react">
									<i class="fa-solid fa-share"></i>
									<button class="share-button">Chia sẻ</button>
								</div>
							</li>
						</ul>
					</div>
				</div>
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
								<h4><%=profileInformation.getFullName() %></h4>
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

				<div id="postList">
					<!-- Danh sách bài viết sẽ được thêm vào đây -->
				</div>

			</div>
		</div>
	</div>
	</div>
</body>
<%
}

}
%>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
	crossorigin="anonymous"></script>

 </script>
 <script src="<%=url%>/js/Profile.js" type="text/javascript"></script>
</html>
