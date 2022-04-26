<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="${context}/css/admin/provider/provider.css?rd=${rand}">
<div class="tab__content">
    <div class="search">
        <form action="${context}/admin/providers/search" class="search__form">
            <input type="search" class="search__bar form-control" name="keyword">
            <select name="field" id="" class="provider__search-option">
                <option value="name">Theo tên nhà cung cấp</option>
                <option value="address">Theo địa chỉ</option>
                <option value="phoneNumber">Theo số điện thoại</option>
                <option value="email">Theo email</option>
                <option value="taxId">Theo mã số thuế</option>
            </select>
            <button type="submit" class="btn btn-primary btn-search">
                <i class="las la-search"></i>
            </button>
        </form>
        <a href="#" class="search__add-btn btn btn-primary"
           title="Thêm nhà cung cấp" data-bs-toggle="modal" data-bs-target="#addModal"><i
                class="las la-plus"></i></a>
        <!-- MODAL CONTENT -->
        <div class="modal fade" id="addModal" tabindex="-1" aria-labelledby="addModalLabel"
             aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">

                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addModalLabel">
                            Thêm nhà cung cấp
                        </h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form action="${context}/admin/providers" method="post"
                              class="d-flex align-items-center flex-column">
                            <input type="hidden" value="create" name="action">
                            <div class="form-group form-floating">
                                <input type="text" name="name"
                                       class="form-control" placeholder="Tên nhà cung cấp" autofocus>
                                <label class="form-label">Tên nhà cung cấp</label>
                            </div>
                            <div class="form-group form-floating">
                                <input type="text" name="email"
                                       class="form-control" placeholder="Email" autofocus>
                                <label class="form-label">Email</label>
                            </div>
                            <div class="form-group form-floating">
                                <input type="text" name="phoneNumber"
                                       class="form-control" placeholder="Số điện thoại" autofocus>
                                <label class="form-label">Số điện thoại</label>
                            </div>
                            <div class="form-group form-floating">
                                <input type="text" name="address"
                                       class="form-control" placeholder="Địa chỉ" autofocus>
                                <label class="form-label">Địa chỉ</label>
                            </div>
                            <div class="form-group form-floating">
                                <input type="text" name="taxId"
                                       class="form-control" placeholder="Mã số thuế" autofocus>
                                <label class="form-label">Mã số thuế</label>
                            </div>



                            <button type="submit" class="btn btn-primary save-btn">Thêm</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

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
            <c:if test="${not empty providerList}">
                <c:forEach items="${providerList}" var="provider" varStatus="loop">
                    <tr>
                        <td class="">${loop.count}</td>
                        <td class="">${provider.providerName}</td>
                        <td class="">${provider.address}</td>
                        <td>${provider.phoneNumber}</td>
                        <td>${provider.email}</td>
                        <td>${provider.taxId}</td>
                        <td>
                            <a href="#" class="btn-edit btn btn-primary"
                               title="Sửa nhà cung cấp" data-bs-toggle="modal"
                               data-bs-target="#editModal${provider.providerId}">Chỉnh
                                sửa</a>
                            <!-- MODAL CONTENT -->
                            <div class="modal fade" id="editModal${provider.providerId}" tabindex="-1"
                                 aria-labelledby="editModal${provider.providerId}Label"
                                 aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered">

                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="editModal${provider.providerId}Label">
                                                Sửa thông tin nhà cung cấp
                                            </h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                    aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <form action="${context}/admin/providers" method="post"
                                                  class="d-flex align-items-center flex-column">
                                                <input type="hidden" value="update" name="action">
                                                <input type="hidden" name="id" value="${provider.providerId}" >
                                                <div class="form-group form-floating">
                                                    <input type="text" name="name"
                                                           class="form-control" placeholder="Tên nhà cung cấp" autofocus>
                                                    <label class="form-label">Tên nhà cung cấp</label>
                                                </div>
                                                <div class="form-group form-floating">
                                                    <input type="text" name="email"
                                                           class="form-control" placeholder="Email" autofocus>
                                                    <label class="form-label">Email</label>
                                                </div>
                                                <div class="form-group form-floating">
                                                    <input type="text" name="phoneNumber"
                                                           class="form-control" placeholder="Số điện thoại" autofocus>
                                                    <label class="form-label">Số điện thoại</label>
                                                </div>
                                                <div class="form-group form-floating">
                                                    <input type="text" name="address"
                                                           class="form-control" placeholder="Địa chỉ" autofocus>
                                                    <label class="form-label">Địa chỉ</label>
                                                </div>
                                                <div class="form-group form-floating">
                                                    <input type="text" name="taxId"
                                                           class="form-control" placeholder="Mã số thuế" autofocus>
                                                    <label class="form-label">Mã số thuế</label>
                                                </div>



                                                <button type="submit" class="btn btn-primary save-btn">Lưu</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </td>
                        <td>
                            <button class="btn btn-delete" title="Xoá nhà cung cấp"
                                    data-bs-toggle="modal"
                                    data-bs-target="#deleteProviderModal${provider.providerId}">
                                Xoá
                            </button>
                            <!-- MODAL CONTENT -->
                            <div class="modal fade"
                                 id="deleteProviderModal${provider.providerId}"
                                 tabindex="-1"
                                 aria-labelledby="deleteProviderModal${provider.providerId}Label"
                                 aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered">

                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title"
                                                id="deleteProviderModal${provider.providerId}Label">
                                                Xóa nhà cung cấp
                                            </h5>
                                            <button type="button" class="btn-close"
                                                    data-bs-dismiss="modal"
                                                    aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <form action="${context}/admin/providers"
                                                  class="delete-form" method="post">
                                                Bạn có chắc chắn xóa nhà cung cấp
                                                    ${provider.providerName}?
                                                <input type="hidden" name="action" value="delete">
                                                <input type="hidden" name="id"
                                                       value="${provider.providerId}">
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