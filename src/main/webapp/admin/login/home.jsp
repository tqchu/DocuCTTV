<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Title</title>
    <link rel="stylesheet" href="${context}/css/style.css">
</head>
<body>
<a href="" class="admin-change-information">
    Thay đổi thông tin
</a>
<div class="admin-control-bar">
    <a href="" class="btn admin-control-bar__add-btn">Thêm quản trị viên</a>
</div>
<div class="admin-list">
    <table>
        <tr>
            <th>STT</th>
            <th>Họ và tên</th>
            <th>Tên đăng nhập</th>
            <th>Vai trò</th>
        </tr>
        <tr>
            <td>1</td>
            <td>Nguyễn Văn Vương</td>
            <td>nvvuong</td>
            <td>Admin</td>
        </tr>
    </table>
</div>

</body>
</html>
