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
Doanh mục</span>
                    </div>
                    <div class="category-list">
                        <c:forEach items="${categoryList}" var="category">
                            <a href="" class="category-item">
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
                                <c:forEach items="${productList}" var="product">
                                    <div class="col col-3">

                                        <a href="${context}/product?id=${product.productId}" class="product">
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
                                <%--<div class="col col-3">

                                    <a href="" class="product">
                                        <div class="product__image" style="background-image:
                                                url('${context}/images/products/ban-an-roma-1.jpg');">
                                        </div>
                                        <div class="product__name">
                                            Bàn ăn Roma 6 chỗ
                                        </div>
                                        <div class="product__price">9,720,000</div>
                                    </a>
                                </div>
                                <div class="col col-3">
                                    <a href="" class="product">
                                        <div class="product__image" style="background-image:
                                                url('${context}/images/products/ban-an-bridge-1.jpg');">
                                        </div>
                                        <div class="product__name">
                                            Bàn ăn Bridge 6 chỗ
                                        </div>
                                        <div class="product__price">8,720,000</div>
                                    </a>
                                </div>
                                <div class="col col-3">
                                    <a href="" class="product">
                                        <div class="product__image" style="background-image:
                                                url('${context}/images/products/ban-an-soul-1.jpg');">
                                        </div>
                                        <div class="product__name">
                                            Bàn ăn Soul
                                        </div>
                                        <div class="product__price">7,720,000</div>
                                    </a>
                                </div>
                                <div class="col col-3">
                                    <a href="" class="product">
                                        <div class="product__image" style="background-image:
                                                url('${context}/images/products/giuong-iris-1.jpg');">
                                        </div>
                                        <div class="product__name">
                                            Giường ngủ Iris
                                        </div>
                                        <div class="product__price">79,720,000</div>
                                    </a>
                                </div>
                                <div class="col col-3">
                                    <a href="" class="product">
                                        <div class="product__image" style="background-image:
                                                url('${context}/images/products/giuong-lac-vien-1.jpg');">
                                        </div>
                                        <div class="product__name">
                                            Giường ngủ lạc viên
                                        </div>
                                        <div class="product__price">89,720,000</div>
                                    </a>
                                </div>
                                <div class="col col-3">
                                    <a href="" class="product">
                                        <div class="product__image" style="background-image:
                                                url('${context}/images/products/ban-an-roma-1.jpg');">
                                        </div>
                                        <div class="product__name">
                                            Bàn ăn Roma 6 chỗ
                                        </div>
                                        <div class="product__price">9,720,000</div>
                                    </a>
                                </div>
                                <div class="col col-3">
                                    <a href="" class="product">
                                        <div class="product__image" style="background-image:
                                                url('${context}/images/products/ban-an-roma-1.jpg');">
                                        </div>
                                        <div class="product__name">
                                            Bàn ăn Roma 6 chỗ
                                        </div>
                                        <div class="product__price">9,720,000</div>
                                    </a>
                                </div>
                                <div class="col col-3">
                                    <a href="" class="product">
                                        <div class="product__image" style="background-image:
                                                url('${context}/images/products/ban-an-roma-1.jpg');">
                                        </div>
                                        <div class="product__name">
                                            Bàn ăn Roma 6 chỗ
                                        </div>
                                        <div class="product__price">9,720,000</div>
                                    </a>
                                </div>
                                <div class="col col-3">
                                    <a href="" class="product">
                                        <div class="product__image" style="background-image:
                                                url('${context}/images/products/ban-an-roma-1.jpg');">
                                        </div>
                                        <div class="product__name">
                                            Bàn ăn Roma 6 chỗ
                                        </div>
                                        <div class="product__price">9,720,000</div>
                                    </a>
                                </div>
                                <div class="col col-3">
                                    <a href="" class="product">
                                        <div class="product__image" style="background-image:
                                                url('${context}/images/products/ban-an-roma-1.jpg');">
                                        </div>
                                        <div class="product__name">
                                            Bàn ăn Roma 6 chỗ
                                        </div>
                                        <div class="product__price">9,720,000</div>
                                    </a>
                                </div>
                                <div class="col col-3">
                                    <a href="" class="product">
                                        <div class="product__image" style="background-image:
                                                url('${context}/images/products/ban-an-roma-1.jpg');">
                                        </div>
                                        <div class="product__name">
                                            Bàn ăn Roma 6 chỗ
                                        </div>
                                        <div class="product__price">9,720,000</div>
                                    </a>
                                </div>--%>
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
