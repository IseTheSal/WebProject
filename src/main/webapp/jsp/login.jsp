<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>Authorization</title>
</head>
<body>
<div style="background-image: url(/img/login-background-blur.jpg);
background-size: cover; background-attachment: fixed; min-height: 100%; overflow: hidden">
    <%@include file="header.jsp" %>
    <div style="padding-top: 15%">
        <h5 class="neon-title-green" style="text-align: center">${requestScope.registrationComplete}</h5>
        <form class="needs-validation" novalidate method="post" action="/controller">
            <input type="hidden" name="command" value="login"/>
            <div style="max-width: 350px;margin: auto">
                <h2 class="neon-title-cyan" style="text-align: center"> Authorization </h2>
                <div class="form-group" style="margin-bottom: 14px">
                    <label class="neon-title-white" for="txtLogin">Username</label>
                    <input name="login" style="border-width: medium"
                           class="form-control" id="txtLogin"
                           placeholder="Enter login" required/>
                    <div class="invalid-feedback"><span class="fas fa-times"></span>Login can`t be empty</div>
                </div>
                <div class="form-group" style="margin-bottom: 34px;">
                    <label class="neon-title-white" for="txtPasswordAuth">Password</label>
                    <input name="password" style="border-width: medium" type="password"
                           class="form-control form-control-custom" id="txtPasswordAuth"
                           placeholder="Enter password" required>
                    <div class="invalid-feedback"><span class="fas fa-times"></span>Password can`t be empty</div>
                    <label style="position: absolute; margin-left: 5%; color: red"> ${errorSingIn} </label>
                </div>
                <input type="submit" name="btnLogin" value="Login" id="btnLogin" class="button-glow-blue">
            </div>
        </form>
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