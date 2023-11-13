<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Animal List</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
<h1>Check my animal list!</h1>
<ol id="list">
    <%
        Map<String, List<String>> animals = (Map<String, List<String>>) request.getAttribute("animals");
        for (Map.Entry<String, List<String>> entry : animals.entrySet()) {
    %>
    <li class="top-level closed" onclick="toggleList(this)">
        <%= entry.getKey() %>
        <span class="delete-btn" onclick="deleteItem(this)">[X]</span>
        <ul class="nested">
            <% for (String animal : entry.getValue()) { %>
            <li>
                <%= animal %>
                <span class="delete-btn-nested" onclick="deleteItem(this)">[X]</span>
            </li>
            <% } %>
        </ul>
    </li>
    <% } %>

</ol>

<script>
    function toggleList(item) {
        const nestedList = item.querySelector(".nested");
        if (item.classList.contains("closed")) {
            item.classList.remove("closed");
            item.classList.add("open");
            nestedList.style.display = "block";
        } else {
            item.classList.remove("open");
            item.classList.add("closed");
            nestedList.style.display = "none";
        }

    }

    function deleteItem(btn) {
        const listItem = btn.parentElement;
        const listContainer = listItem.parentElement;

        listItem.parentNode.removeChild(listItem);

        event.stopPropagation();
    }
</script>

</body>
</html>
