<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--BOOTRAP JS--%>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="${context}/css/customer/order.css?rd=${rand}">
<c:if test="${not empty successMessage}">
    <div class="success-message fs-6">
        ${successMessage}
    </div>
    <c:remove var="successMessage" scope="session"/>
</c:if>
<div class="user__manage-account__purchase-content">
    <div class="purchase-bar purchase-bar__five-column">
        <a href="${context}/user/purchase/pending" class="purchase-tab-item ${statusTab=='pending'?'active':''}">
            Chờ xác nhận
        </a>
        <a href="${context}/user/purchase/to-ship" class="purchase-tab-item ${statusTab=='to-ship'?'active':''}">
            Chờ vận chuyển
        </a>
        <a href="${context}/user/purchase/to-receive" class="purchase-tab-item ${statusTab=='to-receive'?'active':''}">
            Đang giao
        </a>
        <a href="${context}/user/purchase/completed" class="purchase-tab-item ${statusTab=='completed'?'active':''}">
            Đã giao
        </a>
        <a href="${context}/user/purchase/canceled" class="purchase-tab-item ${statusTab=='canceled'?'active':''}">
            Đã hủy
        </a>
    </div>
    <c:choose>
        <c:when test="${ not empty orderList}">
            <div class="purchase-search-bar">

                <form action="${context}/user/purchase/${statusTab}/search" id="search-form">
        <span class="purchase-search-bar__search-icon-wrapper">
        <i class="las la-search purchase-search-bar__search-icon"></i>
        </span>
                    <input type="search" name="keyword" class="purchase-search-bar__input" value="${param.keyword}"
                           placeholder="Nhập mã đơn hàng, hoặc  tên sản phẩm">
                </form>
            </div>
            <div class="order-bar purchase-order-bar">
    <span class="order-bar__text-heading">
            Sắp xếp theo
    </span>
                <span class="order-bar__option
     ${((param.sortBy=='time'|| empty param.sortBy) && (param.order=='DESC'|| empty param.order))?'active':''}"
                      data-sort="time" data-order="DESC"
                >Mới
            nhất</span>
                <span class="order-bar__option  ${(param.sortBy=='time'&& param.order=='ASC')?'active':''}"
                      data-sort="time"
                      data-order="ASC">Lâu
        nhất</span>
            </div>
            <div class="purchase-list">
                <c:forEach items="${orderList}" var="order">
                    <div class="purchase-item">
                        <a href="${context}/user/purchase/${order.orderId}" class="purchase-item__meta-info">
                            <div class="purchase-item__meta-info__order-id">
                                    ${order.orderId}
                            </div>
                            <div class="purchase-item__meta-info__order-status">
                                <c:choose>
                                    <c:when test="${order.status=='PENDING'}">
                                        ĐƠN HÀNG ĐANG CHỜ XÁC NHẬN
                                    </c:when>
                                    <c:when test="${order.status=='TO_SHIP'}">
                                        ĐƠN HÀNG CHỜ VẬN CHUYỂN
                                    </c:when>
                                    <c:when test="${order.status=='TO_RECEIVE'}">
                                        ĐƠN HÀNG ĐANG GIAO
                                    </c:when>
                                    <c:when test="${order.status=='COMPLETED'}">
                                        ĐƠN HÀNG ĐÃ GIAO
                                    </c:when>

                                </c:choose>
                            </div>
                        </a>
                        <div class="purchase-item__product-list">
                            <c:forEach items="${order.orderDetailList}" var="orderDetail">
                                <a href="${context}/products/${not empty orderDetail.product?orderDetail.product.uri:'ngung-kinh-doanh'}"
                                   class="order__product-item">
                                    <div class="order__product-item__img" style="background-image:
                                            url('${context}/${orderDetail.product.imagePathList[0].path}');"></div>
                                    <div class="order__product-item__name-item">
                                        <div class="order__product-item__product-name">

                                            <c:if test="${ not empty orderDetail.product}">
                                                ${orderDetail.product.name}
                                            </c:if>
                                            <c:if test="${empty orderDetail.product}">
                                                ${orderDetail.productName}
                                            </c:if>


                                        </div>
                                        <div class="order__product-item__quantity">x${orderDetail.quantity}</div>
                                    </div>
                                    <div class="order__product-item__price">
                                        ${orderDetail.price}
                                    </div>
                                </a>
                            </c:forEach>
                        </div>
                        <div class="purchase-item__summary">
                            <div class="purchase-item__summary__total-price">
                                <div class="purchase-item__summary__total-price__name">
                                    Tổng tiền:
                                </div>
                                <div class="purchase-item__summary__total-price__value">
                                    ${order.totalPrice}
                                </div>
                            </div>
                        </div>
                       <c:if test="${statusTab=='pending'}">
                           <div class="purchase-item__action">
                               <a href="" class="btn btn-delete purchase-item__action__btn"
                                  data-bs-toggle="modal"
                                  data-bs-target="#cancelModal${order.orderId}">Hủy đơn</a>
                               <!-- MODAL CONTENT -->
                               <div class="modal fade" id="cancelModal${order.orderId}" tabindex="-1"
                                    aria-labelledby="cancelModal${order.orderId}Label"
                                    aria-hidden="true">
                                   <div class="modal-dialog modal-dialog-centered">

                                       <div class="modal-content">
                                           <div class="modal-header">
                                               <h5 class="modal-title" id="cancelModal${order.orderId}Label">
                                                   Xác nhận
                                               </h5>
                                               <button type="button" class="btn-close"
                                                       data-bs-dismiss="modal"
                                                       aria-label="Close"></button>
                                           </div>
                                           <div class="modal-body">
                                               <form action="${context}/user/purchase" method="post"
                                                     class="d-flex align-items-center flex-column">
                                                   <input type="hidden" value="" name="from">
                                                   <input type="hidden" value="cancel" name="action">
                                                   <input type="hidden" value="${order.orderId}" name="id">
                                                   Bạn có chắc chắn hủy đơn hàng ${order.orderId}
                                                   <button type="submit"
                                                           class="btn btn-delete mt-5">
                                                       Xác nhận
                                                   </button>
                                               </form>
                                           </div>
                                       </div>
                                   </div>
                               </div>
                           </div>
                       </c:if>
                    </div>
                </c:forEach>


            </div>
            <jsp:include page="../../../common/pagination.jsp"/>

            <!-- SURROGATE FORM -->
            <form action="${requestURI}" id="surrogateForm">
                <input type="hidden" name="keyword" value="${param.keyword}" ${empty param.keyword?'disabled':''}>
                <input type="hidden" name="page" value="${not empty param.page? param.page: 1}">
                <input type="hidden" name="sortBy" value="${param.sortBy}" ${empty param.sortBy?'disabled':''}>
                <input type="hidden" name="order" value="${param.order}" ${empty param.order?'disabled':''}>
            </form>
            <script src="${context}/js/customer/order.js?rd=${rand}"></script>
        </c:when>
        <c:otherwise>
            <div class="no-records-found">KHÔNG CÓ ĐƠN HÀNG NÀO</div>
        </c:otherwise>

    </c:choose>
</div>