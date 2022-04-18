<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<c:set var="rand"><%= java.lang.Math.round(java.lang.Math.random() * 10000) %>
</c:set>
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
    <%--    JS--%>

    <%-- DATA TABLE--%>
    <link rel="stylesheet" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
</head>
<body>
<jsp:include page="../common/header.jsp"/>
<div class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col col-2-4">
                <div class="slide-navbar">
                    <%--<div class="collapse-button">
                        <i class="las la-angle-double-left"></i>
                    </div>--%>
                    <%--<div class="expand-button">
                        <i class="las la-angle-double-right"></i>
                    </div>--%>
                    <div class="navbar__tab-list">
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
                            <table class="data-table  table table-hover table-bordered">
                                <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Tên sản phẩm</th>
                                    <th>Ảnh sản phẩm</th>
                                    <th>Kích thước</th>
                                    <th>Vật liệu</th>
                                    <th>Giá</th>
                                    <th>Số lượng</th>
                                    <th>Doanh mục</th>
                                    <th>Đánh giá</th>
                                    <th></th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:if test="${not empty list}">
                                    <c:forEach items="${list}" var="product" varStatus="loop">
                                        <td>${loop.count}</td>
                                        <td class="product__name">${product.name}</td>
                                        <td class="product__image">
                                            <div id="product-image-slide" class="carousel slide"
                                                 data-bs-ride="carousel">
                                                <div class="carousel-inner">
                                                    <c:forEach items="${product.imagePathList}" var="path"
                                                               varStatus="loop">
                                                        <div class="carousel-item ${loop.count==1?'active':''}">
                                                            <img src="${context}/${path.path}"
                                                                 class="d-block w-100 product__main-image">
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                                <button class="carousel-control-prev" type="button"
                                                        data-bs-target="#product-image-slide"
                                                        data-bs-slide="prev">
                                                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                                    <span class="visually-hidden">Previous</span>
                                                </button>
                                                <button class="carousel-control-next" type="button"
                                                        data-bs-target="#product-image-slide"
                                                        data-bs-slide="next">
                                                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                                    <span class="visually-hidden">Next</span>
                                                </button>
                                            </div>

                                        </td>
                                        <td class="product__dimension">
                                            <ul>
                                            <c:forEach items="${product.dimensionList}" var="dimension">
                                                <li class="product__dimension-item">
                                                        ${dimension.length}D x ${dimension.width}R x
                                                        ${dimension.height}C (cm)
                                                </li>
                                            </c:forEach>

                                            </ul>
                                        </td>
                                        <td class="product__material">
                                            <ul>
                                            <c:forEach items="${product.materialList}" var="material">
                                                <li class="product__material-item">
                                                        ${material.materialName}
                                                </li>
                                            </c:forEach>

                                            </ul>
                                        </td>
                                        <td class="product__price">${product.price}đ</td>
                                        <td class="product__quantity">${product.quantity}</td>
                                        <td class="product__category">${product.category.categoryName}</td>
                                        <td class="product__rating">-</td>
                                        <td>
                                            <a href="${context}/admin/products?action=update"
                                               class="btn-edit btn btn-primary">
                                                Chỉnh sửa
                                            </a>

                                        </td>
                                        <td>
                                            <a href="" class="btn btn-delete">
                                                Xóa
                                            </a>
                                        </td>
                                    </c:forEach>
                                </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:if>
                <c:if test="${tab=='categories'}">
                    <script src="${context}/js/admin/category/category.js"></script>
                    <div class="tab__content">
                        <div class="search">
                            <form action="" class="search__form">
                                <input type="search" class="search__bar form-control">
                                <button type="submit" class="btn btn-primary btn-search">
                                    <i class="las la-search"></i>
                                </button>

                            </form>
                            <a href="" class="search__add-btn btn btn-primary"
                               title="Thêm doanh mục" data-bs-toggle="modal" data-bs-target="#addModal"><i
                                    class="las la-plus"></i></a>
                            <!-- MODAL CONTENT -->
                            <div class="modal fade" id="addModal" tabindex="-1" aria-labelledby="addModalLabel"
                                 aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered">

                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="addModalLabel">
                                                Thêm doanh mục
                                            </h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                    aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <form action="${context}/admin/categories" method="post"
                                                  class="d-flex align-items-center flex-column">
                                                <input type="hidden" value="create" name="action">
                                                <div class="form-group form-floating">
                                                    <input type="text" name="categoryName"
                                                           class="form-control" placeholder="Tên doanh mục" autofocus>
                                                    <label class="form-label">Tên doanh mục</label>
                                                </div>


                                                <button type="submit" class="btn btn-primary save-btn">Thêm</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <c:if test="${successMessage!=null}">
                            <div class="toast align-items-center toast-message toast-message--success" role="alert"
                                 aria-live="assertive"
                                 aria-atomic="true" data-bs-autohide="false">
                                <div class="d-flex ">
                                    <div class="toast-body">
                                            ${successMessage}
                                    </div>
                                    <button type="button" class="btn-close me-2 m-auto" data-bs-dismiss="toast"
                                            aria-label="Close"></button>
                                </div>
                            </div>
                            <c:remove var="successMessage" scope="session"/>
                        </c:if>
                        <c:if test="${errorMessage!=null}">
                            <div class="toast align-items-center toast-message toast-message--error" role="alert"
                                 aria-live="assertive"
                                 aria-atomic="true" data-bs-autohide="false">
                                <div class="d-flex ">
                                    <div class="toast-body">
                                            ${errorMessage}
                                    </div>
                                    <button type="button" class="btn-close me-2 m-auto" data-bs-dismiss="toast"
                                            aria-label="Close"></button>
                                </div>
                            </div>
                            <c:remove var="errorMessage" scope="session"/>

                        </c:if>
                        <div class="list">
                            <table class="data-table   table table-hover table-bordered ">
                                <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Tên doanh mục</th>
                                    <th class="column__action"></th>
                                    <th class="column__action"></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:if test="${not empty list}">
                                    <c:forEach items="${list}" var="currentRow" varStatus="loop">
                                        <tr>
                                            <td>${loop.count}</td>
                                            <td>${currentRow.categoryName}</td>
                                            <td><a href="" class="btn-edit btn btn btn-primary"
                                                   data-bs-toggle="modal"
                                                   data-bs-target="#editModal">Chỉnh sửa</a>
                                                <!-- MODAL CONTENT -->
                                                <div class="modal fade" id="editModal" tabindex="-1"
                                                     aria-labelledby="editModalLabel"
                                                     aria-hidden="true">
                                                    <div class="modal-dialog modal-dialog-centered">

                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h5 class="modal-title" id="editModalLabel">
                                                                    Sửa doanh mục
                                                                </h5>
                                                                <button type="button" class="btn-close"
                                                                        data-bs-dismiss="modal"
                                                                        aria-label="Close"></button>
                                                            </div>
                                                            <div class="modal-body">
                                                                <form action="${context}/admin/categories" method="post"
                                                                      class="d-flex align-items-center flex-column">
                                                                    <input type="hidden" value="update" name="action">
                                                                    <input type="hidden" name="categoryId"
                                                                           value="${currentRow.categoryId}">
                                                                    <div class="form-group form-floating">
                                                                        <input type="text" name="categoryName"
                                                                               class="form-control"
                                                                               placeholder="Tên doanh mục"
                                                                               value="${currentRow.categoryName}"
                                                                               autofocus>
                                                                        <label class="form-label">Tên
                                                                            doanh mục</label>
                                                                    </div>
                                                                    <button type="submit"
                                                                            class="btn btn-primary save-btn">
                                                                        Lưu
                                                                    </button>
                                                                </form>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>


                                            <td class="delete-category-btn">

                                                    <button class="btn btn-delete" title="Xoá doanh mục" data-bs-toggle="modal"
                                                            data-bs-target="#deleteCategoryModal${currentRow.categoryId}">
                                                        Xoá
                                                    </button>
                                                    <!-- MODAL CONTENT -->
                                                    <div class="modal fade"
                                                         id="deleteCategoryModal${currentRow.categoryId}"
                                                         tabindex="-1"
                                                         aria-labelledby="deleteCategoryModal${currentRow.categoryId}Label"
                                                         aria-hidden="true">
                                                        <div class="modal-dialog modal-dialog-centered">

                                                            <div class="modal-content">
                                                                <div class="modal-header">
                                                                    <h5 class="modal-title"
                                                                        id="deleteCategoryModal${currentRow.categoryId}Label">
                                                                       Xóa doanh mục
                                                                    </h5>
                                                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                                            aria-label="Close"></button>
                                                                </div>
                                                                <div class="modal-body">
                                                                    <form action="${context}/admin/categories"
                                                                          class="delete-form" method="post">
                                                                        Bạn có chắc chắn xóa doanh mục
                                                                            ${currentRow.categoryName}?
                                                                        <input type="hidden" name="action" value="delete">
                                                                        <input type="hidden" name="categoryId"
                                                                               value="${currentRow.categoryId}">
                                                                        <button
                                                                                class="confirm-delete-btn btn">
                                                                            Xác nhận
                                                                        </button>
                                                                    </form>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>

<script src="${context}/js/admin/home.js?rd=${rand}"></script>
<jsp:include page="../../common/footer.jsp"/>

</body>
</html>
