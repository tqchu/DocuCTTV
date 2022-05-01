<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<div class="tab__content">
    <div class="search">
        <form action="${context}/admin/categories/search" class="search__form" method="get">
            <input type="search" class="search__bar form-control" name="keyword" placeholder="Nhập tên doanh mục"
                   value="${param.keyword}">
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
    <div class="order-bar">
        <span class="order-bar__text-heading">
            Sắp xếp theo
        </span>
        <input type="hidden" name="query-string"
               value="${not empty param.keyword?'keyword='+=param.keyword:''}">
        <span class="order-bar__option ${param.orderBy=='default'|| empty param.orderBy?'active':''}"
              data-sort="default">Mới
            nhất</span>
        <span class="order-bar__option  ${param.orderBy=='name'?'active':''}" data-sort="name">Tên</span>
    </div>
    <div class="list">
        <table class="table table-hover table-bordered ">
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

                            <button class="btn btn-delete" title="Xoá doanh mục"
                                    data-bs-toggle="modal"
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
                                            <button type="button" class="btn-close"
                                                    data-bs-dismiss="modal"
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
    <c:set var="queryString" scope="page"><%= request.getQueryString() %></c:set>
    <nav>
        <ul class="pagination justify-content-center pagination-lg">
            <li class="page-item">
                <a class="page-link" href="#" aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
           <%-- <li class="page-item"><a class="page-link"
                                     href="${requestURI+=(not empty keyword?"?keyword="+=keyword+"&":"?")+=(not empty
                                     field?"&field="+=field+"&":"")+=(not empty orderBy?"orderBy+="+=orderBy)}">
                1</a></li>--%>

            <li class="page-item"><a class="page-link"
                                     href="${queryString!="null"
                                     ?requestURI+="?"+=queryString+="&page=1":requestURI+="&page=2"}">2
            </a></li>
            <li class="page-item"><a class="page-link" href="#">3</a></li>
            <li class="page-item">
                <a class="page-link" href="#" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav>
</div>
<script src="${context}/js/admin/category/category.js?rd=${rand}"></script>
