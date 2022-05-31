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
<div class="list">
    <table class="table table-hover table-bordered ">
        <thead>
        <tr>
            <th>STT</th>
            <th class="column__full-name">Họ và tên</th>
            <th class="column__phone-number">Số điện thoại</th>
            <th class="column__email">Email</th>
            <th class="column__day-of-birth">Ngày sinh</th>
            <th class="column__gender">Giới tính</th>

            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${empty customerList}">
            <tr>
                <td colspan="7" class="no-records">
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
                    <td>${currentRow.dateOfBirth}</td>
                    <td>
                        <c:choose>
                            <c:when test="${currentRow.gender=='MALE'}">Nam</c:when>
                            <c:when test="${currentRow.gender=='FEMALE'}">Nữ</c:when>
                            <c:when test="${currentRow.gender=='OTHER'}">Khác</c:when>
                        </c:choose>
                    </td>
                    <td>
                        <button class="btn btn-delete" title="Xoá khách hàng"
                                data-bs-toggle="modal"
                                data-bs-target="#deleteCustomerModal${currentRow.userId}">
                            Xoá
                        </button>
                    </td>
                    <!-- MODAL CONTENT -->
                    <div class="modal fade"
                         id="deleteCustomerModal${currentRow.userId}"
                         tabindex="-1"
                         aria-labelledby="deleteCustomerModal${currentRow.userId}Label"
                         aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered">

                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title"
                                        id="deleteCustomerModal${currentRow.userId}Label">
                                        Xóa khách hàng
                                    </h5>
                                    <button type="button" class="btn-close"
                                            data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <div class="form-in-modal">
                                        <form action="${context}/admin/customers"
                                              class="delete-form" method="post">
                                            Bạn có chắc chắn xóa khách hàng này?
                                            (Thông tin khách hàng đã xóa sẽ không
                                            thể khôi phục)
                                            <input type="hidden" name="action" value="delete">
                                            <input type="hidden" name="id"
                                                   value="${currentRow.userId}">
                                            <button type="submit"
                                                    class="confirm-delete-btn btn text-center">
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

