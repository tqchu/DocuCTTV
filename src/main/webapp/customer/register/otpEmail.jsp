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
</head>
<body>
<c:set var="action" value="Hoàn tất đăng ký" scope="request"/>
<jsp:include page="../common/search-header.jsp"/>
<div class="register-box">

    <div class="form__heading-text">Vui lòng nhập mã xác minh</div>


    <c:if test="${not empty errorMessage}">
        <div class="error-message">
                ${errorMessage}
        </div>
        <c:remove var="errorMessage" scope="session"/>
    </c:if>
    <form action="${context}/register" method="post" class="register-form form" autocomplete="off">
        <input type="hidden" name="phase" value="otp-email">
        <div class="form-group form-floating w-100">
            <input type="text" class="form-control " id="otp" name="otp" placeholder="Mã xác minh">
            <label for="otp" class="form-label">Mã xác minh</label>
        </div>
        <button id="resend-btn" class="btn btn-primary">
            Gửi lại
        </button>

        <button type="submit" class="btn submit-btn w-100">Tiếp tục</button>
    </form>


</div>

</body>
</html>
