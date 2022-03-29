<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<%--FONT AWESOME ICON--%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
      integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g=="
      crossorigin="anonymous" referrerpolicy="no-referrer"/>
<%--CONTENT--%>
<div class="customer-header">
    <div class="customer-header__contact">
        <div class="customer-header__phone">
            <i class="fa-solid fa-phone"></i>
            <a href="tel:0395397398" class="customer-header__phone__text">0395397398</a>
        </div>
        <div class="customer-header__social">
            <span class="customer-header__social__text">Hỗ trợ:</span>
            <span class="customer-header__facebook-icon"><i class="fa-brands fa-facebook"></i></span>
            <span class="customer-header__insta-icon">
                <i class="fa-brands fa-instagram"></i>
            </span>
        </div>
        <c:if test="${customer!=null}">
            <div class="information">
                <span class="information__icon">
                    <i class="fa-solid fa-user"></i>
                </span>
                <span class="information__name">
                        ${customer.fullName}
                </span>
                <div class="information__popover">
                    <a href="${context}/customer/update" class="information__popover-item">Thay đổi thông
                        tin</a>
                    <a href="${context}/customer/logout" class="information__popover-item">Đăng xuất</a>
                </div>
            </div>
        </c:if>
    </div>
    <div class="customer-header__account"></div>
</div>