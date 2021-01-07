<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<body>
<%@include file="header.jsp" %>
<h5 style="color: green; text-align: center">${requestScope.registrationComplete}</h5>
</body>
<form method="post" action="/controller">
    <input type="hidden" name="command" value="login"/>
    <div style="max-width: 350px;margin-right: auto; margin-left: auto">
        <h2 style="text-align: center"> Authorisation </h2>
        <label for="txtUsername">Username</label>
        <input name="login" type="text" id="txtUsername"
               class="form-control" placeholder="Enter Username" required/>
        <br/>
        <label for="txtPassword">Password</label>
        <input name="password" type="password" id="txtPassword"
               class="form-control" placeholder="Enter Password" required/>
        <h5 style="text-align: center; color: red"> ${errorSingIn} </h5>
        <hr/>
        <input type="submit" name="btnSignup" value="Login" id="btnSignup" class="btn btn-primary"
               style="margin-left: 290px"/>
    </div>
</form>