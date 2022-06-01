<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<thead>
<tr>
    <th>Mã đơn hàng</th>
    <th>Tên khách hàng</th>
    <th>Thời gian giao hàng cho vận chuyển</th>
    <th>Tổng giá</th>
    <th></th>
    <th></th>

</tr>
</thead>
<tbody>
<c:forEach items="${orderList}" var="order" varStatus="loop">
    <tr>
        <td>${order.orderId}</td>
        <td>${order.customerName}</td>
        <td>
                ${order.shipTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))}
        </td>
        <td>${order.totalPrice}</td>
        <td class="column__action">
            <a href="${context}/admin/orders/${order.orderId}" class="btn-primary btn btn-edit">Xem chi tiết</a>
        </td>
        <td class="purchase-item__action">
            <a href="" class="btn btn-primary purchase-item__action__btn"
               data-bs-toggle="modal"
               data-bs-target="#confirmReceivingModal${order.orderId}">Đã giao</a>
            <!-- MODAL CONTENT -->
            <div class="modal fade" id="confirmReceivingModal${order.orderId}" tabindex="-1"
                 aria-labelledby="confirmReceivingModal${order.orderId}Label"
                 aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">

                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="confirmReceivingModal${order.orderId}Label">
                                Xác nhận đã giao
                            </h5>
                            <button type="button" class="btn-close"
                                    data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form action="${context}/admin/orders" method="post"
                                  class="d-flex align-items-center flex-column">
                                <input type="hidden" value="" name="from">
                                <input type="hidden" value="completed" name="action">
                                <input type="hidden" value="${order.orderId}" name="id">
                                Xác nhận đã giao đơn hàng ${order.orderId}?
                                <button type="submit"
                                        class="btn btn-primary mt-5">
                                    Đã giao
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </td>

    </tr>
</c:forEach>
</tbody>