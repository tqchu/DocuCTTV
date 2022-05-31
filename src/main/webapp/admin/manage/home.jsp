<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<c:set var="context" value="${pageContext.request.contextPath}" scope="request"/>
<c:set var="rand" scope="request"><%= java.lang.Math.round(java.lang.Math.random() * 10000) %>
</c:set>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Trang chủ</title>

    <!-- RESET CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css"
          integrity="sha512-NmLkDIU1C/C88wi324HBc+S2kLhi08PN5GDeUVVVC/BVt/9Izdsc9SVeVfA1UZbY3sHUlDSyRXhCzHfr6hmPPw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <!-- BOOSTRAP CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- FONT   -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">

    <%-- APP--%>
    <link rel="stylesheet" href="${context}/css/base.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/style.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/admin/home.css?rd=${rand}">
    <%--    JS--%>


    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<jsp:include page="../common/header.jsp"/>
<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col col-2-4">
                <div class="slide-navbar">
                    <%--<div class="collapse-button">
                        <i class="las la-angle-double-left"></i>
                    </div>--%>
                    <%--<div class="expand-button">
                        <i class="las la-angle-double-right"></i>
                    </div>--%>
                    <div class="navbar__tab-list">
                        <a href="${context}/admin/products" class="navbar__tab ${tab=='products'?'active':''}">
                            <span class="navbar__tab__icon"><i class="las la-chair"></i></span>

                            <span class="navbar__tab__text-description">Sản phẩm</span>
                        </a>
                        <a href="${context}/admin/inventory" class="navbar__tab
                        inventory__tab ${tab=='inventory'|| tab=='inventoryHistory'?'active':''}">
                            <span class="navbar__tab__icon"><i class="las la-warehouse"></i></span>
                            <span class="navbar__tab__text-description">Kho hàng</span>
                            <div class="inventory__tabs">
                                <div class="inventory_tabs__item ${tab=='inventory'?'active':''}"
                                     href="${context}/admin/inventory">Kho
                                </div>
                                <div class="inventory_tabs__item ${tab=='inventoryHistory'?'active':''}"
                                     href="${context}/admin/inventory/history">Nhập hàng
                                </div>
                            </div>

                        </a>
                        <a href="${context}/admin/categories" class="navbar__tab ${tab=='categories'?'active':''}">
                            <span class="navbar__tab__icon"><i class="las la-list-ul"></i></span>

                            <span class="navbar__tab__text-description">Danh mục</span>
                        </a>
                        <a href="${context}/admin/providers" class="navbar__tab ${tab=='providers'?'active':''}">
                            <span class="navbar__tab__icon"><i class="las la-industry"></i></span>
                            <span class="navbar__tab__text-description">Nhà cung cấp</span>
                        </a>
                        <a href="${context}/admin/orders" class="navbar__tab ${tab=='orders'?'active':''}">
                        <span class="navbar__tab__icon">
                            <i class="las la-shopping-bag"></i>
                        </span>
                            <span class="navbar__tab__text-description">Đơn hàng</span>
                        </a>
                        <a href="${context}/admin/statistics" class="navbar__tab ${tab=='statistics'?'active':''}">
                            <span class="navbar__tab__icon"><i class="las la-balance-scale"></i></span>
                            <span class="navbar__tab__text-description">Thống kê</span>
                        </a>
                        <a href="${context}/admin/customers" class="navbar__tab ${tab=='customers'?'active':''}">
                            <span class="navbar__tab__icon"><i class="las la-user-tie"></i></span>
                            <span class="navbar__tab__text-description">Khách hàng</span>
                        </a>
                        <c:if test="${admin.role=='super'}">
                            <a href="${context}/admin/admins" class="navbar__tab ${tab=='admins'?'active':''}">
                                <span class="navbar__tab__icon"><i class="las la-user-cog"></i></span>
                                <span class="navbar__tab__text-description">Quản trị viên</span>
                            </a>
                        </c:if>
                    </div>
                </div>
            </div>
            <div class="col col-9-6">
                <div class="tab__content">
                    <c:if test="${tab=='products'}">
                        <jsp:include page="product/productHome.jsp"/>
                    </c:if>
                    <c:if test="${tab=='inventory'}">
                        <jsp:include page="inventory/inventoryHome.jsp"/>
                    </c:if>
                    <c:if test="${tab=='inventoryHistory'}">
                        <jsp:include page="inventory/history.jsp"/>
                    </c:if>
                    <c:if test="${tab=='categories'}">
                        <jsp:include page="category/categoryHome.jsp"/>
                    </c:if>
                    <c:if test="${tab=='admins'}">
                        <jsp:include page="admins/adminHome.jsp"/>
                    </c:if>
                    <c:if test="${tab=='providers'}">
                        <jsp:include page="provider/providerHome.jsp"/>
                    </c:if>
                    <c:if test="${tab=='orders'}">
                        <jsp:include page="orders/ordersHome.jsp"/>
                    </c:if>
                    <c:if test="${tab=='orderDetail'}">
                        <jsp:include page="orders/orderDetail.jsp"/>
                    </c:if>
                    <c:if test="${tab=='statistics'}">
                        <jsp:include page="../statistics.jsp"/>
                    </c:if><c:if test="${tab=='customers'}">
                    <jsp:include page="customer/customerHome.jsp"/>
                </c:if>
                    <!-- PAGINATION -->
                    <c:if test="${tab!='orderDetail'&&tab!='statistics'}">
                        <jsp:include page="../../common/pagination.jsp"/>
                    </c:if>
                </div>

            </div>
        </div>
    </div>
</div>

<script src="${context}/js/admin/home.js?rd=${rand}"></script>
<%--<jsp:include page="../../common/footer.jsp"/>--%>

</body>
</html>