<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="language.language"/>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/payment-style.css">
</head>
<body style="background-image: url(/img/main-background.png);
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
            <c:if test="${sessionScope.currentUser.role == 'CLIENT'}">
                <ctg:clientBalance/>
            </c:if>
            <a style="text-decoration: none; color: white;" id="shopMap"
               class="neon-title-white-shadow-light"
               href="${pageContext.request.contextPath}/jsp/cart.jsp"><span
                    class="fas fa-shopping-cart"></span>&nbsp&#40;${sessionScope.cartAmount}&#41;
            </a>
        </c:if>
        <div style="display: inline-grid; margin-left: 50px">
            <c:if test="${empty sessionScope.currentUser}">
                <a href="${pageContext.request.contextPath}/jsp/registration.jsp"
                   style="text-decoration: none" class="neon-title-lime white-hover">
                    <span class="far fa-user" style="margin-right:0.40em; display:inline-block;"></span><fmt:message
                        key="header.signUp"/></a>
                <a href="${pageContext.request.contextPath}/jsp/login.jsp"
                   style="text-decoration: none" class="neon-title-orange white-hover">
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
                         style="background:  rgba(0,0,0,0.9);
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
                            <input type="hidden" name="currentPage" value="${pageContext.request.requestURI}">
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
    <c:if test="${empty requestScope.resetToken}">
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
    </c:if>
</nav>
<br>

<button type="button" id="balanceButton" class="btn btn-primary invisible" style="position: absolute"
        data-toggle="modal" data-target="#exampleModalCenter">
</button>
<div class="modal" style="border: none !important; background-color: rgba(0,0,0,0.5) !important"
     id="exampleModalCenter"
     tabindex="-1" role="dialog"
     aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog center-modal" style="border: none !important;" role="document">
        <div class="modal-content"
             style="background-color: rgba(105,105,105,0.7); border: none !important; margin-top: -90px;">
            <div class="modal-header" style="border-bottom: 0 none; color: white">
                <fmt:message key="header.increase.balance"/>
                <button type="button" id="clsBtn" class="close neon-title-red" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <main class="page payment-page">
                    <div class="container">
                        <form method="post" action="${pageContext.request.contextPath}/increaseBalance.do">
                            <input type="hidden" name="command" value="increase_balance"/>
                            <input name="clientToken" type="hidden" value="${sessionScope.serverToken}"/>
                            <input type="hidden" name="currentPage" value="${pageContext.request.requestURI}">
                            <div class="card-details">
                                <h3 style="color: white" class="title"><fmt:message key="header.credit.card"/></h3>
                                <div class="row">
                                    <div class="form-group col-sm-7">
                                        <label style="color: white" for="card-holder"><fmt:message
                                                key="header.card.holder"/></label>
                                        <input id="card-holder" type="text" class="form-control"
                                               placeholder="<fmt:message
                                                key="header.card.holder"/>" aria-label="Card Holder" required
                                               minlength="3" maxlength="30"
                                               aria-describedby="basic-addon1">
                                    </div>
                                    <div class="form-group col-sm-5">
                                        <label style="color: white"><fmt:message key="header.card.date"/></label>
                                        <div class="input-group expiration-date">
                                            <input id="payMM" type="text" class="form-control"
                                                   placeholder="<fmt:message key="header.card.date.month"/>"
                                                   aria-label="MM"
                                                   aria-describedby="basic-addon1" pattern="^[0-9]{2}$" minlength="2"
                                                   maxlength="2">
                                            <input id="payYY" type="text" class="form-control"
                                                   placeholder="<fmt:message key="header.card.date.year"/>"
                                                   aria-label="YY"
                                                   aria-describedby="basic-addon1" pattern="^[0-9]{2}$" minlength="2"
                                                   maxlength="2">
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-8">
                                        <label style="color: white" for="card-number"><fmt:message
                                                key="header.card.number"/></label>
                                        <input id="card-number" type="text" class="form-control"
                                               placeholder="<fmt:message key="header.card.number"/>"
                                               aria-label="Card Holder"
                                               aria-describedby="basic-addon1" maxlength="16" minlength="16">
                                    </div>
                                    <div class="form-group col-sm-4">
                                        <label style="color: white" for="cvc"><fmt:message
                                                key="header.card.cvc"/></label>
                                        <input id="cvc" type="text" class="form-control"
                                               placeholder="<fmt:message key="header.card.cvc"/>"
                                               aria-label="Card Holder" aria-describedby="basic-addon1" required
                                               pattern="^[0-9]{3}$" minlength="3" maxlength="3">
                                    </div>
                                    <div class="form-group" style="margin-left: 30%">
                                        <label style="color: white" for="amount"><fmt:message key="header.card.money"/>
                                            &dollar;</label>
                                        <input id="amount" name="moneyAmount" type="text" class="form-control"
                                               placeholder="<fmt:message key="header.card.money"/>"
                                               aria-label="Card Holder" aria-describedby="basic-addon1" required
                                               pattern="^(\d)*(\.\d{1,2})?$" minlength="1" maxlength="10">
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <button type="submit" class="btn btn-success btn-block"><fmt:message
                                                key="header.card.btn"/></button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </main>
            </div>
        </div>
    </div>
</div>

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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/toast-script.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/payment.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var serverError = ${not empty requestScope.serverError};
        if (serverError) {
            funcBtns.alertError('<fmt:message key="alert.header.error"/>', '<fmt:message key="server.error"/>')
        }
        var fail = ${not empty requestScope.fail};
        if (fail) {
            funcBtns.alertWarning('<fmt:message key="alert.header.fail"/>');
        }
        let success = ${not empty requestScope.success};
        if (success) {
            funcBtns.alertOkOnlyTitle('<fmt:message key="alert.header.done"/>');
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
            funcBtns.alertWarning("<fmt:message key="alert.header.code.exist"/>");
        }

        var passwordChanged = ${not empty requestScope.passwordChanged};
        if (passwordChanged) {
            funcBtns.alertOkOnlyTitle('<fmt:message key="alert.header.password.changed"/>');
        }

        var tokenNotExist = ${not empty requestScope.tokenNotExist};
        if (tokenNotExist) {
            funcBtns.alertWarning('<fmt:message key="alert.header.token.fail"/>');
        }
    });
</script>

</body>
</html>