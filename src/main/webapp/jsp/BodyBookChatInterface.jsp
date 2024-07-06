<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<%
	String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
%>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>BodyBook Chat Interface</title>
  <link rel="stylesheet" href="<%=url%>/css/messenger.css">
  <link rel="stylesheet" href="<%=url%>/css/base.css">
  <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
</head>

<body>
  <div class="chat-app">
    <!-- Chat List Section -->

    <div class="chat-app__list">
      <h3 class="logo">BodyBook</h3>
      <div class="chat-app__list-item chat-app__list-item--active">
        <img src="https://via.placeholder.com/40" alt="Profile Picture">
        <div>
          <strong>Username 1</strong><br>
          <small>Last message preview...</small>
        </div>
      </div>
      <div class="chat-app__list-item">
        <img src="https://via.placeholder.com/40" alt="Profile Picture">
        <div>
          <strong>Username 2</strong><br>
          <small>Last message preview...</small>
        </div>
      </div>
      <!-- Add more chat items as needed -->
    </div>

    <!-- Chat Messages Section -->
    <div class="chat-app__content">
      <div class="chat-app__messages">
        <div class="chat-app__message chat-app__message--receiver">
          <div class="chat-app__message-text chat-app__message-text--receiver">
            I'm dead
          </div>
        </div>
        <div class="chat-app__message chat-app__message--sender">
          <div class="chat-app__message-text chat-app__message-text--sender">
            Hey, how are you?
          </div>
        </div>

        <!-- Add more messages as needed -->
      </div>
      <div class="chat-app__input">
        <img src="https://via.placeholder.com/40" alt="User Profile">
        <input type="text" class="form-control" placeholder="Type a message...">
        <button class="btn m-2"><i class="fas fa-paper-plane"></i></button>
      </div>
    </div>
  </div>

  <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>

</html>
