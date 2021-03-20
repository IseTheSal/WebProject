<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="language.language"/>
<head>
    <title><fmt:message key="main.page.title"/></title>
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
        <form autocomplete="off" method="get" action="${pageContext.request.contextPath}/game.do">
            <div style="position: fixed; margin-left: 1%; margin-top: 1%">
                <input type="hidden" name="command" value="open_game"/>
                <button type="submit" value="" class="button-search-blue"
                        style="display: inline; position: absolute;margin-left: 190px; height: 30px; border-radius: 100%; width: 30px; background: rgba(186,186,186,0.5)">
                    <span class="fa fa-search search-span"></span>
                </button>
                <div class="autocomplete; neon-title-cyan-light "
                     style="width:300px; cursor: pointer; display: inline;">
                    <input id="myInput" class="custom-input" type="text" placeholder="<fmt:message key="main.search"/>"
                           style="color: black;border-radius: 10%; box-shadow:0 0 30px #a8a8a8;"/>
                    <input type="hidden" id="gameIdClass" value="1" name="gameId"/>
                </div>
            </div>
        </form>


        <div class="container t1">
            <div class="row row-cols-3" style="row-gap: 60px; margin-left: 45px">
                <c:forEach items="${sessionScope.gameList}" var="game">
                    <div class="col" style="max-width: 360px">
                        <a href="${pageContext.request.contextPath}/game.do?command=open_game&gameId=${game.id}"
                           class="card custom-card" style="width: 18rem; text-decoration: none">
                            <img class="image-card" src="${pageContext.request.contextPath}${game.imagePath}"
                                 onerror="this.onerror = null; this.src='${pageContext.request.contextPath}/img/IMAGE_UNAVAILABLE.jpg'"
                                 alt="game">
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
                <div id="prev" style="cursor: pointer;display: inline;" class="">
                    <a id="prevLink" style="color:#d2d2d2" class="button-glow-gray"
                       rel="' + 1 + '">&emsp;&#60;&#60;<fmt:message
                            key="main.page.previous"/>&emsp;</a>
                </div>
                <div id="nav" style="cursor: pointer; display: inline;" class="text-center"></div>
                <div id="next" style="cursor: pointer;display: inline;" class="">
                    <a id="nextLink" style="color:#d2d2d2" class="button-glow-gray" rel="' + 3 + '">&emsp;
                        <fmt:message key="main.page.next"/>&#62;&#62;&emsp;</a>
                </div>
            </div>
            <jsp:include page="support/footer.jsp"/>
        </div>
    </div>
</body>

<script src="${pageContext.request.contextPath}/js/custom-search.js"></script>
<script src="${pageContext.request.contextPath}/js/main-page.js"></script>

<script>
    $(document).ready(function () {
        var orderCreated = ${requestScope.orderCreated};
        if (orderCreated) {
            funcBtns.alertOkOnlyTitle('<fmt:message key='main.orderSuccess'/>');
        }
    });
</script>

</html>