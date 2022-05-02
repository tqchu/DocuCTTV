<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" href="${context}/css/admin/inventory/home.css?rd=${rand}">
<div class="search">
    <form action="${context}/admin/inventory/search" class="search__form" method="get">
        <input type="search" class="search__bar form-control" name="productName" placeholder="Nhập tên sản phẩm">
        <button type="submit" class="btn btn-primary btn-search">
            <i class="las la-search"></i>
        </button>

    </form>
    <%--        <a href="${context}/admin/inventory/history" class="inventory__history-link">Lịch sử nhập hàng</a>--%>
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
    <span class="order-bar__option ${param.sortBy=='default'|| empty param.sortBy?'active':''}"
          data-sort="default">Mới
            nhất</span>
    <span class="order-bar__option  ${param.sortBy=='name'?'active':''}" data-sort="name">Tên</span>
</div>
<div class="list">
    <table class="table table-hover table-bordered ">
        <thead>
        <tr>
            <th>STT</th>
            <th>Người nhập</th>
            <th>Nhà cung cấp</th>
            <th>Thành tiền</th>
            <th>Ngày nhập</th>
            <th class="column__action"></th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${empty importList}">
            <tr>
                <td colspan="6" class="no-records">
                    Không có dữ liệu
                </td>
            </tr>

        </c:if>
        <c:if test="${not empty importList}">
            <c:forEach items="${importList}" var="currentRow" varStatus="loop">
                <tr>
                    <td>${loop.count}</td>
                    <td>${currentRow.importerName}</td>
                    <td>${currentRow.providerName}</td>
                    <td>${currentRow.totalPrice}</td>
                    <td>${currentRow.importDate}</td>
                    <td class="">
                        <a href="">Xem chi tiết</a>
                    </td>


                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
</div>
<script src="${context}/js/admin/inventory/inventory.js?rd=${rand}"></script>
