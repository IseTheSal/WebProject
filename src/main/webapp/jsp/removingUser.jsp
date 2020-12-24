<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Remove</title>
</head>
<h2>Remove by id</h2>
<body>
<form name="registrationForm" method="post" action="controller">
    <input type="hidden" name="command" value="remove_user"/>
    ${requestScope.get("userName")}
    ${requestScope.get("userPassword")}
    <br/>ID:<br/>
    <input type="text" name="userId" value=""/>
    <br/>
    ${errorRemovingMessage}
    <br/>
    <input type="submit" value="Remove">
</form>
</body>
</html>
