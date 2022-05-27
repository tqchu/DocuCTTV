<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" href="https://cdn.datatables.net/1.11.5/css/dataTables.bootstrap5.min.css">
<link rel="stylesheet" href="${context}/css/admin/statistics/statistics.css?rd=${rand}">
<form action="${context}/admin/statistics/" id="period-form">
    <select name="period" id="period-select">
        <option value="this-month" ${statisticsTab=='this-month'?'selected':''}>Tháng này</option>
        <option value="this-quarter" ${statisticsTab=='this-quarter'?'selected':''}>Quý này</option>
        <option value="this-year" ${statisticsTab=='this-year'?'selected':''}>Năm này</option>
    </select>
</form>
<div class="revenue-import">
    <div class="revenue-import-information">
        <div class="revenue-import-information-item">
            <div class="revenue-import-information-item__text">Doanh thu</div>
            <div class="revenue-import-information-item__value">${totalRevenue}</div>
        </div>
        <div class="revenue-import-information-item">
            <div class="revenue-import-information-item__text">Tiền nhập hàng</div>
            <div class="revenue-import-information-item__value">${totalImportAmount}</div>
        </div>
    </div>
    <div id="revenueImportChart"></div>
</div>
<div id="orderChart">
</div>
<div class="stats-product-list">
    <div class="stats-product-list__heading-text">DANH SÁCH SẢN PHẨM</div>
    <table class="table table-hover table-bordered " id="product-ranking-table">
        <thead>
        <tr>
            <th>Mã sản phẩm</th>
            <th>Tên sản phẩm</th>
            <th>Số lượng bán</th>
            <th>Số lượng nhập</th>
            <th>Doanh thu</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${statsProductList}" var="statsProduct">
            <tr>
                <td>${statsProduct.product.productId}</td>
                <td class="product-name">
                        <a href="${context}/admin/products?action=view&id=${statsProduct.product.productId}">
                                ${statsProduct.product.name}</a>
                </td>
                <td>
                        ${statsProduct.soldQuantity}</td>
                <td>${statsProduct.importQuantity}</td>
                <td>${statsProduct.revenue}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>
<script>
    window.onload = function () {

        var revenueImportChart = new CanvasJS.Chart("revenueImportChart", {
            theme: "light2",
            title: {
                text: "Doanh thu và tiền nhập hàng trong <%= request.getAttribute("title")%>"
            },
            axisY: [{
                suffix: " VND"
            }, {
                suffix: " VND"
            }],
            legend: {
                horizontalAlign: "right", // "center" , "right"
                verticalAlign: "center",  // "top" , "bottom"
                dockInsidePlotArea: true,
                fontSize: 15
            },
            data: [{
                type: "line",
                showInLegend: true,
                legendText: "Doanh thu",
                legendMarkerType: "circle",
                toolTipContent: "<b>{label}</b>: {y} VND",
                dataPoints: <%out.print(request.getAttribute("revenuePointList"));%>
            },
                {
                    type: "line",
                    showInLegend: true,
                    legendText: "Nhập hàng",
                    legendMarkerType: "circle",
                    toolTipContent: "<b>{label}</b>: {y} VND",
                    dataPoints: <%= request.getAttribute("importPointList")%>

                }]
        });
        revenueImportChart.render();

        var orderChart = new CanvasJS.Chart("orderChart", {
            theme: "light2", // "light1", "dark1", "dark2"
            animationEnabled: true,
            title: {
                text: "<%= request.getAttribute("numberOfTotalOrder")%> đơn hàng"
            },
            data: [{
                type: "pie",
                toolTipContent: "<b>{label}</b>: {y} đơn",
                indexLabelFontSize: 16,
                indexLabel: "{label}: {y} đơn",
                dataPoints: <%=request.getAttribute("orderChartElementList")%>
            }]
        });
        orderChart.render();

    }
</script>
<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<script src="${context}/js/admin/statistics/statistics.js?rd=${rand}"></script>
