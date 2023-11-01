<%@ page import="org.example.AdModel" %>
<%@ page import="java.util.PriorityQueue" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="style-light.css"
          id="style-link">
</head>

<button id="light-theme-button">Light Theme</button>
<button id="dark-theme-button">Dark Theme</button>
<body>
<h1>Ad board</h1>

<% String username = (String) session.getAttribute("username"); %>
<% if (username != null) { %>
<h2>Welcome, <%= username %>!</h2>
<a href="${pageContext.request.contextPath}/Logout">Logout</a><br>
<form action="/laba_15/AddAd" method="post">
    <label for="adTitle">title:</label>
    <input type="text" name="adTitle" id="adTitle" required><br>

    <label for="adText">text:</label>
    <textarea name="adText" id="adText" required></textarea><br>

    <button type="submit">Add ad</button>
</form>
<% } else { %>
<a href="${pageContext.request.contextPath}/Login">Login</a><br>
<% } %>

<h2>Ads list</h2>
<% PriorityQueue<AdModel> ads = (PriorityQueue<AdModel>) request.getServletContext().getAttribute("ads"); %>
<% if (ads != null) {
    for (AdModel ad : ads) { %>
<div class="ad-title"><%= ad.getTitle() %>
</div>
<div class="ad-text"><%= ad.getText() %>
</div>
<div class="ad-username"><%= ad.getUsername() %>
</div>
<div class="ad-timestamp"><%= ad.getDate() %>
</div>
<div class="like-section">
    <form action="${pageContext.request.contextPath}/Like" method="post">
        <input type="hidden" name="adTitle" value="<%= ad.getTitle() %>">
        <div class="like-count"><%= ad.getLikeCounter() %>
        </div>
        <% if (username != null) { %>
        <button type="submit" class="like-button">Like</button>
        <% } %>
    </form>
</div>
<%}%>
<%
    } %>

<script>
    const lightThemeButton = document.getElementById('light-theme-button');
    const darkThemeButton = document.getElementById('dark-theme-button');
    const styleLink = document.getElementById('style-link');

    const savedStyle = getCookie('theme') || sessionStorage.getItem('theme');

    if (savedStyle) {
        styleLink.href = savedStyle;
    }

    lightThemeButton.addEventListener('click', function () {
        styleLink.href = 'style-light.css';
        document.cookie = 'theme=style-light.css';
        sessionStorage.setItem('theme', 'style-light.css');
    });

    darkThemeButton.addEventListener('click', function () {
        styleLink.href = 'style-dark.css';
        document.cookie = 'theme=style-dark.css';
        sessionStorage.setItem('theme', 'style-dark.css');
    });

    function getCookie(name) {
        return `${document.cookie}`;
    }
</script>

</body>
</html>
