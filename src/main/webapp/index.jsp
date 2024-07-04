<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Post</title>
</head>
<body>
<h1>Posts</h1>

<form action="post" method="POST" enctype="multipart/form-data">
    <textarea name="content" placeholder="What's on your mind?"></textarea>
    <input type="file" name="image">
    <button type="submit">Post</button>
</form>

<div>
    <c:forEach var="post" items="${posts}">
        <p>${post.content}</p>
        <c:if test="${not empty post.image}">
            <img src="${post.image}" alt="Post Image">
        </c:if>
    </c:forEach>
</div>
</body>
</html>
