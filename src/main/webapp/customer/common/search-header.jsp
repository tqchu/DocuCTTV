<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="context" value="${pageContext.request.contextPath}"/>
<%--CONTENT--%>
<div class="header">
    <a href="${context}" class="header__logo"></a>
    <c:if test="${not empty action}">
        <div class="header__action">
            ${action}
        </div>
    </c:if>
    <c:if test="${empty action}">
        <div class="search">
            <form action="${context}/products/search" class="search-form">
                <input type="search" class="search-input" name="keyword" value="${param.keyword}">
                <button class="btn btn-primary btn-search"><i class="las la-search"></i></button>
            </form>
        </div>
        <a href="${context}/user/cart" class="cart">
            <i class="las la-shopping-cart"></i>
        </a>
    </c:if>
</div>