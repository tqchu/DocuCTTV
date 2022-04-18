<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%-- set context path--%>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<c:set var="rand"><%= java.lang.Math.round(java.lang.Math.random() * 10000) %></c:set>

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
    <!-- BOOSTRAP CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <%--    LINE-AWESOME--%>
    <link rel="stylesheet"
          href="https://maxst.icons8.com/vue-static/landings/line-awesome/font-awesome-line-awesome/css/all.min.css">
    <!-- APP -->
    <link rel="stylesheet" href="${context}/css/base.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/style.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/customer/product.css?rd=${rand}">
</head>
<body>
<jsp:include page="../common/header.jsp"/>
<jsp:include page="../common/search-header.jsp"/>
<div class="content">
    <div class="container-fluid">
        <div class="product__header">
            <div class="container-fluid">
                <div class="row">
                    <div class="col col-4">
                        <div class="d-flex align-items-center position-relative h-100">
                            <div class="product__image">
                                <div id="product-image-slide" class="carousel slide" data-bs-ride="carousel">
                                    <div class="carousel-inner">
                                        <c:forEach items="${product.imagePathList}" var="path" varStatus="loop">
                                            <div class="carousel-item ${loop.count==1?'active':''}">
                                                <img src="${context}/${path.path}"
                                                     class="d-block w-100">
                                            </div>
                                        </c:forEach>

                                    </div>
                                    <button class="carousel-control-prev" type="button"
                                            data-bs-target="#product-image-slide"
                                            data-bs-slide="prev">
                                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                        <span class="visually-hidden">Previous</span>
                                    </button>
                                    <button class="carousel-control-next" type="button"
                                            data-bs-target="#product-image-slide"
                                            data-bs-slide="next">
                                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                        <span class="visually-hidden">Next</span>
                                    </button>
                                </div>
                            </div>
                            <div class="product__category">
                                <span class="product__category__text-heading">
                                   Doanh mục
                                </span>
                                <a href="" class="product__category_text-description">
                                    ${product.category.categoryName}
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="col col-8">
                        <div class="product__description-and-action">
                            <div class="product__name">
                                ${product.name}
                            </div>
                            <div class="product__price">
                                <fmt:formatNumber type="number" maxFractionDigits="0"
                                                  value="${product.price}"/>
                            </div>
                            <div class="product__info">
                                <span class="product__info__text-heading">
                                    Kích thước
                                </span>
                                <ul class="dimension-group">
                                    <c:forEach items="${product.dimensionList}" var="dimension">
                                    <li class="product__info__text-content">
                                        ${dimension.length}D x ${dimension.width}R x ${dimension.height}C (cm)
                                    </li>
                                    </c:forEach>
                                </ul>
                            </div>
                            <div class="product__info">
<span class="product__info__text-heading">
                                    Vật liệu
                                </span>
                                <ul class="material-group">
                                    <c:forEach items="${product.materialList}" var="material">
                                        <li class="product__info__text-content">${material.materialName}</li>
                                    </c:forEach>
                                </ul>
                            </div>
                            <div class="product__info">
<span class="product__info__text-heading">
                                    Bảo hành
                                </span>
                                <span class="product__info__text-content">${product.warrantyPeriod} tháng</span>
                            </div>
                            <div class="product__quantity">
                                <span class="product__action__text-description">Số lượng</span>
                                <div class="product__quantity__spinner">
                                    <input type="number" value="1" min="1" max="${product.quantity}" step="1"/>
                                </div>
                                <span class="product__quantity__stock">
                                    ${product.quantity} Sản phẩm có sẵn
                                </span>
                            </div>
                            <div class="product__action">
                                <div class="product__action__add-to-cart"><i
                                        class="las la-shopping-cart product__action__add-to-cart__icon"></i>Thêm
                                    vào giỏ hàng
                                </div>
                                <div class="product__action__buy">Mua ngay</div>

                            </div>


                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="product__meta-info">
            <div class="product__meta-info__tabs">
                <span class="product__meta-info__tab-item  active">Mô tả</span>
                <span class="product__meta-info__tab-item">Đánh giá (0)</span>
            </div>
            <div class="product__meta-info__content">
                <div class="product__meta-info__content-of-item product__meta-info__content__description">
                   ${product.description}
                </div>
                <div class="product__meta-info__content-of-item product__meta-info__content__reviews">

                </div>
            </div>
        </div>
        <div class="product__similar-product">
            <div class="product__similar-product__text-heading">
                SẢN PHẨM TƯƠNG TỰ
            </div>
            <div class="product__similar-product__product-list">

            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://shaack.com/projekte/bootstrap-input-spinner/src/bootstrap-input-spinner.js"></script>
<script src="${context}/js/customer/product.js?rd=${rand}"></script>
<jsp:include page="../../common/footer.jsp"/>


</body>
</html>
