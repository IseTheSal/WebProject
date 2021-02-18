<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="language.language"/>
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
        <form autocomplete="off" method="get" action="${pageContext.request.contextPath}/toCart.do">
            <div style="position: absolute; margin-left: 1%; margin-top: 1%">
                <input type="hidden" name="command" value="open_game"/>
                <button type="submit" value="" class="button-search-purple"
                        style="display: inline; position: absolute;margin-left: 190px; height: 30px; border-radius: 100%; width: 30px; background: rgba(11,79,175,0.5)">
                    <span class="fa fa-search search-span"></span>
                </button>
                <div class="autocomplete; neon-title-cyan-light" style="width:300px; cursor: pointer; display: inline;">
                    <input id="myInput" class="custom-input" type="text" placeholder="<fmt:message key="main.search"/>"
                           style="color: black;border-radius: 10%; box-shadow:0 0 30px #0B4FAF;"/>
                    <input type="hidden" class="gameIdClass" value="1" name="gameId"/>
                </div>
            </div>
        </form>

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
            <div style="text-align: center;margin-bottom: 5%; font-size: 20px" class="notcopy">
                <div id="prev" style="cursor: pointer;display: inline;" class=""></div>
                <div id="nav" style="cursor: pointer; display: inline;" class="text-center"></div>
                <div id="next" style="cursor: pointer;display: inline;" class=""></div>
            </div>
            <jsp:include page="support/footer.jsp"/>
        </div>
    </div>
</body>

<%----%>
<script>
    function autocomplete(inp, arr, idArr) {
        var currentFocus;
        inp.addEventListener("input", function (e) {
            var a, b, i, val = this.value;
            closeAllLists();
            if (!val) {
                return false;
            }
            currentFocus = -1;
            a = document.createElement("DIV");
            a.setAttribute("id", this.id + "autocomplete-list");
            a.setAttribute("class", "autocomplete-items");
            this.parentNode.appendChild(a);
            /*for each item in the array...*/
            for (i = 0; i < arr.length; i++) {
                if (arr[i].substr(0, val.length).toUpperCase() == val.toUpperCase()) {
                    b = document.createElement("DIV");
                    b.innerHTML = "<strong class='neon-title-white'>" + arr[i].substr(0, val.length) + "</strong>";
                    b.innerHTML += arr[i].substr(val.length);
                    var list = document.getElementsByClassName('gameIdClass');
                    var n;
                    for (n = 0; n < list.length; ++n) {
                        list[n].value = idArr[i];
                    }
                    b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";
                    b.addEventListener("click", function (e) {
                        inp.value = this.getElementsByTagName("input")[0].value;
                        closeAllLists();
                    });
                    a.appendChild(b);
                }
            }
        });
        inp.addEventListener("keydown", function (e) {
            var x = document.getElementById(this.id + "autocomplete-list");
            if (x) x = x.getElementsByTagName("div");
            if (e.keyCode == 40) {
                currentFocus++;
                addActive(x);
            } else if (e.keyCode == 38) {
                currentFocus--;
                addActive(x);
            } else if (e.keyCode == 13) {
                e.preventDefault();
                if (currentFocus > -1) {
                    if (x) x[currentFocus].click();
                }
            }
        });

        function addActive(x) {
            if (!x) return false;
            removeActive(x);
            if (currentFocus >= x.length) currentFocus = 0;
            if (currentFocus < 0) currentFocus = (x.length - 1);
            x[currentFocus].classList.add("autocomplete-active");
        }

        function removeActive(x) {
            for (var i = 0; i < x.length; i++) {
                x[i].classList.remove("autocomplete-active");
            }
        }

        function closeAllLists(elmnt) {
            var x = document.getElementsByClassName("autocomplete-items");
            for (var i = 0; i < x.length; i++) {
                if (elmnt != x[i] && elmnt != inp) {
                    x[i].parentNode.removeChild(x[i]);
                }
            }
        }

        document.addEventListener("click", function (e) {
            closeAllLists(e.target);
        });
    }

    var col = $('.card-title');
    var gameNames = [];
    var col1 = $('.custom-card');
    var gameId = [];
    for (i = 0; i < col.length; i++) {
        gameNames.push(col[i].innerHTML);
        gameId.push(col1[i].href.slice(-1));
    }
    autocomplete(document.getElementById("myInput"), gameNames, gameId);
</script>
<script>
    $(document).ready(function () {
        var colShown = 3;
        var colTotal = $('.t1 .col').length;
        var numPages = colTotal / colShown;
        $('#prev').append('<a id="prevLink"  style="color:#d2d2d2" class="button-glow-gray" rel="' + 1 + '">&emsp;' + '&#60;&#60;<fmt:message key="main.page.previous"/>' + '&emsp;</a> ');
        for (i = 0; i < numPages; i++) {
            var pageNum = i + 1;
            $('#nav').append('<a style="color: #f4fffd; border-radius: 20%;text-align: center" class="neon-title-white btn-outline-secondary" rel="' + i + '">&emsp;' + pageNum + '&emsp;</a> ');
        }
        $('#next').append('<a id="nextLink" style="color:#d2d2d2" class="button-glow-gray" rel="' + 3 + '">&emsp;' + '<fmt:message key="main.page.next"/>&#62;&#62;' + '&emsp;</a> ');


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

<script>
    $(document).ready(function () {
        var orderCreated = ${requestScope.orderCreated};
        if (orderCreated) {
            funcBtns.alertOkOnlyTitle('<fmt:message key='main.orderSuccess'/>');
        }
    });
</script>

</html>