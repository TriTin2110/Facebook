<%@page import="Model.Post"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Post</title>
</head>
<%
	String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
%>
<body>
<h1>Posts</h1>

<form action="<%=url%>/posting" method="POST" enctype="multipart/form-data">
    <textarea name="content" placeholder="What's on your mind?"></textarea>
    <input type="file" name="image">
    <button type="submit">Post</button>
</form>

<div>
    <% List<Post> posts = (List<Post>) request.getAttribute("posts"); %>
    <% if (posts != null && !posts.isEmpty()) { %>
        <% for (Post post : posts) { %>
            <% if (post.getPostImage() != null && !post.getPostImage().isEmpty()) { %>
                <img src="<%= post.getPostImage() %>" alt="Post Image">
            <% } %>
        <% } %>
    <% } else { %>
        <p>No posts available.</p>
    <% } %>
</div>

</body>
</html>
