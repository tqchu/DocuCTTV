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
    <!-- BOOSTRAP CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- FONT   -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <%-- APP--%>
    <link rel="stylesheet" href="${context}/css/base.css">
    <link rel="stylesheet" href="${context}/css/style.css">
    <div class = "pop-up__message">

    </div>
</head>
<body>
<jsp:include page="../common/header.jsp"/>
<div class="create-admin__form-box">
    <div class="form__heading-text">
        <c:if test="${action=='create'}">
            Thêm quản trị viên
        </c:if>
        <c:if test="${action=='update'}">
            Thay đổi thông tin quản trị viên
        </c:if>
    </div>
    <form action="${context}/admin/manage-admin" method="post" class="create-admin-form form" autocomplete="off">
        <input type="hidden" name="action" value="${action}">
        <div class="form-group form-floating">
            <input type="text" class="form-control" id="fullName" name="fullName" placeholder="Tên đăng nhập"
                   >
            <label for="fullName" class="form-label">Họ và tên</label>
        </div>
        <div class="form-group form-floating">
            <input type="text" class="form-control" id="username" name="username" placeholder="Tên đăng nhập">
            <label for="username" class="form-label">Tên đăng nhập</label>
        </div>
        <div class="form-group form-floating">
            <input type="password" class="form-control" id="password" name="password" placeholder="Mật khẩu">
            <label for="password" class="form-label">Mật khẩu</label>
        </div>
        <%-- SELECT ROLE--%>
        <select class="form-select  create-admin-form__select" name="role">
            <option value="admin" selected>Admin</option>
            <option value="super">Super admin</option>
        </select>
        <button type="submit" class="btn submit-btn">Thêm</button>
    </form>
</div>
<jsp:include page="../../common/footer.jsp"/>


</body>
</html>
