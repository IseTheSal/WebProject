<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="property.language"/>
<head>
    <title>
        <fmt:message key="registration.title"/>
    </title>
</head>
<body>
<div style="background-image: url(/img/registration-background.jpg);
background-size: cover; background-attachment: fixed; min-height: 100%; overflow: hidden">
    <jsp:include page="support/header.jsp"/>
    <div style="padding-top: 5%">
        <form class="needs-validation" novalidate method="post" action="/controller">
            <input type="hidden" name="command" value="registration"/>
            <h2 class="neon-title-cyan" style="text-align:center">
                <fmt:message key="registration.title"/>
            </h2>
            <div style="max-width: 700px;margin-left: 30%;margin-right:auto; zoom: 0.95">
<%--            <div style="max-width: 580px;margin-left: auto;margin-right:auto; zoom: 0.95">--%>
                <div class="form-group" style="margin-bottom: 5px">
                    <label class="neon-title-white" for="txtUsername"
                           style="position: relative; margin-bottom: 1px">
                        <fmt:message key="registration.username"/>
                    </label>
                    <div class="form-inline">
                        <input name="login" id="txtUsername"
                               type="text" style="width: 300px; border-width: medium"
                               class="form-control" aria-describedby="usernameHelp"
                               placeholder="<fmt:message key="registration.usernamePlaceHolder"/>"
                               required pattern="^[a-z0-9]([_](?![_])|[a-zA-Z0-9]){4,10}[a-z0-9]$"
                               minlength="6" maxlength="12"/>
                        <small id="usernameHelp" style="margin-left: 20px;
                 white-space: pre-line; color: darkgrey"> <fmt:message key="registration.helpUsername"/>
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
                        <input name="password" style="width: 300px; border-width: medium" type="password"
                               class="form-control" id="txtPassword"
                               placeholder="<fmt:message key="registration.passwordPlaceHolder"/>"
                               aria-describedby="passwordHelp"
                               required pattern="[a-zA-Z0-9]{8,20}"
                               minlength="8" maxlength="20">
                        <small id="passwordHelp" style="margin-left: 20px; color: darkgrey;
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
                        <input name="repeatPassword" style="width: 300px; border-width: medium" type="password"
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
                        <small id="firstnameHelp" style="margin-left: 20px; color: darkgrey;
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
                        <small id="lastnameHelp" style="margin-left: 20px; color: darkgrey;
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
                        <input name="email" style="width: 300px;border-width: medium" class="form-control" id="txtEmail"
                               placeholder="<fmt:message key="registration.emailPlaceHolder"/>"
                               aria-describedby="emailHelp" required type="email"
                               maxlength="320">
                        <small id="emailHelp" style="margin-left: 20px; color: darkgrey">
                            <fmt:message key="registration.helpEmail"/>
                        </small>
                        <div class="valid-feedback"><span class="fas fa-check"></span><fmt:message
                                key="registration.validInput"/></div>
                        <div class="invalid-feedback"><span class="fas fa-times"></span><fmt:message
                                key="registration.invalidInput"/></div>
                    </div>
                </div>
                <div class="form-group form-check">
                    <label class="form-check-label" style="color: white">
                        <input class="form-check-input" type="checkbox" name="remember" required/>
                        <fmt:message key="registration.agreement"/> <a href="/jsp/support/terms.jsp"
                                                                       target="_blank"
                                                                       style="text-decoration: none"><fmt:message
                            key="registration.condition"/></a>
                        <div class="invalid-feedback"><fmt:message key="registration.invalidCheckBox"/></div>
                    </label>
                </div>
                <label class="form-inline"
                       style="color: red"> ${requestScope.registrationFail} </label>
                <input type="submit" name="btnSignup" value="<fmt:message key="registration.btnSignUp"/>" id="btnSignup"
                       class="button-glow-lime"/>
            </div>
        </form>
        <div style="margin-top: 5%">
            <jsp:include page="support/footer.jsp"/>
        </div>
    </div>
</body>


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
    window.onload = function () {
        var txtPassword = document.getElementById("txtPassword");
        var txtConfirmPassword = document.getElementById("txtConfirmPassword");
        txtPassword.onchange = ConfirmPassword;
        txtConfirmPassword.onkeyup = ConfirmPassword;

        function ConfirmPassword() {
            txtConfirmPassword.setCustomValidity("");
            if (txtPassword.value !== txtConfirmPassword.value) {
                txtConfirmPassword.setCustomValidity("Passwords do not match.");
            }
        }
    }
</script>