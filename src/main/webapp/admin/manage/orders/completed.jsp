<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<thead>
<tr>
    <th>Mã đơn hàng</th>
    <th>Tên khách hàng</th>
    <th>Thời gian đặt</th>
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
            <fmt:parseDate value="${order.orderTime}" type="time" var="orderTime"
                           pattern="yyyy-MM-dd'T'HH:mm:ss"/>
            <fmt:formatDate value="${orderTime}" type="TIME" pattern="dd/MM/yyyy HH:mm:ss"/>
        </td>
        <td>${order.totalPrice}</td>
        <td class="column__action">
            <a href="${context}/admin/orders/${order.orderId}" class="btn-primary btn btn-edit">Xem chi tiết</a>
        </td>
        <td class="column__action">
            Xuất file
        </td>

    </tr>
</c:forEach>
</tbody>