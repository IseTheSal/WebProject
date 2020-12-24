<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<form name="registrationForm" method="post" action="controller">
    <input type="hidden" name="command" value="registration"/>
    Имя:<br/>
    <input type="text" name="name" value=""/>
    <br/>Логин:<br/>
    <input type="text" name="login" value=""/>
    <br/>Пароль:<br/>
    <input type="password" name="password" value=""/>
    <br/>
    ${errorRegistrationMessage}
    <br/>
    <input type="submit" value="Register">
</body>
</html>
