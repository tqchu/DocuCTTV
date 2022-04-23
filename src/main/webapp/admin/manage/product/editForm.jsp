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
    <%--FONT AWESOME--%>
    <!-- FONT    -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <!-- APP -->
    <link rel="stylesheet" href="${context}/css/base.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/style.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/admin/editForm.css?rd=${rand}">


    <!-- BOOSTRAP CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
          integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>

    <%--    SCRIPT--%>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="${context}/js/admin/product/image-picker.js?rd=${rand}"></script>
    <script src="${context}/js/admin/product/editForm.js?rd=${rand}"></script>

</head>
<body>
<jsp:include page="../../common/header.jsp"/>
<div class="content">

    <form action="${context}/admin/products" enctype="multipart/form-data" method="post">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="productId" value="${product.productId}"/>
        <div class="form-group">
            <span class="form-group__label">Tên sản phẩm</span>

            <input type="text" name="productName" id="productName" value="${product.name}">

        </div>
        <div class="form-group">
            <span class="form-group__label">Ảnh</span>

            <div class="input-images-wrapper">
                <div id="coba">
                    <!-- <input type="file" name="images" id="images" multiple>
                     <div class="input-images">-->

                    <!--<img src="500-71353-nha-xinh-hang-trang-tri-chim-gom4.jpg" alt="" class="input-images-item">
                    <img src="500-71353-nha-xinh-hang-trang-tri-chim-gom81.jpg" alt="" class="input-images-item">
                    <img src="500-71389-nha-xinh-hang-trang-tri-chen-gom.jpg" alt="" class="input-images-item">
                    <img src="500-71390-nha-xinh-hang-trang-tri-to-gom21.jpg" alt="" class="input-images-item">
                    <img src="500-71353-nha-xinh-hang-trang-tri-chim-gom4.jpg" alt="" class="input-images-item">-->
                    <!--                    </div>-->
                </div>
            </div>
        </div>

        <div class="form-group">
            <span class="form-group__label">Mô tả sản phẩm</span>

            <textarea name="description" id="" cols="60" rows="8">${product.description}</textarea>
        </div>
        <div class="form-group">
            <span class="form-group__label">Kích thước</span>
            <div class="dimension-form-group multiple-row__form-group">
                <c:forEach items="${product.dimensionList}" var="dimension">
                    <div class="dimension-form-group__row">
                        <div class="dimension-form-item">
                            <span class="dimension-form-item__text-label">Dài</span>
                            <input type="number" name="length" value="${dimension.length}">
                        </div>
                        <div class="dimension-form-item">
                            <span class="dimension-form-item__text-label">Rộng</span>

                            <input type="text" name="width" value="${dimension.width}">
                        </div>
                        <div class="dimension-form-item">
                            <span class="dimension-form-item__text-label">Cao</span>

                            <input type="text" name="height" value="${dimension.height}">
                        </div>
                        <span class="form-group__minus-row-btn">
                   <i class="fa-solid fa-minus"></i>
                </span>
                    </div>
                </c:forEach>

                <div class="dimension-form-group__add-row-btn">
                    <i class="fa-solid fa-plus"></i>
                </div>
            </div>
        </div>
        <div class="form-group">
            <span class="form-group__label">Chất liệu</span>
            <div class="material-form-group multiple-row__form-group">
                <c:forEach items="${product.materialList}" var="material">
                    <div class="material-form-group__row">
                        <input type="text" name="material" id="material" value="${material.materialName}">
                        <span class="form-group__minus-row-btn">
                   <i class="fa-solid fa-minus"></i>
                </span>
                    </div>
                </c:forEach>

                <div class="material-form-group__add-row-btn">
                    <i class="fa-solid fa-plus"></i>
                </div>

            </div>
        </div>
        <div class="form-group">
            <span class="form-group__label">Thời gian bảo hành</span>
            <input type="number" name="warrantyPeriod" value="${product.warrantyPeriod}">
            <span class="warranty-period-unit">tháng</span>
        </div>
        <div class="form-group">
            <span class="form-group__label">Danh mục</span>
            <select class="form-select category-select" name="categoryId">
                <option value="" ${product.category==null?'selected':''}>Chưa phân loại</option>
                <c:forEach items="${categoryList}" var="category">
                    <option value="${category.categoryId}"
                        ${category.categoryId==product.category.categoryId?'selected':''}>${category.categoryName}</option>
                </c:forEach>

            </select>
        </div>
        <div class="form-group">
            <span class="form-group__label">Đơn giá</span>
            <input type="text" name="price" value="${product.price}">
        </div>
        <button class="btn btn-primary save-btn">
            Lưu sản phẩm
        </button>

    </form>
</div>
<%--<jsp:include page="../../common/footer.jsp"/>--%>
</body>
</html>
