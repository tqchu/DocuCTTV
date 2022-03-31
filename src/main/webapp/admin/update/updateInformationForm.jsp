<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Title</title>
    <link rel="stylesheet" href="${context}/css/base.css">
    <link rel="stylesheet" href="${context}/css/style.css">
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
</head>
<body>
<jsp:include page="../common/header.jsp"/>
<div class="admin__update-box">
    <c:if test="${errorMessage!=null}">
        <div class="admin__update__error-message">${errorMessage}</div>
    </c:if> <c:if test="${successMessage!=null}">
    <div class="admin__update__success-message">${successMessage}</div>
</c:if>

    <form action="${context}/admin/update" method="post" class="admin__update-form">

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
            <input type="password" class="form-control" id="password" name="password" placeholder="Mật khẩu"
                   value="${admin.password}">
            <label for="password" class="">Mật khẩu</label>
        </div>
        <button type="submit" class="btn btn-lg btn-primary admin__update__btn">Lưu</button>
    </form>
</div>
<jsp:include page="../../common/footer.jsp"/>

</body>
</html>
