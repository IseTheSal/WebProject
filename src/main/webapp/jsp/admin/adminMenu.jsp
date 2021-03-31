<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="language.language"/>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<html>
<head>
    <title><fmt:message key="admin.menu.title"/></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin-menu-style.css">
</head>
<body class="custom-admin-body">
<jsp:include page="../support/header.jsp"/>
<div class="modal" style="border: none !important; background-color: rgba(0,0,0,0.5) !important;" id="adminCodeModal"
     tabindex="-1" role="dialog"
     aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog center-modal" style="border: none !important;" role="document">
        <div class="modal-content" style="background-color: rgba(105,105,105,0.7); border: none !important;">
            <div class="modal-header neon-title-white-light" style="border-bottom: 0 none">
                <fmt:message key="admin.menu.add.gamecode"/>
                <button type="button" id="clsBtn" class="close neon-title-red" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form autocomplete="off" method="post" action="${pageContext.request.contextPath}/addGameCode.do">
                    <div style="position: relative; margin-left: 1%; margin-top: 1%">
                        <input type="hidden" name="command" value="add_game_code"/>
                        <input name="clientToken" type="hidden" value="${sessionScope.serverToken}"/>
                        <input type="hidden" name="currentPage" value="${pageContext.request.requestURI}">
                        <div class="autocomplete; neon-title-cyan-light "
                             style="width:300px; cursor: pointer; display: inline;">
                            <input id="adminInputSearch" class="custom-admin-input" type="text"
                                   placeholder="<fmt:message key="admin.menu.game.placeholder"/>" required
                                   pattern="^[A-z0-9`\s:]{2,35}$"
                                   minlength="2" maxlength="35"
                                   style="color: #ff2525; box-shadow:0 0 30px #ffffff;float: left"/>
                            <input type="hidden" id="gameIdClass" value="-1" name="gameId"/>
                            <input type="text" class="custom-admin-input"
                                   placeholder="<fmt:message key="admin.menu.code.placeholder"/>"
                                   name="gameCode" required
                                   pattern="^([A-z0-9]{5}-)([A-z0-9]{5}-)([A-z0-9]{5})$"
                                   maxlength="17"
                                   minlength="17"
                                   style=" box-shadow:0 0 30px #ffffff; float: right;text-transform:uppercase">
                            <input type="submit" class="button-search-gray"
                                   value="<fmt:message key="admin.menu.add.gamecode"/>"
                                   style="position: relative; margin-left: 13%;margin-top: 5%;border-radius: 0; margin-bottom: 3%; height: 40px; background: rgba(154,154,154,0.5)"/>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<button type="button" id="codeBtn" class="btn btn-primary invisible" style="position: absolute"
        data-toggle="modal" data-target="#adminCodeModal">
</button>

<div class="modal" style="border: none !important; background-color: rgba(0,0,0,0.5) !important"
     id="adminCouponModal"
     tabindex="-1" role="dialog"
     aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog center-modal" style="border: none !important" role="document">
        <div class="modal-content" style="background-color: rgba(105,105,105,0.7); border: none !important">
            <div class="modal-header neon-title-white-light" style="border-bottom: 0 none;">
                <fmt:message key="admin.menu.add.coupon"/>
                <button type="button" id="clsCouponBtn" class="close neon-title-red" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form method="post" action="${pageContext.request.contextPath}/createCoupon.do">
                    <div style="position: relative; margin-left: 1%; margin-top: 1%">
                        <input type="hidden" name="command" value="create_coupon"/>
                        <input name="clientToken" type="hidden" value="${sessionScope.serverToken}"/>
                        <input type="hidden" name="currentPage" value="${pageContext.request.requestURI}">
                        <div class="autocomplete; neon-title-cyan-light "
                             style="width:300px; cursor: pointer; display: inline;">
                            <input id="couponCode" class="custom-admin-input" type="text"
                                   placeholder="<fmt:message key="admin.menu.coupon.code.placeholder"/>"
                                   name="couponCode" required pattern="[a-zA-Z0-9]{5,10}"
                                   minlength="5" maxlength="10"
                                   style="color: #ff2525; box-shadow:0 0 30px #ffffff;float: left; text-transform: uppercase"/>
                            <input id="discount" class="custom-admin-input" type="text"
                                   placeholder="<fmt:message key="admin.menu.coupon.discount.placeholder"/>" required
                                   pattern="^[1-9][0-9]?$"
                                   minlength="1" maxlength="2"
                                   name="couponDiscount"
                                   style="color: #ff2525; box-shadow:0 0 30px #ffffff;float: right"/>
                            <input type="text" class="custom-admin-input"
                                   placeholder="<fmt:message key="admin.menu.coupon.amount.placeholder"/>"
                                   name="couponAmount" required
                                   pattern="^[1-9][0-9]{0,4}$"
                                   minlength="1"
                                   maxlength="5"
                                   style=" box-shadow:0 0 30px #ffffff; text-transform:uppercase; margin-top: 3%; margin-left: 30%">
                            <input type="submit" class="button-search-gray"
                                   value="<fmt:message key="admin.menu.add.coupon"/>"
                                   style="position: relative; margin-left: 13%;margin-top: 5%;border-radius: 0; margin-bottom: 3%; height: 40px; background: rgba(154,154,154,0.5)"/>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<button type="button" id="couponBtn" class="btn btn-primary invisible" style="position: absolute"
        data-toggle="modal" data-target="#adminCouponModal">
