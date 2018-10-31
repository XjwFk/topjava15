<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://topjava.ru/functions" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<a href="meals?action=create">Add meal</a>
<h2>Meals</h2>
<table border="1">
    <thead><tr><td>Date and Time</td><td>Description</td><td>Calories</td><td>Exceeded</td><td colspan="2"></td></tr></thead>
<c:forEach var="meal" items="${meals}">
    <tr bgcolor="${meal.exceed ? 'red' : 'green'}">
        <td>${f:formatLocalDateTime(meal.dateTime)}</td>
        <td>${meal.description}</td>
        <td>${meal.calories}</td>
        <td>${meal.exceed}</td>
        <td><a href="meals?action=update&id=${meal.id}">update</a></td>
        <td><a href="meals?action=delete&id=${meal.id}">delete</a></td>
    </tr>
</c:forEach>
</table>
</body>
</html>