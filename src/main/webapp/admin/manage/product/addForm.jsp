<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%-- set context path--%>
<c:set var="context" value="${pageContext.request.contextPath}"/>
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
    <!-- BOOSTRAP CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- FONT   -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <!-- APP -->
    <link rel="stylesheet" href="${context}/css/base.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/style.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/admin/product/common.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/admin/addForm.css?rd=${rand}">
    <!-- FONT AWESOME -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
          integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
</head>
<body>
<jsp:include page="../../common/header.jsp"/>
<div class="content">
    <form action="${context}/admin/products" enctype="multipart/form-data" method="post" class="products__add-form">
        <input type="hidden" name="action" value="create">
        <div class="form-group">
            <span class="form-group__label">Tên sản phẩm</span>

            <input type="text" name="productName" id="productName">
        </div>
        <div class="form-group">
            <span class="form-group__label">Ảnh</span>

            <div id="coba"></div>
        </div>

        <div class="form-group">
            <span class="form-group__label">Mô tả sản phẩm</span>

            <textarea name="description" id="" cols="60" rows="8"></textarea>
        </div>
        <div class="form-group dimen-mater-price-group">
            <div class="dimension-form-group">
                <span class="dimension-form-group__label form-group__label">Kích thước</span>
                <div class="dimension-form-group__input">
                    <div class="dimension-form-item">
                        <input type="number" name="length">
                        <span class="dimension-form-item__text-label">D</span>
                    </div>
                    <div class="dimension-form-item">

                        <input type="text" name="width">
                        <span class="dimension-form-item__text-label">R</span>
                    </div>
                    <div class="dimension-form-item">

                        <input type="text" name="height">
                        <span class="dimension-form-item__text-label">C</span>
                    </div>
                </div>
            </div>
            <div class="material-form-group">
                             <span class="material-form-group__label form-group__label">
                                Chất liệu
                             </span>
                <div class="material-form-group__input">
                    <input type="text" name="material" id="material"
                    >
                </div>
            </div>
            <div class="price-form-group">
                <span class="price-form-group__label form-group__label">Đơn giá</span>
                <div class="price-form-group__input">
                    <input type="text" name="price">
                </div>

            </div>
            <div class="form-group__minus-row-btn">
                <i class="fa-solid fa-minus"></i>
            </div>
        </div>
        <div class="form-group__add-row-btn">
            <i class="las la-plus-circle"></i>
        </div>

        <div class="form-group">
            <span class="form-group__label">Thời gian bảo hành</span>
            <input type="number" name="warrantyPeriod">
            <span class="warranty-period-unit">tháng</span>
        </div>
        <div class="form-group">
            <span class="form-group__label">Doanh mục</span>
            <%-- SELECT ROLE --%>
            <select class="form-select category-select" name="categoryId">
                <option value="" selected>Chưa phân loại</option>
                <c:forEach items="${categoryList}" var="category">
                    <option value="${category.categoryId}">${category.categoryName}</option>
                </c:forEach>
            </select>
        </div>


        <button class="btn btn-primary save-btn ">
            Thêm sản phẩm
        </button>

    </form>
</div>
<%--<jsp:include page="../../common/footer.jsp"/>--%>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="${context}/js/admin/product/image-picker.js?rd=${rand}"></script>
<script src="${context}/js/admin/product/common.js?rd=${rand}"></script>
<script src="${context}/js/admin/product/addProduct.js?rd=${rand}"></script>

</body>
</html>
