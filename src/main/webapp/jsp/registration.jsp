<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src='https://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.3.min.js'></script>
<script type="text/javascript"
        src='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.0.3/js/bootstrap.min.js'></script>
<link rel="stylesheet" href='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.0.3/css/bootstrap.min.css'
      media="screen"/>

<form method="post" action="controller">
    <input type="hidden" name="command" value="registration"/>
    <div style="max-width: 400px;margin-right: auto; margin-left: auto">
        <h2 style="text-align: center"> Registration</h2>
        <label for="txtUsername">
            Username</label>
        <input name="login" type="text" id="txtUsername"
               title="Username must contain only lower upper case letters and/or numbers from 8 to 20 symbols"
               class="form-control" placeholder="Enter Username" required pattern="^[a-z0-9]{6,15}"/>
        <br/>
        <label for="txtPassword">
            Password</label>
        <input name="password" type="password" id="txtPassword"
               title="Password must contain letters and/or numbers from 8 to 20 symbols"
               class="form-control" placeholder="Enter Password" required pattern="[a-zA-Z0-9]{8,20}"/>
        <br/>
        <label for="txtConfirmPassword">
            Confirm Password</label>
        <input name="repeatPassword" type="password" id="txtConfirmPassword"
               class="form-control" placeholder="Confirm Password"/>
        <br/>
        <label for="txtFirstname">
            Firstname</label>
        <input name="name" type="text" id="txtFirstname"
               title="Firstname must contain letters from 2 to 20 symbols"
               class="form-control" placeholder="Enter firstname" required pattern="^[A-Za-z|А-Яа-я]{2,20}"/>
        <br/>
        <label for="txtLastname">
            Lastname</label>
        <input name="lastname" type="text" id="txtLastname"
               title="Lastname must contain letters from 2 to 20 symbols"
               class="form-control" placeholder="Enter lastname" required pattern="^[A-Za-z|А-Яа-я]{2,20}"/>
        <br/>
        <label for="txtEmail">
            Email</label>
        <input name="email" id="txtEmail" class="form-control" placeholder="Enter Email"
               required type="email"/>
        <h5 style="text-align: center; color: red"> ${registrationFail} </h5>
        <hr/>
        <input type="submit" name="btnSignup" value="Sign up" id="btnSignup" class="btn btn-primary"/>
    </div>
</form>
<script type="text/javascript">
    window.onload = function () {
        var txtPassword = document.getElementById("txtPassword");
        var txtConfirmPassword = document.getElementById("txtConfirmPassword");
        txtPassword.onchange = ConfirmPassword;
        txtConfirmPassword.onkeyup = ConfirmPassword;

        function ConfirmPassword() {
            txtConfirmPassword.setCustomValidity("");
            if (txtPassword.value != txtConfirmPassword.value) {
                txtConfirmPassword.setCustomValidity("Passwords do not match.");
            }
        }
    }
</script>