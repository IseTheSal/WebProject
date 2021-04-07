<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="language.language"/>
<html>
<head>
    <title><fmt:message key="registration.condition"/></title>
</head>
<body>
<h1 style="text-align: center"><fmt:message key="terms.title"/> <fmt:message key="registration.condition"/></h1>
<small style="position: absolute; left: 44%">
    <fmt:message key="registration.condition"/>
</small>
</body>
</html>
