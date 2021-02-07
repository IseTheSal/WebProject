<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="property.language"/>
<head>
    <title><fmt:message key="authorization.title"/></title>
</head>
<body>
<div style="background-image: url(/img/login-background-blur.jpg);
background-size: cover; background-attachment: fixed; min-height: 100%; overflow: hidden">
    <jsp:include page="support/header.jsp"/>
    <div style="padding-top: 15%">
        <h5 class="neon-title-green" style="text-align: center">${requestScope.registrationComplete}</h5>
        <form class="needs-validation" novalidate method="post" action="/controller">
            <input type="hidden" name="command" value="login"/>
            <div style="max-width: 350px;margin: auto">
                <h2 class="neon-title-cyan" style="text-align: center"><fmt:message key="authorization.title"/></h2>
                <div class="form-group" style="margin-bottom: 14px">
                    <label class="neon-title-white" for="txtLogin"><fmt:message key="authorization.username"/></label>
                    <input name="login" style="border-width: medium"
                           class="form-control" id="txtLogin"
                           placeholder="<fmt:message key="authorization.usernamePlaceHolder"/>" required/>
                    <div class="invalid-feedback"><span class="fas fa-times"></span><fmt:message
                            key="authorization.invalidLogin"/></div>
                </div>
                <div class="form-group" style="margin-bottom: 34px;">
                    <label class="neon-title-white" for="txtPasswordAuth"><fmt:message
                            key="authorization.password"/></label>
                    <input name="password" style="border-width: medium" type="password"
                           class="form-control form-control-custom" id="txtPasswordAuth"
                           placeholder="<fmt:message key="authorization.passwordPlaceHolder"/>" required>
                    <div class="invalid-feedback"><span class="fas fa-times"></span><fmt:message
                            key="authorization.invalidPassword"/></div>
                    <c:if test="${not empty requestScope.errorSingIn}">
                        <label style="position: absolute; margin-left: 5%; color: red">
                            <fmt:message key="authorization.incorrectLoginOrPassword"/>
                        </label>
                    </c:if>
                </div>
                <input type="submit" name="btnLogin" value="<fmt:message key="authorization.btnLogin"/>" id="btnLogin"
                       class="button-glow-blue">
                <input name="clientToken" type="hidden" value="${serverToken}"/>
            </div>
        </form>
    </div>
    <div style="margin-top: 15%">
        <jsp:include page="support/footer.jsp"/>
    </div>
</div>
</body>

<script type="text/javascript">
    (function () {
        'use strict';
        window.addEventListener('load', function () {
            // Fetch all the forms we want to apply custom Bootstrap validation styles to
            var forms = document.getElementsByClassName('needs-validation');
            // Loop over them and prevent submission
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