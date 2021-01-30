<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>Main</title>
</head>
<html>
<body>
<div style="background-image: url(/img/registration-background.jpg);
background-size: cover; background-attachment: fixed; min-height: 100%; overflow: hidden">
    <jsp:include page="support/header.jsp"/>
    <div style="padding-top: 5%">
        <div class="container">
            <div class="row row-cols-3" style="row-gap: 60px; margin-left: 45px">
                <div class="col">
                    <a href="#" class="card custom-card" style="width: 18rem; text-decoration: none">
                        <img class="image-card" src="https://upload.wikimedia.org/wikipedia/en/f/f8/ACValhalla.jpg">
                        <div class="card-body" style="text-align: center">
                            <p class="card-title neon-title-white">Assassin`s creed: VALHALLA</p>
                            <p class="card-text neon-title-white">30$</p>
                        </div>
                    </a>
                </div>
                <div class="col">
                    <a href="#" class="card custom-card" style="width: 18rem; text-decoration: none">
                        <img class="image-card" src="https://mir-s3-cdn-cf.behance.net/project_modules/2800_opt_1/81a4e680815973.5cec6bcf6aa1a.jpg">
                        <div class="card-body" style="text-align: center">
                            <p class="card-title neon-title-white">Cyberpunk 2077</p>
                            <p class="card-text neon-title-white">30$</p>
                        </div>
                    </a>
                </div>
                <div class="col">
                    <a href="#" class="card custom-card" style="width: 18rem; text-decoration: none">
                        <img class="image-card"
                             src="https://upload.wikimedia.org/wikipedia/en/4/40/Black_Ops_Cold_War.jpeg">
                        <div class="card-body" style="text-align: center">
                            <p class="card-title neon-title-white">Call of duty: COLD WAR</p>
                            <p class="card-text neon-title-white">30$</p>
                        </div>
                    </a>
                </div>
                <div class="col">
                    <a href="#" class="card custom-card" style="width: 18rem; text-decoration: none">
                        <img class="image-card" src="https://wallpapercave.com/wp/wp7480782.jpg">
                        <div class="card-body" style="text-align: center">
                            <p class="card-title neon-title-white">GHOSTRUNNER</p>
                            <p class="card-text neon-title-white">10$</p>
                        </div>
                    </a>
                </div>
                <div class="col">
                    <a href="#" class="card custom-card" style="width: 18rem; text-decoration: none">
                        <img class="image-card"
                             src="https://upload.wikimedia.org/wikipedia/en/thumb/d/dc/Resident_Evil_3.jpg/220px-Resident_Evil_3.jpg">
                        <div class="card-body" style="text-align: center">
                            <p class="card-title neon-title-white">Resident Evil 3</p>
                            <p class="card-text neon-title-white">20$</p>
                        </div>
                    </a>
                </div>
                <div class="col">
                    <a href="#" class="card custom-card" style="width: 18rem; text-decoration: none">
                        <img class="image-card"
                             src="https://thumbnails.pcgamingwiki.com/2/2d/Death_Stranding_cover.jpg/300px-Death_Stranding_cover.jpg">
                        <div class="card-body" style="text-align: center">
                            <p class="card-title neon-title-white">Death Stranding</p>
                            <p class="card-text neon-title-white">40$</p>
                        </div>
                    </a>
                </div>
            </div>
        </div>
        <div style="margin-top: 5%">
            <jsp:include page="support/footer.jsp"/>
        </div>
    </div>
</body>

</html>