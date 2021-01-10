<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<head>
    <title>Registration</title>
</head>
<body>
<div style="background-image: url(https://cutewallpaper.org/21/cyberpunk-steam-backgrounds/Steam-Community-Guide-Beautiful-Steam-Backgrounds.jpg);
background-size: cover; background-attachment: fixed; min-height: 100%; overflow: hidden">
    <%@include file="header.jsp" %>
    <div style="padding-top: 70px">
    <form class="needs-validation" novalidate method="post" action="/controller">
        <input type="hidden" name="command" value="registration"/>
        <div style="max-width: 570px;margin-right: auto; margin-left: auto;zoom: 0.95">
            <h2 class="neon-title-cyan" style="text-align:center">Registration</h2>
            <div class="form-group" style="margin-bottom: 5px">
                <label class="neon-title-white" for="txtUsername"
                       style="position: relative;margin-bottom: 1px">Username</label>
                <div class="form-inline">
                    <input name="login" id="txtUsername"
                           type="text" style="width: 300px; border-width: medium"
                           class="form-control" aria-describedby="usernameHelp"
                           placeholder="Enter username"
                           required pattern="^[a-z0-9]([_](?![_])|[a-zA-Z0-9]){4,10}[a-z0-9]$"
                           minlength="6" maxlength="12"/>
                    <small id="usernameHelp" style="margin-left: 20px;
                 white-space: pre-line; color: darkgrey"> May contain only letters, numbers, and _
                        Cannot start or end with _
                        Can be 6 to 12 characters long
                    </small>
                    <div class="valid-feedback"><span class="fas fa-check"></span>Ok</div>
                    <div class="invalid-feedback"><span class="fas fa-times"></span>Invalid input</div>
                </div>
            </div>
            <div class="form-group" style="margin-bottom: 14px">
                <label class="neon-title-white" for="txtPassword">Password</label>
                <div class="form-inline">
                    <input name="password" style="width: 300px; border-width: medium" type="password"
                           class="form-control" id="txtPassword"
                           placeholder="Enter password"
                           aria-describedby="passwordHelp" required pattern="[a-zA-Z0-9]{8,20}">
                    <small id="passwordHelp" style="margin-left: 20px; color: darkgrey;
                 white-space: pre-line"> May contain only letters and numbers
                        Can be 8 to 20 characters long
                    </small>
                    <div class="valid-feedback"><span class="fas fa-check"></span>Ok</div>
                    <div class="invalid-feedback"><span class="fas fa-times"></span>Invalid input</div>
                </div>
            </div>
            <div class="form-group" style="margin-bottom: 14px">
                <label class="neon-title-white" for="txtConfirmPassword">Confirm</label>
                <div class="form-inline">
                    <input name="repeatPassword" style="width: 300px; border-width: medium" type="password"
                           class="form-control"
                           id="txtConfirmPassword"
                           placeholder="Repeat password"
                           aria-describedby="passwordHelp" required pattern="[a-zA-Z0-9]{8,20}">
                    <div class="valid-feedback"><span class="fas fa-check"></span></div>
                    <div class="invalid-feedback"><span class="fas fa-times"></span>Passwords don't match</div>
                </div>
            </div>
            <div class="form-class" style="margin-bottom: 14px">
                <label class="neon-title-white" for="txtFirstname">Firstname</label>
                <div class="form-inline">
                    <input name="firstname" style="width: 300px; border-width: medium" type="text"
                           class="form-control"
                           id="txtFirstname"
                           placeholder="Enter firstname"
                           aria-describedby="firstnameHelp" required pattern="^[A-Za-z|А-я]{2,20}$">
                    <small id="firstnameHelp" style="margin-left: 20px; color: darkgrey">
                        May contain only latin or cyrillic letters
                    </small>
                    <div class="valid-feedback"><span class="fas fa-check"></span>Ok</div>
                    <div class="invalid-feedback"><span class="fas fa-times"></span>Invalid input</div>
                </div>
            </div>
            <div class="form-class" style="margin-bottom: 14px">
                <label class="neon-title-white" for="txtLastname">Lastname</label>
                <div class="form-inline">
                    <input name="lastname" style="width: 300px; border-width: medium" type="text"
                           class="form-control"
                           id="txtLastname"
                           placeholder="Enter lastname"
                           aria-describedby="lastnameHelp" required pattern="^[A-Za-z|А-я]{2,20}$">
                    <small id="lastnameHelp" style="margin-left: 20px; color: darkgrey">
                        May contain only latin or cyrillic letters
                    </small>
                    <div class="valid-feedback"><span class="fas fa-check"></span>Ok</div>
                    <div class="invalid-feedback"><span class="fas fa-times"></span>Invalid input</div>
                </div>
            </div>
            <div class="form-class" style="margin-bottom: 14px">
                <label class="neon-title-white" for="txtEmail">Email</label>
                <div class="form-inline">
                    <input name="email" style="width: 300px;border-width: medium" class="form-control" id="txtEmail"
                           placeholder="Enter email"
                           aria-describedby="emailHelp" required type="email">
                    <small id="emailHelp" style="margin-left: 20px; color: darkgrey">
                        (Example: ivan.ivanov@gmail.com)
                    </small>
                    <div class="valid-feedback"><span class="fas fa-check"></span>Ok</div>
                    <div class="invalid-feedback"><span class="fas fa-times"></span>Invalid input</div>
                </div>
            </div>
            <div class="form-group form-check">
                <label class="form-check-label" style="color: white">
                    <input class="form-check-input" type="checkbox" name="remember" required/>
                    I am 18+ years old and agree with <a href="/jsp/terms.jsp"
                                                         target="_blank">terms conditions and privacy policy.</a>
                    <div class="invalid-feedback">Agree to continue</div>
                </label>
            </div>
            <label class="form-inline"
                   style="margin-left: auto; margin-right: auto; color: red"> ${registrationFail} </label>
            <input type="submit" name="btnSignup" value="Sign up" id="btnSignup" class="button-glow-blue">
        </div>
    </form>
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