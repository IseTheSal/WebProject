<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="language.language"/>
<head>
    <title><fmt:message key="main.page.title"/></title>
    <link rel="icon" href="${pageContext.request.contextPath}/favicon.png"/>
    <link rel="stylesheet" type="text/css"
          href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.4.1/css/bootstrap.min.css">
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/price-slider.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/checkbox-style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/radioButton-style.css">

</head>
<html>
<body>
<div style="background-image: url(/img/registration-background.jpg);
    background-size: cover; background-attachment: fixed; min-height: 100%; overflow: hidden;">
    <jsp:include page="support/header.jsp"/>

    <div style="padding-top: 5%">
        <form method="get" action="${pageContext.request.contextPath}/sort.do" id="sortForm" class="notcopy"
              style="position: absolute; right: 16.5%; top: 11%">
            <input type="hidden" name="command" value="order_games">
            <strong style="color:white;"><fmt:message key="main.sort"/></strong>
            <input class="custom-radio" type="radio" id="titleAsc" name="sortBy" value="titleAsc"
                   onclick="sortSubmit()" ${sessionScope.sortBy.equals('titleAsc') ? "checked" : ""}>
            <label for="titleAsc"><i style="color: white" class="fas fa-sort-alpha-down"></i></label>

            <input class="custom-radio" type="radio" id="titleDesc" name="sortBy" value="titleDesc"
                   onclick="sortSubmit()" ${sessionScope.sortBy.equals('titleDesc') ? "checked" : ""}>
            <label for="titleDesc"><i style="color: white" class="fas fa-sort-alpha-up"></i></label>

            <input class="custom-radio" type="radio" id="priceASC" name="sortBy" value="priceAsc"
                   onclick="sortSubmit()" ${sessionScope.sortBy.equals('priceAsc') ? "checked" : ""}>
            <label style="color: white" for="priceASC"><strong>&dollar;</strong><i class="fas fa-long-arrow-alt-up"></i></label>

            <input class="custom-radio" type="radio" id="priceDesc" name="sortBy" value="priceDesc"
                   onclick="sortSubmit()" ${sessionScope.sortBy.equals('priceDesc') ? "checked" : ""}>
            <label style="color: white" for="priceDesc"><strong>&dollar;</strong><i
                    class="fas fa-long-arrow-alt-down"></i></label>
        </form>
        <form autocomplete="off" method="get" action="${pageContext.request.contextPath}/game.do">
            <div style="position: fixed; margin-left: 1%; margin-top: 2%">
                <input type="hidden" name="command" value="open_game"/>
                <button type="submit" value="" class="button-search-blue"
                        style="display: inline; position: absolute;margin-left: 190px; height: 30px; border-radius: 100%; width: 30px; background: rgba(186,186,186,0.5)">
                    <span class="fa fa-search search-span"></span>
                </button>
                <div class="autocomplete; neon-title-white"
                     style="width:300px; cursor: pointer; display: inline;">
                    <input id="myInput" class="custom-input" type="text" placeholder="<fmt:message key="main.search"/>"
                           style="color: black;border-radius: 10%; box-shadow:0 0 30px #a8a8a8;"/>
                    <input type="hidden" id="gameIdClass" value="1" name="gameId"/>
                </div>
            </div>
        </form>
        <div style="position: fixed">
            <div class="container notcopy" style="position: fixed; float: right; margin-left: 85%; margin-top: -0.5%">
                <strong style="margin-left: 6.5%; color: white"><fmt:message key="main.price"/></strong>
                <div class="row" style="cursor: pointer; margin-top: 1%">
                    <div class="col-sm-12">
                        <div style="width: 170px" id="slider-range"></div>
                    </div>
                </div>
                <div class="row slider-labels" style="margin-bottom: 1%">
                    <div class="col-xs-6 caption neon-title-white-light" style="position: fixed !important;">
                        <span id="slider-range-value1"></span>
                    </div>
                    <div class="col-xs-6 text-right caption neon-title-white-light"
                         style="margin-left: 11.5%;position: fixed !important;">
                        <span id="slider-range-value2"></span>
                    </div>
                </div>
                <form id="filter_form" method="get" action="${pageContext.request.contextPath}/filter_price.do">
                    <input type="hidden" name="command" value="filter_game">
                    <input type="hidden" name="minPrice" id="fromPrice" value="${sessionScope.minPrice + 0}">
                    <input type="hidden" name="maxPrice" id="toPrice" value="${sessionScope.maxPrice + 0}">
                    <strong style="padding-left: 5.5%;color: white"><fmt:message key="main.genres"/></strong>
                    <div class="checkbox-container">
                        <ul class="ks-cboxtags">
                            <li><input type="checkbox" id="checkboxOneCt" class="genre"
                            ${sessionScope.genreFilter.contains('ACTION') ? "checked" : ""}
                                       value="ACTION"><label
                                    for="checkboxOneCt"><fmt:message key="main.genre.action"/></label></li>
                            <li><input type="checkbox" id="checkboxTwoCt" class="genre"
                            ${sessionScope.genreFilter.contains('ADVENTURE') ? "checked" : ""}
                                       value="ADVENTURE"><label
                                    for="checkboxTwoCt"><fmt:message key="main.genre.adventure"/></label></li>
                            <li><input type="checkbox" id="checkboxThreeCt" class="genre"
                            ${sessionScope.genreFilter.contains('ARCADE') ? "checked" : ""}
                                       value="ARCADE"><label
                                    for="checkboxThreeCt"><fmt:message key="main.genre.arcade"/></label>
                            </li>
                            <li><input type="checkbox" id="checkboxFourCt" class="genre"
                            ${sessionScope.genreFilter.contains('CASUAL') ? "checked" : ""}
                                       value="CASUAL"><label
                                    for="checkboxFourCt"><fmt:message key="main.genre.casual"/></label>
                            </li>
                            <li><input type="checkbox" id="checkboxFiveCt" class="genre"
                            ${sessionScope.genreFilter.contains('HORROR') ? "checked" : ""}
                                       value="HORROR"><label
                                    for="checkboxFiveCt"><fmt:message key="main.genre.horror"/></label></li>
                            <li><input type="checkbox" id="checkboxSixCt" class="genre"
                            ${sessionScope.genreFilter.contains('INDIE') ? "checked" : ""}
                                       value="INDIE"><label
                                    for="checkboxSixCt"><fmt:message key="main.genre.indie"/></label></li>
                            <li><input type="checkbox" id="checkboxSevenCt" class="genre"
                            ${sessionScope.genreFilter.contains('OPEN_WORLD') ? "checked" : ""}
                                       value="OPEN_WORLD"><label
                                    for="checkboxSevenCt"><fmt:message key="main.genre.openWorld"/></label>
                            </li>
                            <li><input type="checkbox" id="checkboxEightCt" class="genre"
                            ${sessionScope.genreFilter.contains('RACING') ? "checked" : ""}
                                       value="RACING"><label
                                    for="checkboxEightCt"><fmt:message key="main.genre.racing"/></label>
                            </li>
                            <li><input type="checkbox" id="checkboxNineCt" class="genre"
                            ${sessionScope.genreFilter.contains('RPG') ? "checked" : ""}
                                       value="RPG"><label
                                    for="checkboxNineCt"><fmt:message key="main.genre.rpg"/></label></li>
                            <li><input type="checkbox" id="checkboxTenCt" class="genre"
                            ${sessionScope.genreFilter.contains('SHOOTERS') ? "checked" : ""}
                                       value="SHOOTERS"><label
                                    for="checkboxTenCt"><fmt:message key="main.genre.shooters"/></label>
                            </li>
                            <li class=""><input type="checkbox" id="checkboxElevenCt" class="genre"
                            ${sessionScope.genreFilter.contains('SIMULATORS') ? "checked" : ""}
                                                value="SIMULATORS"><label
                                    for="checkboxElevenCt"><fmt:message key="main.genre.simulators"/></label></li>
                            <li><input type="checkbox" id="checkboxTwelveCt" class="genre"
                            ${sessionScope.genreFilter.contains('SPORT') ? "checked" : ""}
                                       value="SPORT"><label
                                    for="checkboxTwelveCt"><fmt:message key="main.genre.sport"/></label></li>
                            <li><input type="checkbox" id="checkboxThirteenCt" class="genre"
                            ${sessionScope.genreFilter.contains('STRATEGIES') ? "checked" : ""}
                                       value="STRATEGIES"><label
                                    for="checkboxThirteenCt"><fmt:message key="main.genre.strategies"/></label></li>
                            <li><input type="checkbox" id="checkboxFourteenCt" class="genre"
                            ${sessionScope.genreFilter.contains('SURVIVAL') ? "checked" : ""}
                                       value="SURVIVAL"><label
                                    for="checkboxFourteenCt"><fmt:message key="main.genre.survival"/></label>
                            </li>
                            <li><input type="checkbox" class="genre"
                                       id="checkboxFifteenCt"
                            ${sessionScope.genreFilter.contains('VR') ? "checked" : ""}
                                       value="VR"><label
                                    for="checkboxFifteenCt"><fmt:message key="main.genre.vr"/></label>
                            </li>
                        </ul>
                    </div>
                    <div style="margin-top: 15%;  margin-bottom: 8%;">
                        <strong style="padding-left: 4%; margin-top: 1%; color: white"><fmt:message
                                key="main.categories"/></strong>
                        <div class="checkbox-container">
                            <ul class="ks-cboxtags" style="font-size: 10px !important">
                                <li><input type="checkbox" class="category"
                                           id="checkboxOne" ${sessionScope.categoryFilter.contains('SINGLEPLAYER') ? "checked" : ""}
                                           value="SINGLEPLAYER"><label
                                        for="checkboxOne"><fmt:message key="main.category.singleplayer"/></label></li>
                                <li style="margin-left: 5%"><input type="checkbox" class="category"
                                                                   id="checkboxTwo" ${sessionScope.categoryFilter.contains('MULTIPLAYER') ? "checked" : ""}
                                                                   value="MULTIPLAYER"><label
                                        for="checkboxTwo"><fmt:message key="main.category.multiplayer"/></label></li>
                                <li><input type="checkbox" class="category"
                                           id="checkboxThree" ${sessionScope.categoryFilter.contains('COOPERATIVE') ? "checked" : ""}
                                           value="COOPERATIVE"><label
                                        for="checkboxThree"><fmt:message key="main.category.cooperative"/></label>
                                </li>
                                <li style="margin-left: 5.5%"><input type="checkbox" class="category"
                                                                     id="checkboxFour" ${sessionScope.categoryFilter.contains('ONLINE_COOP') ? "checked" : ""}
                                                                     value="ONLINE_COOP"><label
                                        for="checkboxFour"><fmt:message key="main.category.onlineCoop"/></label>
                                </li>
                                <li style="margin-left: 23% !important;">
                                    <input type="checkbox" class="category"
                                           id="checkboxFive" ${sessionScope.categoryFilter.contains('CONTROLLER_FRIENDLY') ? "checked" : ""}
                                           value="CONTROLLER_FRIENDLY">
                                    <label for="checkboxFive"><fmt:message
                                            key="main.category.controllerFriendly"/></label></li>
                            </ul>
                        </div>
                    </div>

                    <input type='checkbox' checked="checked" name="categoryFilter"
                           style="opacity:0; position:absolute; left:9999px;"
                           id='categoryFilter' value="">
                    <input type='checkbox' checked="checked" name="genreFilter"
                           style="opacity:0; position:absolute; left:9999px;"
                           id='genreFilter' value="">
                    <span style="border-bottom: 3px solid; background-color: white !important; color: #c6c6c6 !important; display: block; width: 190px; margin-left: -1%; margin-bottom: 2%"></span>
                    <button class="btn btn-outline-success neon-title-green button-border-green"
                            style="min-width: 4% !important; font-size: 16px; float: right; margin-right: 84%"
                            onclick="connect('categoryFilter','category'); connect('genreFilter','genre')"
                            type="submit"><fmt:message key="main.apply"/>
                    </button>
                </form>
                <form method="post" action="${pageContext.request.contextPath}/clearFilters.do">
                    <input type="hidden" name="command" value="home">
                    <button class="btn btn-outline-danger neon-title-red button-border-red"
                            style="min-width: 4% !important;font-size: 16px; margin-left: -1%"
                            type="submit"><fmt:message key="main.clear"/>
                    </button>
                </form>

            </div>
        </div>
        <c:if test="${empty sessionScope.gameList}">
            <h1 class="neon-title-white" style="margin-left: 34%; margin-top: 14.1%; margin-bottom: 10%">
                <fmt:message key="main.empty"/></h1>
        </c:if>
        <div class="container t1">
            <div class="row row-cols-3" style="row-gap: 60px; margin-left: 45px; column-gap: 30px">
                <c:forEach items="${sessionScope.gameList}" var="game">
                    <div class="col" style="max-width: 330px">
                        <a href="${pageContext.request.contextPath}/game.do?command=open_game&gameId=${game.id}"
                           class="card custom-card" style="width: 18rem; text-decoration: none">
                            <img class="image-card notcopy" src="${pageContext.request.contextPath}${game.imagePath}"
                                 onerror="this.onerror = null; this.src='${pageContext.request.contextPath}/img/IMAGE_UNAVAILABLE.jpg'"
                                 alt="game">
                            <div class="card-body" style="text-align: center">
                                <p class="card-title neon-title-white">${game.title}</p>
                                <p class="card-text neon-title-white notcopy">${game.price}&dollar;</p>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div style="margin-top: 10%">
            <div style="text-align: center;margin-bottom: 5%; font-size: 20px" class="notcopy">
                <div id="prev" style="cursor: pointer;display: inline;" class="">
                    <c:if test="${not empty sessionScope.gameList}">
                        <a id="prevLink" style="color:#d2d2d2" class="button-glow-gray"
                           rel="' + 1 + '">&emsp;&#60;&#60;<fmt:message
                                key="main.page.previous"/>&emsp;</a>
                    </c:if>
                </div>
                <div id="nav" style="cursor: pointer; display: inline;" class="text-center"></div>
                <div id="next" style="cursor: pointer;display: inline;" class="">
                    <c:if test="${not empty sessionScope.gameList}">
                        <a id="nextLink" style="color:#d2d2d2" class="button-glow-gray" rel="' + 3 + '">&emsp;
                            <fmt:message key="main.page.next"/>&#62;&#62;&emsp;</a>
                    </c:if>
                </div>
            </div>
            <jsp:include page="support/footer.jsp"/>
        </div>
    </div>
