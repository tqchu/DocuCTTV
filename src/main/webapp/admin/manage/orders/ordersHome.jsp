<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>

<link rel="stylesheet" href="${context}/css/admin/orders/home.css?rd=${rand}">
<div class="purchase-bar purchase-bar__four-column">
    <a href="${context}/admin/orders/pending" class="purchase-tab-item ${statusTab=='pending'?'active':''}">
        Chờ xác nhận
    </a>
    <a href="${context}/admin/orders/to-ship" class="purchase-tab-item ${statusTab=='to-ship'?'active':''}">
        Chờ vận chuyển
    </a>
    <a href="${context}/admin/orders/to-receive" class="purchase-tab-item ${statusTab=='to-receive'?'active':''}">
        Đang giao
    </a>
    <a href="${context}/admin/orders/completed" class="purchase-tab-item ${statusTab=='completed'?'active':''}">
        Đã giao
    </a>
</div>
<div class="purchase-search-bar">

    <form action="${context}/admin/orders/${statusTab}/search" id="search-form">
        <span class="purchase-search-bar__search-icon-wrapper">
        <i class="las la-search purchase-search-bar__search-icon"></i>
        </span>
        <input type="search" name="keyword" class="purchase-search-bar__input" value="${param.keyword}"
               placeholder="Nhập mã đơn hàng hoặc tên khách hàng">
    </form>
</div>
<div class="order-bar purchase-order-bar">
    <span class="order-bar__text-heading">
            Sắp xếp theo
    </span>
    <span class="order-bar__option
     ${((param.sortBy=='time'|| empty param.sortBy) && (param.order=='DESC'|| empty param.order))?'active':''}"
          data-sort="time" data-order="DESC"
    >Mới
            nhất</span>
    <span class="order-bar__option  ${(param.sortBy=='time'&& param.order=='ASC')?'active':''}" data-sort="time"
          data-order="ASC">Lâu
        nhất</span>
</div>
<div class="purchase-list">
    <table class="table table-hover table-bordered">
        <c:choose>
            <c:when test="${statusTab=='pending'}">
                <jsp:include page="pending.jsp"/>
            </c:when>
            <c:when test="${statusTab=='to-ship'}">
                <jsp:include page="to-ship.jsp"/>
            </c:when>
            <c:when test="${statusTab=='to-receive'}">
                <jsp:include page="to-receive.jsp"/>

            </c:when>
            <c:when test="${statusTab=='completed'}">
                <jsp:include page="completed.jsp"/>

            </c:when>
        </c:choose>
    </table>

</div>
<%--<jsp:include page="../../common/footer.jsp"/>--%>

<!-- SURROGATE FORM -->
<form action="${requestURI}" id="surrogateForm">
    <input type="hidden" name="keyword" value="${param.keyword}" ${empty param.keyword?'disabled':''}>
    <input type="hidden" name="page" value="${not empty param.page? param.page: 1}">
    <input type="hidden" name="sortBy" value="${param.sortBy}" ${empty param.sortBy?'disabled':''}>
    <input type="hidden" name="order" value="${param.order}" ${empty param.order?'disabled':''}>
</form>
<script src="${context}/js/admin/order/order.js?rd=${rand}"></script>
