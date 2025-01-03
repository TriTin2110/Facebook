<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Insert title here</title>
    <link rel="stylesheet" href="../css/EmailConfirmSuccess.css" />
  </head>
  <%
  	String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
	+ request.getContextPath(); 
  %>
  <body>
    <div class="container">
      <div class="message-box">
        <h1 class="success-message">Bạn đã xác minh email thành công!</h1>
        <p class="redirect-message">
          Trang sẽ tự động chuyển hướng sau <span id="count">5</span> giây nữa!
        </p>
      </div>
    </div>
  </body>

  <script type="text/javascript">
    document.getElementById("count").innerHTML = 5;
    var count = 4;
    setInterval(() => {
    	if (count == -1) {
            window.location.href="<%=url%>";
          }
    	else {
    		 document.getElementById("count").innerHTML = count;
		}
      count--;
    }, 1000);
  </script>
</html>
