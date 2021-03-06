<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="language.language"/>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <script src="https://kit.fontawesome.com/c1f7a487ad.js" crossorigin="anonymous"></script>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/text-style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/button-style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/form-style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer-style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/card-style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/modal-window.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/toast-window.css">

</head>

<body style="background-image: url(/img/registration-background.jpg);
background-size: cover; background-attachment: fixed; min-height: 100%">
<nav class="navbar fixed-top navbar-dark"
     style="padding-top: 2px;padding-bottom: 2px; zoom: 0.9;
      background-image: linear-gradient(45deg, rgba(255,255,255,.07) 100%, transparent 10%)">
    <div class="navbar-brand">
        <a href="${pageContext.request.contextPath}/index.jsp" class="texas-blue-animated"
           style="text-decoration: none"><fmt:message
                key="header.mainLink"/></a>
        <a href="${pageContext.request.contextPath}/jsp/support/about.jsp" class="texas-purple-animated"
           style="text-decoration: none;margin-left: 30px"><fmt:message
                key="header.supportLink"/></a>
    </div>

    <div class="navbar pull-right">
        <c:if test="${sessionScope.currentUser.role != 'ADMIN'}">
            <a style="text-decoration: none; color: white;" id="shopMap"
               class="neon-title-white-shadow-light"
               href="${pageContext.request.contextPath}/jsp/cart.jsp"><span
                    class="fas fa-shopping-cart"></span>&nbsp(${sessionScope.cartAmount})</a>
        </c:if>
        <div style="display: inline-grid; margin-left: 50px">
            <c:if test="${empty sessionScope.currentUser}">
                <a href="${pageContext.request.contextPath}/jsp/registration.jsp"
                   style="text-decoration: none" class="neon-title-lime">
                    <span class="far fa-user" style="margin-right:0.40em; display:inline-block;"></span><fmt:message
                        key="header.signUp"/></a>
                <a href="${pageContext.request.contextPath}/jsp/login.jsp"
                   style="text-decoration: none" class="neon-title-orange">
                    <span class="fas fa-sign-in-alt"
                          style="margin-right:0.30em; display:inline-block;"></span><fmt:message
                        key="header.login"/></a>
            </c:if>
        </div>

        <div style="display: inline">
            <c:if test="${not empty sessionScope.currentUser}">
                <div class="nav-item dropdown">
                    <a class="nav-link neon-title-lime" href="" id="navbarDropdown" role="button"
                       data-toggle="dropdown" aria-haspopup="true"
                       style="text-decoration: none; color:#56ff42;">
                        <span class="fas fa-user" style="margin-right:0.40em"></span>
                            ${sessionScope.currentUser.firstname}
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown"
                         style="background:  rgba(152,148,148,0.2);
                          border: none; position: fixed; left: 90%; top: 8%; width: 40px">
                        <a class="dropdown-item neon-title-white"
                           style="text-align: center; background-color: transparent"
                           href="${pageContext.request.contextPath}/jsp/profile.jsp"><fmt:message
                                key="header.profile"/></a>
                        <c:if test="${sessionScope.currentUser.role == 'ADMIN'}">
                            <a class="dropdown-item neon-title-white"
                               style="text-align: center; background-color: transparent"
                               href="${pageContext.request.contextPath}/jsp/admin/adminMenu.jsp"><fmt:message
                                    key="header.admin.menu"/></a>
                        </c:if>
                        <form method="post" action="${pageContext.request.contextPath}/logout.do" style="height: 15px">
                            <input name="clientToken" type="hidden" value="${sessionScope.serverToken}"/>
                            <input type="hidden" name="command" value="logout"/>
                            <button class="btn btn-outline-danger neon-title-red button-border-red"
                                    type="submit"><fmt:message key="header.logout"/>
                            </button>
                        </form>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
    <form method="post" action="${pageContext.request.contextPath}/changeLanguage.do"
          style="position: absolute; top: 80px; left: 20px">
        <input type="hidden" name="command" value="change_locale"/>
        <input type="hidden" name="currentPage" value="${pageContext.request.requestURI}">
        <button type="submit" name="currentLocale" value="en_US" class="button-locale-change neon-title-white">
            <fmt:message key="header.English"/>
        </button>
        <button type="submit" name="currentLocale" value="pl_PL" class="button-locale-change neon-title-white">
            <fmt:message key="header.Polish"/>
        </button>
        <button type="submit" name="currentLocale" value="ru_RU" class="button-locale-change neon-title-white">
            <fmt:message key="header.Russian"/>
        </button>
        <h1 class="neon-title-white">${pageContext.request.contextPath}</h1>
    </form>
</nav>
<br>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/toast-script.js"></script>
<script>
    $(document).ready(function () {
        var serverError = ${not empty requestScope.serverError};
        if (serverError) {
            funcBtns.alertError('Error', '<fmt:message key="server.error"/>')
        }
        var fail = ${not empty requestScope.fail};
        if (fail) {
            funcBtns.alertWarning('Fail');
        }
        var success = ${not empty requestScope.success};
        if (success) {
            funcBtns.alertOkOnlyTitle('Success');
        }
        var validIssues = ${not empty requestScope.validIssues};
        if (validIssues) {
            let jsArray = [
                <c:forEach items="${requestScope.validIssues}" var="elem" varStatus="currentStatus">
                "${elem}"
                <c:if test="${not currentStatus.last}">
                ,
                </c:if>
                </c:forEach>
            ];
            for (let i = 0; i < jsArray.length; i++) {
                funcBtns.alertWarning(jsArray[i]);
            }
        }
        var codeExist = ${not empty requestScope.gameCodeExists};
        if (codeExist) {
            funcBtns.alertWarning("This code probably exists");
        }
    });
</script>
</body>
</html>