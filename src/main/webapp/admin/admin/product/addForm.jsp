<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%-- set context path--%>
<c:set var="context" value="${pageContext.request.contextPath}"/>
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
    <link rel="stylesheet" href="${context}/css/base.css">
    <link rel="stylesheet" href="${context}/css/style.css">
</head>
<body>
<jsp:include page="../../common/header.jsp"/>
<form action="">
    <div class="form-group">
        <label for="productName">Tên sản phẩm</label>
        <input type="text" name="productName" id="productName">
    </div>
    <div class="form-group">
        <label for="images">Ảnh</label>
        <input type="file" name="images" id="images" multiple>
    </div>
    <div class="dimension-form-group">
        <span class="dimension-form-group__text-label">
            Kích thước
        </span>
        <div class="dimension-form-item">
            <label for="length">Dài</label>
            <input type="number" name="length" id="length">
        </div>
        <div class="dimension-form-item">
            <label for="width">Rộng</label>
            <input type="text" name="width" id="width">
        </div>
        <div class="dimension-form-item">
            <label for="height">Cao</label>
            <input type="text" name="height" id="height">
        </div>
    </div>
   <%-- <div class="form-group">
        <label for="productName"></label>
        <input type="text" name="productName" id="productName">
    </div>--%>

</form>
<%--<jsp:include page="../../common/footer.jsp"/>--%>


</body>
</html>
