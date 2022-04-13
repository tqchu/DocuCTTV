<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Trang chủ</title>
    <link rel="shortcut icon" href="${context}/favicon.ico"/>

    <!-- RESET CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css"
          integrity="sha512-NmLkDIU1C/C88wi324HBc+S2kLhi08PN5GDeUVVVC/BVt/9Izdsc9SVeVfA1UZbY3sHUlDSyRXhCzHfr6hmPPw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <!-- BOOSTRAP CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <!-- FONT   -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <!-- ICON -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
          integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <%-- APP--%>
    <link rel="stylesheet" href="${context}/css/base.css">
    <link rel="stylesheet" href="${context}/css/style.css">
</head>
<body>
<jsp:include page="../common/header.jsp"/>
<div class="super-admin-search-header">
    <a href="${context}/admin/manage-admin?action=create" class="super-admin-search-header__add-btn">
        Thêm quản trị viên
    </a>
    <form action="" class="super-admin-search-header-form" method="get">
        <input type="search" name="keyword" id="keyword" class="super-admin-search-header__search-input"/>
        <span class="super-admin-search-header__selections">

        </span>
        <button class="super-admin-search-header__search-icon" type="submit">
            <i class="las la-search"></i>
        </button>
    </form>


</div>
<div class="super-admin-content">
    <table class="super-admin-content__table table table-hover table-bordered">
        <thead>
        <tr>
            <th>STT</th>
            <th>Họ và tên</th>
            <th>Tên đăng nhập</th>
            <th>Email</th>
            <th>Vai trò</th>

            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${not empty adminList}">
            <c:forEach items="${adminList}" var="currentAdmin" varStatus="loop">
                <tr>
                    <td>${loop.count}</td>
                    <td>${currentAdmin.fullName}</td>
                    <td>${currentAdmin.username}</td>
                    <td>${currentAdmin.email}</td>
                    <td>
                        <form action="${context}/admin/manage-admin" method="post">
                            <input
                                    type="hidden" name="action" value="update">
                            <input type="hidden" name="id" value="${currentAdmin.userId}">
                            <select class="form-select" name="role">
                                <option value="admin" ${currentAdmin.role=='admin'?'selected':''}>Nhân
                                    viên
                                </option>
                                <option value="super"
                                    ${currentAdmin.role=='super'?'selected':''}>Quản lý
                                </option>
                            </select>
                            <button type="submit" class="super-admin-content__table__save-btn">Lưu</button>
                        </form>

                    </td>


                    <td>
                        <a href="${context}/admin/manage-admin?action=delete&id=${currentAdmin.userId}"
                           class="super-admin-content__table__action super-admin-content__table__delete-action">Xóa</a>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>

    </table>
</div>
<jsp:include page="../../common/footer.jsp"/>
</body>
</html>
