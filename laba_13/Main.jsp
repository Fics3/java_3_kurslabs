<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Map, java.util.ArrayList,java.lang.String,java.util.Objects" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Notebook</title>
</head>
<body>
<h1>Notebook Entries</h1>
<ul>
    <%Map<String, ArrayList<String>> notes = (Map<String, ArrayList<String>>) request.getAttribute("notes");%>
    <% for (Map.Entry<String, ArrayList<String>> entry : notes.entrySet()) { %>
    <p>Name: <%= entry.getKey() %>
    </p>
    <p>Phones: <%= entry.getValue() %>
    </p>
    <% } %>
</ul>
<h2>Search Results:</h2>
<ul>
    <%String searchResult = request.getParameter("searchResult");%>
    <% if (notes.containsKey(searchResult)) {%>
    <p>Name: <%= searchResult %>
    </p>
    <p>Phones: <%= notes.get(searchResult) %>
    </p>
    <% }%>
</ul>
<form method="GET" action="${pageContext.request.contextPath}/NoteBook/add">
    Name: <input type="text" name="name">
    Phone: <input type="text" name="phone">
    <input type="submit" value="Add">
</form>
<br>
<form method="GET" action="/laba_13/NoteBook/searchResult">
    Name: <input type="text" name="searchResult">
    <input type="submit" value="Search">
</form>

<a href="${pageContext.request.contextPath}/NoteBook/reset">Reset</a>
</body>
</html>
