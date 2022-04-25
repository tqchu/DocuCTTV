<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%-- set context path--%>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<c:set var="rand"><%= java.lang.Math.round(java.lang.Math.random() * 10000) %></c:set>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Trang chủ</title>
    <link rel="shortcut icon" href="${context}/favicon.ico"/>

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
    <link rel="stylesheet" href="${context}/css/customer/common.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/customer/home.css?rd=${rand}">
</head>
<body>
<jsp:include page="../common/header.jsp"/>
<jsp:include page="../common/search-header.jsp"/>
<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col col-2-4">
                <div class="category-wrapper">
                    <div class="category__header">
                        <i class="las la-bars category__header__menu-icon"></i>
                        <span class="category__header__text">
Danh mục</span>
                    </div>
                    <div class="category-list">
                        <a href="${context}" class="category-item ${empty param?'active':''}">Tất cả</a>
                        <c:forEach items="${categoryList}" var="category">
                            <a href="${context}/products?category=${category.categoryName}" class="category-item
                            ${category.categoryName==param.category?'active':''}">
                                ${category.categoryName}
                            </a>
                        </c:forEach>


                    </div>
                </div>
            </div>
            <div class="col col-9-6">
                <div class="products-wrapper">
                    <div class="products__sort-selection">
                        <div class="products__sort-selection__heading-text">
                            Sắp xếp theo
                        </div>
                        <div class="products__sort-selection__item">
                            Mới nhất
                        </div>
                        <div class="products__sort-selection__price products__sort-selection__item">
                            <div class="products__sort-selection__price__selected">
                                <span class="products__sort-selection__price__selected__text">Giá</span>
                                <i class="las la-angle-down products__sort-selection__price__selected__icon">
                                </i>
                            </div>
                            <div class="products__sort-selection__price__popover">
                                <a href="" class="products__sort-selection__price-item">
                                    Giá: Thấp đến cao
                                </a>
                                <a href="" class="products__sort-selection__price-item">
                                    Giá: Cao đến thấp
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="products">
                        <div class="container-fluid">
                            <div class="row">
                                <c:choose>
                                    <c:when test="${not empty productList}">
                                        <c:forEach items="${productList}" var="product">
                                            <div class="col col-3">

                                                <a href="${context}/products?id=${product.productId}" class="product">
                                                    <div class="product__image" style="background-image:
                                                            url('${context}/${product.imagePathList[0].path}');">
                                                    </div>
                                                    <div class="product__name">
                                                            ${product.name}
                                                    </div>
                                                    <div class="product__price">
                                                        <fmt:formatNumber value="${product.price}"
                                                                          type="number" maxFractionDigits="0"/>
                                                    </div>
                                                </a>
                                            </div>
                                        </c:forEach>

                                    </c:when>
                                    <c:otherwise>
                                        <div class="no-products-found">
                                            RẤT TIẾC, KHÔNG CÓ SẢN PHẨM NÀO
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../../common/footer.jsp"/>


</body>
</html>
