<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%-- set context path--%>
<c:set var="context" value="${pageContext.request.contextPath}" scope="request"/>
<c:set var="rand"><%= java.lang.Math.round(java.lang.Math.random() * 10000) %>
</c:set>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Thêm đơn</title>
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
    <link rel="stylesheet" href="${context}/css/admin/inventory/addForm.css?rd=${rand}">

</head>
<body>
<jsp:include page="../../common/header.jsp"/>
<div class="content">
    <form action="${context}/admin/inventory" class="" method="post" id="addInventoryForm">
        <input type="hidden" name="action" value="create">
        <input type="hidden" name="importerName" value="${admin.fullName}">
        <div class="form-group">
            <span class="form-group__label">Nhà cung cấp</span>
            <select name="providerId">
                <c:forEach items="${providerList}" var="provider">
                    <option value="${provider.providerId}">${provider.providerName}</option>
                </c:forEach>
            </select>
        </div>
        <%--TỪNG SẢN PHẨM --%>
        <%-- BEGIN--%>

        <div class="product-row">
            <div class="form-group">
                <span class="form-group__label">Sản phẩm</span>
                <select name="productId">
                    <c:forEach items="${productList}" var="product">
                        <option value="${product.productId}">${product.name}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <span class="form-group__label">Số lượng</span>

                <input type="number" name="quantity">

            </div>
            <div class="form-group">
                <span class="form-group__label">Giá</span>

                <input type="number" name="price">
            </div>
            <div class="form-group">
                <span class="form-group__label">Thuế</span>
                <input type="number" name="tax"> %
            </div>
            <div class="form-group__minus-row-btn">
                <i class="las la-minus-circle"></i>
            </div>
        </div>
        <div class="form-group__add-row-btn">
            <i class="las la-plus-circle"></i>
        </div>
        <%-- END--%>
        <button class="btn btn-primary submit-btn">
            Thêm
        </button>
    </form>
</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="${context}/js/admin/inventory/addForm.js"></script>
<%--<jsp:include page="../../common/footer.jsp"/>--%>


</body>
</html>
