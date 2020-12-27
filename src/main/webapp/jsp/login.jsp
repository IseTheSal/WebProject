<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form name="loginForm" method="post" action="controller">
    <input type="hidden" name="command" value="login"/>
    Логин:<br/>
    <input type="text" name="login" value=""/>
    <br/>Пароль:<br/>
    <input type="password" name="password" value=""/>
    <br/>
    ${errorSingInMessage}
    <br/>
    <input type="submit" value="Log in">
</form>
</body>
</html>