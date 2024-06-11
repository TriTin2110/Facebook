<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Facebook Messenger</title>
  <link rel="stylesheet" href="../css/base.css">
  <link rel="stylesheet" href="../css/bootstrap.min.css">
  <link rel="stylesheet" href="../css/messenger.css">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet" />
  <style>
		.right {
		    width: 100%;
		    text-align: right; 
		    float: right;
		}
		
		.left {
		    width: 100%;
		    text-align: left; 
		    float: left;
		}
</style>
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
	if(message.data.includes("<li class="))
	{
		document.getElementById("userAvaiable").innerHTML += message.data;
	}
	else {
		//Hiển thị nội dung đoạn chat
		document.getElementById("outputChatting").innerHTML += "<div contenteditable=\"false\" class=\"d-flex flex-row justify-content-start\"> <p class=\"small p-2 ms-3 mb-1 rounded-3\" style=\"background-color: #f5f6f7;\">"+message.data+"</p></div><br>";
	}
    
}

function processClose(message) {
    document.getElementById("outputChatting").innerHTML += "";
}

function processError(message) {
    document.getElementById("outputChatting").innerHTML += "Có lỗi xảy ra\n";
}
</script>
<body onload="setUser()">
  <section style="background-color: white;">
    <div class="container py-5">
      <h3>Messenger</h3>
        <div class="row">
          <div class="col-md-12">
            <div class="card" id="chat3">
              <div class="card-body">

                <div class="row">
                  <div class="col-md-6 col-lg-5 col-xl-4 mb-4 mb-md-0">

                    <div class="p-3">

                      <div class="input-group rounded mb-3" id="search-input">
                        <input type="search" class="form-control rounded" placeholder="Search" aria-label="Search"
                          aria-describedby="search-addon" />
                        <span class="input-group-text border-0" id="search-addon">
                          <button type="submit" style="border-radius: 15px"><i class="fa fa-search"></i></button>
                        </span>
                      </div>

                      <div data-mdb-perfect-scrollbar-init style="position: relative; height: 400px">
                        <ul class="list-unstyled mb-0" id="userAvaiable">
                        
                        <!-- Hiển thị danh sách người dùng -->
                          
                          
                        </ul>
                      </div>
                    </div>
                  </div>
                  
                  <div class="col-md-6 col-lg-7 col-xl-8">
                    <div class="pt-3 pe-3" style="position: relative; height: 400px">
                    <!-- Hiển thị nội dung chat -->
                      <div id="outputChatting" style="overflow-y:auto; max-height: 400px;">
                      </div>
                    </div>

					<!-- Hiển thị khung input -->
                    <div class="text-muted d-flex justify-content-start align-items-center pe-3 pt-3 mt-2">
                      <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava6-bg.webp"
                        alt="avatar 3" style="width: 40px; height: 100%;">
                      <input type="text" class="form-control form-control-lg" id="userInput" 
                        placeholder="Type message">
                      <a class="ms-1 text-muted" href="#!"><i class="fas fa-paperclip"></i></a>
                      <a class="ms-3 text-muted" href="#!"><i class="fas fa-smile"></i></a>
                      <input onclick="sendMessage()" type="submit" class="ms-3 link-info">
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
    </div>
  </section>
</body>

<script type="text/javascript">
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
	  document.getElementById("outputChatting").innerHTML += "<div contenteditable=\"false\" class=\"d-flex flex-row justify-content-end\">"+"<p class=\"small p-2 me-3 mb-1 text-white rounded-3 bg-primary\">"+document.getElementById("userInput").value+"</p></div><br>";
  }

  function connectToUser(user) {
	//Clear toàn bộ cửa sổ message hiện tại
	document.getElementById("outputChatting").innerHTML = "";
	//Gửi tên của user muốn kết nối
	websocket.send(user);
}
  	
</script>

</html>
