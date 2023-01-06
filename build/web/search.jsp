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

    mark{
        background-color: sienna;
        color: white;
    }
</style>

<div id="content-left">
    <h3>CATEGORY</h3>
    <ul>
        <a href="<%=contextPath%>/search?cateId=${all}&searchKey=${searchKey}&page=${page}"><li>All</li></a>
                <c:forEach items="${CateList}" var="Category">
                    <c:choose>
                        <c:when test="${Category.getCategoryID() eq param.cateId}">
                    <a href="<%=contextPath%>/search?cateId=${Category.getCategoryID()}&searchKey=${searchKey}&page=${page}"><li><mark>${Category.getCategoryName()}</mark></li></a>
                        </c:when>
                        <c:otherwise>
                    <a href="<%=contextPath%>/search?cateId=${Category.getCategoryID()}&searchKey=${searchKey}&page=${page}"><li>${Category.getCategoryName()}</li></a> 
                        </c:otherwise>
                    </c:choose>
        </c:forEach>
    </ul>
</div>
<div id="content-right">
    <div class="path">Product</div>
    <div class="pagination" style="display: block; margin-left: 10px">
        <c:forEach begin="${1}" end="${num}" var="i">
            <a class="${i==page?"active":""}" href="search?page=${i}&searchKey=${param.searchKey}&cateId=${param.cateId}">${i}</a>
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
                        <a href="<%=contextPath%>/detail?id=${hotPro.getProductID()}"><img src="img/1.jpg" width="100%"/></a>
                        <div class="name"><a href="<%=contextPath%>/detail?id=${hotPro.getProductID()}">${hotPro.getProductName()}</a></div>
                        <div class="price">$${hotPro.getUnitPrice()}</div>
                        <div><a href="<%=contextPath%>/detail?id=${hotPro.getProductID()}&addCart=${hotPro.getProductID()}&buy=true">Buy now</a></div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>

</div>

<%@include file="template/footer.jsp" %>
