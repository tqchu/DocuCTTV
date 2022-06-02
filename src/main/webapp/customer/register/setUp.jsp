<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%-- set context path--%>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<c:set var="rand"><%= java.lang.Math.round(java.lang.Math.random() * 10000) %></c:set>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>
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
    <%-- APP --%>
    <link rel="stylesheet" href="${context}/css/base.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/style.css?rd=${rand}">
</head>
<body>
<c:set var="action" value="Hoàn tất đăng ký" scope="request" />
<jsp:include page="../common/search-header.jsp"/>

<div class="register-box">
    <div class="form__heading-text">Thiết lập tài khoản</div>

    <form action="${context}/register" method="post" class="register-form form" autocomplete="off">
        <input type="hidden" name="phase" value="set-up">
        <div class="form-group form-floating">
            <input type="text" class="form-control" id="fullName" name="fullName"
                   placeholder="Họ tên">
            <label for="fullName" class="form-label">Họ tên</label>
        </div>
        <div class="form-group form-floating">
            <input type="password" class="form-control" id="password" name="password" placeholder="Mật khẩu">
            <label for="password" class="form-label">Mật khẩu</label>
        </div>
        <div class="form-group form-floating">
            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword"
                   placeholder="Xác nhận mật khẩu">
            <label for="confirmPassword" class="form-label">Xác nhận mật khẩu</label>
        </div>

        <div class="form-group form-floating">
            <input type="date" class="form-control" id="dateOfBirth" name="dateOfBirth"
                   placeholder="Ngày sinh">
            <label for="dateOfBirth" class="form-label">Ngày sinh</label>
        </div>
        <div class="form-group register__gender">
            Giới tính:
            <br>
            <span class="register__gender-option">
                <input type="radio" id="gender-male" name="gender" value="male"
                       placeholder="Giới tính">
                <label for="gender-male" class="form-label">Nam</label>
            </span>
            <span class="register__gender-option">
                <input type="radio" id="gender-female" name="gender" value="female"
                       placeholder="Giới tính">
                <label for="gender-female" class="form-label">Nữ</label>

            </span>

            <span class="register__gender-option">
                <input type="radio" id="gender-undefined" name="gender" value="undefined"
                       placeholder="Giới tính">
                <label for="gender-undefined" class="form-label">Khác</label>
            </span>
        </div>
        <div class="form-group form-floating">
            <input type="text" class="form-control" id="address" name="address"
                   placeholder="Địa chỉ">
            <label for="address" class="form-label">Địa chỉ</label>
        </div>
        <div class="form-group register__accept-policy">
            <input type="checkbox" id="accept-policy" name="accept-policy" value="accept">
            <label for="accept-policy" class="accept-policy">Tôi đồng ý với các <a href="" style="color: #f03a0a">điều
                khoản sử
                dụng</a> của
                CTVV.</label>
        </div>

        <button type="submit" class="btn submit-btn">Đăng ký</button>
    </form>
</div>
<jsp:include page="../../common/footer.jsp"/>


</body>
</html>
