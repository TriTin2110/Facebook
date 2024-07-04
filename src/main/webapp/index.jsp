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
    <% List<Post> posts = (List<Post>) request.getAttribute("posts"); %>
    <% if (posts != null && !posts.isEmpty()) { %>
        <% for (Post post : posts) { %>
            <p><%= post.getContent() %></p>
            <% if (post.getImage() != null && !post.getImage().isEmpty()) { %>
                <img src="<%= post.getImage() %>" alt="Post Image">
            <% } %>
        <% } %>
    <% } else { %>
        <p>No posts available.</p>
    <% } %>
</div>

</body>
</html>
