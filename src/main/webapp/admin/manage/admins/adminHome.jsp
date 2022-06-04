<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="search">
    <form action="${context}/admin/admins/search" class="search__form">
        <input type="search" class="search__bar form-control" name="keyword"
               placeholder="Tìm kiếm"
               value="${param.keyword}">
        <button type="submit" class="btn btn-primary btn-search">
            <i class="las la-search"></i>
        </button>

    </form>
    <a href="${context}/admin/admins?action=create" class="search__add-btn btn btn-primary"
       title="Thêm quản trị viên"><i
            class="las la-plus"></i></a>

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
<c:if test="${errorMessage!=null}">
    <div class="toast align-items-center toast-message toast-message--error show" role="alert"
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
    <table class="table table-hover table-bordered ">
        <thead>
        <tr>
            <th>STT</th>
            <th class="admin__full-name">Họ và tên</th>
            <th class="admin__user-name">Tên đăng nhập</th>
            <th class="admin__email">Email</th>
            <th>Vai trò</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${empty adminList}">
            <tr>
                <td colspan="7" class="no-records">
                    Không có dữ liệu
                </td>
            </tr>

        </c:if>
        <c:if test="${not empty adminList}">
            <c:forEach items="${adminList}" var="currentRow" varStatus="loop">
                <tr>
                    <td>${loop.count + (not empty param.page?param.page-1:0) * 10}</td>
                    <td>${currentRow.fullName}</td>
                    <td>${currentRow.username}</td>
                    <td>${currentRow.email}</td>
                    <td>
                        <form action="${context}/admin/admins" method="post" id="editRoleForm${currentRow.userId}">
                            <input
                                    type="hidden" name="action" value="update">
                            <input type="hidden" name="id" value="${currentRow.userId}">
                            <select class="form-select role-select" name="role">
                                <option value="admin" ${currentRow.role=='admin'?'selected':''}>Nhân
                                    viên
                                </option>
                                <option value="super"
                                    ${currentRow.role=='super'?'selected':''}>Quản lý
                                </option>
                            </select>

                        </form>

                    </td>
                    <td>
                        <button type="submit" class="btn-edit btn btn btn-primary"
                                form="editRoleForm${currentRow.userId}">Lưu
                        </button>
                    </td>
                    <td>
                        <button class="btn btn-delete" title="Xoá quản trị viên"
                                data-bs-toggle="modal"
                                data-bs-target="#deleteAdminModal${currentRow.userId}">
                            Xoá
                        </button>
                    </td>
                    <!-- MODAL CONTENT -->
                    <div class="modal fade"
                         id="deleteAdminModal${currentRow.userId}"
                         tabindex="-1"
                         aria-labelledby="deleteAdminModal${currentRow.userId}Label"
                         aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered">

                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title"
                                        id="deleteAdminModal${currentRow.userId}Label">
                                        Xóa quản trị viên
                                    </h5>
                                    <button type="button" class="btn-close"
                                            data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <form action="${context}/admin/admins"
                                          class="delete-form" method="post">
                                        Bạn có chắc chắn xóa quản trị viên
                                            ${currentRow.fullName}?
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="id"
                                               value="${currentRow.userId}">
                                        <button type="submit"
                                                class="confirm-delete-btn btn">
                                            Xác nhận
                                        </button>
                                    </form>
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

