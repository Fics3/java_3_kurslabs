<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Map, java.util.ArrayList,com.example.NoteBookModel,java.lang.String" %>
<%@ page import="com.example.NoteBookModel" %>
<%!  NoteBookModel noteBookModel = new NoteBookModel(); %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Notebook</title>
</head>
<body>
<h1>Notebook Entries</h1>
<ul>
<%Map<String, ArrayList<String>> notes = (Map<String, ArrayList<String>> )request.getAttribute("notes");%>
<% for (Map.Entry<String, ArrayList<String>> entry : notes.entrySet()) { %>
<p>Name: <%= entry.getKey() %></p>
<p>Phones: <%= entry.getValue() %></p>
<% } %>
</ul>
<form method="GET" action="${pageContext.request.contextPath}NoteBook/add">
    Name: <input type="text" name="name">
    Phone: <input type="text" name="phone">
    <input type="submit" value="Add">
</form>

<a href="${pageContext.request.contextPath}/NoteBook/reset">Reset</a>
</body>
</html>
