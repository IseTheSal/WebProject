<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="language.language"/>
<html>
<head>
    <title><fmt:message key="edit.game.name"/></title>
    <link rel="stylesheet"
          href="https://res.cloudinary.com/dxfq3iotg/raw/upload/v1569006288/BBBootstrap/choices.min.css?version=7.0.0">
</head>
<body>
<div style="background-image: url(/img/main-background.png);
background-size: cover; background-attachment: fixed; min-height: 100%; overflow: hidden">
    <jsp:include page="../support/header.jsp"/>
    <div style="padding-top: 5%">
        <form class="needs-validation" novalidate method="post"
              action="${pageContext.request.contextPath}/editGame.do">
            <input type="hidden" name="command" value="edit_game"/>
            <input name="clientToken" type="hidden" value="${sessionScope.serverToken}"/>
            <input type="hidden" name="currentPage" value="${pageContext.request.requestURI}">
            <input type="hidden" name="gameId" value="${sessionScope.currentGame.id}">
            <h2 class="neon-title-cyan" style="text-align:center">
                <fmt:message key="edit.game.page.name"/>
            </h2>
            <br>
            <div style="max-width: 900px;margin-left: 36%">
                <div class="form-group">
                    <label class="neon-title-white" for="txtGameTitle"
                           style="">
                        <fmt:message key="create.game.game.title"/>
                    </label>
                    <div class="form-inline">
                        <input name="gameTitle" id="txtGameTitle"
                               type="text" style="width: 420px; border-width: medium"
                               class="form-control"
                               placeholder="<fmt:message key="edit.game.title.placeholder"/>"
                               required
                               pattern="^[A-z0-9`\s:]{2,35}$"
                               minlength="2" maxlength="35"
                               value="${sessionScope.currentGame.title}"/>
                        <div class="valid-feedback"><span class="fas fa-check"></span><fmt:message
                                key="creategame.correct"/></div>
                        <div class="invalid-feedback"><span class="fas fa-times"></span><fmt:message
                                key="creategame.incorrect"/></div>
                    </div>
                </div>
                <div class="form-group" style="">
                    <label class="neon-title-white" for="customFile"
                           style="">
                        <fmt:message key="creategame.imagepath"/>
                    </label>
                    <br>
                    <div class="custom-file" style="width: 420px !important;">
                        <input id="customFile" type="text" name="imagePath"
                               value="${sessionScope.currentGame.imagePath}" class="form-control"
                               minlength="10" maxlength="100"
                               placeholder="<fmt:message key="edit.game.image.placeholder"/>">
                        <input type="hidden" name="imagePathCopy" value="${sessionScope.currentGame.imagePath}">
                        <div class="valid-feedback"><span class="fas fa-check"></span><fmt:message
                                key="creategame.correct"/></div>
                        <div class="invalid-feedback"><span class="fas fa-times"></span><fmt:message
                                key="creategame.incorrect"/></div>
                    </div>
                    <br>
                </div>
                <br>
                <div class="form-group" style="">
                    <label class="neon-title-white" for="txtDescription">
                        <fmt:message key="creategame.description"/>
                    </label>
                    <div class="form-inline">
                        <textarea name="gameDescription"
                                  style="width: 420px; border-width: medium; min-height: 80px; max-height: 500px"
                                  class="form-control"
                                  id="txtDescription"
                                  placeholder="<fmt:message key="edit.game.description.placeholder"/>"
                                  required
                                  minlength="8" maxlength="300">${sessionScope.currentGame.description}</textarea>
                        <div class="valid-feedback"><span class="fas fa-check"></span><fmt:message
                                key="creategame.correct"/></div>
                        <div class="invalid-feedback"><span class="fas fa-times"></span><fmt:message
                                key="creategame.incorrect"/></div>
                    </div>
                </div>
                <div class="form-group" style="">
                    <label class="neon-title-white" for="txtPrice"
                           style="position: relative; margin-bottom: 1px">
                        <fmt:message key="creategame.price"/>&#36;
                    </label>
                    <div class="form-inline">
                        <input name="gamePrice" id="txtPrice"
                               type="text" style="width: 420px; border-width: medium"
                               class="form-control" placeholder="<fmt:message key="edit.game.price.placeholder"/>"
                               required
                               pattern="^(\d)*(\.\d{1,2})?$"
                               minlength="1"
                               maxlength="7"
                               value="${sessionScope.currentGame.price}"/>
                        <div class="valid-feedback"><span class="fas fa-check"></span><fmt:message
                                key="creategame.correct"/></div>
                        <div class="invalid-feedback"><span class="fas fa-times"></span><fmt:message
                                key="creategame.incorrect"/></div>
                    </div>
                </div>
                <div class="form-group" style="">
                    <label class="neon-title-white" for="txtYoutube"
                           style="position: relative; margin-bottom: 1px">
                        <fmt:message key="creategame.trailer"/>
                    </label>
                    <label style="color: gray" for="txtYoutube">
                        <fmt:message key="creategame.youtube.trailer"/></label>
                    <div class="form-inline">
                        <input name="gameTrailerLink" id="txtYoutube"
                               type="text" style="width: 420px; border-width: medium"
                               class="form-control" placeholder="<fmt:message key="edit.game.link.placeholder"/>"
                               required
                               pattern="^(http:\/\/www\.|https:\/\/www\.|http:\/\/|https:\/\/)?[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?$"
                               minlength="5" maxlength="150"
                               value="${sessionScope.currentGame.trailer}"/>
                        <div class="valid-feedback"><span class="fas fa-check"></span><fmt:message
                                key="creategame.correct"/></div>
                        <div class="invalid-feedback"><span class="fas fa-times"></span><fmt:message
                                key="creategame.incorrect"/></div>
                    </div>
                </div>
                <div class="form-group" style="">
                    <label class="neon-title-white" for="select-genres"
                           style="position: relative; margin-bottom: 1px">
                        <fmt:message key="creategame.genres"/>&#42;
                    </label>
                    <div style="width: 420px;">
                        <select id="select-genres" class="select-form" name="gameGenres" required
                                multiple>
                            <c:forEach items="${sessionScope.genresMap}" var="genre">
                                <c:if test="${sessionScope.gameGenresMap.containsKey(genre.key)}">
                                    <option selected value="${genre.key}">${genre.value}</option>
                                </c:if>
                                <c:if test="${!sessionScope.gameGenresMap.containsKey(genre.key)}">
                                    <option value="${genre.key}">${genre.value}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <div class="valid-feedback"><span class="fas fa-check"></span></div>
                        <div class="invalid-feedback"><span class="fas fa-times"></span></div>
                    </div>
                    <div style="width: 420px;">
                        <label class="neon-title-white" for="select-categories"
                               style="position: relative; margin-bottom: 1px">
                            <fmt:message key="creategame.categories"/>&#42;
                        </label>
                        <select id="select-categories" name="gameCategories" required
                                multiple>
                            <c:forEach items="${sessionScope.categoriesMap}" var="category">
                                <c:if test="${sessionScope.gameCategoriesMap.containsKey(category.key)}">
                                    <option selected value="${category.key}">${category.value}</option>
                                </c:if>
                                <c:if test="${!sessionScope.gameCategoriesMap.containsKey(category.key)}">
                                    <option value="${category.key}">${category.value}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <c:forEach items="${requestScope.validIssues}" var="issue">
                    <label class="form-inline"
                           style="color: red"> ${issue} </label>
                </c:forEach>
                <input type="submit" style="width: 420px" class="button-glow-purple"
                       value="<fmt:message key="edit.game.button"/>" id="btnCreate"/>
            </div>
        </form>
        <div style="margin-top: 5%">
            <jsp:include page="../support/footer.jsp"/>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/js/custom-validation.js" type="text/javascript"></script>
    <script src="https://res.cloudinary.com/dxfq3iotg/raw/upload/v1569006273/BBBootstrap/choices.min.js?version=7.0.0"></script>
    <script>
        $(document).ready(function () {
            var multipleCancelButton = new Choices('#select-genres', {
                removeItemButton: true,
                maxItemCount: 10,
                searchResultLimit: 4,
                renderChoiceLimit: 4
            });
            var multipleCancelButton1 = new Choices('#select-categories', {
                removeItemButton: true,
                maxItemCount: 10,
                searchResultLimit: 4,
                renderChoiceLimit: 4
            });
        });
    </script>
    <script>
        console.log(${sessionScope.gameCategoriesMap})
        console.log(${sessionScope.gameGenresMap})
        $(".custom-file-input").on("change", function () {
            var fileName = $(this).val().split("\\").pop();
            $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
        });
    </script>
</body>
</html>
