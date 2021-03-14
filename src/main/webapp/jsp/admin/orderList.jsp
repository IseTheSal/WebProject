<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="language.language"/>
<html>
<head>
    <title><fmt:message key="admin.order.list.title"/></title>
    <link href=https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.0/css/bootstrap.min.css rel=stylesheet>
    <link href=https://cdnjs.cloudflare.com/ajax/libs/datatables/1.10.20/css/dataTables.bootstrap4.min.css
          rel=stylesheet>
</head>
<body>
<div style="background-image: url(/img/registration-background.jpg);
    background-size: cover; background-attachment: fixed; min-height: 100%; overflow: hidden">
    <jsp:include page="../support/header.jsp"/>
    <div style="padding-top: 5%; margin-left: 10%; margin-right: 10%">
        <table id="dtBasicExample" class="table table-dark table-bordered text-center">
            <thead class="">
            <tr>
                <th scope="col"><fmt:message key="admin.order.list.login"/></th>
                <th scope="col"><fmt:message key="admin.order.list.price"/></th>
                <th scope="col" data-sortable="false"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sessionScope.clientOrders}" var="order">
                <tr>
                    <th scope="row" title="Email: ${order.key.email}" style="cursor: pointer">${order.key.login}</th>
                    <c:if test="${order.value.price.unscaledValue() == 0}">
                        <td>&minus;</td>
                    </c:if>
                    <c:if test="${order.value.price.unscaledValue() != 0}">
                        <td>${order.value.price.stripTrailingZeros()}&dollar;</td>
                    </c:if>
                    <td>
                        <button type="button" id="clsButton" class="btn btn-secondary"
                                data-toggle="modal" data-target="#${order.key.id}"><i class="fas fa-search"></i>
                        </button>
                    </td>
                    <div class="modal"
                         style="border: none !important; background-color: rgba(0,0,0,0.5) !important;"
                         id="${order.key.id}"
                         tabindex="-1" role="dialog"
                         aria-labelledby="adminModalLabel"
                         aria-hidden="true">
                        <div class="modal-dialog" style="border: none !important;" role="document">
                            <div class="modal-content"
                                 style="background-color: rgba(105,105,105,1); border: none !important;">
                                <div class="modal-header" style="border-bottom: 0 none; color: white">
                                    <div class="modal-title text-center w-100" style="margin-left: 30px">
                                        <fmt:message key="admin.order.list.games.title"/>
                                    </div>
                                    <button type="button" id="clsBtn" class="close close-red-button"
                                            data-dismiss="modal"
                                            aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body text-center" style="white-space: normal">
                                    <c:if test="${not empty order.value.gameMap}">
                                        <c:forEach items="${order.value.gameMap}" var="game">
                                            <div class="toc" style="color: white">
                                                <p>
                                                    <label class="title neon-title-white"
                                                           style="float: left;">${game.key.title}</label>
                                                    <label class="chapter"
                                                           style="float: right; ">&times;${game.value}</label>
                                                    <br>
                                                </p>
                                            </div>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${empty order.value.gameMap}">
                                        <label class="neon-title-white">LIST IS EMPTY</label>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
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
</body>
</html>
