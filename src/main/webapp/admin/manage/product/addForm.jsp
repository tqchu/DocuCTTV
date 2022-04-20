<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <!-- APP -->
    <link rel="stylesheet" href="${context}/css/base.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/style.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/admin/addForm.css?rd=${rand}">
</head>
<body>
<jsp:include page="../../common/header.jsp"/>
<form action="">
    <div class="form-group">
        <span class="form-group__label">Tên sản phẩm</span>

        <input type="text" name="productName" id="productName">
    </div>
    <div class="form-group">
        <span class="form-group__label">Ảnh</span>

        <input type="file" name="images" id="images" multiple>
    </div>

    <div class="form-group">
        <span class="form-group__label">Mô tả sản phẩm</span>

        <textarea name="description" id="" cols="60" rows="8"></textarea>
    </div>
    <div class="form-group">
        <span class="form-group__label">Kích thước</span>
        <div class="dimension-form-group">
            <div class="dimension-form-group__row">
                <div class="dimension-form-item">
                    <span class="dimension-form-item__text-label">Dài</span>
                    <input type="number" name="length">
                </div>
                <div class="dimension-form-item">
                    <span class="dimension-form-item__text-label">Rộng</span>

                    <input type="text" name="width">
                </div>
                <div class="dimension-form-item">
                    <span class="dimension-form-item__text-label">Cao</span>

                    <input type="text" name="height">
                </div>
            </div>

            <div class="dimension-form-group__add-row-btn">
                <i class="las la-plus-circle"></i>
            </div>
        </div>
    </div>
    <div class="form-group">
        <span class="form-group__label">Chất liệu</span>
        <div class="material-form-group">

            <div class="material-form-group__row">
                <input type="text" name="length" id="material">
            </div>

            <div class="material-form-group__add-row-btn">
                <i class="las la-plus-circle"></i>
            </div>
        </div>
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
            <c:forEach items="${categoryList}" var="category">
                <option value="${category.categoryId}">${category.categoryName}</option>
            </c:forEach>
        </select>
    </div>
    <div class="form-group">
        <span class="form-group__label">Đơn giá</span>
        <input type="text" name="price">
    </div>
    <div class="form-group">
        <span class="form-group__label">Số lượng</span>

        <input type="text" name="quantity">
    </div>
    <button class="btn btn-primary save-btn">
        Thêm sản phẩm
    </button>

</form>
<%--<jsp:include page="../../common/footer.jsp"/>--%>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="${context}/js/admin/product/addProduct.js?rd=${rand}"></script>

</body>
</html>
