<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="language.language"/>
<html>
<head>
    <title><fmt:message key="about.title"/></title>
</head>
<body>
<div style="background-image: url(/img/registration-background.jpg);
background-size: cover; background-attachment: fixed; min-height: 100%; overflow: hidden">
    <jsp:include page="header.jsp"/>
    <div style="padding-top: 5%">
        <div style="color:white; text-align: center; margin-left: 20%; margin-right: 20%; margin-top: 2%">
            <h2 class="neon-title-white-shadow-light" style="margin-bottom: 15%"><fmt:message
                    key="about.gamespot"/></h2>
            <p style="margin-bottom: 15%" class="neon-title-white-shadow-light"><fmt:message key="about.one"/></p>
            <p class="neon-title-white-shadow-light"><fmt:message key="about.two"/>
                <a href="${pageContext.request.contextPath}/jsp/support/terms.jsp"
                   target="_blank"
                   style="text-decoration: none; color: #00ffd4"><fmt:message
                        key="registration.condition"/>
                </a>
            </p>
        </div>
        <div style="bottom: 0; position: fixed; width: 100%">
            <jsp:include page="footer.jsp"/>
        </div>
    </div>
</body>
</html>
