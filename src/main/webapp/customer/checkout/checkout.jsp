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
    <link rel="stylesheet" href="${context}/css/base.css?rd=">
    <link rel="stylesheet" href="${context}/css/style.css?rd=">
</head>
<body>
<c:set var="action" value="Thanh toán" scope="request"/>
<jsp:include page="../common/header.jsp"/>
<jsp:include page="../common/search-header.jsp"/>
<div class="content">
    <div class="address"></div>
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
            <div class="product-list__item">
                <div class="product-list__product-column">
                    <div class="product-column__product-img"
                         style="background-image: url('https://cf.shopee.vn/file/fd55a12d80f6668a31796c39a6c13269_tn')">

                    </div>
                    <div class="product-column__product-name">Áo thun nam POLO, áo có cổ trơn vải cá sấu cotton cao cấp
                        ngắn tay cực sang trọng
                    </div>
                </div>
                <div class="product-list__price-column">
                    69.000
                </div>
                <div class="product-list__quantity-column">
                    69.000
                </div>

            </div>

        </div>
    </div>
</div>

<%--<jsp:include page="../../common/footer.jsp"/>--%>


</body>
</html>
