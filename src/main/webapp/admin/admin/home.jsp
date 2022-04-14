<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Trang chủ</title>
    <link rel="stylesheet" href="${context}/css/base.css">

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
    <link rel="stylesheet" href="${context}/css/admin/home.css">
</head>
<body>
<jsp:include page="../common/header.jsp"/>
<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col col-2-4">
                <div class="slide-navbar">
                    <a href="${context}/admin/products" class="navbar__tab ${tab=='products'?'active':''}">
                        <span class="navbar__tab__icon"><i class="las la-wallet"></i></span>

                        <span class="navbar__tab__text-description">Sản phẩm</span>
                    </a>
                    <a href="${context}/admin/categories" class="navbar__tab ${tab=='categories'?'active':''}">
                        <span class="navbar__tab__icon"><i class="las la-wallet"></i></span>

                        <span class="navbar__tab__text-description">Doanh mục</span>
                    </a>
                    <a href="${context}/admin/orders" class="navbar__tab ${tab=='orders'?'active':''}">
                        <span class="navbar__tab__icon">
                            <i class="las la-shopping-bag"></i>
                        </span>
                        <span class="navbar__tab__text-description">Đơn hàng</span>
                    </a>
                    <a href="${context}/admin/statistics" class="navbar__tab ${tab=='statistics'?'active':''}">
                        <span class="navbar__tab__icon"><i class="las la-wallet"></i></span>
                        <span class="navbar__tab__text-description">Thống kê</span>
                    </a>
                </div>
            </div>
            <div class="col col-9-6">
                <c:if test="${tab=='products'}">
                    <div class="tab__content">
                        <div class="search">
                            <form action="" class="search__form">
                                <input type="search" class="search__bar form-control">
                                <button type="submit" class="btn btn-primary btn-search">
                                    <i class="las la-search"></i>
                                </button>
                            </form>
                            <a href="" class="search__add-btn btn btn-primary"
                               title="Thêm sản phẩm"><i
                                    class="las la-plus"></i></a>

                        </div>
                        <div class="list">
                            <table class=" table table-hover table-bordered">
                                <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Tên sản phẩm</th>
                                    <th>Doanh mục</th>
                                    <th>Kích thước</th>
                                    <th>Số lượng</th>
                                    <th>Giá</th>
                                    <th>Đánh giá</th>
                                    <th></th>
                                    <th></th>
                                </tr>
                                </thead>

                            </table>
                        </div>
                    </div>
                </c:if>
                <c:if test="${tab=='categories'}">
                    <div class="tab__content">
                        <div class="search">
                            <form action="" class="search__form">
                                <input type="search" class="search__bar form-control">
                                <button type="submit" class="btn btn-primary btn-search">
                                    <i class="las la-search"></i>
                                </button>

                            </form>
                            <a href="" class="search__add-btn btn btn-primary"
                               title="Thêm doanh mục"  data-bs-toggle="modal" data-bs-target="#addModal"><i
                                    class="las la-plus"></i></a>
                            <!-- MODAL CONTENT -->
                            <div class="modal fade" id="addModal" tabindex="-1" aria-labelledby="modalLabel"
                                 aria-hidden="true">
                                <div class="modal-dialog">

                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="modalLabel">
                                                Thêm doanh mục
                                            </h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                    aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <form action="${context}/admin/categories" method="post" >
                                                <input type="hidden" value="create" name="action">
                                                <div class="form-group form-floating">
                                                    <input type="text" name="categoryName" id="categoryName"
                                                           class="form-control" placeholder="Tên doanh mục">
                                                    <label for="categoryName" class="form-label">Tên doanh mục</label>
                                                </div>


                                                <button type="submit" class="btn btn-primary save-btn">Thêm</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="list">
                            <table class=" table table-hover table-bordered">
                                <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Tên doanh mục</th>
                                    <th></th>
                                    <th></th>
                                </tr>
                                </thead>

                            </table>
                        </div>
                    </div>
                </c:if>


            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="${context}/js/admin/home.js"></script>
<jsp:include page="../../common/footer.jsp"/>

</body>
</html>
