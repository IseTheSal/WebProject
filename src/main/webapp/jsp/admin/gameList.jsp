<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="language.language"/>
<html>
<head>
    <title><fmt:message key="admin.games.list.title"/></title>
    <link href=https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.0/css/bootstrap.min.css rel=stylesheet>
    <link href=https://cdnjs.cloudflare.com/ajax/libs/datatables/1.10.20/css/dataTables.bootstrap4.min.css
          rel=stylesheet>
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
                    <fmt:message key="admin.games.amount"/> ${requestScope.gameCodeAmount}
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
        <table id="dtBasicExample" class="table table-dark table-bordered text-center">
            <thead class="">
            <tr>
                <th scope="col"><fmt:message key="admin.games.list.game.title"/></th>
                <th scope="col"><fmt:message key="admin.games.list.game.price"/></th>
                <th scope="col" data-sortable="false"><fmt:message key="admin.games.list.game.amount"/></th>
                <th scope="col" data-sortable="false"></th>
            </tr>
            </thead>
            <tbody class="">
            <c:forEach items="${sessionScope.gameList}" var="game">
                <tr>
                    <th scope="row" style="text-align: center; vertical-align: middle">${game.title}</th>
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
<script src=https://cdnjs.cloudflare.com/ajax/libs/datatables/1.10.20/js/jquery.dataTables.min.js></script>
<script src=https://cdnjs.cloudflare.com/ajax/libs/datatables/1.10.20/js/dataTables.bootstrap4.min.js></script>
<script>
    $(document).ready(function () {
        $('#dtBasicExample').dataTable({
            "paging": false,
            "bInfo": false,
            "oLanguage": {
                "sSearch": "<label style='color:white'><fmt:message key="admin.table.search"/></label>"
            }
        });
        $('.dataTables_length').addClass('bs-select');
    });
</script>
<script>
    $(document).ready(function () {
        var gameAmount = ${requestScope.gameCodeAmount};
        if (gameAmount != null) {
            $("#clsButton").click();
            if (gameAmount != 0) {
                funcBtns.alertOK("<fmt:message key="admin.games.list.game.amount.script"/>" + gameAmount);
            }
        }
    });
</script>
</body>
</html>
