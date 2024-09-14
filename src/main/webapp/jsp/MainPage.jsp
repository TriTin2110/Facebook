<%@page import="DAO.UserDAO"%>
<%@page import="Model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath();

%>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="<%=url%>/css/MainPage.css" />
<link rel="stylesheet" href="<%=url%>/css/responsive.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" />

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
	crossorigin="anonymous"></script>
<title>BodyBook</title>
</head>
<body>
	<div class="app">
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
							src="<%=url%>/img/avt.jpg" alt="" id="open-profile" />
							<div class="acc_more">
								<ul>
									<li><a href="<%=url%>/jsp/Profile.jsp"> <img src="<%=url%>/img/avt.jpg" alt="">
											<h3>Chí Nguyên</h3>
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
									<li><a href="<%=url%>/html/LoginPage.jsp"> <i
											class="fa-solid fa-right-from-bracket"></i>
											<h4>Đăng xuất</h4>
									</a></li>
								</ul>
							</div>
					</a></li>
				</ul>
			</div>
		</div>
		<div class="body">
			<div class="section-left hide-on-mobile-tablet">
				<div class="left-top">
					<ul>
						<li><a href="#"><img
								src="<%=url%>/img/avt.jpg" alt="" />
								<h5>Chí Nguyên</h5> </a></li>
						<li><a href="#"><i
								class="fa-solid fa-user-group"></i>
								<h5>Bạn bè</h5> </a></li>
						<li><a href="#"><i
								class="fa-solid fa-heart"></i>
								<h5>Kỷ niệm</h5> </a></li>
						<li><a href="#"><i
								class="fa-solid fa-bookmark"></i>
								<h5>Đã lưu</h5> </a></li>
						<li><a href="#"><i
								class="fa-solid fa-people-group"></i>
								<h5>Nhóm</h5> </a></li>
						<li><a href="#"><i
								class="fa-solid fa-video"></i>
								<h5>Video</h5> </a></li>
						<li><a href="#"><i
								class="fa-solid fa-caret-down"></i>
								<h5>Xem thêm</h5> </a></li>
					</ul>
				</div>
				<div class="left-bot">
					<h4>Lối tắt của bạn</h4>
					<ul>
						<li><a href="#"> <img src="<%=url%>/img/group1.jpg"
								alt="" />
								<h5>Đảo mèo</h5>
						</a></li>
						<li><a href="#"> <img src="<%=url%>/img/group2.jpg"
								alt="" />
								<h5>Đảo chó</h5>
						</a></li>
						<li><a href="#"> <img src="<%=url%>/img/group3.jpg"
								alt="" />
								<h5>Cộng đồng lập trình</h5>
						</a></li>
						<li><a href="#"> <img src="<%=url%>/img/group4.jpg"
								alt="" />
								<h5>Sinh viên 2k3</h5>
						</a></li>
						<li><a href="#"> <img src="<%=url%>/img/group5.jpg"
								alt="" />
								<h5>Cộng đồng sinh viên IUH</h5>
						</a></li>
						<li><a href="#"><i
								class="fa-solid fa-caret-down"></i>
								<h5>Xem thêm</h5> </a></li>
					</ul>
				</div>
			</div>
			<div class="post">
				<div class="post_list">
					<div class="story">
						<img src="<%=url%>/img/story0.jpg" alt="" /> <img
							src="<%=url%>/img/story1.jpg" alt="" /> <img
							src="<%=url%>/img/story2.jpg" alt="" /> <img
							src="<%=url%>/img/story3.jpg" alt="" /> <img
							src="<%=url%>/img/story0.jpg" alt="" /> <img
							src="<%=url%>/img/story1.jpg" alt="" /> <img
							src="<%=url%>/img/story2.jpg" alt="" /> <img
							src="<%=url%>/img/story3.jpg" alt="" />
					</div>
					<button id="moveLeftButton">
						<i class="fa-solid fa-angle-right"></i>
					</button>
				</div>
				<div class="status">
					<div class="post-input">
						<img src="<%=url%>/img/avt.jpg" alt="" /> <span type="button"
							class="btn btn-primary" data-bs-toggle="modal"
							data-bs-target="#myModal">Nguyên ơi, bạn
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
						<img src="<%=url%>/img/avt.jpg" alt="">
						<div class="name-acc_post">
							<h4>Chí Nguyên</h4>
							<span>3 phút</span>
						</div>
					</div>
					<div class="caption_post">
						<span>Hello World</span>
					</div>
					<div class="content_post">
						<img src="<%=url%>/img/post.jpg" alt="">
					</div>
					<div class="interact_post">
						<div class="number_react">
							<i class="fa-solid fa-thumbs-up color_like"></i> <i
								class="fa-solid fa-heart color_heart"></i> <a href="#"> <span>20.230</span>
							</a>
						</div>
						<div class="cmt-share">
							<a href="#"> <span>13.232</span> <i
								class="fa-solid fa-comment color_cmt"></i> <span>12.234</span> <i
								class="fa-solid fa-share color_cmt"></i>
							</a>
						</div>
					</div>
					<div class="interact_btn">
						<ul>
							<li><i class="fa-solid fa-thumbs-up"></i>
								<h4>Thích</h4></li>
							<li><i class="fa-solid fa-comment"></i>
								<h4>Bình luận</h4></li>
							<li><i class="fa-solid fa-paper-plane"></i>
								<h4>Gửi</h4></li>
							<li><i class="fa-solid fa-share"></i>
								<h4>Chia sẻ</h4></li>
						</ul>
					</div>
				</div>
				<div class="post_content">
					<div class="header_post">
						<img src="<%=url%>/img/avt.jpg" alt="">
						<div class="name-acc_post">
							<h4>Chí Nguyên</h4>
							<span>2 giờ</span>
						</div>
					</div>
					<div class="caption_post">
						<span>My Love</span>
					</div>
					<div class="content_post">
						<img src="<%=url%>/img/post2.jpg" alt="">
					</div>
					<div class="interact_post">
						<div class="number_react">
							<i class="fa-solid fa-thumbs-up color_like"></i> <i
								class="fa-solid fa-heart color_heart"></i> <a href="#"> <span>12.232</span>
							</a>
						</div>
						<div class="cmt-share">
							<a href="#"> <span>2.232</span> <i
								class="fa-solid fa-comment color_cmt"></i> <span>3.234</span> <i
								class="fa-solid fa-share color_cmt"></i>
							</a>
						</div>
					</div>
					<div class="interact_btn">
						<ul>
							<li><i class="fa-solid fa-thumbs-up"></i>
								<h4>Thích</h4></li>
							<li><i class="fa-solid fa-comment"></i>
								<h4>Bình luận</h4></li>
							<li><i class="fa-solid fa-paper-plane"></i>
								<h4>Gửi</h4></li>
							<li><i class="fa-solid fa-share"></i>
								<h4>Chia sẻ</h4></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="section-right hide-on-mobile-tablet">
				<div class="advertise">
					<h4>Được tài trợ</h4>
					<ul>
						<li><a href="#"> <img src="<%=url%>/img/anhqc.jpg" alt="" />
								<div class="des">
									<h5>Đặt Xe Ngay</h5>
									<h6>xanhsm.com</h6>
								</div>
						</a></li>
						<li><a href="#"> <img src="<%=url%>/img/anhqc2.jpg"
								alt="" />
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
					<div class="newfriend">
						<img src="<%=url%>/img/j97.jpg" alt="" class="nf-avt" />
						<div class="nf-information">
							<div class="name-acpt">
								<h5>Anh Dắc 97</h5>
								<a href="#">Xác nhận</a>
							</div>
							<div class="time-refuse">
								<h6>32 phút</h6>
								<a href="#">Xóa</a>
							</div>
						</div>
					</div>
				</div>
				<div class="friend">
					<h4>Người liên hệ</h4>
					<ul>
						<li><img src="<%=url%>/img/friend1.jpg" alt="" /> <span
							class="name-friend">Đặng Thiện</span></li>
						<li><img src="<%=url%>/img/friend2.jpg" alt="" /> <span
							class="name-friend">Trí Tín</span></li>
						<li><img src="<%=url%>/img/friend3.jpg" alt="" /> <span
							class="name-friend">Minh Triết</span></li>
					</ul>
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
					<div class="acc__post">
						<img src="<%=url%>/img/avt.jpg" alt="">
						<div class="acc__name">
							<h4>Chí Nguyên</h4>
							<select name="aithay" id="user_seen">
								<option value="congkhai">Công khai</option>
								<option value="banbe">Bạn bè</option>
								<option value="chiminhtoi">Chỉ mình tôi</option>
							</select>
						</div>
					</div>
					<div class="content__post">
						<textarea name="noidungpost" id="content_area"
							placeholder="Nguyên ơi, bạn đang nghĩ gì thế?"></textarea>
					</div>
					<div class="upload__post">
						<button type="submit">Đăng</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script>
		// MỞ THÔNG TIN THÊM KHI NHẤN VÀO ẢNH AVT
		var open = document.getElementById("open-profile");
		var moreProfile = document.querySelector(".acc_more");
		open.onclick = function(e) {
			e.preventDefault();
			if (moreProfile.style.display == 'none') {
				moreProfile.style.display = 'block';
			} else {
				moreProfile.style.display = 'none';
			}
		}

		// CHUYỂN ẢNH GIỮA CÁC STORY
		document.addEventListener('DOMContentLoaded', function() {
			const moveLeftButton = document.getElementById('moveLeftButton');
			const imageList = document.querySelector('.story');
			const images = document.querySelectorAll('.story img');
			const imageWidth = images[0].offsetWidth + 10; // Kích thước ảnh + margin
			let currentIndex = 0;

			moveLeftButton.addEventListener('click', function() {
				if (currentIndex < images.length - 4) { // Số lượng ảnh hiển thị trong khung là 6
					currentIndex++;
					imageList.style.transform = "translateX(-"
							+ (currentIndex * imageWidth) + "px)";
				}
			});
		});
	</script>
</body>
</html>