</button>
<div style="position:fixed; color: transparent !important; -webkit-touch-callout: none;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none">
    <c:forEach items="${sessionScope.gameList}" var="element">
        <label class="gameIdText" style="color: transparent !important;">${element.id}</label>
        <label class="gameTitleText" style="color: transparent !important;">${element.title}</label>
    </c:forEach>
</div>

<div class="custom-admin-container">
    <div class="custom-admin-card">
        <div class="face face1">
            <div class="content-admin">
                <i class="fab fa-jedi-order"></i>
                <h3><fmt:message key="admin.menu.games"/></h3>
            </div>
        </div>
        <div class="face face2">
            <div class="content-admin" style="text-align: center">
                <a class="custom-admin-a"
                   href="${pageContext.request.contextPath}/createGame.do?command=open_game_creator"
                   type="button"><fmt:message key="admin.menu.add.game"/></a>
                <a class="custom-admin-a" style="cursor: pointer" onclick="$('#codeBtn').click();"
                   type="button"><fmt:message key="admin.menu.add.code"/></a>
                <a class="custom-admin-a" href="${pageContext.request.contextPath}/jsp/admin/gameList.jsp"
                   type="button"><fmt:message key="admin.menu.game.list"/></a>
            </div>
        </div>
    </div>
    <div class="custom-admin-card ">
        <div class="face face1">
            <div class="content-admin">
                <i class="fas fa-tags"></i>
                <h3><fmt:message key="admin.menu.orders"/></h3>
            </div>
        </div>
        <div class="face face2">
            <div class="content-admin" style="text-align: center">
                <a class="custom-admin-a"
                   href="${pageContext.request.contextPath}/openOrders.do?command=open_order_list"
                   type="button"><fmt:message key="admin.menu.order.list"/></a>
                <a class="custom-admin-a"
                   href="${pageContext.request.contextPath}/openCoupons.do?command=open_coupon_list"
                   type="button"><fmt:message key="admin.menu.coupon.list"/></a>
                <a class="custom-admin-a" style="cursor: pointer" onclick="$('#couponBtn').click();"
                   type="button"><fmt:message key="admin.menu.add.coupon"/></a>
            </div>
        </div>
    </div>
    <div class="custom-admin-card">
        <div class="face face1">
            <div class="content-admin">
                <i class="fas fa-users"></i>
                <h3><fmt:message key="admin.menu.users"/></h3>
            </div>
        </div>
        <div class="face face2">
            <div class="content-admin" style="text-align: center">
                <a class="custom-admin-a" href="${pageContext.request.contextPath}/openUsers.do?command=open_user_list"
                   type="button"><fmt:message key="admin.menu.user.list"/></a>
                <a class="custom-admin-a" onclick="$('#adminBtn').click()" style="cursor: pointer"
                   type="button"><fmt:message
                        key="admin.menu.add.admin"/></a>
            </div>
        </div>
    </div>
</div>

