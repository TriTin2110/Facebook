<!DOCTYPE html>
<html lang="en">
<%
String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath();
%>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>BodyBook Chat Interface</title>
<link rel="stylesheet" href="<%=url%>/css/messenger.css">
<link rel="stylesheet" href="<%=url%>/css/base.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"
	rel="stylesheet">

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>

<script type="text/javascript">
var websocket = new WebSocket("ws://localhost:8080/SummerProject/serverChatting");
websocket.onopen = function(message) {processOpen(message);};
websocket.onmessage = function(message) {processMessage(message);};
websocket.onclose = function(message) {processClose(message);};
websocket.onerror = function(message) {processError(message);};

function processOpen(message) {
    document.getElementById("outputChatting").innerHTML += "";
    //Thêm người dùng vào List<Session>
	websocket.send("<%=request.getParameter("username")%>");
}

function processMessage(message) {
	//Hiển thị danh sách người dùng
	if(message.data.includes("user-name: "))
	{
		var username = message.data.split(": ")[1];
		var otherUser = document.createElement("div");
		otherUser.innerHTML = returnOtherUserTag(username);
		otherUser.id = username;
		document.getElementById("userAvaiable").appendChild(otherUser);
	}
	else if(message.data.includes("remove-user:"))
	{
		var nameUserRemove = message.data.split(":")[1];
		var userRemove = document.getElementById(nameUserRemove);
		userRemove.parentNode.removeChild(userRemove);
	}
	else if(message.data == "clear-text")
	{
		document.getElementById("outputChatting").innerHTML = "";
	}
	else 
	{	
		document.getElementById("outputChatting").innerHTML+=message.data;
	}
}

function processClose(message) {
    document.getElementById("outputChatting").innerHTML += "";
}

function processError(message) {
    document.getElementById("outputChatting").innerHTML += "Có lỗi xảy ra\n";
}
</script>

<body>
	<div class="chat-app">
		<div class="chat-app__list">
			<h3 class="logo">BodyBook</h3>
			
			<!-- Danh sách user -->
			<div id="userAvaiable">

			</div>

		</div>

		<div class="chat-app__content">
		<!-- Nội dung chat -->
			<div class="chat-app__messages">
				<!-- Add more messages as needed -->
				<div id="outputChatting"></div>
			</div>
			
			<div class="chat-app__input">
				<img src="<%=url%>/img/friend2.jpg" alt="User Profile">
				<input type="text" class="form-control"
					placeholder="Type a message..." id="userInput">
				<button onclick="sendMessage()" type="submit" class="btn m-2">
					<i class="fas fa-paper-plane"></i>
				</button>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
document.getElementById("userInput").addEventListener("keypress", (event) => {
	if(event.key == "Enter")
	{
		sendMessage();
	}
	})
function sendMessage() {
    if(typeof websocket != 'undefined' && websocket.readyState == WebSocket.OPEN)
    {
  	  var textContent = document.getElementById("userInput").value;
  	  showSelfMessage();
        websocket.send(textContent);
        document.getElementById("userInput").value = "";
    }
}

function showSelfMessage() {
	var message = document.getElementById("userInput").value;
	message = message.replaceAll(";", " ");
	if(message.trim().length !==0)
		document.getElementById("outputChatting").innerHTML += "<div class=\"chat-app__message chat-app__message--receiver\">	<div class=\"chat-app__message-text chat-app__message-text--receiver\">"+message+"</div></div>";
}

function connectToUser(user) {
	//Clear toàn bộ cửa sổ message hiện tại
	document.getElementById("outputChatting").innerHTML = "";
	//Gửi tên của user muốn kết nối
	websocket.send(user);
}
function returnOtherUserTag(userName) {
	return "<div onclick=\"connectToUser('connectToUser=" + userName + "')\" class=\"chat-app__list-item chat-app__list-item--active\"> <img src=\"<%=url%>/img/friend2.jpg\" alt=\"Profile Picture\"><div><strong>"+userName+"</strong><br><small>Last messagepreview...</small></div></div>";
}
</script>
</html>
