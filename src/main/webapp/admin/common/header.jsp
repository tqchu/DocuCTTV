<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="context" value="${pageContext.request.contextPath}"/>
<%--FONT AWESOME ICON--%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<%--CONTENT--%>
<div class="header">
    <a href="${context}/admin" class="header__logo"></a>
    <c:if test="${headerAction!=null}">

        <div class="header__action">${headerAction}</div>
    </c:if>
    <c:if test="${admin!=null}">
        <div class="information">
                <span class="information__icon">
                    <i class="fa-solid fa-user"></i>
                </span>
            <span class="information__name">
                    ${admin.fullName}
            </span>
            <div class="information__popover">
                <a href="${context}/admin/update" class="information__popover-item">Thay đổi thông tin</a>
                <a href="${context}/admin/logout" class="information__popover-item">Đăng xuất</a>
            </div>
        </div>
    </c:if>
</div>