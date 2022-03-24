<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- set context path--%>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>
    <link rel="stylesheet" href="${context}/css/style.css">
</head>
<body>
<%--<jsp:include page="${context}/common/header.jsp"/>--%>
<div class="heading-text">Đăng nhập</div>
<form action="${context}/admin" method="post">
    <div class="form-group">
        <label for="username" class="form-label">Tên đăng nhập</label>
        <input type="text" class="form-control" id="username" name="username" placeholder="Tên đăng nhập">
    </div>
    <div class="form-group">
        <label for="password" class="form-label">Mật khẩu</label>
        <input type="text" class="form-control" id="password" name="password" placeholder="Mật khẩu">
    </div>
    <button type="submit" class="btn">Đăng nhập</button>
</form>
</body>
</html>
