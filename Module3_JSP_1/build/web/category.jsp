<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="template/header.jsp" %>
<c:set var="CateList" value="${CateList}"/>
<c:set var="id" value="${param.id}"/>
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
        <c:forEach items="${CateList}" var="Category">
            <c:choose>
                <c:when test="${Category.getCategoryID() eq id}">
                    <a href="category?id=${Category.getCategoryID()}"><li><mark>${Category.getCategoryName()}</mark></li></a>
                </c:when>
                <c:otherwise>
                    <a href="category?id=${Category.getCategoryID()}"><li>${Category.getCategoryName()}</li></a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </ul>
</div>

<div id="content-right">
    <div class="path">Category:
        <c:forEach items="${CateList}" var="category">
            <c:if test="${category.getCategoryID() eq id}">
                <c:out value="${category.getCategoryName()}"/>
            </c:if>
        </c:forEach>
    </div>
    <div class="pagination" style="display: block; margin-left: 10px">
        <c:forEach begin="${1}" end="${num}" var="i">
            <a class="${i==page?"active":""}" href="category?id=${id}&page=${i}">${i}</a>
        </c:forEach>
    </div>

    <div class="content-main">
        <c:choose>
            <c:when test="${page > num}">
                <p style="margin-left: 10px;">There are no product in this page</p>
            </c:when>
            <c:otherwise>
                <c:forEach items="${listByCateID}" var="pro">
                    <div class="product">
                        <a href="detail?id=${pro.getProductID()}"><img src="img/1.jpg" width="100%"/></a>
                        <div class="name"><a href="detail?id=${pro.getProductID()}">${pro.getProductName()}</a></div>
                        <div class="price">$${pro.getUnitPrice()}</div>
                        <div><a href="">Buy now</a></div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<%@include file="template/footer.jsp" %>
