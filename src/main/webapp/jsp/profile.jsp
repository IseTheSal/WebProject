<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="language.language"/>
<html>
<head>
    <title><fmt:message key="profile.title"/></title>

    <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.css"/>
    <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/swiper-style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/collapse-style.css">
</head>
<body>
<div style="background-image: url(/img/registration-background.jpg);
background-size: cover; background-attachment: fixed; min-height: 100%; overflow: hidden">
    <jsp:include page="support/header.jsp"/>
    <div style="padding-top: 5%; font-size: 18px">
        <h2 class="neon-title-cyan-light" style="text-align:center">
            <fmt:message key="profile.title"/>
        </h2>
        <div style="max-width: 300px; margin-left: 40%">
            <div style="display: inline">
                <label class="neon-title-white">
                    <fmt:message key="profile.firstname"/>
                </label>
                <label class="neon-title-white" style="float: right">
                    ${sessionScope.currentUser.firstname}
                </label>
            </div>
            <br>
            <div style="display: inline">
                <label class="neon-title-white">
                    <fmt:message key="profile.lastname"/>
                </label>
                <label class="neon-title-white" style="float: right">
                    ${sessionScope.currentUser.lastname}
                </label>
            </div>
            <br>

            <div class="form-group" style="padding-bottom: 0">
                <button class="collapsible neon-title-white" style="width: 100%;padding: 0 !important;">
                    <fmt:message key="profile.email"/> ${sessionScope.currentUser.email}</button>
                <div class="content">
                    <form class="needs-validation" novalidate method="post" action="${pageContext.request.contextPath}/changeEmail.do">
                        <input type="hidden" name="command" value="change_email"/>
                        <input type="hidden" name="clientToken" value="${sessionScope.serverToken}"/>
                        <input type="hidden" name="currentPage"
                               value="${pageContext.request.requestURI}">
                        <br>
                        <input name="email" id="newEmail" style="width: 300px; height: 40px; border-width: medium"
                               class="form-control"
                               placeholder="<fmt:message key="profile.newEmail"/>" required type="email"
                               maxlength="320">
                        <br>
                        <div class="form">
                            <input name="repeatEmail" id="newEmailRepeat"
                                   style="width: 300px; height: 40px; border-width: medium"
                                   class="form-control"
                                   placeholder="<fmt:message key="profile.newEmailRepeat"/>" required type="email"
                                   maxlength="320">
                            <div class="invalid-feedback"><span class="fas fa-times"></span><fmt:message
                                    key="registration.invalidInput"/></div>
                        </div>
                        <br>
                        <button class="button-glow-lime" type="submit"
                                style="cursor:pointer;height: 25px; width: 100px; font-size: 10px; box-shadow: none !important; border-radius: 0; float: right ">
                            <fmt:message key="profile.button.change"/>
                        </button>
                    </form>
                </div>
            </div>

            <div class="form-group" style="padding-bottom: 3%">
                <button class="collapsible neon-title-white" style="width: 100%;padding: 0 !important;">
                    <fmt:message key="profile.password"/>
                </button>
                <div class="content">
                    <form class="needs-validation" novalidate method="post" action="${pageContext.request.contextPath}/changePassword.do">
                        <input type="hidden" name="command" value="change_password"/>
                        <input type="hidden" name="clientToken" value="${sessionScope.serverToken}"/>
                        <input type="hidden" name="currentPage"
                               value="${pageContext.request.requestURI}">
                        <br>
                        <input name="oldPassword" style="width: 300px; height: 40px;  border-width: medium"
                               type="password"
                               class="form-control" id="txtOldPassword"
                               placeholder="<fmt:message key="profile.oldPassword"/>"
                               required>
                        <br>
                        <input name="password" style="width: 300px; height: 40px;  border-width: medium"
                               type="password"
                               class="form-control" id="txtNewPassword"
                               placeholder="<fmt:message key="profile.newPassword"/>"
                               required pattern="[a-zA-Z0-9]{8,20}$"
                               minlength="8" maxlength="20">
                        <br>
                        <div class="form-inline">
                            <input name="repeatPassword" style="width: 300px; height: 40px;  border-width: medium"
                                   type="password"
                                   class="form-control"
                                   id="txtConfirmPassword"
                                   placeholder="<fmt:message key="profile.newPasswordRepeat"/>"
                                   required pattern="^[a-zA-Z0-9]{8,20}$"
                                   minlength="8" maxlength="20">
                            <div class="invalid-feedback"><span class="fas fa-times"></span><fmt:message
                                    key="registration.invalidPasswordMatch"/></div>
                        </div>
                        <br>
                        <button class="button-glow-lime" type="submit"
                                style="cursor:pointer;height: 25px; width: 100px; font-size: 10px; box-shadow: none !important; border-radius: 0; float: right ">
                            <fmt:message key="profile.button.change"/>
                        </button>
                    </form>
                </div>
            </div>
        </div>
        <c:if test="${empty requestScope.orderHistoryList and sessionScope.currentUser.role != 'ADMIN'}">
            <div style="text-align: center; margin-top: 2%">
                <form method="get" action="${pageContext.request.contextPath}/findOrderHistory.do">
                    <input type="hidden" name="command" value="find_order_history">
                    <input type="hidden" name="clientToken" value="${sessionScope.serverToken}"/>
                    <input type="hidden" name="currentPage"
                           value="${pageContext.request.requestURI}">
                    <button class="button-glow-purple" type="submit"
                            style="cursor:pointer; height: 40px; width: 300px;font-size: 20px; border-radius: 15% !important;">
                        <fmt:message key="profile.buttonHistory"/>
                    </button>
                </form>
            </div>
        </c:if>
    </div>

    <c:if test="${not empty requestScope.orderHistoryPrice}">
        <div style="text-align: center">
            <label class="neon-title-white" style="text-align: center; display: inline">
                <fmt:message key="profile.totalPrice"/>
            </label>
            <label class="neon-title-lime" style="text-align: center; display: inline">
                    ${requestScope.orderHistoryPrice.stripTrailingZeros()}$
            </label>
        </div>
    </c:if>
    <c:if test="${not empty requestScope.orderHistoryList}">
        <div class="swiper-container" style="max-height: 50%">
            <br>
            <br>
            <div class="swiper-wrapper" style="zoom: 0.6;">
                <c:forEach items="${requestScope.orderHistoryList}" var="game">
                    <div class="swiper-slide" style="background: transparent; height: 400px !important;">
                        <a href="${pageContext.request.contextPath}/game.do?command=open_game&gameId=${game.id}"
                           class="card custom-card"
                           style="width: 18rem; text-decoration: none; height: 425px !important;">
                            <img class="image-card"
                                 onerror="this.onerror = null; this.src='${pageContext.request.contextPath}/img/IMAGE_UNAVAILABLE.jpg'"
                                 src="${pageContext.request.contextPath}${game.imagePath}">
                            <div class="card-body" style="text-align: center">
                                <p class="card-title neon-title-white">${game.title}</p>
                            </div>
                        </a></div>
                </c:forEach>
            </div>
            <div class="swiper-button-next" style="display: inline; color: white"></div>
            <div class="swiper-button-prev" style="display: inline; color: white"></div>
        </div>
    </c:if>
