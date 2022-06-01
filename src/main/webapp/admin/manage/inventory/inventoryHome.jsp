<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" href="${context}/css/admin/inventory/home.css?rd=${rand}">
<div class="search">
    <form action="${context}/admin/inventory/search" class="search__form" method="get">
        <input type="search" class="search__bar form-control" name="keyword" placeholder="Nhập tên sản phẩm">
        <button type="submit" class="btn btn-primary btn-search">
            <i class="las la-search"></i>
        </button>

    </form>

</div>
<c:if test="${successMessage!=null}">
    <div class="toast align-items-center toast-message toast-message--success" role="alert"
         aria-live="assertive"
         aria-atomic="true" data-bs-autohide="false">
        <div class="d-flex ">
            <div class="toast-body">
                    ${successMessage}
            </div>
            <button type="button" class="btn-close me-2 m-auto" data-bs-dismiss="toast"
                    aria-label="Close"></button>
        </div>
    </div>
    <c:remove var="successMessage" scope="session"/>
</c:if>
<c:if test="${errorMessage!=null}">
    <div class="toast align-items-center toast-message toast-message--error" role="alert"
         aria-live="assertive"
         aria-atomic="true" data-bs-autohide="false">
        <div class="d-flex ">
            <div class="toast-body">
                    ${errorMessage}
            </div>
            <button type="button" class="btn-close me-2 m-auto" data-bs-dismiss="toast"
                    aria-label="Close"></button>
        </div>
    </div>
    <c:remove var="errorMessage" scope="session"/>

</c:if>
<div class="order-bar">
        <span class="order-bar__text-heading">
            Sắp xếp theo
        </span>
    <input type="hidden" name="query-string"
           value="${not empty param.keyword?'keyword='+=param.keyword:''}">
    <span class="order-bar__option  ${param.sortBy=='name'|| empty param.sortBy?'active':''}" data-sort="name">Tên</span>
    <span class="order-bar__option ${param.sortBy=='quantity'?'active':''}"
          data-sort="quantity">Số lượng</span>
</div>
<div class="list">
    <table class="table table-hover table-bordered ">
        <thead>
        <tr>
            <th>STT</th>
            <th>Mã sản phẩm</th>
            <th>Tên sản phẩm</th>
            <th>Tổng số lượng</th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${empty list}">
            <tr>
                <td colspan="5" class="no-records">
                    Không có dữ liệu
                </td>
            </tr>

        </c:if>
        <c:if test="${not empty list}">
            <c:forEach items="${list}" var="currentRow" varStatus="loop">
                <tr>
                    <td>${loop.count + (not empty param.page?param.page-1:0) * 10}</td>
                    <td>${currentRow.productId}</td>
                    <td><a href="${context}/admin/products?action=view&id=${currentRow.productId}">${currentRow.productName}</a></td>
                    <td>${currentRow.quantity}</td>

                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
</div>
<!-- SURROGATE FORM -->
<form action="${requestURI}" id="surrogateForm">
    <input type="hidden" name="keyword" value="${param.keyword}" ${empty param.keyword?'disabled':''}>
    <input type="hidden" name="field" value="${param.field}" ${empty param.field?'disabled':''}>
    <input type="hidden" name="page" value="${not empty param.page? param.page: 1}">
    <input type="hidden" name="sortBy" value="${param.sortBy}" ${empty param.sortBy?'disabled':''}>
    <input type="hidden" name="order" value="${param.order}" ${empty param.order?'disabled':''}>
</form>
<script src="${context}/js/admin/inventory/inventory.js?rd=${rand}"></script>
