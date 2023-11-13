<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../style-dark.css" id="style-link">
</head>
<body>
<h1>Enter to the system</h1>

<form action="Login" method="post">
    <label for="username">Username:</label>
    <input type="text" name="username" id="username" required><br>

    <label for="password">Password:</label>
    <input type="password" name="password" id="password" required><br>

    <button type="submit">Login</button>
</form>

<% if (request.getParameter("error") != null) { %>
<p style="color: red;">Incorrect password or login</p>
<% } %>
<a href="/laba_15">Back to the main page</a>
<script>
    const styleLink = document.getElementById('style-link');

    const savedStyle = getCookie('theme') || sessionStorage.getItem('theme');

    if (savedStyle) {
        styleLink.href = savedStyle;
    }

    function getCookie(name) {
        return `${document.cookie}`;
    }
</script>
</body>
</html>
