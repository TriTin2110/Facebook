<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="../css/LoginPage.css" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous" />
<link rel="stylesheet" href="../css/base.css" />
<title>Đăng nhập Facebook</title>
</head>

<body>
	<section class="vh-100 d-flex justify-content-end align-items-center">
		<div class="container">
			<div class="row justify-content-center align-items-center">
				<div class="col-md-6">
					<div class="container-left text-center">
						<h1 class="logo">BodyBook</h1>
						<h3 class="description">Bodybook giúp bạn kết nối và chia sẻ
							với mọi người trong cuộc sống của của bạn.</h3>
					</div>
				</div>
				<div class="col-md-6">
					<div class="container-right">
						<div class="card shadow-2-strong">
							<div class="card__body p-5 text-center">
								<h3 class="form__title mb-5">Đăng nhập</h3>
								<form class="form">
									<div class="form__group form-outline mb-4">
										<input type="email" id="typeEmail"
											class="form__input form-control"
											placeholder="Email hoặc số điện thoại" required />
									</div>
									<div class="form__group form-outline mb-4">
										<input type="password" id="typePassword"
											class="form__input form-control" placeholder="Mật khẩu"
											required />
									</div>
									<div
										class="form__check form-check d-flex justify-content-start mb-4">
										<input class="form__check-input form-check-input"
											type="checkbox" value="" id="rememberMe" />
										<label class="form__check-label form-check-label"
											for="rememberMe"> Lưu mật
											khẩu </label>
									</div>
									<button class="form__submit btn btn-primary btn-block mb-4"
										style="font-size: 20px" type="submit">
										Đăng nhập</button>
									<a href="#" class="form__forgot-password">Quên mật khẩu?</a>
									<hr class="form__separator my-4" />
									<button
										class="form__create-account btn btn-lg btn-block btn-primary mb-2"
										id="creat-account" type="button" data-bs-toggle="modal"
										data-bs-target="#myModal">Tạo
										tài khoản mới</button>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="footer">
				<p>&copy; 2024 Facebook</p>
			</div>
		</div>
	</section>

	<!-- The Modal -->
	<div class="modal fade" id="myModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<div class="title">
						<h2 class="modal-title">Đăng ký</h2>
						<h6 class="title-des">Nhanh chóng và dễ dàng.</h6>
					</div>
					<button type="button" class="close" data-bs-dismiss="modal">
						&times;</button>
				</div>

				<!-- Modal body -->
				<div class="modal-body">
					<form action="#">
						<div class="form-group">
							<input type="text" id="ho" class="name" name="ho" required
								placeholder="Họ" /> <input type="text"
								id="ten" class="name" name="ten" required placeholder="Tên" />
						</div>

						<div class="form-group">
							<input type="text" id="sdt" name="sdt" required
								placeholder="Số di động hoặc email" />
						</div>

						<div class="form-group">
							<input type="password" id="matkhau" name="matkhau" required
								placeholder="Mật khẩu mới" />
						</div>

						<h6 for="ngaysinh">Ngày sinh</h6>
							<div class="form-group">
							<select id="ngaysinh" name="ngaysinh" required>
							<%
								for(int i = 1; i <= 31; i++)
								{
									%><option value="<%=i%>"><%=i%></option><%
								}
							%>
								
							</select>
				
							<select id="thangsinh" name="thangsinh" onchange="checkingDay()"
								required>
								<%
								for (int i = 1; i <= 12; i++) {
								%>
								<option value="<%=i%>">Tháng
									<%=i%></option>
								<%
								}
								%>
							</select> <select id="namsinh" name="namsinh" onchange="checkingDay()" required>
								<%
								int year = new Date(System.currentTimeMillis()).getYear() + 1900;
								for (int i = 1960; i <= year; i++) {
								%>
								<option value="<%=i%>"><%=i%></option>
								<%
								}
								%>
							</select>
						</div>

						<h6 for="gioitinh">Giới tính</h6>
						<div class="form-group">
							<div class="form-group-1">
								<label for="nu">Nữ</label> <input type="radio" id="nu"
									name="gioitinh" value="nu" />
							</div>
							<div class="form-group-1">
								<label for="nam">Nam</label> <input type="radio" id="nam"
									name="gioitinh" value="nam" />
							</div>
						</div>

						<div>
							<h6>
								Những người dùng dịch vụ của chúng tôi có thể đã tải thông tin
								liên hệ của bạn lên Facebook. <a href="#">Tìm hiểu thêm.</a>
							</h6>
						</div>

						<div>
							<h6>
								Bằng cách nhấp vào Đăng ký, bạn đồng ý với <a href="#">Điều
									khoản</a>, <a href="#">Chính sách quyền riêng tư</a> và <a href="#">Chính
									sách cookie</a> của chúng tôi. Bạn có thể nhận được thông báo của
								chúng tôi qua SMS và hủy nhận bất kỳ lúc nào.
							</h6>
						</div>

						<button type="submit" class="btn-register">Đăng ký</button>
					</form>
				</div>

				<!-- Modal footer -->
			</div>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
</body>

<script type="text/javascript">
	function checkingDay() {
		document.getElementById("ngaysinh").length = 0;
		var ngay = 1;
		month = document.getElementById("thangsinh").value;
		year = document.getElementById("namsinh").value;
		switch (month) {
		case "1":
		case "3":
		case "5":
		case "7":
		case "8":
		case "10":
		case "12":
			ngay = 31;
		break;

		case "4":
		case "6":
		case "9":
		case "11":
			ngay = 30;
		break;

		case "2":
			year = parseInt(year);
			if(year % 4 == 0 && year % 100 != 0 || year % 400 ==0)
				ngay = 29;
			else {
				ngay = 28;
			}
		break;
		}

		
		for (var i = 1; i <= ngay; i++) {
			var option = document.createElement('option');
			option.value = i;
			option.innerHTML = i;
			document.getElementById("ngaysinh").appendChild(option);
		}
	}
</script>
</html>
