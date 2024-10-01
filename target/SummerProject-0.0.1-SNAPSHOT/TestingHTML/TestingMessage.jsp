<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>	
</head>
<body>
	<form id="myForm" action="../jsp/BodyBookChatInterface.jsp" method="get">
		<input id="myInput" type="text" name="username">
	</form>	

</body>
<script type="text/javascript">	
	document.getElementById("myInput").addEventListener("keypress", (event) => {
		if(event.key == "Enter")
		{
			document.getElementById("myForm").submit();
		}
		})
</script>
</html>