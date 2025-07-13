<%@page import="DAO.AnnounceDAO"%>
<%@page import="DAO.UserDAO"%>
<%@page import="Model.Announce"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.List"%>
<%@page import="Model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=url%>/css/MainPage.css" />
<link rel="stylesheet" href="<%=url%>/css/responsive.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" />
<link rel="stylesheet" type="text/css"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous" />
</head>
<%
List<String> searched = (List<String>) request.getSession().getAttribute("dataSearched");

User user = (User) request.getSession().getAttribute("user");

List<Announce> announces = user.getAnnounces();

announces.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
request.setAttribute("announces", announces);
request.setCharacterEncoding("UTF-8");
%>
<body>
	<div class="nav">
		<div class="logo-search">
			<div class="logo">
				<a href="<%=url%>">B</a>
			</div>

			<div class="search">
				<form action="<%=url%>/Friend" method="get" id="searching-form">
					<input type="hidden" name="method" value="search"> <input
						type="text" id="search" name="searchedData"
						placeholder="Tìm kiếm trên BodyBook" autocomplete="off" />
					<%
					if (!searched.isEmpty()) {
					%>
					<div class="dropdown">

						<ul>
							<%
							for (String item : searched) {
							%>
							<li><a href="<%=url%>/Friend?method=search&&searchedData=<%=item%>"
								class="dropdown-item"><%=item%></a></li>
							<%
							}
							%>
						</ul>
					</div>
					<%
					}
					%>
					<i class="fa-solid fa-arrow-left icon_left"></i> <i
						class="fa-solid fa-magnifying-glass icon_search"></i>
				</form>
			</div>
		</div>

		<div class="nav-list">
			<ul>
				<li><a href="<%=url%>"><i class="fa-solid fa-house"></i></a></li>
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
				<li class="nm_item"><a href="#" class="nm_link"
					id="open-notify"> <i class="nm_icon fa-solid fa-bell"></i>
						<div class="notify_more">
							<ul id="announce-list">
								<%for(Announce announce: announces){
									%>
									<li><%=announce.getMessage()%></li>
									<%
								} %>
							</ul>
						</div>
				</a></li>

				<li class="nm_item"><a href="" class="nm_link"> <img
						src="<%=url%>/img/<%=user.getAvatar()%>" id="open-profile" />
						<div class="acc_more">
							<ul>
								<li><a href="<%=url%>/jsp/Profile.jsp"> <img
										src="<%=url%>/img/<%=user.getAvatar()%>" alt="">
										<h3><%=user.getUserInformation().getFullName()%></h3>
								</a></li>
								<li><a href="<%=url%>/jsp/InfoChange.jsp"> <i
										class="fa-solid fa-gear"></i>
										<h4>Cài đặt & quyền riêng tư</h4>
								</a></li>
								<li><a href="#"> <i class="fa-solid fa-circle-question"></i>
										<h4>Trợ giúp & hỗ trợ</h4>
								</a></li>
								<li><a href="#"> <i class="fa-solid fa-moon"></i>
										<h4>Màn hình & trợ năng</h4>
								</a></li>
								<li><a href="#"> <i class="fa-solid fa-exclamation"></i>
										<h4>Đóng góp ý kiến</h4>
								</a></li>
								<li><a href="<%=url%>/jsp/LoginPage.jsp"> <i
										class="fa-solid fa-right-from-bracket"></i>
										<h4>Đăng xuất</h4>
								</a></li>
							</ul>
						</div>
				</a></li>
			</ul>
		</div>
	</div>
</body>
<script type="text/javascript" src="<%=url%>/javascript/Header.js"></script>
<script type="text/javascript">
	let announceList = document.getElementById("announce-list")
	var ws = new WebSocket("ws://localhost:8080/SummerProject/NotificationWebSocket")
	ws.onopen = function(message) {proccessOpen(message)};
	ws.onmessage = function(message) {proccessMessage(message)};
	ws.onclose = function(message) {proccessClose(message)};
	ws.onerror = function(message) {proccessError(message)};
	var userId = '<%=user.getUserId()%>'
	function proccessOpen(message)
	{
		console.log('Connect to server successfully')
		ws.send(userId)
	}
	function proccessMessage(message)
	{
		let data = message.data
		announceList.innerHTML = data
	}
	function proccessClose(message)
	{
	}
	function proccessError(message)
	{
	}
	
	</script>
</html>