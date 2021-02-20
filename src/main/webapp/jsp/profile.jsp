<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="language.language"/>
<html>
<head>
    <title><fmt:message key="profile.title"/></title>
    <%--    <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.css"/>--%>
    <%--    <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css"/>--%>
    <%--    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/swiper-style.css">--%>
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
                <label class="neon-title-white" style="text-align: center">
                    ${sessionScope.currentUser.firstname}
                </label>
            </div>
            <br>
            <div style="display: inline">
                <label class="neon-title-white">
                    <fmt:message key="profile.lastname"/>
                </label>
                <label class="neon-title-white" style="text-align: center">
                    ${sessionScope.currentUser.lastname}
                </label>
            </div>
            <br>

            <div class="form-group" style="padding-bottom: 0">
                <button class="collapsible neon-title-white" style="width: 100%;padding: 0 !important;">
                    <fmt:message key="profile.email"/> ${sessionScope.currentUser.email}</button>
                <div class="content">
                    <form class="needs-validation" novalidate method="post" action="changeEmail.do">
                        <input type="hidden" name="command" value="change_email"/>
                        <input type="hidden" name="clientToken" value="${serverToken}"/>
                        <input type="hidden" name="currentPage"
                               value="${pageContext.request.requestURI}">
                        <br>
                        <input name="email" id="newEmail" style="width: 300px; height: 40px; border-width: medium"
                               class="form-control"
                               placeholder="<fmt:message key="profile.newEmail"/>" required type="email"
                               maxlength="320">
                        <br>
                        <input name="repeatEmail" id="newEmailRepeat"
                               style="width: 300px; height: 40px; border-width: medium"
                               class="form-control"
                               placeholder="<fmt:message key="profile.newEmailRepeat"/>" required type="email"
                               maxlength="320">
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
                    <form class="needs-validation" novalidate method="post" action="changePassword.do">
                        <input type="hidden" name="command" value="change_password"/>
                        <input type="hidden" name="clientToken" value="${serverToken}"/>
                        <input type="hidden" name="currentPage"
                               value="${pageContext.request.requestURI}">
                        <br>
                        <input name="oldPassword" style="width: 300px; height: 40px;  border-width: medium"
                               type="password"
                               class="form-control" id="txtOldPassword"
                               placeholder="<fmt:message key="profile.oldPassword"/>"
                               required pattern="[a-zA-Z0-9]{8,20}"
                               minlength="8" maxlength="20">
                        <br>
                        <input name="password" style="width: 300px; height: 40px;  border-width: medium"
                               type="password"
                               class="form-control" id="txtNewPassword"
                               placeholder="<fmt:message key="profile.newPassword"/>"
                               required pattern="[a-zA-Z0-9]{8,20}"
                               minlength="8" maxlength="20">
                        <br>
                        <input name="repeatPassword" style="width: 300px; height: 40px;  border-width: medium"
                               type="password"
                               class="form-control"
                               id="txtConfirmPassword"
                               placeholder="<fmt:message key="profile.newPasswordRepeat"/>"
                               required pattern="^[a-zA-Z0-9]{8,20}$"
                               minlength="8" maxlength="20">
                        <br>
                        <button class="button-glow-lime" type="submit"
                                style="cursor:pointer;height: 25px; width: 100px; font-size: 10px; box-shadow: none !important; border-radius: 0; float: right ">
                            <fmt:message key="profile.button.change"/>
                        </button>
                    </form>
                </div>
            </div>
            <div style="display: inline">
                <label class="neon-title-white">
                    Email:
                </label>
                <label class="neon-title-white" style="text-align: center">
                    ${sessionScope.currentUser.email}
                </label>
            </div>
        </div>
    </div>
    <%--    <div style="position: relative; transform: translateY(130%); width: 100%;">--%>
    <%--        <jsp:include page="support/footer.jsp"/>--%>
    <%--    </div>--%>
    <%--    <div class="swiper-container">--%>
    <%--        <div class="swiper-wrapper">--%>
    <%--            <c:forEach items="${sessionScope.gameList}" var="game">--%>
    <%--                <div class="swiper-slide">${game.title}</div>--%>
    <%--            </c:forEach>--%>
    <%--        </div>--%>
    <%--        <!-- Add Arrows -->--%>
    <%--        <div class="swiper-button-next"></div>--%>
    <%--        <div class="swiper-button-prev"></div>--%>
    <%--    </div>--%>

</div>
<%--<script src="https://unpkg.com/swiper/swiper-bundle.js"></script>--%>
<%--<script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>--%>
<%--<script>--%>
<%--    var swiper = new Swiper('.swiper-container', {--%>
<%--        slidesPerView: 4,--%>
<%--        centeredSlides: true,--%>
<%--        spaceBetween: 30,--%>
<%--        pagination: {--%>
<%--            el: '.swiper-pagination',--%>
<%--            clickable: true,--%>
<%--        },--%>
<%--        navigation: {--%>
<%--            nextEl: '.swiper-button-next',--%>
<%--            prevEl: '.swiper-button-prev',--%>
<%--        },--%>
<%--    });--%>
<%--</script>--%>


<script>

    (function () {
        'use strict';
        window.addEventListener('load', function () {
            console.log("LALALAL");
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
        txtConfirmEmail.onchange = ConfirmEmail;

        function ConfirmEmail() {
            txtConfirmEmail.setCustomValidity("");
            if (txtEmail.value !== txtConfirmEmail.value) {
                txtConfirmEmail.setCustomValidity("Emails don`t match");
                funcBtns.alertError('Fail', 'Emails don`t match')
            }
        }

        var txtPassword = document.getElementById("txtNewPassword");
        var txtConfirmPassword = document.getElementById("txtConfirmPassword");
        txtPassword.onchange = ConfirmPassword;
        txtConfirmPassword.onchange = ConfirmPassword;

        function ConfirmPassword() {
            txtConfirmPassword.setCustomValidity("");
            if (txtPassword.value !== txtConfirmPassword.value) {
                txtConfirmPassword.setCustomValidity("Passwords don`t match");
                funcBtns.alertError('Fail', 'Passwords don`t match')
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
