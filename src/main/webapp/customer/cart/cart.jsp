<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%-- set context path--%>
<c:set var="context" value="${pageContext.request.contextPath}" scope="request"/>
<c:set var="rand"><%= java.lang.Math.round(java.lang.Math.random() * 10000) %>
</c:set>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>
    <link rel="shortcut icon" href="${context}/favicon.ico">
    <!-- RESET CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css"
          integrity="sha512-NmLkDIU1C/C88wi324HBc+S2kLhi08PN5GDeUVVVC/BVt/9Izdsc9SVeVfA1UZbY3sHUlDSyRXhCzHfr6hmPPw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>

    <!-- FONT   -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <!-- APP -->
    <link rel="stylesheet" href="${context}/css/base.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/style.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/customer/common.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/customer/cart.css?rd=${rand}">
</head>
<body>
<c:set var="action" value="Giỏ hàng" scope="request"/>
<jsp:include page="../common/header.jsp"/>
<jsp:include page="../common/search-header.jsp"/>
<div class="content">
    <div class="cart-header">
        <div class="cart__check-btn">
            <input type="checkbox" name="all-item"
            >
        </div>
        <div class="cart-header__product-column">
            Sản phẩm
        </div>
        <div class="cart-header__price-column">
            Đơn giá
        </div>
        <div class="cart-header__quantity-column">
            Số lượng
        </div>
        <div class="cart-header__action-column">
            Thao tác
        </div>
    </div>
    <div class="cart-list">
        <div class="cart-item">
            <div class="cart__check-btn">
                <input type="checkbox" name="item"
                >
            </div>
            <div class="cart-header__product-column">
                <a href="" class="cart-header__product-link">
                    <div class="cart-header__product-image"
                         style="background-image: url('https://cf.shopee.vn/file/fd55a12d80f6668a31796c39a6c13269_tn')"></div>
                    <div class="cart-header__product-name">Áo thun nam POLO, áo có cổ trơn vải cá sấu cotton cao cấp
                        ngắn tay cực sang trọng
                    </div>
                </a>

            </div>
            <div class="cart-header__price-column">
                69.000
            </div>
            <div class="cart-header__quantity-column">
                <input type="number" value="50" min="0" max="10" step="1" name="quantity"/>
            </div>
            <div class="cart-header__action-column">
                Xóa
            </div>
        </div>
        <div class="cart-item">
            <div class="cart__check-btn">
                <input type="checkbox" name="item" value=""
                >
            </div>
            <div class="cart-header__product-column">
                <a href="" class="cart-header__product-link">
                    <div class="cart-header__product-image"
                         style="background-image: url('https://cf.shopee.vn/file/fd55a12d80f6668a31796c39a6c13269_tn')"></div>
                    <div class="cart-header__product-name">Áo thun nam POLO, áo có cổ trơn vải cá sấu cotton cao cấp
                        ngắn tay cực sang trọng
                    </div>
                </a>

            </div>
            <div class="cart-header__price-column">
                69.000
            </div>
            <div class="cart-header__quantity-column">
                <input type="number" value="50" min="0" max="10" step="1" name="quantity"/>
            </div>
            <div class="cart-header__action-column">
                Xóa
            </div>
        </div>
        <div class="cart-item">
            <div class="cart__check-btn">
                <input type="checkbox" name="item"
                >
            </div>
            <div class="cart-header__product-column">
                <a href="" class="cart-header__product-link">
                    <div class="cart-header__product-image"
                         style="background-image: url('https://cf.shopee.vn/file/fd55a12d80f6668a31796c39a6c13269_tn')"></div>
                    <div class="cart-header__product-name">Áo thun nam POLO, áo có cổ trơn vải cá sấu cotton cao cấp
                        ngắn tay cực sang trọng
                    </div>
                </a>

            </div>
            <div class="cart-header__price-column">
                69.000
            </div>
            <div class="cart-header__quantity-column">
                <input type="number" value="50" min="0" max="10" step="1" name="quantity"/>
            </div>
            <div class="cart-header__action-column">
                Xóa
            </div>
        </div>
        <div class="cart-item">
            <div class="cart__check-btn">
                <input type="checkbox" name="item"
                >
            </div>
            <div class="cart-header__product-column">
                <a href="" class="cart-header__product-link">
                    <div class="cart-header__product-image"
                         style="background-image: url('https://cf.shopee.vn/file/fd55a12d80f6668a31796c39a6c13269_tn')"></div>
                    <div class="cart-header__product-name">Áo thun nam POLO, áo có cổ trơn vải cá sấu cotton cao cấp
                        ngắn tay cực sang trọng
                    </div>
                </a>

            </div>
            <div class="cart-header__price-column">
                69.000
            </div>
            <div class="cart-header__quantity-column">
                <input type="number" value="50" min="0" max="10" step="1" name="quantity"/>
            </div>
            <div class="cart-header__action-column">
                Xóa
            </div>
        </div>
        <div class="cart-item">
            <div class="cart__check-btn">
                <input type="checkbox" name="item"
                >
            </div>
            <div class="cart-header__product-column">
                <a href="" class="cart-header__product-link">
                    <div class="cart-header__product-image"
                         style="background-image: url('https://cf.shopee.vn/file/fd55a12d80f6668a31796c39a6c13269_tn')"></div>
                    <div class="cart-header__product-name">Áo thun nam POLO, áo có cổ trơn vải cá sấu cotton cao cấp
                        ngắn tay cực sang trọng
                    </div>
                </a>

            </div>
            <div class="cart-header__price-column">
                69.000
            </div>
            <div class="cart-header__quantity-column">
                <input type="number" value="50" min="0" max="10" step="1" name="quantity"/>
            </div>
            <div class="cart-header__action-column">
                Xóa
            </div>
        </div>
    </div>
</div>
<div class="cart__payment">
    <div class="cart__payment__delete">Xóa</div>
    <div class="cart__payment__summary">
        <span class="cart__payment__summary-text">Tổng thanh toán (<span id="numOfItems">10</span> sản phẩm): </span>
        <span class="cart__payment__summary-price" id="totalAmount">400.000 đ</span>
    </div>
    <a href="" class="cart__payment__checkout-btn btn btn-primary btn-lg">
        Mua hàng
    </a>
</div>

<%--<jsp:include page="../../common/footer.jsp"/>--%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://shaack.com/projekte/bootstrap-input-spinner/src/bootstrap-input-spinner.js"></script>
<script src="${context}/js/customer/spinnerHandler.js"></script>
<script src="${context}/js/lib/accounting.min.js"></script>
<script src="${context}/js/customer/cart.js?rd=${rand}"></script>
</body>
</html>
