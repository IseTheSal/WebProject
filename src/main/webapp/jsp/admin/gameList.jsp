<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="language.language"/>
<html>
<head>
    <title>Game list</title>
</head>
<body>
<div style="background-image: url(/img/registration-background.jpg);
background-size: cover; background-attachment: fixed; min-height: 100%; overflow: hidden">
    <jsp:include page="../support/header.jsp"/>
    <div style="padding-top: 5%; margin-left: 10%; margin-right: 10%">
        <table class="table table-bordered">
            <thead class="thead-light">
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Title</th>
                <th scope="col">Price</th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody class="table-light">
            <c:forEach items="${sessionScope.gameList}" var="game">
                <tr>
                    <th scope="row" style="width: 3%; text-align: center">${game.id}</th>
                    <td style="text-align: center">${game.title}</td>
                    <td style="width: 5%; text-align: center">${game.price}$</td>
                    <td style="width: 10%;text-align: center">
                        <button type="button" class="btn btn-success"><i class="fas fa-edit"></i></button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
