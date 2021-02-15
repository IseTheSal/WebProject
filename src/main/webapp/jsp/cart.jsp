<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="language.language"/>
<html>
<head>
    <title><fmt:message key="cart.title"/></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/cart-style.css">
</head>
<body>
<div style=" background-image: url(/img/cart-background.jpg);
background-size: cover; background-attachment: fixed; min-height: 100%; overflow: hidden">
    <jsp:include page="support/header.jsp"/>
    <div style="padding-top: 5%">
        <div class="wrap cf">
            <h1 class="projTitle"><fmt:message key="cart.title"/></h1>
            <div class="cart">
                <ul class="cartWrap">
                    <c:forEach items="${sessionScope.cartMap}" var="games">
                        <li class="items even neon-box-white" id="gameCheck" style="border-color: #a51616;">
                            <div class="infoWrap">
                                <div class="cartSection" style="width: 90%">
                                    <img src="${games.key.imagePath}" alt=""
                                         class="itemImg"/>
                                    <p class="itemNumber">${games.key.categories}</p>
                                    <h3>${games.key.title}</h3>
                                    <div>
                                        <p id="amount" class="amountClass"
                                           style="display:inline; color: black; font-size: 18px; margin-left: 20px">${games.value}</p>
                                        x
                                        <p id="price" class="priceClass"
                                           style="display:inline; color: black; font-size: 18px">${games.key.price}$</p>
                                    </div>
                                    <form style="display: inline" method="post" action="decreaseCart.do">
                                        <input type="hidden" name="command" value="change_cart_amount"/>
                                        <input name="clientToken" type="hidden" value="${serverToken}"/>
                                        <input type="hidden" name="operation" value="-">
                                        <input type="hidden" name="currentPage"
                                               value="${pageContext.request.requestURI}">
                                        <input type="hidden" name="gameId" value="${games.key.id}">
                                        <input type="submit" value="-" class="remove decrease"
                                               style="cursor: pointer;color: red; width: 25px"/>
                                    </form>
                                    <form style="display: inline" method="post" action="increaseCart.do">
                                        <input type="hidden" name="command" value="change_cart_amount"/>
                                        <input name="clientToken" type="hidden" value="${serverToken}"/>
                                        <input type="hidden" name="operation" value="+">
                                        <input type="hidden" name="currentPage"
                                               value="${pageContext.request.requestURI}">
                                        <input type="hidden" name="gameId" value="${games.key.id}">
                                        <input type="submit" value="+" class="remove increase"
                                               style="cursor: pointer;color: green; width: 25px"/>
                                    </form>
                                </div>
                                <div class="cartSection removeWrap">
                                    <form method="post" action="removeFromCart.do">
                                        <input type="hidden" name="command" value="remove_from_cart"/>
                                        <input name="clientToken" type="hidden" value="${serverToken}"/>
                                        <input type="hidden" name="currentPage"
                                               value="${pageContext.request.requestURI}">
                                        <input name="gameId" type="hidden" value="${games.key.id}"/>
                                        <p id="gameTotalPrice" class="gameTotalPrice"
                                           style="color: black; font-size: 18px">0.00$</p>
                                        <input type="submit" value="x" class="remove" style="cursor: pointer"/>
                                    </form>
                                </div>
                            </div>
                        </li>
                        <br>
                    </c:forEach>
                </ul>
            </div>
            <div class="form-group promoCode" style="width: 400px">
                <label for="promo" style="color:white"><fmt:message key="cart.promoCode"/></label>
                <form class="needs-validation" novalidate method="post" action="usePromocode.do">
                    <input type="hidden" name="command" value="use_promocode"/>
                    <input name="clientToken" type="hidden" value="${serverToken}"/>
                    <input type="hidden" name="currentPage"
                           value="${pageContext.request.requestURI}">
                    <input class="form-control" type="text" aria-describedby="promocodeHelp" name="couponCode"
                           style="width: 300px; border-radius: 0" required
                           pattern="[a-zA-Z0-9]{5,10}"
                           minlength="5" maxlength="10" id="promo"/>
                    <button type="submit" style="width: 60px; height: 42px; border-radius: 0"
                            class="btn promo-btn"></button>
                    <small class="invalid-feedback" id="promocodeHelp" style="color: red;margin-bottom: 20px">
                        <fmt:message key="cart.notValidPromo"/></small>
                    <c:if test="${requestScope.couponExist == false}"><small
                            style="color: red;margin-bottom: 20px">
                        <fmt:message key="cart.notValidPromo"/></small></c:if>
                </form>
            </div>
            <div class="subtotal cf">
                <ul>
                    <li class="totalRow"><span class="label" style="margin-right: 4%"><fmt:message
                            key="cart.price"/></span><span
                            class="value totalPriceClass"></span></li>
                    <li class="totalRow"><span class="label"><fmt:message key="cart.promo"/></span>
                        <span class="value promoClass"
                              id="promoText"></span>
                    </li>
                    <div class="custom-btn"
                         style="margin-left: 45%;height: 2px; background-color: #EAEFF5; width: 53%;"><br></div>
                    <li class="totalRow final"><span class="label"><fmt:message key="cart.total"/></span><span
                            class="value billClass"></span></li>

                    <li class="totalRow">
                        <form method="post" action="makeOrder.do">
                            <input type="hidden" name="command" value="make_order"/>
                            <input name="clientToken" type="hidden" value="${serverToken}"/>
                            <input type="hidden" name="currentPage"
                                   value="${pageContext.request.requestURI}">
                            <input type="submit" value="Buy" style="width: 175px"
                                   class="btn custom-btn"/>
                        </form>
                    </li>
                </ul>
            </div>
        </div>
        <div style="margin-top: 5%">
            <jsp:include page="support/footer.jsp"/>
        </div>
    </div>
    <script>
        // Remove Items From Cart
        $('a.remove').click(function (e) {
            e.preventDefault();
            $(this).parent().parent().parent().hide(400);
        })
        // Just for testing, show all items
        $('a.btn.continue').click(function () {
            $('li.items').show(400);
        });

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

        var cardElements = document.getElementsByClassName("items");
        var size = cardElements.length;
        var totalPriceHtml = document.getElementsByClassName('totalPriceClass');
        var totalPrice = 0;
        var promoClassHtml = document.getElementsByClassName('promoClass');
        var promoCode = 10;
        var billPriceElements = document.getElementsByClassName('billClass');
        var billPrice = billPriceElements.item(0).innerHTML;
        $(document).ready(
            function () {
                var promoText = document.getElementById('promoText');
                console.log(parseFloat(promoText.innerHTML));
                for (var i = 0; i < size; i++) {
                    var price = cardElements.item(i).getElementsByClassName('priceClass').item(0).innerHTML;
                    var amount = cardElements.item(i).getElementsByClassName('amountClass').item(0).innerHTML;
                    var gameTotalPrice = parseFloat(price) * parseInt(amount);
                    totalPrice += gameTotalPrice;
                    cardElements.item(i).getElementsByClassName('gameTotalPrice').item(0).innerHTML = gameTotalPrice + '$';
                }
                totalPriceHtml.item(0).innerHTML = totalPrice + '$';
                var discount = ${sessionScope.couponDiscount};
                document.getElementById('promoText').innerHTML = discount + '%';
                billPriceElements.item(0).innerHTML = (totalPrice - totalPrice * discount / 100) + '$';
            }
        )
    </script>
</body>
</html>
