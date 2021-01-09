<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">


    <script src="https://kit.fontawesome.com/c1f7a487ad.js" crossorigin="anonymous"></script>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

</head>
<body>
<nav class="navbar navbar-dark bg-dark" style="line-height: normal">
    <div class="navbar-brand">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation" style="">
            <span class="navbar-toggler-icon"></span>
        </button>
        <a href="/index.jsp" style="color: white; text-decoration: none">Web task</a>
    </div>

    <div class="navbar pull-right">
        <div style="display: inline-grid">
            <c:if test="${empty sessionScope.firstname}">
                <a href="${pageContext.request.contextPath}/jsp/registration.jsp"
                   style="text-decoration: none;color:lawngreen;">
                    <span class="far fa-user" style="margin-right:0.40em; display:inline-block;"></span>Sign up</a>
                <a href="${pageContext.request.contextPath}/jsp/login.jsp"
                   style="text-decoration: none;color:#ff9900">
                    <span class="fas fa-sign-in-alt" style="margin-right:0.30em; display:inline-block;"></span>Login</a>
            </c:if>
        </div>

        <div style="display: inline">
            <c:if test="${not empty sessionScope.firstname}">
                <div class="nav-item dropdown">
                    <a class="nav-link" href="" id="navbarDropdown" role="button"
                       data-toggle="dropdown" aria-haspopup="true"
                       style="text-decoration: none; color:#56ff42; margin-left: 6.0em"><span
                            class="fas fa-user" style="margin-right:0.40em"></span>${sessionScope.get("firstname")}</a>

                    <div class="dropdown-menu" aria-labelledby="navbarDropdown"
                         style="text-align: center; border: none; padding-top: 5px; padding-bottom: 5px">
                        <a class="dropdown-item" href="#">Action</a>
                        <a class="dropdown-item" href="#">Another action</a>
                        <div class="dropdown-divider"
                             style="padding: 0 0;"></div>
                        <form method="post" action="/controller" style="height: 15px">
                            <input type="hidden" name="command" value="logout"/>
                            <button class="btn btn-outline-danger"
                                    style="border: none; min-width: 100%"
                                    type="submit">Log out
                            </button>
                        </form>
                    </div>
                </div>
            </c:if>
        </div>
    </div>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Link</a>
            </li>

            <li class="nav-item">
                <a class="nav-link disabled" href="#">Disabled</a>
            </li>
        </ul>
        <form class="form-inline my-2 my-lg-0">
            <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form>
    </div>
</nav>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
</body>
</html>

