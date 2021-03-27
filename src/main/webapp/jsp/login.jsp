<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="language.language"/>
<html>
<head>
    <title><fmt:message key="authorization.title"/></title>
</head>
<body>
<div style="background-image: url(/img/login-background-blur.jpg);
background-size: cover; background-attachment: fixed; min-height: 100%; overflow: hidden">
    <jsp:include page="support/header.jsp"/>
    <div style="padding-top: 15%">
        <c:if test="${requestScope.registrationComplete}">
            <h5 class="neon-title-green" style="text-align: center"><fmt:message
                    key="authorization.registration.complete"/></h5>
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
                    <a href="#" id="codeBtn" class="neon-title-blue"
                       style="float: left; text-align: center; margin-bottom: 3%; text-decoration: none; cursor: pointer"
                       data-toggle="modal" data-target="#forgotPasswordModal"><fmt:message key="authorization.forgot"/>
                    </a>
                    <c:if test="${not empty requestScope.errorSingIn}">
                        <p class="neon-title-red" style="text-align: center;float: right">
                            <fmt:message key="authorization.incorrectLoginOrPassword"/>
                        </p>
                    </c:if>
                    <c:if test="${not empty requestScope.checkEmail}">
                        <p class="neon-title-green" style="text-align: center;float: right">
                            <fmt:message key="authorization.check"/>
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

<div class="modal" style="border: none !important; background-color: rgba(0,0,0,0.5) !important;"
     id="forgotPasswordModal"
     tabindex="-1" role="dialog"
     aria-hidden="true">
    <div class="modal-dialog center-modal" style="border: none !important;" role="document">
        <div class="modal-content text-center" style="background-color: rgba(161,161,161,0.9)">
            <div class="modal-header neon-title-white-light" style="border-bottom: 0 none;">
                <fmt:message key="authorization.reset.title"/>
                <button type="button" id="clsBtn" class="close neon-title-red" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form method="post" action="${pageContext.request.contextPath}/forgotPassword.do">
                    <div style="position: relative; margin-left: 1%; margin-top: 1%">
                        <input type="hidden" name="command" value="forgot_password"/>
                        <input name="clientToken" type="hidden" value="${sessionScope.serverToken}"/>
                        <input type="hidden" name="currentPage" value="${pageContext.request.requestURI}">
                        <div class="neon-title-cyan-light"
                             style="width:300px; cursor: pointer; display: inline">
                            <input name="reestablishValue" class="custom-user-input" type="text"
                                   placeholder="<fmt:message key="authorization.email.or.login"/>" required
                                   pattern="(^[a-z0-9]([_](?![_])|[a-zA-Z0-9]){4,10}[a-z0-9]$)|(^[\w!#$%&'*+/=?`{|}~^-]+(?:\.[\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,6}$)"
                                   minlength="6" maxlength="320"
                                   style="box-shadow:0 0 30px #ffffff;"/>
                            <input type="submit" class="button-search-gray"
                                   value="<fmt:message key="authorization.reset.btn"/>"
                                   style="position: relative; margin-top: 5%;border-radius: 5%; height: 40px; background: rgba(154,154,154,0.5)"/>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


<script src="${pageContext.request.contextPath}/js/custom-validation.js" type="text/javascript"></script>
<script type="text/javascript">
    $(document).ready(function () {
        var needAuthorization = ${not empty requestScope.loginFirst};
        if (needAuthorization) {
            funcBtns.alertWarning('<fmt:message key='authorization.needAuthorization'/>');
        }
    });
</script>
</body>
</html>