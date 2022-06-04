<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<c:set var="rand"><%= java.lang.Math.round(java.lang.Math.random() * 10000) %>
</c:set>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Cập nhật thông tin</title>
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
</head>
<body>
<jsp:include page="../common/header.jsp"/>
<div class="admin__update-box">
    <c:if test="${errorMessage!=null}">
        <div class="admin__update__error-message">${errorMessage}</div>
    </c:if> <c:if test="${successMessage!=null}">
    <div class="success-message">${successMessage}</div>
</c:if>
    <form action="${context}/admin/update" method="post" class="admin__update-form form">
        <div class="admin__update__form-group form-floating">
            <input type="text" class="form-control" id="fullName" name="fullName" placeholder="Tên"
                   value="${admin.fullName}">
            <label for="fullName" class="">Tên</label>
        </div>
        <div class="admin__update__form-group form-floating">
            <input type="text" class="form-control" id="username" name="username" placeholder="Tên đăng nhập"
                   value="${admin.username}">
            <label for="username" class="">Tên đăng nhập</label>
        </div>
        <div class="admin__update__form-group form-floating">
            <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" placeholder="Số điện thoại"
                   value="${admin.phoneNumber}">
            <label for="phoneNumber" class="">Số điện thoại</label>

            <div class="admin__update__form-group form-floating">
                <input type="text" class="form-control" id="email" name="email" placeholder="Email"
                       value="${admin.email}">
                <label for="username" class="">Email</label>
            </div>
        </div>
        <div class="admin__update__form-group form-floating">
            <input type="text" class="form-control" id="address" name="address" placeholder="Địa chỉ"
                   value="${admin.address}">
            <label for="username" class="">Địa chỉ</label>
        </div>
        <div class="admin__update__form-group form-floating">
            <input type="password" class="form-control" id="password" name="password" placeholder="Mật khẩu">
            <label for="password" class="">Mật khẩu</label>
        </div>
        <button type="submit" class="btn btn-lg btn-primary admin__update__btn">Lưu</button>
    </form>
</div>
<jsp:include page="../../common/footer.jsp"/>

</body>
</html>
