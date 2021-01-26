<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Profile</title>
</head>
<body>
<div style="background-image: url(/img/registration-background.jpg);
background-size: cover; background-attachment: fixed; min-height: 100%; overflow: hidden">
    <jsp:include page="support/header.jsp"/>
    <div style="padding-top: 5%">
        <h2 class="neon-title-cyan-light" style="text-align:center">
            Profile
        </h2>
        <div style="max-width: 300px;margin-left: 34%;margin-right:auto; display: grid; font-size: larger">
            <div style="display: inline">
                <label class="neon-title-white">
                    Balance:
                </label>
                <a href="#" style="margin-left: 50%"><i class="fas fa-plus-circle"></i></a>
            </div>
            <div style="display: inline">
                <label class="neon-title-white">
                    Firstname:
                </label>
                <label class="neon-title-white" style="left: 100px">
                    ${sessionScope.currentUser.firstname}
                </label>
            </div>
            <label class="neon-title-white">
                Lastname: ${sessionScope.currentUser.lastname}
            </label>
            <label class="neon-title-white">
                Email: ${sessionScope.currentUser.email}
            </label>
        </div>
    </div>
    <div style="position: fixed; width: 100%; bottom: 0">
        <jsp:include page="support/footer.jsp"/>
    </div>
</div>
</body>
</html>
