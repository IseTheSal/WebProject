<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="language.language"/>
<html>
<head>
    <title><fmt:message key="admin.user.list.title"/></title>
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
                <th scope="col"><fmt:message key="admin.user.list.id"/></th>
                <th scope="col"><fmt:message key="admin.user.list.login"/></th>
                <th scope="col"><fmt:message key="admin.user.list.firstname"/></th>
                <th scope="col"><fmt:message key="admin.user.list.secondname"/></th>
                <th scope="col"><fmt:message key="admin.user.list.email"/></th>
                <th scope="col"><fmt:message key="admin.user.list.role"/></th>
                <th scope="col" data-sortable="false"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sessionScope.userSet}" var="user">
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
                                <fmt:message key="admin.user.list.edit.email"/>
                                <button type="button" id="clsBtn" class="close neon-title-red" data-dismiss="modal"
                                        aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <form class="was-validated" method="post"
                                      action="${pageContext.request.contextPath}/changeEmail.do">
                                    <input type="hidden" name="command" value="change_email"/>
                                    <input type="hidden" name="clientToken" value="${sessionScope.serverToken}"/>
                                    <input type="hidden" name="currentPage"
                                           value="${pageContext.request.requestURI}">
                                    <input type="hidden" name="userId" value="${user.id}">
                                    <div class="form" style="margin-left: 17%">
                                        <label for="newEmail${user.id}" class="neon-title-white">New email: </label>
                                        <div class="form-inline">
                                            <input name="email" id="newEmail${user.id}"
                                                   style="width: 300px; height: 40px; border-width: medium"
                                                   class="form-control"
                                                   onkeyup="check(document.getElementById('newEmailRepeat${user.id}'), this)"
                                                   placeholder="<fmt:message key="profile.newEmail"/>" required
                                                   type="email"
                                                   maxlength="320">
                                            <div class="valid-feedback">
                                                <span class="fas fa-times"></span>
                                                <fmt:message key="registration.invalidInput"/>
                                            </div>
                                        </div>
                                    </div>
                                    <br>
                                    <div class="form" style="margin-left: 17%">
                                        <label for="newEmailRepeat${user.id}" class="neon-title-white">Repeat new
                                            email: </label>
                                        <div class="form-inline">
                                            <input name="repeatEmail" id="newEmailRepeat${user.id}"
                                                   style="width: 300px; height: 40px; border-width: medium"
                                                   class="form-control"
                                                   onkeyup="check(this, document.getElementById('newEmail${user.id}'))"
                                                   placeholder="<fmt:message key="profile.newEmailRepeat"/>" required
                                                   type="email"
                                                   maxlength="320">
                                        </div>
                                    </div>
                                    <br>
                                    <input type="submit" class="button-search-purple"
                                           value="<fmt:message key="admin.user.list.edit.btn"/>"
                                           style="position: relative;color: white; margin-left: 13%;margin-top: 5%;border-radius: 0; margin-bottom: 3%; height: 40px; background: rgba(154,154,154,0.5)"/>
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

    $(document).ready(
        function () {
            let emailExist =  ${not empty requestScope.emailExist};
            if (emailExist) {
                funcBtns.alertWarningBody('Fail', 'Email exists');
            }
        });

    function check(repeatEmail, email) {
        console.log(repeatEmail.value)
        console.log(email.value)
        repeatEmail.setCustomValidity("");
        if (repeatEmail.value != email.value) {
            repeatEmail.setCustomValidity('Emails dont match');
            funcBtns.alertError("Emails dont match");
        }
    }
</script>
</body>
</html>

