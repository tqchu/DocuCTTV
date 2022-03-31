<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>

<%-- FONT ICON--%>
<link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
<%--CONTENT--%>
<div class="user-header">
    <div class="user-header__contact">
        <div class="user-header__phone">
            <i class="las la-phone-volume"></i>
            <a href="tel:0395397398" class="user-header__phone__text">0395397398</a>
        </div>
        <div class="user-header__social">
            <span class="user-header__social__text">Hỗ trợ:</span>
            <span class="user-header__facebook-icon"><i class="lab la-facebook"></i></span>
            <span class="user-header__insta-icon">
                <i class="lab la-instagram"></i>
            </span>
        </div>
        <c:if test="${user!=null}">
            <div class="information">
                <span class="information__icon">
                    <i class="fa-solid fa-user"></i>
                </span>
                <span class="information__name">
                        ${user.fullName}
                </span>
                <div class="information__popover">
                    <a href="${context}/user/update" class="information__popover-item">Thay đổi thông
                        tin</a>
                    <a href="${context}/user/logout" class="information__popover-item">Đăng xuất</a>
                </div>
            </div>
        </c:if>
    </div>
    <div class="user-header__account"></div>
</div>