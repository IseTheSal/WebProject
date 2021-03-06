<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="language.language"/>
<html>
<head>
    <title>${sessionScope.currentGame.title}</title>
</head>
<body>
<div style="background-image: url(/img/registration-background.jpg);
background-size: cover; background-attachment: fixed; min-height: 100%; overflow: hidden">
    <jsp:include page="support/header.jsp"/>
    <div class="modal" style="border: none !important; background-color: rgba(0,0,0,0.5) !important;" id="gameModal"
         tabindex="-1" role="dialog"
         aria-labelledby="exampleModalLabel"
         aria-hidden="true">
        <div class="modal-dialog center-modal" style="border: none !important;" role="document">
            <div class="modal-content" style="background-color: rgba(105,105,105,0.7); border: none !important;">
                <div class="modal-header neon-title-white-light" style="border-bottom: 0 none">
                    <fmt:message key="game.outOfStock1"/> ${sessionScope.currentGame.title} <fmt:message
                        key="game.outOfStock2"/>
                    <button type="button" id="clsBtn" class="close neon-title-red" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
        </div>
    </div>

    <button type="button" id="clsButton" class="btn btn-primary invisible" style="position: absolute"
            data-toggle="modal" data-target="#gameModal">
    </button>

    <div style="padding-top: 5%; margin-left: 40px">
        <div class="container" style="">
            <div class="row" style="">
                <div class="col-sm" style="text-align: center">
                    <p style="font-size: 40px"
                       class="neon-title-cyan">${sessionScope.currentGame.title}</p>
                    <iframe style="box-shadow: 0 0 40px #de7a7a;" height="350" width="650"
                            src="${sessionScope.currentGame.trailer}">
                    </iframe>
                </div>
                <div class="col-sm"
                     style="margin: 3% 4% 0 3%;position: relative; transform: translateY(75px)">
                    <c:if test="${sessionScope.cartMap.containsKey(sessionScope.currentGame)}">
                        <small id="orderHelp" style="color: darkgrey;text-align: center;margin-left: 34%"><fmt:message
                                key="game.cart"/>
                            <span style="color: #0b820b" class="fas fa-check"></span></small>
                    </c:if>
                    <form method="post" action="${pageContext.request.contextPath}/addToCart.do">
                        <input type="hidden" name="command" value="add_to_cart"/>
                        <input name="clientToken" type="hidden" value="${sessionScope.serverToken}"/>
                        <input type="hidden" name="currentPage" value="${pageContext.request.requestURI}">
                        <input type="hidden" name="gameId" value="${sessionScope.currentGame.id}"/>
                        <input type="submit" name="btnBuy" aria-describedby="orderHelp"
                               value="${sessionScope.currentGame.price}$" id="btnBuy"
                               class="button-glow-buy"/>
                    </form>
                    <p style="margin-top: 10px" class="neon-title-purple"><fmt:message key="game.genre"/>:
                        <c:forEach items="${sessionScope.currentGame.genres}" var="genre">
                            <h style="font-size: 15px;" class="neon-title-white-light">${genre}</h>
                        </c:forEach>
                    </p>
                    <p class="neon-title-purple"><fmt:message key="game.category"/>:
                        <c:forEach items="${sessionScope.currentGame.categories}" var="category">
                            <h style="font-size: 15px" class="neon-title-white-light">${category}</h>
                        </c:forEach>
                    </p>
                    <p style="display: inline" class="neon-title-purple"><fmt:message key="game.description"/>: </p>
                    <p style="display: inline"
                       class="neon-title-white-light">${sessionScope.currentGame.description}</p>
                </div>
            </div>
        </div>
        <div style="position: fixed; bottom: 0; width: 100%">
            <jsp:include page="support/footer.jsp"/>
        </div>
    </div>
    <script>
        $(document).ready(function () {
            var gameInStock = ${requestScope.gameInStock};
            if (!gameInStock) {
                $("#clsButton").click();
                funcBtns.alertWarningBody('<fmt:message key='game.outOfStock1'/>' + '<fmt:message key='game.outOfStockToast2'/>');
            }
        });
    </script>
</body>
</html>
