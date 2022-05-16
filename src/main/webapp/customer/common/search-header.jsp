<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="context" value="${pageContext.request.contextPath}"/>
<%--CONTENT--%>
<div class="header">
    <a href="${context}" class="header__logo"></a>
    <c:if test="${not empty action}">
        <div class="header__action">
                ${action}
        </div>
    </c:if>
    <c:if test="${empty action}">
        <div class="search">
            <form action="${context}/products/search" class="search-form">
                <input type="search" class="search-input" name="keyword" value="${param.keyword}">
                <button class="btn btn-primary btn-search"><i class="las la-search"></i></button>
            </form>
        </div>
        <div class="cart">
            <a href="${context}/user/cart" class="cart-icon">
                <i class="las la-shopping-cart"></i>
            </a>
            <div class="cart-preview">
                <div class="cart-preview__heading">
                    Sản phẩm mới thêm
                </div>
                <div class="cart-preview__cart-list">
                    <a href="" class="cart-preview__cart-item">
                        <div class="cart-preview__cart-item__img"
                             style="background-image: url('https://cf.shopee.vn/file/04f74da736c4fbf6d8c52cb3c620075a_tn')">
                        </div>
                        <div class="cart-preview__cart-item__name">Áo thun nam POLO, áo có cổ trơn vải cá sấu cotton cao
                            cấp ngắn tay cực sang trọng
                        </div>
                        <div class="cart-preview__cart-item__price">
                            69.000
                        </div>

                    </a>
                    <a href="" class="cart-preview__cart-item">
                        <div class="cart-preview__cart-item__img"
                             style="background-image: url('https://cf.shopee.vn/file/04f74da736c4fbf6d8c52cb3c620075a_tn')">
                        </div>
                        <div class="cart-preview__cart-item__name">Áo thun nam POLO, áo có cổ trơn vải cá sấu cotton cao
                            cấp ngắn tay cực sang trọng
                        </div>
                        <div class="cart-preview__cart-item__price">
                            69.000
                        </div>

                    </a>
                    <a href="" class="cart-preview__cart-item">
                        <div class="cart-preview__cart-item__img"
                             style="background-image: url('https://cf.shopee.vn/file/04f74da736c4fbf6d8c52cb3c620075a_tn')">
                        </div>
                        <div class="cart-preview__cart-item__name">Áo thun nam POLO, áo có cổ trơn vải cá sấu cotton cao
                            cấp ngắn tay cực sang trọng
                        </div>
                        <div class="cart-preview__cart-item__price">
                            69.000
                        </div>

                    </a>
                    <a href="" class="cart-preview__cart-item">
                        <div class="cart-preview__cart-item__img"
                             style="background-image: url('https://cf.shopee.vn/file/04f74da736c4fbf6d8c52cb3c620075a_tn')">
                        </div>
                        <div class="cart-preview__cart-item__name">Áo thun nam POLO, áo có cổ trơn vải cá sấu cotton cao
                            cấp ngắn tay cực sang trọng
                        </div>
                        <div class="cart-preview__cart-item__price">
                            69.000
                        </div>

                    </a>
                    <a href="" class="cart-preview__cart-item">
                        <div class="cart-preview__cart-item__img"
                             style="background-image: url('https://cf.shopee.vn/file/04f74da736c4fbf6d8c52cb3c620075a_tn')">
                        </div>
                        <div class="cart-preview__cart-item__name">Áo thun nam POLO, áo có cổ trơn vải cá sấu cotton cao
                            cấp ngắn tay cực sang trọng
                        </div>
                        <div class="cart-preview__cart-item__price">
                            69.000
                        </div>

                    </a>
                </div>
                <div class="cart-preview__footer">
                    <span class="cart-preview__items-sum">14 thêm vào giỏ hàng</span>
                    <a href="" class="cart-preview__view-action btn btn-primary btn-lg">Xem giỏ hàng</a>
                </div>
            </div>
        </div>
    </c:if>
</div>