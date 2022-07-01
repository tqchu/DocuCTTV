<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" href="${context}/css/admin/inventory/home.css?rd=${rand}">
<div class="search">
    <form action="${context}/admin/inventory/history/search" class="search__form" method="get">
        <input type="search" class="search__bar form-control" name="keyword" placeholder="Tìm kiếm">
        <div class="form-group date-form-group">
            <label for="from">Từ</label>
            <input type="date" name="from" id="from">

        </div>
        <div class="form-group date-form-group">
            <label for="to">Đến</label>
            <input type="date" name="to" id="to">

        </div>
        <button type="submit" class="btn btn-primary btn-search">
            <i class="las la-search"></i>
        </button>

    </form>
    <a href="${context}/admin/inventory?action=create" class="search__add-btn btn btn-primary"
       title="Thêm đơn"><i
            class="las la-plus"></i></a>
</div>
<c:if test="${successMessage!=null}">
    <div class="toast align-items-center toast-message toast-message--success show" role="alert"
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
    <%--    <span class="order-bar__option  ${param.sortBy=='name'?'active':''}" data-sort="name">Tên</span>--%>
</div>
<div class="list">
    <table class="table table-hover table-bordered ">
        <thead>
        <tr>
            <th>Mã đơn</th>
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
                    <td>${currentRow.importId}</td>
                    <td>${currentRow.importerName}</td>
                    <td>${currentRow.providerName}</td>
                    <td>
                        <fmt:formatNumber value="${currentRow.totalPrice}" type="number" maxFractionDigits="0"/>
                    </td>
                    <td>
                            ${currentRow.importDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))}
                    </td>
                    <td class="column__action">
                        <a href="${context}/admin/inventory/history/view?id=${currentRow.importId}" class="btn-edit btn btn-primary">Xem chi tiết</a>
                    </td>

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
