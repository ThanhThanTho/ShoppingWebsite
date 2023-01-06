<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="template/headerAdmin.jsp" %>
<a href="<%=path%>/admin/dashboard"><li>Dashboard</li></a>
<a href="<%=path%>/admin/orders"><li><mark>Orders</mark></li></a>
<a href="<%=path%>/admin/products"><li>Products</li></a>
<a href="#"><li>Customers</li></a>
</ul>
</div>

<div id="content-right">
    <div class="path-admin">ORDER DETAIL</b></div>
    <div class="content-main">
        <div id="content-main-dashboard">
            <div>
                <div class="profile-order-title">
                    <div class="profile-order-title-left">
                        <div>OrderID: ${order.getOrderID()}</div>
                        <div>Order creation date: <fmt:formatDate value="${order.getOrderDate()}" pattern="dd/MM/yyyy"></fmt:formatDate></div>
                        </div>
                        <div class="profile-order-title-right">
                        <c:if test="${order.getRequiredDate() eq null && order.getShippedDate() eq null}">
                            <p style="color: red;">Order canceled</p>
                        </c:if>
                        <c:if test="${order.getRequiredDate() eq null && order.getShippedDate() ne null}">
                            <p style="color: green;">Completed</p>
                        </c:if>
                        <c:if test="${order.getRequiredDate() ne null && order.getShippedDate() eq null}"> 
                            <p style="color: blue;">Pending</p>
                        </c:if>
                        <c:if test="${order.getRequiredDate() ne null && order.getShippedDate() ne null}">
                            <p style="color: green;">Completed</p>
                        </c:if>
                    </div>
                </div>
                <c:choose>
                    <c:when test="${fn:length(detailList)==0}">
                        <p>There aren't any detail for your order</p>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${detailList}" var="detail">
                            <div class="profile-order-content" style="background-color: white;">
                                <div class="profile-order-content-col1">
                                    <a href="detail.html"><img src="../img/2.jpg" width="100%"/></a>
                                </div>
                                <div class="profile-order-content-col2">${detail.getProductName()}</div>
                                <div class="profile-order-content-col3">Quantity: ${detail.getQuantity()}</div>
                                <div class="profile-order-content-col4">${detail.getTotal()}$</div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>

            </div>
        </div>
    </div>
</div>
<%@include file="template/footerAdmin.jsp" %>
