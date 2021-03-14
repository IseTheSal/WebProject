<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="language.language"/>
<head>
    <title><fmt:message key="authorization.title"/></title>
</head>
<body>
<div style="background-image: url(/img/login-background-blur.jpg);
background-size: cover; background-attachment: fixed; min-height: 100%; overflow: hidden">
    <jsp:include page="support/header.jsp"/>
    <div style="padding-top: 15%">
        <c:if test="${requestScope.registrationComplete}">
            <h5 class="neon-title-green" style="text-align: center">Registration complete</h5>
        </c:if>
        <form class="needs-validation" novalidate method="post" action="${pageContext.request.contextPath}/login.do">
            <input type="hidden" name="command" value="login"/>
            <input name="clientToken" type="hidden" value="${sessionScope.serverToken}"/>
            <input type="hidden" name="currentPage" value="${pageContext.request.requestURI}">
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
                <div class="form-group">
                    <label class="neon-title-white" for="txtPasswordAuth"><fmt:message
                            key="authorization.password"/></label>
                    <input name="password" style="border-width: medium" type="password"
                           class="form-control form-control-custom" id="txtPasswordAuth"
                           placeholder="<fmt:message key="authorization.passwordPlaceHolder"/>" required>
                    <div class="invalid-feedback"><span class="fas fa-times"></span><fmt:message
                            key="authorization.invalidPassword"/></div>
                    <c:if test="${not empty requestScope.errorSingIn}">
                        <p style="text-align: center; color: red">
                            <fmt:message key="authorization.incorrectLoginOrPassword"/>
                        </p>
                    </c:if>
                </div>
                <input type="submit" name="btnLogin" value="<fmt:message key="authorization.btnLogin"/>" id="btnLogin"
                       class="button-glow-blue">
            </div>
        </form>
    </div>
    <div style="margin-top: 18%">
        <jsp:include page="support/footer.jsp"/>
    </div>
</div>
</body>
<script type="text/javascript">
    $(document).ready(function () {
        var needAuthorization = ${not empty requestScope.loginFirst};
        if (needAuthorization) {
            funcBtns.alertWarning('<fmt:message key='authorization.needAuthorization'/>');
        }
    });
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