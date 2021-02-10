<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="property.language"/>
<html>
<head>
    <title><fmt:message key="cart.title"/></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/test.css">
</head>
<body>
<div style="background-color: black;
background-size: cover; background-attachment: fixed; min-height: 100%; overflow: hidden">
    <jsp:include page="support/header.jsp"/>
    <div style="padding-top: 5%">
        <div class="wrap cf">
            <h1 class="projTitle">Cart</h1>
            <div class="cart">
                <ul class="cartWrap">
                    <c:forEach items="${sessionScope.cartMap}" var="games">
                        <li class="items even" id="gameCheck">
                            <div class="infoWrap">
                                <div class="cartSection">
                                    <img src="${games.key.imagePath}" alt=""
                                         class="itemImg"/>
                                    <p class="itemNumber">${games.key.title}</p>
                                    <h3>${games.key.title}</h3>
                                    <p id="amount" class="amountClass"
                                       style="display:inline;">${games.value}</p> x
                                    <p id="price" class="priceClass"
                                       style="display:inline">${games.key.price}$</p>
                                </div>
                                <div class="prodTotal cartSection">
                                    <p id="gameTotalPrice" class="gameTotalPrice">$15.00</p>
                                </div>
                                <div class="cartSection removeWrap">
                                    <a onclick="" class="remove" style="cursor: pointer;">x</a>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="promoCode"><label for="promo">Promo code</label>
                <input type="text" name="promo" id="promo" placholder="Enter Code"/>
                <a href="#" class="btn"></a></div>
            <div class="subtotal cf">
                <ul>
                    <li class="totalRow"><span class="label">Price</span><span class="value">$35.00</span></li>
                    <li class="totalRow"><span class="label">Promo</span><span class="value">$5.00</span></li>
                    <li class="totalRow final"><span class="label">Total</span><span class="value">$44.00</span></li>
                    <li class="totalRow"><a href="#" class="btn continue">Checkout</a></li>
                </ul>
            </div>
        </div>
        <div style="margin-top: 5%">
            <jsp:include page="support/footer.jsp"/>
        </div>
    </div>
    <script>
        // Remove Items From Cart
        $('a.remove').click(function () {
            event.preventDefault();
            $(this).parent().parent().parent().hide(400);
        })
        // Just for testing, show all items
        $('a.btn.continue').click(function () {
            $('li.items').show(400);
        });

        var elementsByClassName = document.getElementsByClassName("items");
        var size = elementsByClassName.length;

        $(document).ready(
            function () {
                for (var i = 0; i < size; i++) {
                    var price = elementsByClassName.item(i).getElementsByClassName('priceClass').item(0).innerHTML;
                    var amount = elementsByClassName.item(i).getElementsByClassName('amountClass').item(0).innerHTML;
                    var gameTotalPrice = parseFloat(price) * parseInt(amount);
                    elementsByClassName.item(i).getElementsByClassName('gameTotalPrice').item(0).innerHTML = gameTotalPrice + '$';
                }
            }
        )

    </script>
</body>
</html>
