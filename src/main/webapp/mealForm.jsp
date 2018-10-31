<%--
  Created by IntelliJ IDEA.
  User: un1
  Date: 31.10.2018
  Time: 17:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>${param.action == 'create' ? 'Create meal' : 'Update meal'}</h2>
<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request" />
    <form method="post" >
        <input type="hidden" name="id" value="${meal.id}">
        DateTime: <input type="datetime-local" value="${meal.dateTime}" name="dateTime" required>
        Description: <input type="text" value="${meal.description}" name="description" required>
        Calories: <input type="number" value="${meal.calories}" name="calories" required>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</body>
</html>
