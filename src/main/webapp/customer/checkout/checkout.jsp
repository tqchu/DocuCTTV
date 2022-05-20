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
    <link rel="stylesheet" href="${context}/css/customer/checkout.css?rd=${rand}">

</head>
<body>
<c:set var="action" value="Thanh toán" scope="request"/>
<jsp:include page="../common/header.jsp"/>
<jsp:include page="../common/search-header.jsp"/>
<div class="content">
    <div class="address">
        <div class="address__heading-text">
            Địa chỉ nhận hàng
        </div>
        <div class="address__content">
            <div class="address__recipient-infor">
                ${customer.address.recipientName} (${customer.address.phoneNumber})
                <input type="hidden" name="recipientName" value="${customer.address.recipientName}"
                       form="checkout-form">
                    <input type="hidden" name="customerId" value="${customer.userId}" form="checkout-form">
                    <input type="hidden" name="customerName" value="${customer.fullName}" form="checkout-form">
                <input type="hidden" name="phoneNumber" value="${customer.address.phoneNumber}"
                       form="checkout-form">

            </div>
            <div class="address__shipping-address">
                ${customer.address.address}
                    <input type="hidden" name="address" value="${customer.address.address}"
                           form="checkout-form">
            </div>
            <div class="address__change">Thay đổi</div>
        </div>
    </div>
    <div class="product-list">
        <div class="product-list__header">
            <div class="product-list__product-column">
                Sản phẩm
            </div>
            <div class="product-list__price-column">
                Đơn giá
            </div>
            <div class="product-list__quantity-column">
                Số lượng
            </div>

        </div>
        <div class="product-list__content">
            <c:forEach items="${checkoutList}" var="item">
<%--                CHECKOUT FORM INPUT--%>
                <input type="hidden" name="productId" value="${item.product.productId}"
                       form="checkout-form">
                <input type="hidden" name="productName" value="${item.product.name}"
                       form="checkout-form">
                <input type="hidden" name="price" value="${item.product.price}"
                       form="checkout-form">
                <input type="hidden" name="quantity" value="${item.quantity}"
                       form="checkout-form">

                <div class="product-list__item">
                    <div class="product-list__product-column">
                        <div class="product-column__product-img"
                             style="background-image: url('${context}/${item.product.imagePathList[0].path}')">

                        </div>
                        <div class="product-column__product-name">
                                ${item.product.name}
                        </div>
                    </div>
                    <div class="product-list__price-column">
                            ${item.product.price}
                    </div>
                    <div class="product-list__quantity-column">
                            ${item.quantity}
                    </div>

                </div>
            </c:forEach>


        </div>
    </div>
    <div class="summary">
        <div class="summary-item">
            <div class="summary-item__name">Tổng tiền hàng</div>
            <div class="summary-item__value price">${totalPrice}</div>
        </div>
        <div class="summary-item">
            <div class="summary-item__name">Phí vận chuyển</div>
            <div class="summary-item__value price">${shippingFee}</div>
            <input type="hidden" name="shippingFee" value="${shippingFee}" form="checkout-form">
        </div>
        <div class="summary-item">
            <div class="summary-item__name">Tổng tiền</div>
            <div class="summary-item__value price summary-item__total-price">${totalPrice+shippingFee}</div>
        </div>
        <div class="summary-item">
            <div class="summary-item__name">Phương thức thanh toán</div>
            <div class="summary-item__value">Thanh toán khi nhận hàng</div>
        </div>
    </div>
    <div class="confirm-bar">
        <button type="submit" class="btn btn-primary btn-lg" form="checkout-form">Đặt hàng</button>
    </div>
</div>
<form action="${context}/user/purchase/" id="checkout-form" method="POST">
    <input type="hidden" name="action" value="create">
</form>
<%--<jsp:include page="../../common/footer.jsp"/>--%>


</body>
</html>
