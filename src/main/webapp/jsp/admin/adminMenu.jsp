<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="language.language"/>
<html>
<head>
    <%--    fixme fmt--%>
    <title>Admin menu</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin-menu-style.css">
</head>
<body class="custom-admin-body">
<jsp:include page="../support/header.jsp"/>

<div class="modal" style="border: none !important; background-color: rgba(0,0,0,0.5) !important;" id="adminCodeModal"
     tabindex="-1" role="dialog"
     aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog center-modal" style="border: none !important;" role="document">
        <div class="modal-content" style="background-color: rgba(105,105,105,0.7); border: none !important;">
            <div class="modal-header neon-title-white-light" style="border-bottom: 0 none">
                Add gamecode
                <button type="button" id="clsBtn" class="close neon-title-red" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form autocomplete="off" method="post" action="${pageContext.request.contextPath}/addGameCode.do">
                    <div style="position: relative; margin-left: 1%; margin-top: 1%">
                        <input type="hidden" name="command" value="add_game_code"/>
                        <input name="clientToken" type="hidden" value="${sessionScope.serverToken}"/>
                        <input type="hidden" name="currentPage" value="${pageContext.request.requestURI}">
                        <div class="autocomplete; neon-title-cyan-light "
                             style="width:300px; cursor: pointer; display: inline;">
                            <input id="adminInputSearch" class="custom-admin-input" type="text"
                                   placeholder="Game" required pattern="^[A-z0-9`\s:]{2,35}$"
                                   minlength="2" maxlength="35"
                                   style="color: #ff2525; box-shadow:0 0 30px #ffffff;float: left"/>
                            <input type="hidden" id="gameIdClass" value="-1" name="gameId"/>
                            <input type="text" class="custom-admin-input" placeholder="AAAAA-BBBBB-33333"
                                   name="gameCode" required
                                   pattern="^([A-z0-9]{5}-)([A-z0-9]{5}-)([A-z0-9]{5})$"
                                   maxlength="17"
                                   minlength="17"
                                   style=" box-shadow:0 0 30px #ffffff; float: right;text-transform:uppercase">
                            <input type="submit" class="button-search-purple" value="ADD GAMECODE"
                                   style="position: relative; margin-left: 13%;margin-top: 5%;border-radius: 0; margin-bottom: 3%; height: 40px; background: rgba(154,154,154,0.5)"/>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<button type="button" id="codeBtn" class="btn btn-primary invisible" style="position: absolute"
        data-toggle="modal" data-target="#adminCodeModal">
</button>

<div class="modal" style="border: none !important; background-color: rgba(0,0,0,0.5) !important"
     id="adminCouponModal"
     tabindex="-1" role="dialog"
     aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog center-modal" style="border: none !important" role="document">
        <div class="modal-content" style="background-color: rgba(105,105,105,0.7); border: none !important">
            <div class="modal-header neon-title-white-light" style="border-bottom: 0 none;">
                Add coupon
                <button type="button" id="clsCouponBtn" class="close neon-title-red" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form method="post" action="${pageContext.request.contextPath}/createCoupon.do">
                    <div style="position: relative; margin-left: 1%; margin-top: 1%">
                        <input type="hidden" name="command" value="create_coupon"/>
                        <input name="clientToken" type="hidden" value="${sessionScope.serverToken}"/>
                        <input type="hidden" name="currentPage" value="${pageContext.request.requestURI}">
                        <div class="autocomplete; neon-title-cyan-light "
                             style="width:300px; cursor: pointer; display: inline;">
                            <input id="couponCode" class="custom-admin-input" type="text"
                                   placeholder="Coupon code" name="couponCode" required pattern="[a-zA-Z0-9]{5,10}"
                                   minlength="5" maxlength="10"
                                   style="color: #ff2525; box-shadow:0 0 30px #ffffff;float: left; text-transform: uppercase"/>
                            <input id="discount" class="custom-admin-input" type="text"
                                   placeholder="Discount %" required pattern="^[1-9][0-9]?$"
                                   minlength="1" maxlength="2"
                                   name="couponDiscount"
                                   style="color: #ff2525; box-shadow:0 0 30px #ffffff;float: right"/>
                            <input type="text" class="custom-admin-input" placeholder="Amount"
                                   name="couponAmount" required
                                   pattern="^[1-9][0-9]{0,4}$"
                                   minlength="1"
                                   maxlength="5"
                                   style=" box-shadow:0 0 30px #ffffff; text-transform:uppercase; margin-top: 3%; margin-left: 30%">
                            <input type="submit" class="button-search-purple" value="ADD COUPON"
                                   style="position: relative; margin-left: 13%;margin-top: 5%;border-radius: 0; margin-bottom: 3%; height: 40px; background: rgba(154,154,154,0.5)"/>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<button type="button" id="couponBtn" class="btn btn-primary invisible" style="position: absolute"
        data-toggle="modal" data-target="#adminCouponModal">
</button>
<div style="position:fixed; color: transparent !important;  -webkit-touch-callout: none;
    -webkit-user-select: none;
       -moz-user-select: none;
        -ms-user-select: none;
            user-select: none">
    <c:forEach items="${sessionScope.gameList}" var="element">
        <label class="gameIdText" style="color: transparent !important;">${element.id}</label>
        <label class="gameTitleText" style="color: transparent !important;">${element.title}</label>
    </c:forEach>
</div>

<div class="custom-admin-container">
    <div class="custom-admin-card">
        <div class="face face1">
            <div class="content-admin">
                <i class="fab fa-jedi-order"></i>
                <h3>Games</h3>
            </div>
        </div>
        <div class="face face2">
            <div class="content-admin" style="text-align: center">
                <a class="custom-admin-a"
                   href="${pageContext.request.contextPath}/createGame.do?command=open_game_creator" type="button">Add
                    game</a>
                <a class="custom-admin-a" style="cursor: pointer" onclick="$('#codeBtn').click();" type="button">Add
                    code</a>
                <a class="custom-admin-a" href="${pageContext.request.contextPath}/jsp/admin/gameList.jsp"
                   type="button">Game list</a>
            </div>
        </div>
    </div>
    <div class="custom-admin-card ">
        <div class="face face1">
            <div class="content-admin">
                <i class="fas fa-tags"></i>
                <h3>Orders</h3>
            </div>
        </div>
        <div class="face face2">
            <div class="content-admin" style="text-align: center">
                <a class="custom-admin-a" href="#" type="button">Order list</a>
                <a class="custom-admin-a"
                   href="${pageContext.request.contextPath}/openCoupons.do?command=open_coupon_list" type="button">Coupon
                    list</a>
                <a class="custom-admin-a" style="cursor: pointer" onclick="$('#couponBtn').click();" type="button">Add
                    coupon</a>
            </div>
        </div>
    </div>
    <div class="custom-admin-card">
        <div class="face face1">
            <div class="content-admin">
                <i class="fas fa-users"></i>
                <h3>Users</h3>
            </div>
        </div>
        <div class="face face2">
            <div class="content-admin" style="text-align: center">
                <a class="custom-admin-a" href="#" type="button">User list</a>
                <a class="custom-admin-a" href="#" type="button">Add admin</a>
            </div>
        </div>
    </div>
</div>


<script src="${pageContext.request.contextPath}/js/custom-search.js"></script>
<script src="${pageContext.request.contextPath}/js/admin-menu.js"></script>

</body>
</html>
