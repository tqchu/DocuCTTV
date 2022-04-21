<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                    <tr>
                        <td>${loop.count}</td>
                        <td class="product__name">${product.name}</td>
                        <td class="product__image">
                            <div id="product-image-slide${product.productId}" class="carousel slide"
                                 data-bs-ride="carousel">
                                <div class="carousel-inner">
                                    <c:forEach items="${product.imagePathList}" var="path"
                                               varStatus="loop">
                                        <div class="carousel-item ${loop.count==1?'active':''}">
                                            <img src="${context}/${path.path}"
                                                 class="d-block product__main-image">
                                        </div>
                                    </c:forEach>
                                </div>
                                <button class="carousel-control-prev" type="button"
                                        data-bs-target="#product-image-slide${product.productId}"
                                        data-bs-slide="prev">
                                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                    <span class="visually-hidden">Previous</span>
                                </button>
                                <button class="carousel-control-next" type="button"
                                        data-bs-target="#product-image-slide${product.productId}"
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
                        <td class="product__price">
                            <fmt:formatNumber value="${product.price}"
                                              currencySymbol="đ" type="CURRENCY" maxFractionDigits="0"/>
                        </td>
                        <td class="product__quantity">${product.quantity}</td>
                        <td class="product__category">
                            <c:choose>
                                <c:when test="${product.category==null}">
                                <span class="product__no-category">
                                    Chưa phân loại
                                </span>
                                </c:when>
                                <c:otherwise>
                                    ${product.category.categoryName}
                                </c:otherwise>
                            </c:choose>

                        </td>
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
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
    </div>
</div>