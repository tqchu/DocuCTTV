<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="${context}/css/admin/orders/orderDetail.css?rd=${rand}">
<div class="order__meta-info">
  <span class="order__back-btn" onclick="history.back()">
      <i class="las la-angle-left order__back-btn-icon"></i>
            TRỞ VỀ
        </span>
    <span class="order__id-status">

    <span class="order__order-id">
            Mã đơn hàng: ${order.orderId}
        </span>
    <span class="order__order-status">
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
        </span>
    </span>
</div>
<div class="order__customer-info-wrapper">
    <div class="order__customer-info">
        <div class="order__customer-info__heading">Thông Tin Khách Hàng</div>
        <div class="order__customer-info__content">
            <div class="order__customer-info__customer-name">${order.customerName}</div>
        </div>

    </div>
    <div class="order__recipient-info">
        <div class="order__customer-info__heading">Địa Chỉ Nhận Hàng</div>
        <div class="order__customer-info__content">
            <div class="order__customer-info__recipient-item">${order.recipientName}</div>
            <div class="order__customer-info__recipient-item">${order.phoneNumber}</div>
            <div class="order__customer-info__recipient-item">${order.address}</div>
        </div>

    </div>
</div>
<div class="order__product-list">
    <c:forEach items="${order.orderDetailList}" var="orderDetail">
        <c:set var="product" value="${orderDetail.product}"/>
        <a href="${context}/products/${not empty product?product.uri:'ngung-kinh-doanh'}" class="order__product-item">
            <div class="order__product-item__img"
                 style="background-image: url('${context+='/'+=product.imagePathList[0].path}');"></div>
            <div class="order__product-item__name-item">
                <div class="order__product-item__product-name">
                    <c:if test="${ not empty orderDetail.product}">
                        ${product.name}
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
<div class="order__summary">
    <div class="order__summary__time-line">
        <div class="order__summary__time-line-item">
            <div class="order__summary__time-line-item__name">Thời gian đặt</div>
            <div class="order__summary__time-line-item__value">
                <fmt:parseDate value="${order.orderTime}" type="time" var="orderTime"
                               pattern="yyyy-MM-dd'T'HH:mm:ss"/>
                <fmt:formatDate value="${orderTime}" type="TIME" pattern="dd/MM/yyyy HH:mm:ss"/>
            </div>
        </div>
        <c:if test="${not empty order.confirmTime}">
            <div class="order__summary__time-line-item">
                <div class="order__summary__time-line-item__name">Thời gian xác nhận</div>

                <div class="order__summary__time-line-item__value price">
                    <fmt:parseDate value="${order.confirmTime}" type="time" var="confirmTime"
                                   pattern="yyyy-MM-dd'T'HH:mm:ss"/>
                    <fmt:formatDate value="${confirmTime}" type="TIME" pattern="dd/MM/yyyy HH:mm:ss"/>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty order.shipTime}">
            <div class="order__summary__time-line-item">
                <div class="order__summary__time-line-item__name">Thời gian giao hàng cho vận chuyển</div>

                <div class="order__summary__time-line-item__value price">
                    <fmt:parseDate value="${order.shipTime}" type="time" var="shipTime"
                                   pattern="yyyy-MM-dd'T'HH:mm:ss"/>
                    <fmt:formatDate value="${shipTime}" type="TIME" pattern="dd/MM/yyyy HH:mm:ss"/>
                </div>
            </div>
        </c:if>
        <c:if test="${not empty order.completedTime}">
            <div class="order__summary__time-line-item">
                <div class="order__summary__time-line-item__name">Thời gian hoàn thành</div>

                <div class="order__summary__time-line-item__value price">
                    <fmt:parseDate value="${order.completedTime}" type="time" var="completedTime"
                                   pattern="yyyy-MM-dd'T'HH:mm:ss"/>
                    <fmt:formatDate value="${completedTime}" type="TIME" pattern="dd/MM/yyyy HH:mm:ss"/>
                </div>
            </div>
        </c:if>
    </div>
    <div class="order__summary__price">
        <div class="order__summary-item">
            <div class="order__summary-item__name">Tồng tiền hàng</div>
            <div class="order__summary-item__value price">${order.totalPrice-order.shippingFee}</div>
        </div>
        <div class="order__summary-item">
            <div class="order__summary-item__name">Phí vận chuyển</div>
            <div class="order__summary-item__value price">${order.shippingFee}</div>
        </div>
        <div class="order__summary__total">
            <div class="order__summary-item__name">Tổng tiền</div>
            <div class="order__summary-item__value price">${order.totalPrice}</div>
        </div>
        <div class="order__summary-item order__summary__payment-method">
            <div class="order__summary-item__name">Phương thức thanh toán</div>
            <div class="order__summary-item__value">Thanh toán khi nhận hàng</div>
        </div>
    </div>
</div>