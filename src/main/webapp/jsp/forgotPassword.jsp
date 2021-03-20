<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="language.language"/>
<html>
<head>
    <%--    fixme fmt language--%>
    <title>Password</title>
</head>
<body>
<div style="background-image: url(/img/login-background-blur.jpg);
background-size: cover; background-attachment: fixed; min-height: 100%; overflow: hidden">
    <jsp:include page="support/header.jsp"/>
    <div style="padding-top: 15%;">
        <form class="needs-validation" novalidate method="post"
              action="${pageContext.request.contextPath}/resetPassword.do">
            <input type="hidden" name="command" value="reset_password"/>
            <input name="clientToken" type="hidden" value="${sessionScope.serverToken}"/>
            <input type="hidden" name="currentPage" value="${pageContext.request.requestURI}">
            <input type="hidden" name="resetToken" value="${requestScope.resetToken}">
            <div style="max-width: 350px; margin: auto">
                <h2 class="neon-title-cyan" style="text-align: center">Set new password</h2>
                <div class="form-group" style="margin-bottom: 14px">
                    <label class="neon-title-white" for="txtPassword">New password</label>
                    <input name="password" style="border-width: medium" type="password"
                           class="form-control" id="txtPassword"
                           placeholder="<fmt:message key="registration.passwordPlaceHolder"/>"
                           aria-describedby="passwordHelp"
                           required pattern="[a-zA-Z0-9]{8,20}"
                           minlength="8" maxlength="20">
                    <div class="invalid-feedback" style="white-space: pre"><span class="fas fa-times"></span>
                        <fmt:message key="registration.helpPassword"/></div>
                </div>
                <div class="form-group">
                    <label class="neon-title-white" for="txtConfirmPassword">Repeat new password</label>
                    <input name="repeatPassword" style="border-width: medium" type="password"
                           class="form-control"
                           id="txtConfirmPassword"
                           placeholder="<fmt:message key="registration.confirmPlaceHolder"/>"
                           aria-describedby="passwordHelp" required pattern="^[a-zA-Z0-9]{8,20}$"
                           minlength="8" maxlength="20">
                    <div class="invalid-feedback"><span class="fas fa-times"></span><fmt:message
                            key="registration.invalidPasswordMatch"/></div>
                </div>
                <input type="submit" name="btnLogin" value="CHANGE OLD" id="btnLogin"
                       class="button-glow-blue">
            </div>
        </form>
    </div>
    <div style="margin-top: 18%">
        <jsp:include page="support/footer.jsp"/>
    </div>
</div>

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
                txtConfirmPassword.setCustomValidity("Passwords do not match.");
            }
        }
    }
</script>
</body>
</html>
