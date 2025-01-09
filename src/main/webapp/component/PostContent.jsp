<%@page import="java.util.Locale"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="Model.User"%>
<%@page import="Model.Post"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%
String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath();
String pattern = "EEE, dd MMMM yyyy";
Locale locale = new Locale("vi", "vn");
SimpleDateFormat df = new SimpleDateFormat(pattern, locale);
User user = (User) request.getAttribute("postContent");
List<Post> posts = user.getListPost();
%>
<body>
	<%
	if (posts.isEmpty()) {
	%>
	<h3 style="text-align: center; color: gray;">Bạn bạn chưa có bài
		viết nào</h3>
	<%
	} else {
	for (Post post : posts) {
	%>
	<div class="post_content">
		<div class="header_post">
			<img src="<%=url%>/img/<%=user.getAvatar()%>" alt="">
			<div class="name-acc_post">
				<h4><%=user.getUserInformation().getFullName()%></h4>
				<span><%=df.format(post.getCreatedAt())%></span>
			</div>
		</div>
		<div class="caption_post">
			<span><%=post.getPostContent()%></span>
		</div>
		<%
		if (post.getPostImage() != null) {
		%>
		<div class="content_post">
			<img src="<%=post.getPostImage()%>" alt="">
		</div>
		<%
		}
		%>

		<div class="interact_post">
			<div class="number_react">
				<i class="fa-solid fa-thumbs-up color_like"></i> <i
					class="fa-solid fa-heart color_heart"></i> <a href="#"> <span><%=post.getPostInteract()%></span>
				</a>
			</div>
			<div class="cmt-share">
				<a href="#"> <span>13</span> <i
					class="fa-solid fa-comment color_cmt"></i> <span><%=post.getPostCommentList().size()%></span>
					<i class="fa-solid fa-share color_cmt"><%=post.getPostShare()%></i>
				</a>
			</div>
		</div>
		<div class="interact_btn">
			<ul>
				<li><jsp:include page="/component/index/Emotion.jsp">
						<jsp:param value="<%=url%>" name="url" />
					</jsp:include></li>
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
	<%
	}
	}
	%>

</body>
</html>