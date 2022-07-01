<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="search">
    <form action="${context}/admin/customers/search" class="search__form">
        <input type="search" class="search__bar form-control" name="keyword"
               placeholder="Tìm kiếm"
               value="${param.keyword}">
        <button type="submit" class="btn btn-primary btn-search">
            <i class="las la-search"></i>
        </button>

    </form>
</div>

<c:if test="${not empty successMessage}">
    <div class="toast align-items-center toast-message toast-message--success show" role="alert"
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
    <table class="table table-hover table-bordered ">
        <thead>
        <tr>
            <th>STT</th>
            <th class="column__full-name">Họ và tên</th>
            <th class="column__phone-number">Số điện thoại</th>
            <th class="column__email">Email</th>

            <th class="column__status">Trạng thái</th>

            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${empty customerList}">
            <tr>
                <td colspan="8" class="no-records">
                    Không có dữ liệu
                </td>
            </tr>

        </c:if>
        <c:if test="${not empty customerList}">
            <c:forEach items="${customerList}" var="currentRow" varStatus="loop">
                <tr>
                    <td>${loop.count + (not empty param.page?param.page-1:0) * 10}</td>
                    <td>${currentRow.fullName}</td>
                    <td>${currentRow.phoneNumber}</td>
                    <td>${currentRow.email}</td>

                    <td>${currentRow.active?'Bình thường':'Bị khóa'}</td>
                    <td>
                        <button class="btn btn-delete activate-btn" title="${currentRow.active?'Khóa':'Bỏ khóa'}"
                                data-bs-toggle="modal"
                                data-bs-target="#activateCustomerModal${currentRow.userId}">
                                ${currentRow.active?'Khóa':'Bỏ khóa'}
                        </button>
                    </td>
                    <!-- MODAL CONTENT -->
                    <div class="modal fade"
                         id="activateCustomerModal${currentRow.userId}"
                         tabindex="-1"
                         aria-labelledby="activateCustomerModal${currentRow.userId}Label"
                         aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered">

                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title"
                                        id="activateCustomerModal${currentRow.userId}Label">
                                            ${currentRow.active?'Khóa':'Bỏ khóa'} khách hàng
                                    </h5>
                                    <button type="button" class="btn-close"
                                            data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <div class="form-in-modal">
                                        <form action="${context}/admin/customers"
                                              class="delete-form" method="post">
                                            <span style="width: 100%;display: block;text-align: center;">Bạn có chắc chắn ${currentRow.active?'khóa':'bỏ khóa'} tài khoản
                                                khách hàng
                                            này?</span>
                                            <input type="hidden" name="action"
                                                   value="${currentRow.active?'deactivate':'activate'}">
                                            <input type="hidden" name="id"
                                                   value="${currentRow.userId}">
                                            <button type="submit"
                                                    class="confirm-delete-btn btn text-center" style="margin-left:160px">
                                                Xác nhận
                                            </button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
</div>
<!-- SURROGATE FORM -->
<form action="${requestURI}" id="surrogateForm">
    <input type="hidden" name="keyword" value="${param.keyword}" ${empty param.keyword?'disabled':''}>
    <input type="hidden" name="field" value="${param.field}" ${empty param.field?'disabled':''}>
    <input type="hidden" name="page" value="${not empty param.page? param.page: 1}">
    <input type="hidden" name="sortBy" value="${param.sortBy}" ${empty param.sortBy?'disabled':''}>
    <input type="hidden" name="order" value="${param.order}" ${empty param.order?'disabled':''}>
</form>

