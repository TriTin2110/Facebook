<%@page import="Model.User"%>
<%@page import="DAO.UserDAO"%>
<%@page import="DAO.UserInformationDAO"%>
<%@page import="Model.UserInformation"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath();

User user = (User) session.getAttribute("user");
UserInformation userInformation = null;
boolean gender = false;
if(user == null)
{
	response.sendRedirect(url+"/jsp/LoginPage.jsp");
}
else
{
	userInformation = user.getUserInformation();
	if (userInformation.isGender()!=null && userInformation.isGender())
		gender = true;
	%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Account Center</title>
<!-- Bootstrap 5 CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" />
<link rel="stylesheet" href="<%=url%>/css/infochange.css">
<link rel="stylesheet" href="<%=url%>/css/base.css">
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<!-- Sidebar -->
			<nav class="col-md-3 col-lg-2 bg-light sidebar">
				<div class="position-sticky">
					<h4 class="m-1">BodyBook</h4>
					<ul class="nav flex-column">
						<li class="nav-item"><a class="nav-link" href="<%=url%>/jsp/Profile.jsp"> Trang
								cá nhân </a></li>
						<li class="nav-item"><a class="nav-link" href="#"> Mật
								khẩu và bảo mật </a></li>
						<li class="nav-item"><a class="nav-link active" href="#">
								Thông tin cá nhân </a></li>
						<li class="nav-item"><a class="nav-link" href="#"> Tài
								khoản </a></li>
					</ul>
				</div>
			</nav>

			<!-- Main content -->
			<main
				class="col-md-9 col-lg-9 justify-content-end align-items-center content">
				<h3 class="content__title">Thông tin cá nhân</h3>
				<p class="content__description">Meta sử dụng thông tin này để
					xác minh danh tính của bạn và bảo vệ cộng đồng của chúng tôi. Bạn
					là người quyết định những thông tin cá nhân nào sẽ hiển thị với
					người khác.</p>
				<ul class="d-block list-group list-group-flush content__list">
					<li
						class="list-group-item content__item d-flex justify-content-between align-items-center">
						<div>
							<strong class="item__title">Thông tin liên hệ:</strong>
							<p class="item__info"><%=userInformation.getFullName()%></p>
							<p class="item__info"><%=userInformation.getPhoneNumber()%></p>
							<p class="item__info"><%=userInformation.getHomeTown()%></p>
						</div>
						<button class="btn btn-sm" data-bs-toggle="modal"
							data-bs-target="#contactModal">Sửa</button>
					</li>
					<li
						class="list-group-item content__item d-flex justify-content-between align-items-center">
						<div>
							<strong class="item__title">Ngày sinh:</strong>
							<p class="item__info"><%=userInformation.getDateOfBirth() %></p>
						</div>
						<button class="btn btn-sm" data-bs-toggle="modal"
							data-bs-target="#dobModal">Sửa</button>
					</li>
					<li
						class="list-group-item content__item d-flex justify-content-between align-items-center">
						<div>
							<strong class="item__title">Xác nhận danh tính:</strong>
							<p class="item__info mb-0">
								<i class="fab fa-facebook"></i>
							</p>
						</div>
						<button class="btn btn-sm" data-bs-toggle="modal"
							data-bs-target="#identityModal">Sửa</button>
					</li>
					<li
						class="list-group-item content__item d-flex justify-content-between align-items-center">
						<div>
							<strong class="item__title">Quyền sở hữu và kiểm soát
								tài khoản:</strong>
							<p class="item__info">Quản lý dữ liệu, sửa đổi người liên hệ
								thừa kế, vô hiệu hóa hoặc xóa tài khoản và trang cá nhân của
								bạn.</p>
						</div>
						<button class="btn btn-sm" data-bs-toggle="modal"
							data-bs-target="#controlModal">Sửa</button>
					</li>
				</ul>
			</main>
		</div>
	</div>

	<!-- Modals -->
	<!-- Contact Modal -->
	<div class="modal fade" id="contactModal" tabindex="-1"
		aria-labelledby="contactModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="contactModalLabel">Sửa thông tin
						liên hệ</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form action="<%=url%>/ChangeInformation" method="post">
						<input type="text" value="GeneralInformation" name="update"
							style="display: none;">
						<div class="mb-3">
							<label for="newName" class="form-label">Họ và tên:</label> <input
								type="text" class="form-control"
								value="<%=userInformation.getFullName()%>" id="name"
								name="name">
						</div>
						<div class="mb-3">
							<label class="form-label">Giới tính:</label>
							<%

							%>
							<input type="radio" style="width: auto;" id="gender"
								name="gender" value="Nam" <%if (gender) {%> checked="checked"
								<%}%>>Nam <input type="radio" style="width: auto;"
								id="gender" name="gender" value="Nữ" <%if (!gender) {%>
								checked="checked" <%}%>>Nữ
						</div>
						<div class="mb-3">
							<label for="newName" class="form-label">Quê quán:</label> <input
								type="text" class="form-control" name="home-town" id="home-town"
								value="<%=userInformation.getHomeTown()%>">
						</div>
						<div class="mb-3">
							<label for="newPhone" class="form-label">Số điện thoại:</label> <input
								type="number" class="form-control" name="phone" id="newPhone"
								value="<%=userInformation.getPhoneNumber()%>">
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-bs-dismiss="modal">Đóng</button>
							<button type="submit" class="btn" id="save">Lưu</button>
						</div>
					</form>
				</div>

			</div>
		</div>
	</div>

	<!-- DOB Modal -->
	<div class="modal fade" id="dobModal" tabindex="-1"
		aria-labelledby="dobModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="dobModalLabel">Sửa ngày sinh</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form action="<%=url%>/ChangeInformation" method="post">
						<input value="DOB" name="update" style="display: none;">
						<div class="mb-3">
							<label for="newDob" class="form-label">Nhập ngày sinh
								mới:</label> <input type="date" class="form-control" id="newDob" name="dob" value="<%=userInformation.getDateOfBirth()%>">
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-bs-dismiss="modal">Đóng</button>
							<button type="submit" class="btn" id="save">Lưu</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- Identity Modal -->
	<div class="modal fade" id="identityModal" tabindex="-1"
		aria-labelledby="identityModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="identityModalLabel">Sửa xác nhận
						danh tính</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<!-- Identity form content -->
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Đóng</button>
					<button type="button" class="btn" id="save">Lưu</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Control Modal -->
	<div class="modal fade" id="controlModal" tabindex="-1"
		aria-labelledby="controlModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="controlModalLabel">Sửa quyền sở
						hữu và kiểm soát tài khoản</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<!-- Control form content -->
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Đóng</button>
					<button type="button" class="btn" id="save">Lưu</button>
				</div>
			</div>
		</div>
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
	<script src="https://kit.fontawesome.com/a076d05399.js"></script>
</body>
<%
	String notice = (String) request.getAttribute("notice");
	if(notice != null)
	{
		%>
			<script type="text/javascript">
				alert("<%=notice%>");
			</script>
		<%
	}
%>
</html>
	<%
}
%>