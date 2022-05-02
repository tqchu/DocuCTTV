<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- SURROGATE FORM -->
<form action="${requestURI}" id="surrogateForm">
    <input type="hidden" name="keyword" value="${param.keyword}" ${empty param.keyword?'disabled':''}>
    <input type="hidden" name="field" value="${param.field}" ${empty param.field?'disabled':''}>
    <input type="hidden" name="page" value="${not empty param.page? param.page: 1}">
    <input type="hidden" name="sortBy" value="${param.sortBy}" ${empty param.sortBy?'disabled':''}>
    <input type="hidden" name="order" value="${param.order}" ${empty param.order?'disabled':''}>
</form>
<!-- PAGINATION -->
<nav>
    <ul class="pagination justify-content-center">
        <li class="page-item ${(empty param.page|| param.page=='1')?'disabled':''}">
            <button class="page-link " aria-label="Trước">
                <span aria-hidden="true"><</span>
            </button>
        </li>
        <c:forEach begin="1" end="${numberOfPages}" var="i">
            <li class="page-item ${param.page==i||(empty param.page && i==1)?'active':''}">
                <button class="page-link" data-page="${i}"
                >${i}
                </button>
            </li>
        </c:forEach>
        <li class="page-item  ${(param.page==numberOfPages||numberOfPages==1)?'disabled':''}">
            <button class="page-link" aria-label="Sau">
                <span aria-hidden="true">></span>
            </button>
        </li>
    </ul>
</nav>