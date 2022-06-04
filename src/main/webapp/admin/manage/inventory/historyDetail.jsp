<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%-- set context path--%>
<c:set var="context" value="${pageContext.request.contextPath}" scope="request"/>
<c:set var="rand"><%= java.lang.Math.round(java.lang.Math.random() * 10000) %>
</c:set>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Chi tiết đơn nhập</title>
    <link rel="shortcut icon" href="${context}/favicon.ico">
    <!-- RESET CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css"
          integrity="sha512-NmLkDIU1C/C88wi324HBc+S2kLhi08PN5GDeUVVVC/BVt/9Izdsc9SVeVfA1UZbY3sHUlDSyRXhCzHfr6hmPPw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>

    <!-- FONT   -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">
    <!-- BOOSTRAP CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- APP -->
    <link rel="stylesheet" href="${context}/css/base.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/style.css?rd=${rand}">
    <link rel="stylesheet" href="${context}/css/admin/inventory/historyDetail.css?rd=${rand}">

</head>
<body>
<jsp:include page="../../common/header.jsp"/>
<div class="content">
    <div class="import-detail-box">
        <div class="import-detail-box__group">
            <span class="import-detail-box__column__label">Người nhập kho:</span>
            <span class="import-detail-box__column__content"> ${anImport.importerName}
            </span>
        </div>
        <div class="import-detail-box__group">
            <span class="import-detail-box__column__label">Nhà cung cấp:</span>
            <span class="import-detail-box__column__content">${anImport.providerName}
            </span>
        </div><div class="import-detail-box__group">
            <span class="import-detail-box__column__label">Mã số thuế:</span>
            <span class="import-detail-box__column__content">${anImport.providerTaxId}
            </span>
        </div>
        <table class="table table-hover table-bordered import-detail-box__table">
            <thead>
            <tr>
                <th>STT</th>
                <th>Tên sản phẩm</th>
                <th>Số lượng</th>
                <th>Đơn giá</th>
                <th>Thuế</th>
                <Th>Thành tiền</Th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${anImport.importDetailList}" var="importDetail" varStatus="loop">
                <tr>
                    <td>${loop.count}</td>
                    <td>${importDetail.productName}</td>
                    <td>${importDetail.quantity}</td>
                    <td>${importDetail.price}</td>
                    <td>${importDetail.tax*100}%</td>
                    <td>
                        <fmt:formatNumber value="${importDetail.price*importDetail.quantity*(1-importDetail.tax)}"
                                          type="number" maxFractionDigits="0"/>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
        <div class="import-detail-box__group">
            <span class="import-detail-box__column__label">Tổng tiền:</span>
            <span class="import-detail-box__column__content">
                <fmt:formatNumber type="number" value="${anImport.totalPrice}" maxFractionDigits="0"/>
            </span>
        </div>
        <div class="import-detail-box__group"><span class="import-detail-box__column__label">Ngày nhập:</span>
            <span
                    class="import-detail-box__column__content">${anImport.importDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))}
            </span>
            </div>
        <form action="${context}/admin/inventory/history/download" method="post" class="mt-5">
            <input type="hidden" name="id" value="${anImport.importId}">
            <button type="submit" class="btn btn-primary">Xuất file</button>
        </form>
    </div>

</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<%--<jsp:include page="../../common/footer.jsp"/>--%>


</body>
</html>
