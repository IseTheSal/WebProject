<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="property.language"/>
<html>
<head>
    <title>Error 500</title>
</head>
<body>
<div style="background-image: url(/img/registration-background.jpg);
background-size: cover; background-attachment: fixed; min-height: 100%; overflow: hidden">
    <%@include file="../support/header.jsp" %>
    <h1 class="neon-title-cyan-light" style="padding-top: 20%;text-align: center"><fmt:message
            key="error.internalServerError"/></h1>
    <div style="bottom: 0; position: fixed; width: 100%">
        <jsp:include page="../support/footer.jsp"/>
    </div>
</div>
</body>
</html>