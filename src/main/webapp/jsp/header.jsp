<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Main</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-inverse navbar-static-top">
    <a href="/index.jsp" class="navbar-brand">Web task</a>
    <ul class="nav navbar-nav pull-right">
        <c:if test="${empty sessionScope.name}">
            <li><a href="${pageContext.request.contextPath}/jsp/registration.jsp"><span
                    class="glyphicon glyphicon-user"></span> Sign Up</a></li>
            <li><a href="${pageContext.request.contextPath}/jsp/login.jsp"><span
                    class="glyphicon glyphicon-log-in"></span> Login</a></li>
        </c:if>
        <c:if test="${not empty sessionScope.name}">
            <li><a href="${pageContext.request.contextPath}/jsp/profile.jsp"><span
                    class="glyphicon glyphicon-user"></span> ${sessionScope.get("name")}</a></li>
            <li>
                <form method="post" action="/controller">
                    <input type="hidden" name="command" value="logout"/>
                    <input type="submit" value="Log out" class="btn btn-outline-info"
                           style="
                            background-color: #242424;
                            color: darkgrey;
                            border:  #242424;
                            margin-top: 8px"/>
                </form>
            </li>
        </c:if>
    </ul>
</nav>
</body>
</html>
