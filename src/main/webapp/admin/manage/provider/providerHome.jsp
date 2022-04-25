<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="${context}/css/admin/provider/provider.css">
<div class="tab__content">
    <div class="search">
        <form action="" class="search__form">
            <input type="search" class="search__bar form-control">
            <button type="submit" class="btn btn-primary btn-search">
                <i class="las la-search"></i>
            </button>
        </form>
        <a href="${context}/admin/products?action=create" class="search__add-btn btn btn-primary"
           title="Thêm sản phẩm"><i
                class="las la-plus"></i></a>

    </div>
    <c:if test="${successMessage!=null}">
        <div class="toast align-items-center toast-message toast-message--success fade show" role="alert"
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
    <div class="list">
        <table class="data-table  table table-hover table-bordered">
            <thead>
            <tr>
                <th>STT</th>
                <th>Tên nhà cung cấp</th>
                <th>Địa chỉ</th>
                <th>SDT</th>
                <th>Email</th>
                <th>Mã số thuế</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${not empty list}">
                <c:forEach items="${list}" var="product" varStatus="loop">
                    <tr>
                        <td class=""></td>
                        <td class=""></td>
                        <td class=""></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
    </div>
</div>