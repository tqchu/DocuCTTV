<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<thead>
<tr>
    <th>Mã đơn hàng</th>
    <th>Tên khách hàng</th>
    <th>Thời gian hoàn thành</th>
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
                ${order.completedTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))}
        </td>
        <td>${order.totalPrice}</td>
        <td class="column__action">
            <a href="${context}/admin/orders/${order.orderId}" class="btn-primary btn btn-edit">Xem chi tiết</a>
        </td>
        <td class="column__action">
            <button type="submit" class="btn btn-primary" form="export-file-form">Xuất file</button>
        </td>
        <form action="${context}/admin/orders/download" method="post" id="export-file-form">
            <input type="hidden" name="id" value="${order.orderId}">
        </form>

    </tr>
</c:forEach>
</tbody>