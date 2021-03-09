<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="language.language"/>
<html>
<head>
<%--    fixme--%>
    <title>Game list</title>
</head>
<body>
<div style="background-image: url(/img/registration-background.jpg);
background-size: cover; background-attachment: fixed; min-height: 100%; overflow: hidden">
    <jsp:include page="../support/header.jsp"/>

    <div class="modal" style="border: none !important; background-color: rgba(0,0,0,0.5) !important;" id="amountModal"
         tabindex="-1" role="dialog"
         aria-labelledby="exampleModalLabel"
         aria-hidden="true">
        <div class="modal-dialog center-modal" style="border: none !important;" role="document">
            <div class="modal-content" style="background-color: rgba(105,105,105,0.7); border: none !important;">
                <div class="modal-header neon-title-white-light" style="border-bottom: 0 none">
                    Amount of this game - ${requestScope.gameCodeAmount}
                    <button type="button" id="clsBtn" class="close neon-title-red" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
        </div>
    </div>
    <button type="button" id="clsButton" class="btn btn-primary invisible" style="position: absolute"
            data-toggle="modal" data-target="#amountModal">
    </button>

    <div style="padding-top: 5%; margin-left: 10%; margin-right: 10%">
        <table class="table table-bordered text-center">
            <thead class="thead-light">
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Title</th>
                <th scope="col">Price</th>
                <th scope="col">Amount</th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody class="table-light">
            <c:forEach items="${sessionScope.gameList}" var="game">
                <tr>
                    <th scope="row" style="width: 3%; text-align: center; vertical-align: middle">${game.id}</th>
                    <td style="text-align: center; vertical-align: middle">${game.title}</td>
                    <td style="width: 5%; text-align: center; vertical-align: middle">${game.price}$</td>
                    <td style="width: 10%;text-align: center; vertical-align: middle">
                        <form method="get" action="${pageContext.request.contextPath}/findAmount.do">
                            <input type="hidden" name="command" value="find_code_amount">
                            <input type="hidden" name="gameId" value="${game.id}"/>
                            <button type="submit" style="margin-top: 20px" class="btn btn-primary" value="">
                                <i class="fas fa-search"></i></button>
                        </form>
                    </td>
                    <td style="width: 10%;text-align: center; vertical-align: middle">
                        <form method="get" action="${pageContext.request.contextPath}/gameEditor.do">
                            <input type="hidden" name="command" value="open_game_editor">
                            <input type="hidden" name="gameId" value="${game.id}"/>
                            <button type="submit" style="margin-top: 20px" class="btn btn-success" value="">
                                <i class="fas fa-edit"></i></button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<script>
    $(document).ready(function () {
        var gameAmount = ${requestScope.gameCodeAmount};
        if (gameAmount != null) {
            $("#clsButton").click();
            if (gameAmount != 0) {
                funcBtns.alertOK("Amount - " + gameAmount);
            }
        }
    });
</script>
</body>
</html>
