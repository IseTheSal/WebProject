<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="property.language"/>
<head>
    <title>Main</title>
    <link rel="stylesheet" type="text/css"
          href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.4.1/css/bootstrap.min.css">
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

</head>
<html>
<body>
<div style="background-image: url(/img/registration-background.jpg);
background-size: cover; background-attachment: fixed; min-height: 100%; overflow: hidden">
    <jsp:include page="support/header.jsp"/>
    <div style="padding-top: 5%">
        <div class="container t1">
            <div class="row row-cols-3" style="row-gap: 60px; margin-left: 45px">
                <c:forEach items="${sessionScope.gameList}" var="game">
                    <div class="col">
                        <a href="${pageContext.request.contextPath}/toCart.do?command=open_game&gameId=${game.id}"
                           class="card custom-card" style="width: 18rem; text-decoration: none">
                            <img class="image-card" src="${pageContext.request.contextPath}${game.imagePath}">
                            <div class="card-body" style="text-align: center">
                                <p class="card-title neon-title-white">${game.title}</p>
                                <p class="card-text neon-title-white">${game.price}$</p>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div style="margin-top: 10%">
            <div style="text-align: center; margin-bottom: 5%; font-size: 20px" class="notcopy">
                <div id="prev" style="cursor: pointer;display: inline;" class="text-center"></div>
                <div id="nav" style="cursor: pointer; display: inline;" class="text-center"></div>
                <div id="next" style="cursor: pointer;display: inline;" class="text-center"></div>
            </div>
            <jsp:include page="support/footer.jsp"/>
        </div>
    </div>
</body>

<script>
    $(document).ready(function () {
        var colShown = 3;
        var colTotal = $('.t1 .col').length;
        var numPages = colTotal / colShown;
        $('#prev').append('<a id="prevLink"  style="color:#d2d2d2" class="button-glow-gray" rel="' + 1 + '">&emsp;' + '&#60;&#60;Previous' + '&emsp;</a> ');
        for (i = 0; i < numPages; i++) {
            var pageNum = i + 1;
            $('#nav').append('<a style="color: #f4fffd; border-radius: 20%" class="neon-title-white btn-outline-secondary" rel="' + i + '">&emsp;' + pageNum + '&emsp;</a> ');
        }
        $('#next').append('<a id="nextLink" style="color:#d2d2d2" class="button-glow-gray" rel="' + 3 + '">&emsp;' + 'Next>>' + '&emsp;</a> ');


        $('.t1 .col').hide();
        $('.t1 .col').slice(0, colShown).show();
        $('#nav a:nth-child(1)').addClass('active');
        updateLinks();
        $('#prev a').bind('click', function (e) {
                $('a.active').removeClass('active').prev().addClass('active');
                var currPage = $('a.active').attr('rel');
                var startItem = currPage * colShown;
                var endItem = startItem + colShown;
                updateLinks();
                $('.t1 .col').css('opacity', '0').hide().slice(startItem, endItem).css('display', 'flex').animate({
                    opacity: 1
                }, 300);
            }
        );


        $('#next a').bind('click', function (e) {
            $('a.active').removeClass('active').next().addClass('active');
            var currPage = $('a.active').attr('rel');
            var startItem = currPage * colShown;
            var endItem = startItem + colShown;
            updateLinks();
            $('.t1 .col').css('opacity', '0').hide().slice(startItem, endItem).css('display', 'flex').animate({
                opacity: 1
            }, 300);

        });

        $('#nav a').bind('click', function (e) {
            e.preventDefault();
            $('#nav a').removeClass('active');
            $(this).addClass('active');
            var currPage = $(this).attr('rel');
            var startItem = currPage * colShown;
            var endItem = startItem + colShown;

            updateLinks();

            $('.t1 .col').css('opacity', '0').hide().slice(startItem, endItem).css('display', 'flex').animate({
                opacity: 1
            }, 300);
        });

        function updateLinks() {
            var page = $('a.active').attr('rel');
            var prev = document.getElementById("prev");
            if (page == 0) {
                prev.style.pointerEvents = "none";
            } else {
                prev.style.pointerEvents = "auto";
            }

            var next = document.getElementById("next");
            if (page == Math.ceil(numPages) - 1) {
                next.style.pointerEvents = "none";
            } else {
                next.style.pointerEvents = "auto";
            }
        }
    });
</script>

</html>