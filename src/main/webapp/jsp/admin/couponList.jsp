<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="language.language"/>
<html>
<head>
    <title><fmt:message key="admin.coupon.list.title"/></title>
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
                <th scope="col"><fmt:message key="admin.coupon.list.game.code"/></th>
                <th scope="col"><fmt:message key="admin.coupon.list.game.discount"/></th>
                <th scope="col"><fmt:message key="admin.coupon.list.game.amount"/></th>
                <th scope="col" data-sortable="false"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sessionScope.couponList}" var="coupon">
                <tr>
                    <th scope="row">${coupon.codeName}</th>
                    <td>${coupon.discount}&#37;</td>
                    <td>${coupon.amount}</td>
                    <td>
                        <button type="button" id="clsButton" class="btn btn-secondary"
                                data-toggle="modal" data-target="#${coupon.id}"><i class="fas fa-edit"></i>
                        </button>
                    </td>
                    <div class="modal" style="border: none !important; background-color: rgba(0,0,0,0.5) !important;"
                         id="${coupon.id}"
                         tabindex="-1" role="dialog"
                         aria-labelledby="adminModalLabel"
                         aria-hidden="true">
                        <div class="modal-dialog center-modal" style="border: none !important;" role="document">
                            <div class="modal-content"
                                 style="background-color: rgba(105,105,105,0.7); border: none !important;">
                                <div class="modal-header neon-title-white-light" style="border-bottom: 0 none">
                                    <fmt:message key="admin.coupon.list.game.edit.coupon"/>
                                    <button type="button" id="clsBtn" class="close neon-title-red" data-dismiss="modal"
                                            aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <form method="post"
                                          action="${pageContext.request.contextPath}/editCoupon.do">
                                        <div style="position: relative; margin-left: 1%; margin-top: 1%">
                                            <input type="hidden" name="command" value="edit_coupon_amount"/>
                                            <input name="clientToken" type="hidden"
                                                   value="${sessionScope.serverToken}"/>
                                            <input type="hidden" name="currentPage"
                                                   value="${pageContext.request.requestURI}"/>
                                            <input type="hidden" name="couponCode" value="${coupon.codeName}"/>
                                            <div class="neon-title-white-shadow-light"
                                                 style="width:300px; cursor: pointer; display: inline;">
                                                <label id="codeInput" class=""
                                                       style="float: left;"><fmt:message
                                                        key="admin.coupon.game.code"/>&colon; ${coupon.codeName}</label>
                                                <label id="discountInput" class=""
                                                       style="float: right"><fmt:message
                                                        key="admin.coupon.list.game.discount"/>&colon; ${coupon.discount}%</label>
                                                <br>
                                                <div style="margin-top: 3%; margin-left: 30%; ">
                                                    <label for="amountInput" style="margin-left: 20%"><fmt:message
                                                            key="admin.coupon.list.game.amount"/></label>
                                                    <br>
                                                    <c:if test="${coupon.amount > 0}">
                                                        <input type="text" id="amountInput" class="custom-admin-input"
                                                               placeholder="<fmt:message key="admin.coupon.list.game.amount"/>"
                                                               name="couponAmount" required
                                                               pattern="^[1-9][0-9]{0,4}$"
                                                               value="${coupon.amount}"
                                                               minlength="1"
                                                               maxlength="5"
                                                               style=" box-shadow:0 0 30px #ffffff;text-align: center">
                                                    </c:if>
                                                    <c:if test="${coupon.amount <= 0}">
                                                        <label for="amountInput"
                                                               style="margin-left: 28% ;text-align: center">${coupon.amount}</label>
                                                    </c:if>
                                                </div>
                                                <c:if test="${coupon.amount > 0}">
                                                    <input type="submit" class="button-search-gray"
                                                           value="<fmt:message key="admin.coupon.list.game.edit.amount"/>"
                                                           style="position: relative;color: white; margin-left: 13%;margin-top: 5%;border-radius: 0; margin-bottom: 3%; height: 40px; background: rgba(154,154,154,0.5)"/>
                                                </c:if>

                                            </div>
                                        </div>
                                    </form>
                                    <form method="post"
                                          action="${pageContext.request.contextPath}/deleteCoupon.do">
                                        <div style="position: relative; margin-left: 1%; margin-top: 1%">
                                            <input type="hidden" name="command" value="delete_coupon"/>
                                            <input name="clientToken" type="hidden"
                                                   value="${sessionScope.serverToken}"/>
                                            <input type="hidden" name="currentPage"
                                                   value="${pageContext.request.requestURI}">
                                            <input type="hidden" name="couponCode" value="${coupon.codeName}">
                                            <button type="submit" class="button-red-trash"
                                                    style="float: bottom"><i class="fas fa-trash trash-span"></i>
                                            </button>
                                        </div>
                                    </form>
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
<script type="text/javascript">
    $(document).ready(function () {
        if (${requestScope.couponAmountZero}) {
            funcBtns.alertWarning("<fmt:message key="alert.header.coupon.zero"/>");
        }
    });
</script>
</body>
</html>
