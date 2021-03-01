<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="language.language"/>
<html>
<head>
    <title>Admin menu</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin-menu-style.css">
</head>
<body class="custom-admin-body">
<jsp:include page="support/header.jsp"/>

<div class="custom-admin-container">
    <div class="custom-admin-card">
        <div class="face face1">
            <div class="content-admin">
                <i class="fab fa-jedi-order"></i>
                <h3>Games</h3>
            </div>
        </div>
        <div class="face face2">
            <div class="content-admin">
                <a class="custom-admin-a" href="#" type="button">Add game</a>
                <a class="custom-admin-a" href="#" type="button">Add code</a>
                <a class="custom-admin-a" href="#" type="button">Game list</a>
            </div>
        </div>
    </div>

    <div class="custom-admin-card ">
        <div class="face face1">
            <div class="content-admin">
                <i class="fas fa-tags"></i>
                <h3>Orders</h3>
            </div>
        </div>
        <div class="face face2">
            <div class="content-admin">
                <a class="custom-admin-a" href="#" type="button">Order list</a>
                <a class="custom-admin-a" href="#" type="button">Coupon list</a>
            </div>
        </div>
    </div>

    <div class="custom-admin-card">
        <div class="face face1">
            <div class="content-admin">
                <i class="fas fa-users"></i>
                <h3>Users</h3>
            </div>
        </div>
        <div class="face face2">
            <div class="content-admin">
                <a class="custom-admin-a" href="#" type="button">User list</a>
                <a class="custom-admin-a" href="#" type="button">Add admin</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