<div class="modal" style="border: none !important; background-color: rgba(0,0,0,0.5) !important" id="adminUserModal"
     tabindex="-1" role="dialog"
     aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" style="border: none !important" role="document">
        <div class="modal-content"
             style="background-color: rgba(105,105,105,0.7); border: none !important;margin-left: -15%; width: 145% !important;">
            <div class="modal-header neon-title-white-light" style="border-bottom: 0 none">
                <fmt:message key="admin.menu.add.admin"/>
                <button type="button" id="clsAdminAddButton" class="close neon-title-red" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form class="needs-validation" novalidate method="post"
                      action="${pageContext.request.contextPath}/addAdmin.do">
                    <div style="position: relative; margin-left: 1%; margin-top: 1%">
                        <input type="hidden" name="command" value="add_admin"/>
                        <input name="clientToken" type="hidden" value="${sessionScope.serverToken}"/>
                        <div style="width:300px; cursor: pointer; display: inline;">
                            <div style="">
                                <div class="form-group" style="margin-bottom: 14px">
                                    <label class="neon-title-white" for="txtUsername">
                                        <fmt:message key="registration.username"/>
                                    </label>
                                    <div class="form-inline">
                                        <input name="login" id="txtUsername"
                                               type="text" style="width: 300px; border-width: medium"
                                               class="form-control" aria-describedby="usernameHelp"
                                               placeholder="<fmt:message key="registration.usernamePlaceHolder"/>"
                                               required pattern="^[a-z0-9]([_](?![_])|[a-zA-Z0-9]){4,10}[a-z0-9]$"
                                               minlength="6" maxlength="12"/>
                                        <small id="usernameHelp"
                                               style="margin-left: 20px;margin-top: -5%; white-space: pre-line; color: white">
                                            <fmt:message key="registration.helpUsername"/>
                                        </small>
                                        <div class="valid-feedback"><span class="fas fa-check"></span><fmt:message
                                                key="registration.validInput"/></div>
                                        <div class="invalid-feedback"><span class="fas fa-times"></span><fmt:message
                                                key="registration.invalidInput"/></div>
                                    </div>
                                </div>
                                <div class="form-group" style="margin-bottom: 14px">
                                    <label class="neon-title-white" for="txtPassword"><fmt:message
                                            key="registration.password"/>
                                    </label>
                                    <div class="form-inline">
                                        <input name="password" style="width: 300px; border-width: medium"
                                               type="password"
                                               class="form-control" id="txtPassword"
                                               placeholder="<fmt:message key="registration.passwordPlaceHolder"/>"
                                               aria-describedby="passwordHelp"
                                               required pattern="[a-zA-Z0-9]{8,20}"
                                               minlength="8" maxlength="20">
                                        <small id="passwordHelp" style="margin-left: 20px; color: white;
                                   white-space: pre-line"> <fmt:message key="registration.helpPassword"/>
                                        </small>
                                        <div class="valid-feedback"><span class="fas fa-check"></span><fmt:message
                                                key="registration.validInput"/></div>
                                        <div class="invalid-feedback"><span class="fas fa-times"></span><fmt:message
                                                key="registration.invalidInput"/></div>
                                    </div>
                                </div>
                                <div class="form-group" style="margin-bottom: 14px">
                                    <label class="neon-title-white" for="txtConfirmPassword">
                                        <fmt:message key="registration.passwordConfirm"/>
                                    </label>
                                    <div class="form-inline">
                                        <input name="repeatPassword" style="width: 300px; border-width: medium"
                                               type="password"
                                               class="form-control"
                                               id="txtConfirmPassword"
                                               placeholder="<fmt:message key="registration.confirmPlaceHolder"/>"
                                               aria-describedby="passwordHelp" required pattern="^[a-zA-Z0-9]{8,20}$"
                                               minlength="8" maxlength="20">
                                        <div class="valid-feedback"><span class="fas fa-check"></span></div>
                                        <div class="invalid-feedback"><span class="fas fa-times"></span><fmt:message
                                                key="registration.invalidPasswordMatch"/></div>
                                    </div>
                                </div>
                                <div class="form-group" style="margin-bottom: 14px">
                                    <label class="neon-title-white" for="txtFirstname">
                                        <fmt:message key="registration.firstname"/>
                                    </label>
                                    <div class="form-inline">
                                        <input name="firstname" style="width: 300px; border-width: medium" type="text"
                                               class="form-control"
                                               id="txtFirstname"
                                               placeholder="<fmt:message key="registration.firstnamePlaceHolder"/>"
                                               aria-describedby="firstnameHelp" required pattern="^[A-Za-z|А-я]{2,20}$"
                                               minlength="2" maxlength="20">
                                        <small id="firstnameHelp" style="margin-left: 20px; color: white;
                         white-space: pre-line"> <fmt:message key="registration.helpFirstname"/>
                                        </small>
                                        <div class="valid-feedback"><span class="fas fa-check"></span><fmt:message
                                                key="registration.validInput"/></div>
                                        <div class="invalid-feedback"><span class="fas fa-times"></span><fmt:message
                                                key="registration.invalidInput"/></div>
                                    </div>
                                </div>
                                <div class="form-group" style="margin-bottom: 14px">
                                    <label class="neon-title-white" for="txtLastname">
                                        <fmt:message key="registration.lastname"/>
                                    </label>
                                    <div class="form-inline">
                                        <input name="lastname" style="width: 300px; border-width: medium" type="text"
                                               class="form-control"
                                               id="txtLastname"
                                               placeholder="<fmt:message key="registration.lastnamePlaceHolder"/>"
                                               aria-describedby="lastnameHelp" required pattern="^[A-Za-z|А-я]{2,20}$"
                                               minlength="2" maxlength="20">
                                        <small id="lastnameHelp" style="margin-left: 20px; color: white;
                         white-space: pre-line"> <fmt:message key="registration.helpFirstname"/>
                                        </small>
                                        <div class="valid-feedback"><span class="fas fa-check"></span><fmt:message
                                                key="registration.validInput"/></div>
                                        <div class="invalid-feedback"><span class="fas fa-times"></span><fmt:message
                                                key="registration.invalidInput"/></div>
                                    </div>
                                </div>
                                <div class="form-group" style="margin-bottom: 14px">
                                    <label class="neon-title-white" for="txtEmail">
                                        <fmt:message key="registration.email"/>
                                    </label>
                                    <div class="form-inline">
                                        <input name="email" style="width: 300px;border-width: medium"
                                               class="form-control" id="txtEmail"
                                               placeholder="<fmt:message key="registration.emailPlaceHolder"/>"
                                               aria-describedby="emailHelp" required type="email"
                                               maxlength="320">
                                        <small id="emailHelp" style="margin-left: 20px; color: white">
                                            <fmt:message key="registration.helpEmail"/>
                                        </small>
                                        <div class="valid-feedback"><span class="fas fa-check"></span><fmt:message
                                                key="registration.validInput"/></div>
                                        <div class="invalid-feedback"><span class="fas fa-times"></span><fmt:message
                                                key="registration.invalidInput"/></div>
                                    </div>
                                </div>
                                <c:forEach items="${requestScope.registrationFail}" var="info">
                                    <label class="form-inline"
                                           style="color: red">${info}</label>
                                </c:forEach>
                            </div>
                            <input type="submit" class="button-search-gray"
                                   value="<fmt:message key="admin.menu.add.admin"/>"
                                   style="position: relative; margin-top: 5%; margin-left: 18%;border-radius: 0; margin-bottom: 3%; height: 40px; background: rgba(154,154,154,0.5)"/>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<button type="button" id="adminBtn" class="btn btn-primary invisible" style="position: absolute"
        data-toggle="modal" data-target="#adminUserModal">
</button>
<div style="position: fixed; bottom: 0" class="neon-title-white">
    <ctg:adminAccess/>
</div>
<script src="${pageContext.request.contextPath}/js/custom-search.js"></script>
<script src="${pageContext.request.contextPath}/js/admin-menu.js"></script>
<script src="${pageContext.request.contextPath}/js/custom-validation.js" type="text/javascript"></script>

<script type="text/javascript">
    window.onload = function () {
        var txtPassword = document.getElementById("txtPassword");
        var txtConfirmPassword = document.getElementById("txtConfirmPassword");
        txtPassword.onchange = ConfirmPassword;
        txtConfirmPassword.onkeyup = ConfirmPassword;

        function ConfirmPassword() {
            txtConfirmPassword.setCustomValidity("");
            if (txtPassword.value !== txtConfirmPassword.value) {
                txtConfirmPassword.setCustomValidity("<fmt:message key="password.reset.alert"/>");
            }
        }
    }
</script>
<script type="text/javascript">
    $(document).ready(function () {
        if (${requestScope.couponExist}) {
            funcBtns.alertWarning("<fmt:message key="alert.header.code.exist"/>");
        }
    });
</script>
</body>
</html>
