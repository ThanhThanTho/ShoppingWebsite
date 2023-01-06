<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="template/header.jsp" %>
<c:set var="CateList" value="${CateList}"/>
<%
    String contextPath = request.getContextPath();
%>
<style>
    .pagination{
        display: inline-block;
    }

    .pagination a{
        color: black;
        font-size: 22px;
        float: left;
        padding: 8px 16px;
        text-decoration: none;
    }

    .pagination a.active{
        background-color: chocolate;
        color: white;
    }

    .pagination a:hover:not(.active){
        background-color: chocolate;
    }
</style>
<div id="content-left">
    <h3>CATEGORY</h3>
    <ul>
        <c:forEach items="${CateList}" var="Category">
            <a href="category?id=${Category.getCategoryID()}"><li>${Category.getCategoryName()}</li></a>
                </c:forEach>
    </ul>
</div>
<div id="content-right">
    <div class="path">Hot</div>
    <div class="pagination" style="display: block; margin-left: 10px">
        <c:forEach begin="${1}" end="${num}" var="i">
            <a class="${i==page?"active":""}" href="index?page=${i}&pageNew=${param.pageNew}&pageSale=${param.pageSale}">${i}</a>
        </c:forEach>
    </div>
    <div class="content-main">
        <c:choose>
            <c:when test="${page > num}">
                <p style="margin-left: 10px;">There are no product in this page</p>
            </c:when>
            <c:otherwise>
                <c:forEach items="${HotList}" var="hotPro">
                    <div class="product">
                        <a href="detail?id=${hotPro.getProductID()}"><img src="img/1.jpg" width="100%"/></a>
                        <div class="name"><a href="detail?id=${hotPro.getProductID()}">${hotPro.getProductName()}</a></div>
                        <div class="price">$${hotPro.getUnitPrice()}</div>
                        <div><a href="<%=contextPath%>/detail?id=${hotPro.getProductID()}&addCart=${hotPro.getProductID()}&buy=true">Buy now</a></div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="path">Best Sale</b></div>
    <div class="pagination" style="display: block; margin-left: 10px">
        <c:forEach begin="${1}" end="${numSale}" var="i">
            <a class="${i==pageSale?"active":""}" href="index?page=${param.page}&pageNew=${param.pageNew}&pageSale=${i}">${i}</a>
        </c:forEach>
    </div>
    <div class="content-main">
        <c:choose>
            <c:when test="${pageSale > numSale}">
                <p style="margin-left: 10px;">There are no product in this page</p>
            </c:when>
            <c:otherwise>
                <c:forEach items="${SaleList}" var="salePro">
                    <div class="product">
                        <a href="detail?id=${salePro.getProductID()}"><img src="img/1.jpg" width="100%"/></a>
                        <div class="name"><a href="detail?id=${salePro.getProductID()}">${salePro.getProductName()}</a></div>
                        <div class="price">$${salePro.getUnitPrice()}</div>
                        <div><a href="<%=contextPath%>/detail?id=${salePro.getProductID()}&addCart=${salePro.getProductID()}&buy=true">Buy now</a></div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="path">New Product</b></div>
    <div class="pagination" style="display: block; margin-left: 10px">
        <c:forEach begin="${1}" end="${numNew}" var="i">
            <a class="${i==pageNew?"active":""}" href="index?page=${param.page}&pageNew=${i}&pageSale=${param.pageSale}">${i}</a>
        </c:forEach>
    </div>
    <div class="content-main">
        <c:choose>
            <c:when test="${pageNew > numNew}">
                <p style="margin-left: 10px;">There are no product in this page</p>
            </c:when>
            <c:otherwise>
                <c:forEach items="${NewList}" var="newPro">
                    <div class="product">
                        <a href="detail?id=${newPro.getProductID()}"><img src="img/1.jpg" width="100%"/></a>
                        <div class="name"><a href="detail?id=${newPro.getProductID()}">${newPro.getProductName()}</a></div>
                        <div class="price">$${newPro.getUnitPrice()}</div>
                        <div><a href="<%=contextPath%>/detail?id=${newPro.getProductID()}&addCart=${newPro.getProductID()}&buy=true">Buy now</a></div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<%@include file="template/footer.jsp" %>