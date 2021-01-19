<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <script src="https://kit.fontawesome.com/c1f7a487ad.js" crossorigin="anonymous"></script>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/text-style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/button-style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/form-style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer-style.css">
</head>
<body style="background-image: url(/img/registration-background.jpg);
background-size: cover; background-attachment: fixed; min-height: 100%">

<nav class="navbar fixed-top navbar-dark"
     style="padding-top: 2px;padding-bottom: 2px; zoom: 0.9;
      background-image: linear-gradient(45deg, rgba(255,255,255,.07) 100%, transparent 10%)">
    <div class="navbar-brand">
        <a href="/index.jsp" class="texas-blue-animated" style="text-decoration: none">Web task</a>
        <a href="/index.jsp" class="texas-purple-animated" style="text-decoration: none;margin-left: 30px">Link</a>
    </div>

    <div class="navbar pull-right">
        <div style="display: inline-grid">
            <c:if test="${empty sessionScope.firstname}">
                <a href="${pageContext.request.contextPath}/jsp/registration.jsp"
                   style="text-decoration: none" class="neon-title-lime">
                    <span class="far fa-user" style="margin-right:0.40em; display:inline-block;"></span>Sign up</a>
                <a href="${pageContext.request.contextPath}/jsp/login.jsp"
                   style="text-decoration: none" class="neon-title-orange">
                    <span class="fas fa-sign-in-alt" style="margin-right:0.30em; display:inline-block;"></span>Login</a>
            </c:if>
        </div>

        <div style="display: inline">
            <c:if test="${not empty sessionScope.firstname}">
                <div class="nav-item dropdown">
                    <a class="nav-link neon-title-lime" href="" id="navbarDropdown" role="button"
                       data-toggle="dropdown" aria-haspopup="true"
                       style="text-decoration: none; color:#56ff42; margin-left: 6.0em"><span class="fas fa-user"
                                                                                              style="margin-right:0.40em">
                    </span>${sessionScope.get("firstname")}
                    </a>

                    <div class="dropdown-menu" aria-labelledby="navbarDropdown"
                         style="background:  rgba(152,148,148,0.2);
                          border: none; margin-left: 5%; margin-top: 15px">
                        <a class="dropdown-item neon-title-white"
                           style="text-align: center; background-color: transparent"
                           href="${pageContext.request.contextPath}/jsp/profile.jsp">Profile</a>
                        <a class="dropdown-item neon-title-white"
                           style="text-align: center; background-color: transparent"
                           href="#">Another action</a>
                        <form method="post" action="/controller" style="height: 15px">
                            <input type="hidden" name="command" value="logout"/>
                            <button class="btn btn-outline-danger neon-title-red button-border-red"
                                    type="submit">Log out
                            </button>
                        </form>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</nav>
<br>
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

