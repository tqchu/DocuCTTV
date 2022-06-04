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
<jsp:include page="../../common/header.jsp"/>
<div class="create-admin__form-box">
    <div class="form__heading-text">
        Thêm quản trị viên
    </div>
    <c:if test="${successMessage!=null}">
        <div class="success-message">
                ${successMessage}
        </div>
    </c:if>
    <c:if test="${usernameErrorMessage!=null}">
        <div class="error-message">
                ${usernameErrorMessage}
        </div>
    </c:if><c:if test="${emailErrorMessage!=null}">
    <div class="error-message">
            ${emailErrorMessage}
    </div>
</c:if><c:if test="${phoneNumberErrorMessage!=null}">
    <div class="error-message">
            ${phoneNumberErrorMessage}
    </div>
</c:if>
    <form action="${context}/admin/admins" method="post" class="create-admin-form form" autocomplete="off">
        <input
            type="hidden" name="action" value="create">
        <div class="form-group form-floating">
            <input type="text" class="form-control" id="username" name="username" placeholder="Tên đăng nhập"
            >
            <label for="username" class="form-label">Tên đăng nhập</label>
        </div>
        <div class="form-group form-floating">
            <input type="text" class="form-control" id="fullName" name="fullName" placeholder="Họ và tên"
            >
            <label for="fullName" class="form-label">Họ và tên</label>
        </div>
        <div class="form-group form-floating">
            <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" placeholder="Số điện thoại"
            >
            <label for="phoneNumber" class="form-label">Số điện thoại</label>
        </div>

        <div class="form-group form-floating">
            <input type="text" class="form-control" id="email" name="email" placeholder="Email">
            <label for="email" class="form-label">Email</label>
        </div>
        <div class="form-group form-floating">
            <input type="text" class="form-control" id="address" name="address" placeholder="Địa chỉ">
            <label for="address" class="form-label">Địa chỉ</label>
        </div>
        <%-- SELECT ROLE --%>
        <select class="form-select  create-admin-form__select" name="role">
            <option value="admin">Nhân viên</option>
            <option value="super">Quản lý</option>
        </select>
        <button type="submit" class="btn submit-btn">Thêm</button>
    </form>
</div>
<%--<jsp:include page="../../common/footer.jsp"/>--%>


</body>
</html>