</div>


<script src="https://unpkg.com/swiper/swiper-bundle.js"></script>
<script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
<script>
    var swiper = new Swiper('.swiper-container', {
        slidesPerView: 4,
        centeredSlides: true,
        spaceBetween: 30,
        pagination: {
            el: '.swiper-pagination',
            clickable: true,
        },
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },
    });
</script>

<script>
    (function () {
        'use strict';
        window.addEventListener('load', function () {
            var forms = document.getElementsByClassName('needs-validation');
            var validation = Array.prototype.filter.call(forms, function (form) {
                form.addEventListener('submit', function (event) {
                    if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        }, false);
    })();
</script>

<script type="text/javascript">
    $(document).ready(
        function () {
            let emailExist =  ${not empty requestScope.emailExist};
            if (emailExist) {
                funcBtns.alertWarningBody('Fail', 'Email exists');
            }
        });

    window.onload = function () {
        var txtEmail = document.getElementById("newEmail");
        var txtConfirmEmail = document.getElementById("newEmailRepeat");
        txtEmail.onchange = ConfirmEmail;
        txtConfirmEmail.onkeyup = ConfirmEmail;

        function ConfirmEmail() {
            txtConfirmEmail.setCustomValidity("");
            if (txtEmail.value !== txtConfirmEmail.value) {
                txtConfirmEmail.setCustomValidity("Emails don`t match");
            }
        }

        var txtPassword = document.getElementById("txtNewPassword");
        var txtConfirmPassword = document.getElementById("txtConfirmPassword");
        txtPassword.onchange = ConfirmPassword;
        txtConfirmPassword.onkeyup = ConfirmPassword;

        function ConfirmPassword() {
            txtConfirmPassword.setCustomValidity("");
            if (txtPassword.value !== txtConfirmPassword.value) {
                txtConfirmPassword.setCustomValidity("Passwords don`t match");
            }
        }
    }
</script>

<script>
    var coll = document.getElementsByClassName("collapsible");
    var i;

    for (i = 0; i < coll.length; i++) {
        coll[i].addEventListener("click", function () {
            this.classList.toggle("active");
            var content = this.nextElementSibling;
            if (content.style.maxHeight) {
                content.style.maxHeight = null;
            } else {
                content.style.maxHeight = content.scrollHeight + "px";
            }
        });
    }
</script>
</body>
</html>
