<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Main</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">

    <div class="collapse navbar-collapse" id="logoutButton">
        <form action="controller" method="post" class="form-inline my-2 my-lg-0">
            <input type="hidden" name="command" value="logout">
            <button style="margin-inline-end: revert" class="btn btn-outline-danger" type="submit">Logout
            </button>
        </form>
    </div>
</nav>
<h1 style="text-align: center;">Hello ${name}</h1>
</body>
</html>