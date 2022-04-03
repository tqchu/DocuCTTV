<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%-- set context path--%>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý tài khoản</title>
    <link rel="shortcut icon" href="${context}/favicon.ico"/>

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
                        ${customer.fullName}
                    </div>
                    <div class="user__manage-account__menu-list">
                        <div class="user__manage-account__menu-item ">
                            <i class="las la-user-alt"></i>
                            <a href="" class="user__manage-account__menu-item__text user__manage-account__menu-tab">
                                Tài khoản của tôi
                            </a>
                            <div class="user__manage-account__profile-list">
                                <a href="${context}/user/account?tab=profile"
                                   class="user__manage-account__profile-item user__manage-account__menu-tab
                                   ${tab=="profile"?'active':''}">Hồ
                                    sơ</a>
                                <a href="${context}/user/account?tab=password"
                                   class="user__manage-account__profile-item user__manage-account__menu-tab
                                   ${tab=="password"?'active':''}">Đổi mật khẩu</a>
                                <a href="${context}/user/account?tab=address"
                                   class="user__manage-account__profile-item user__manage-account__menu-tab
                                   ${tab=="address"?'active':''}">Địa
                                    chỉ</a>
                            </div>
                        </div>
                        <div class="user__manage-account__menu-item ">
                            <i class="las la-shopping-bag"></i>
                            <a href="${context}/user/account?tab=purchase"
                               class="user__manage-account__menu-item__text user__manage-account__menu-tab
                               ${tab=="purchase"?'active':''}">Đơn mua</a>

                        </div>
                    </div>
                </div>
            </div>
            <div class="col-10">
                <c:choose>
                    <%-- PROFILE--%>
                <c:when test="${tab=='profile'}">
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
                        <form action="${context}/user/account" class="" method="post">
                            <input type="hidden" name="action" value="updateProfile">
                            <div class="user__manage-account__profile-form-group">
                                <label for="fullName">Họ tên</label>
                                <input type="text" name="fullName" id="fullName" value="${customer.fullName}">
                            </div>
                            <div class="user__manage-account__profile-form-group">
                                <label for="phoneNumber">Số điện thoại</label>
                                <input type="text" name="phoneNumber" id="phoneNumber" value="${customer.phoneNumber}"
                                       disabled>
                                <a href="" class="user__manage-account__change-recipient-phone">Đổi SDT</a>
                            </div>
                            <div class="user__manage-account__gender-group user__manage-account__profile-form-group"
                                 id="gender">
                                <label for="gender">Giới tính</label>
                                <div class="user__manage-account__gender-option" style="margin-left: 4px;">
                                    <label for="male">Nam</label>
                                    <input type="radio" name="gender" id="male" value="male" ${customer.gender eq
                                            'MALE'? 'checked':''}>
                                </div>
                                <div class="user__manage-account__gender-option">
                                    <label for="female">Nữ</label>
                                    <input type="radio" name="gender" id="female" value="female"
                                        ${customer.gender eq
                                                'FEMALE'? 'checked':''} >
                                </div>

                                <div class="user__manage-account__gender-option">
                                    <label for="undefined">Khác</label>
                                    <input type="radio" name="gender" id="undefined"
                                           value="undefined" ${customer.gender eq
                                            'OTHER'? 'checked':''}>
                                </div>

                            </div>
                            <div class="user__manage-account__profile-form-group">
                                <label for="dateOfBirth">Ngày sinh</label>
                                <input type="date" name="dateOfBirth" id="dateOfBirth" value="${customer.dateOfBirth}">
                            </div>

                            <input type="submit"
                                   class="btn btn-md btn-primary user__manage-account__profile__submit-btn"
                                   value="Lưu"
                            />
                        </form>
                    </div>

                    </c:when>

                        <%-- CHANGE PASSWORD--%>
                    <c:when test="${tab=='password'}">

                        <div class="user__manage-account__content">
                            <div class="user__manage-account__content__header">
                                <div class="user__manage-account__content__text-heading">
                                    Đổi Mật Khẩu
                                </div>
                                <div class="user__manage-account__content__text-description">
                                    Để bảo mật tài khoản, vui lòng không chia sẻ mật khẩu cho người khác
                                </div>

                            </div>
                            <div class="user__manage-account__content__main">
                                <form action="" class="" method="post">
                                    <input type="hidden" name="action" value="changePassword">

                                    <div class="user__manage-account__profile-form-group">
                                        <label for="oldPassword">Mật Khẩu Hiện Tại</label>
                                        <input type="text" name="oldPassword" id="oldPassword">
                                        <a href="" class="user__manage-account__forgot-password">Quên mật khẩu?</a>
                                    </div>
                                    <div class="user__manage-account__profile-form-group">
                                        <label for="password">Mật Khẩu Mới</label>
                                        <input type="text" name="password" id="password">
                                    </div>

                                    <div class="user__manage-account__profile-form-group">
                                        <label for="confirmedPassword">Xác Nhận Mật Khẩu</label>
                                        <input type="text" name="confirmedPassword" id="confirmedPassword">
                                    </div>
                                    <input type="submit"
                                           class="btn btn-md btn-primary user__manage-account__profile__submit-btn"
                                           value="Xác nhận"
                                    />
                                </form>
                            </div>
                        </div>
                    </c:when>
                        <%--ADDRESS--%>
                    <c:when test="${tab=='address'}">
                        <div class="user__manage-account__content">
                            <div class="user__manage-account__content__header">
                                <div class="user__manage-account__content__text-heading">
                                    Địa chỉ
                                </div>
                                <div class="user__manage-account__content__text-description">
                                    Cập nhật thông tin nhận hàng ở đây
                                </div>

                            </div>


                            <div class="user__manage-account__content__main">
                                <c:if test="${empty customer.address.address}">
                                    <div class="user__manage-account__add-address">
                                        Bạn chưa có địa chỉ nhận hàng, vui lòng thêm địa chỉ nhận hàng.
                                    </div>
                                </c:if>
                                <form action="" class="" method="post">
                                    <input type="hidden" name="action" value="updateAddress">

                                    <div class="user__manage-account__profile-form-group">
                                        <label for="recipientName">Tên người nhận</label>
                                        <input type="text" name="recipientName" id="recipientName"
                                               value="${customer.address.recipientName}">
                                    </div>
                                    <div class="user__manage-account__profile-form-group">
                                        <label for="recipientPhoneNumber">Số điện thoại</label>
                                        <input type="text" name="recipientPhoneNumber" id="recipientPhoneNumber"
                                               value="${customer.address.phoneNumber}" disabled>
                                        <a href="" class="user__manage-account__change-recipient-phone">Đổi
                                            SDT</a>
                                    </div>

                                    <div class="user__manage-account__profile-form-group">
                                        <label for="address">Địa chỉ</label>
                                        <input type="text" name="address" id="address"
                                               value="${customer.address.address}">
                                    </div>
                                    <input type="submit"
                                           class="btn btn-md btn-primary user__manage-account__profile__submit-btn"
                                           value="Xác nhận"
                                    />
                                </form>
                            </div>


                        </div>
                    </c:when>
                    <c:when test="${tab=='purchase'}">
                        <div class="user__manage-account__purchase-content">
                            <div class="user__manage-account__purchase-bar">
                                <a href="" class="user__manage-account__purchase-item">
                                    Chờ xác nhận
                                </a>
                                <a href="" class="user__manage-account__purchase-item">
                                    Đang giao
                                </a>
                                <a href="" class="user__manage-account__purchase-item">
                                    Đã giao
                                </a>
                                <a href="" class="user__manage-account__purchase-item">
                                    Đã hủy
                                </a>
                            </div>
                            <div class="user__manage-account__purchase-list">

                            </div>
                        </div>
                    </c:when>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="../../common/footer.jsp"/>


</body>
</html>
