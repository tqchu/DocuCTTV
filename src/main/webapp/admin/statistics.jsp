<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" href="${context}/css/admin/statistics/statistics.css?rd=${rand}">
<form action="${context}/admin/statistics/" id="period-form">
    <select name="period" id="period-select">
        <option value="this-month" selected>This month</option>
        <option value="this-quarter" >This quarter</option>
        <option value="this-year" >This year</option>
    </select>
</form>
<script src="${context}/js/admin/statistics/statistics.js?rd=${rand}"></script>
