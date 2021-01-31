<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="property.language"/>
<head>
    <title>Main</title>
</head>
<html>
<body>
<div style="background-image: url(/img/registration-background.jpg);
background-size: cover; background-attachment: fixed; min-height: 100%; overflow: hidden">
    <jsp:include page="support/header.jsp"/>
    <div style="padding-top: 5%">
        <div class="container">
            <div class="row row-cols-3" style="row-gap: 60px; margin-left: 45px">
                <c:forEach items="${sessionScope.gameList}" var="game">
                    <div class="col">
                        <a href="#" class="card custom-card" style="width: 18rem; text-decoration: none">
                            <img class="image-card" src="${game.imagePath}">
                            <div class="card-body" style="text-align: center">
                                <p class="card-title neon-title-white">${game.title}</p>
                                <p class="card-text neon-title-white">${game.price}$</p>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div style="margin-top: 5%">
            <jsp:include page="support/footer.jsp"/>
        </div>
    </div>
</body>

</html>