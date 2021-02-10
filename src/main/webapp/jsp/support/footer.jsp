<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${sessionScope.currentLocale}"/>
<fmt:setBundle basename="property.language"/>
<html>
<head>
</head>
<body>
<footer class="mainfooter" role="contentinfo">
    <div class="footer-middle">
        <div class="container">
            <div class="row" style="margin-bottom: 1%; margin-left: 45%">
                <div class="col-md-3">
                    <ul class="social-network social-circle">
                        <li><a href="https://www.linkedin.com/in/illia-aheyeu/" target="_blank" class="icoLinkedIn"><i
                                class="fa fa-linkedin"></i></a></li>
                        <li><a href="https://vk.com/isethesal" target="_blank" class="icoVk"><i
                                class="fa fa-vk"></i></a></li>
                        <li><a href="https://t.me/isethesal" target="_blank" class="icoTelegram"><i
                                class="fab fa-telegram-plane"></i></a></li>
                    </ul>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12 copy">
                    <p class="text-center"><fmt:message key="footer.copyright"/></p>
                </div>
            </div>
        </div>
    </div>
</footer>
</body>
</html>
