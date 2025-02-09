<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%
	String url = request.getParameter("url");
%>
<body>
	<div class="button-react">
		<i class="fa-solid fa-thumbs-up icon_like"></i>
		<button class="like-button">Thích</button>
		<div class="emotions">
			<span> <i class="fa-solid fa-heart-pulse color_heart emotion"
				data-emotion="Rung động"></i>
			</span> <span> <i class="fa-solid fa-face-flushed color_face emotion"
				data-emotion="Rìa lí man"></i>
			</span>
			<span> <i
				class="fa-solid fa-face-grin-tongue-squint color_face emotion"
				data-emotion="Há há"></i> </span> <span"> <i
				class="fa-solid fa-face-sad-cry color_face emotion"
				data-emotion="Suyyy"></i>
			</span> <span> <i class="fa-solid fa-face-angry color_heart emotion"
				data-emotion="Quạo á"></i>
			</span>
		</div>
	</div>
</body>

<script src="<%=url + "/javascript/index.js"%>" type="text/javascript">
	</script>
</html>