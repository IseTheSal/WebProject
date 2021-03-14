<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="language.language"/>
<html>
<head>
    <%--    fixme fmt add--%>
    <title>User list</title>
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
                <%--                fixme <td></td>--%>
                <th scope="col">ID</th>
                <th scope="col">login</th>
                <th scope="col">Firstname</th>
                <th scope="col">Secondame</th>
                <th scope="col">Email</th>
                <th scope="col">Role</th>
                <th scope="col" data-sortable="false"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sessionScope.userList}" var="user">
            <tr>
                <th scope="row" style="cursor: pointer">${user.id}</th>
                <td>${user.login}</td>
                <td> ${user.firstname}</td>
                <td>${user.lastname}</td>
                <td>${user.email}</td>
                <td>${user.role}</td>
                <td>
                    <button type="button" id="clsButton" class="btn btn-secondary"
                            data-toggle="modal" data-target="#${user.id}"><i class="fas fa-edit"></i>
                    </button>
                </td>
            </tr>
            <div class="modal" style="border: none !important; background-color: rgba(0,0,0,0.5) !important;"
                 id="${user.id}"
                 tabindex="-1" role="dialog"
                 aria-labelledby="adminModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog center-modal" style="border: none !important;" role="document">
                    <div class="modal-content"
                         style="background-color: rgba(105,105,105,0.7); border: none !important;">
                        <div class="modal-header neon-title-white-light" style="border-bottom: 0 none">
                            EDIT USER (${user.login}) EMAIL
                            <button type="button" id="clsBtn" class="close neon-title-red" data-dismiss="modal"
                                    aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <form class="needs-validation" novalidate method="post"
                                      action="${pageContext.request.contextPath}/changeEmail.do">
                                    <input type="hidden" name="command" value="change_email"/>
                                    <input type="hidden" name="clientToken" value="${sessionScope.serverToken}"/>
                                    <input type="hidden" name="currentPage"
                                           value="${pageContext.request.requestURI}">
                                    <input type="hidden" name="userId" value="${user.id}">
                                    <div style="margin-left: 17%">
                                        <label for="newEmail" class="neon-title-white">New email: </label>
                                        <input name="email" id="newEmail"
                                               style="width: 300px; height: 40px; border-width: medium"
                                               class="form-control"
                                               placeholder="<fmt:message key="profile.newEmail"/>" required type="email"
                                               maxlength="320">
                                    </div>
                                    <br>
                                    <div class="form" style="margin-left: 17%">
                                        <label for="newEmailRepeat" class="neon-title-white">Repeat new email: </label>
                                        <input name="repeatEmail" id="newEmailRepeat"
                                               style="width: 300px; height: 40px; border-width: medium"
                                               class="form-control"
                                               placeholder="<fmt:message key="profile.newEmailRepeat"/>" required
                                               type="email" pattern="email"
                                               maxlength="320">
                                        <div class="invalid-feedback"><span class="fas fa-times"></span><fmt:message
                                                key="registration.invalidInput"/></div>
                                    </div>
                                    <br>
                                    <button type="submit" class="button-search-purple"
                                            value="CHANGE USER EMAIL"
                                            style="position: relative;color: white; margin-left: 13%;margin-top: 5%;border-radius: 0; margin-bottom: 3%; height: 40px; background: rgba(154,154,154,0.5)">
                                        CHANGE USER EMAIL
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
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
    (function () {
        'use strict';
        window.addEventListener('load', function () {
            var forms = document.getElementsByClassName('needs-validation');
            var validation = Array.prototype.filter.call(forms, function (form) {
                form.addEventListener('submit', function (event) {
                    if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        }, false);
    })();
</script>

<script type="text/javascript">
    $(document).ready(
        function () {
            let emailExist =  ${not empty requestScope.emailExist};
            if (emailExist) {
                funcBtns.alertWarningBody('Fail', 'Email exists');
            }

            let wrongOldPassword = ${not empty requestScope.incorrectOldPassword};
            if (wrongOldPassword) {
                funcBtns.alertWarningBody('Fail', 'Old password is incorrect');
            }
        });

    window.onload = function () {
        var txtEmail = document.getElementById("newEmail");
        var txtConfirmEmail = document.getElementById("newEmailRepeat");
        txtEmail.onchange = ConfirmEmail;
        txtConfirmEmail.onkeyup = ConfirmEmail;

        function ConfirmEmail() {
            txtConfirmEmail.setCustomValidity("");
            if (txtEmail.value !== txtConfirmEmail.value) {
                txtConfirmEmail.setCustomValidity("Emails don`t match");
            }
        }
    }
</script>
</body>
</html>

