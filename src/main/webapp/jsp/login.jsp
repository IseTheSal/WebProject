<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src='https://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.3.min.js'></script>
<script type="text/javascript"
        src='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.0.3/js/bootstrap.min.js'></script>
<link rel="stylesheet" href='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.0.3/css/bootstrap.min.css'
      media="screen"/>
<body>
<%@include file="header.jsp" %>
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