</body>

<script src="${pageContext.request.contextPath}/js/custom-search.js"></script>
<script src="${pageContext.request.contextPath}/js/main-page.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/price.js"></script>

<script>
    var maxPriceValue = document.getElementById('toPrice').value;
    var elementById = document.getElementById('slider-range');
    elementById.onmouseleave = function () {
        document.getElementById('fromPrice').value = document.getElementsByName('minPrice').value;
        document.getElementById('toPrice').value = document.getElementsByName('maxPrice').value;
    }
</script>
<script type="text/javascript">
    function sortSubmit() {
        document.getElementById('sortForm').submit();
    }

    function connect(idName, className) {
        if (className == 'category')
            $('#categoryFilter').prop('checked', true)
        else if (className == 'genre')
            $('#genreFilter').prop('checked', true)
        var categoryArray = document.getElementsByClassName(className);
        var categoryFilter = document.getElementById(idName);
        categoryFilter.value = '';
        categoryValues = '';
        for (let i = 0; i < categoryArray.length; i++) {
            if (categoryArray[i].checked) {
                categoryValues = categoryValues + categoryArray[i].value + ' ';
            }
        }
        categoryValues = categoryValues.substring(0, categoryValues.length - 1);
        categoryFilter.value = categoryValues;
        if (categoryValues.length == 0) {
            if (className == 'category')
                $('#categoryFilter').prop('checked', false)
            if (className == 'genre')
                $('#genreFilter').prop('checked', false)
            document.getElementById(idName).cheked = false;
        }
    }
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