<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="language.language"/>
<html>
<head>
    <title>Create game</title>
    <link rel="stylesheet"
          href="https://res.cloudinary.com/dxfq3iotg/raw/upload/v1569006288/BBBootstrap/choices.min.css?version=7.0.0">
</head>
<body>
<div style="background-image: url(/img/registration-background.jpg);
background-size: cover; background-attachment: fixed; min-height: 100%; overflow: hidden">
    <jsp:include page="support/header.jsp"/>
    <div style="padding-top: 5%">
        <form class="needs-validation" novalidate method="post" action="createGame.do">
            <input type="hidden" name="command" value="create_game"/>
            <input name="clientToken" type="hidden" value="${serverToken}"/>
            <input type="hidden" name="currentPage" value="${pageContext.request.requestURI}">
            <h2 class="neon-title-cyan" style="text-align:center">
                CREATE NEW GAME
            </h2>
            <br>
            <div style="max-width: 900px;margin-left: 36%">
                <div class="form-group" style="margin-bottom: 5px">
                    <label class="neon-title-white" for="txtGameTitle"
                           style="">
                        Game title
                    </label>
                    <div class="form-inline">
                        <input name="gameTitle" id="txtGameTitle"
                               type="text" style="width: 420px; border-width: medium"
                               class="form-control" placeholder="game title"
                               required pattern="^[A-z0-9`\s:]{2,35}$" minlength="1" maxlength="35"/>
                        <div class="valid-feedback"><span class="fas fa-check"></span>Yes</div>
                        <div class="invalid-feedback"><span class="fas fa-times"></span>Nope</div>
                    </div>
                </div>
                <br>
                <div class="form-group" style="">
                    <label class="neon-title-white"
                           style="">
                        Image path
                    </label>
                    <br>
                    <div class="custom-file" style="width: 420px !important;">
                        <input type="file" name="imagePath" class="custom-file-input" id="customFile">
                        <label class="custom-file-label" for="customFile">Choose image</label>
                    </div>
                </div>
                <br>
                <div class="form-group" style="margin-bottom: 14px">
                    <label class="neon-title-white" for="txtDescription">
                        Description
                    </label>
                    <div class="form-inline">
                        <textarea name="gameDescription"
                                  style="width: 420px; border-width: medium; min-height: 80px; max-height: 500px"
                                  class="form-control"
                                  id="txtDescription"
                                  placeholder="Description"
                                  required
                                  minlength="8" maxlength="300"></textarea>
                        <div class="valid-feedback"><span class="fas fa-check"></span>Yes</div>
                        <div class="invalid-feedback"><span class="fas fa-times"></span>Nope</div>
                    </div>
                </div>
                <br>
                <div class="form-group" style="">
                    <label class="neon-title-white" for="txtPrice"
                           style="position: relative; margin-bottom: 1px">
                        Price
                    </label>
                    <div class="form-inline">
                        <input name="gamePrice" id="txtPrice"
                               type="text" style="width: 420px; border-width: medium"
                               class="form-control" placeholder="$"
                               required pattern="^(\d)*(\.\d{1,2})?$" minlength="1"
                               maxlength="7"/>
                        <div class="valid-feedback"><span class="fas fa-check"></span>Yes</div>
                        <div class="invalid-feedback"><span class="fas fa-times"></span>Nope</div>
                    </div>
                </div>
                <br>
                <div class="form-group" style="">
                    <label class="neon-title-white" for="txtYoutube"
                           style="position: relative; margin-bottom: 1px">
                        Youtube trailer
                    </label>
                    <label style="color: gray" for="txtYoutube">https://www.youtube.com/embed/</label>
                    <div class="form-inline">
                        <input name="gameTrailerLink" id="txtYoutube"
                               type="text" style="width: 420px; border-width: medium"
                               class="form-control" placeholder="https://www.youtube.com/embed/"
                               required
                               pattern="^(http:\/\/www\.|https:\/\/www\.|http:\/\/|https:\/\/)?[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?$"
                               minlength="1" maxlength="150"/>
                        <div class="valid-feedback"><span class="fas fa-check"></span>Yes</div>
                        <div class="invalid-feedback"><span class="fas fa-times"></span>Nope</div>
                    </div>
                </div>
                <br>
                <div class="form-group" style="">
                    <label class="neon-title-white" for="choices-multiple-remove-button"
                           style="position: relative; margin-bottom: 1px">
                        Genres
                    </label>
                    <div style="width: 420px;">
                        <select id="choices-multiple-remove-button" name="gameGenres" required
                                multiple>
                            <option value="1">ACTION</option>
                            <option value="2">ADVENTURE</option>
                            <option value="3">ARCADE</option>
                            <option value="4">CASUAL</option>
                            <option value="5">HORROR</option>
                            <option value="6">INDIE</option>
                            <option value="7">OPEN_WORLD</option>
                            <option value="8">RACING</option>
                            <option value="9">RPG</option>
                            <option value="10">SHOOTERS</option>
                            <option value="11">SIMULATORS</option>
                            <option value="12">SPORT</option>
                            <option value="13">STRATEGIES</option>
                            <option value="14">SURVIVAL</option>
                            <option value="15">VR</option>
                        </select>
                    </div>
                    <br>
                    <div style="width: 420px;">
                        <label class="neon-title-white" for="choices-multiple-remove-button_ct"
                               style="position: relative; margin-bottom: 1px">
                            Categories
                        </label>
                        <select id="choices-multiple-remove-button_ct" name="gameCategories" required
                                multiple>
                            <option value="1">SINGLEPLAYER</option>
                            <option value="2">MULTIPLAYER</option>
                            <option value="3">COOPERATIVE</option>
                            <option value="4">ONLINE_COOP</option>
                            <option value="5">CONTROLLER_FRIENDLY</option>
                        </select>
                    </div>
                </div>
                <%--                --%>
                <label class="form-inline"
                       style="color: red"> ${requestScope.createGameFail} </label>
                <input type="submit" value="create" id="btnCreate"/>
            </div>
        </form>
        <div style="margin-top: 5%">
            <jsp:include page="support/footer.jsp"/>
        </div>
    </div>
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
    <script src="https://res.cloudinary.com/dxfq3iotg/raw/upload/v1569006273/BBBootstrap/choices.min.js?version=7.0.0"></script>
    <script>
        $(document).ready(function () {
            var multipleCancelButton = new Choices('#choices-multiple-remove-button', {
                removeItemButton: true,
                maxItemCount: 10,
                searchResultLimit: 4,
                renderChoiceLimit: 4
            });
            var multipleCancelButton1 = new Choices('#choices-multiple-remove-button_ct', {
                removeItemButton: true,
                maxItemCount: 10,
                searchResultLimit: 4,
                renderChoiceLimit: 4
            });
        });
    </script>
    <script>
        $(".custom-file-input").on("change", function () {
            var fileName = $(this).val().split("\\").pop();
            $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
        });
    </script>
</body>
</html>
