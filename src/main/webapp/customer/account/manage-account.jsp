<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%-- set context path--%>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>
    <link rel="stylesheet" href="${context}/css/base.css">

    <link rel="stylesheet" href="${context}/css/style.css">
    <!-- BOOSTRAP CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- RESET CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css"
          integrity="sha512-NmLkDIU1C/C88wi324HBc+S2kLhi08PN5GDeUVVVC/BVt/9Izdsc9SVeVfA1UZbY3sHUlDSyRXhCzHfr6hmPPw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>

    <!-- FONT   -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">

</head>
<body>
<jsp:include page="../common/header.jsp"/>
<jsp:include page="../common/search-header.jsp"/>
<div class="user__manage-account">
    <div class="container-fluid">
        <div class="row">
            <div class="col-2">
                <div class="user__manage-account__menu ">
                    <div class="user__manage-account__menu__heading">
                        Trương Quang Chứ
                    </div>
                    <div class="user__manage-account__menu-list">
                        <div class="user__manage-account__menu-item">
                            <i class="las la-user-alt"></i>
                            <div class="user__manage-account__menu-item__text">
                                Tài khoản của tôi
                            </div>
                            <div class="user__manage-account__profile-list">
                                <a href="" class="user__manage-account__profile-item">Hồ sơ</a>
                                <a href="" class="user__manage-account__profile-item">Đổi mật khẩu</a>
                                <a href="" class="user__manage-account__profile-item">Địa chỉ</a>
                            </div>
                        </div>
                        <div class="user__manage-account__menu-item">
                            <i class="las la-shopping-bag"></i>
                            <div class="user__manage-account__menu-item__text">Đơn mua</div>

                        </div>
                    </div>
                </div>
            </div>
            <div class="col-10">
                <div class="user__manage-account__content">
                    <div class="user__manage-account__content__header">
                        <div class="user__manage-account__content__text-heading">
                            Hồ Sơ Của Tôi
                        </div>
                        <div class="user__manage-account__content__text-description">
                            Quản lý thông tin hồ sơ để bảo mật tài khoản
                        </div>

                    </div>
                    <div class="user__manage-account__content__main">
                        <form action="" class="" method="post">
                            <div class="user__manage-account__profile-form-group">
                                <label for="fullName">Họ tên</label>
                                <input type="text" name="fullName" id="fullName">
                            </div>
                            <div class="user__manage-account__profile-form-group">
                                <label for="phoneNumber">Số điện thoại</label>
                                <input type="text" name="phoneNumber" id="phoneNumber">
                            </div>
                            <div class="user__manage-account__gender-group user__manage-account__profile-form-group"
                                 id="gender">
                                <label for="gender">Giới tính</label>
                                <div class="user__manage-account__gender-option" style="margin-left: 4px;">
                                    <label for="male">Nam</label>
                                    <input type="radio" name="gender" id="male" value="male">
                                </div>
                                <div class="user__manage-account__gender-option">
                                    <label for="male">Nữ</label>
                                    <input type="radio" name="gender" id="female" value="female">
                                </div>

                                <div class="user__manage-account__gender-option">
                                    <label for="undefined">Khác</label>
                                    <input type="radio" name="gender" id="undefined"
                                           value="undefined">
                                </div>

                            </div>
                            <div class="user__manage-account__profile-form-group">
                                <label for="birthday">Ngày sinh</label>
                                <input type="date" name="birthday" id="birthday">
                            </div>
                            <input type="submit" class="btn btn-md btn-primary user__manage-account__profile__submit-btn"
                                   value="Lưu"
                            />
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../../common/footer.jsp"/>


</body>
</html>
