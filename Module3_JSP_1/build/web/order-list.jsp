<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="template/header.jsp" %>
<%@page import="models.OrderDAO" %>
<%@page import="dal.Order" %>
<%@page import="dal.OrderDetail" %>
<%@page import="java.util.ArrayList" %>
<div id="content-left">
    <h3 style="font-weight: normal;">Welcome ${cust.getContactName()}</h3>
    <h3>Account Management</h3>
    <ul>
        <a href="profile"><li>Personal information</li></a>
    </ul>
    <h3>My order</h3>
    <ul>
        <a href="#"><li>All orders</li></a>
        <a href="#"><li>Canceled order</li></a>
    </ul>
</div>
<div id="content-right">
    <div class="path">LIST ORDERS</b></div>
    <div class="content-main">
        <div id="profile-content-order" style="overflow-x: scroll; height:400px;">
            <c:choose>
                <c:when test="${orderList.size() == 0}">
                    <p style="text-align: center; font-size: 20px">You don't have any order yet</p>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${orderList}" var="order">
                        <div>
                            <div class="profile-order-title">
                                <div class="profile-order-title-left">
                                    <div><h3>Order: <a href="#">${order.getOrderID()}</a></h3></div>
                                    <div>Order creation date: ${order.getOrderDate()}</div>
                                </div>
                                <div class="profile-order-title-right">
                                    <c:if test="${order.getShippedDate() eq null}">
                                        <span>Pending</span>
                                    </c:if>
                                    <c:if test="${order.getShippedDate() ne null}">
                                        <span style="color: green;">Complete</span>
                                    </c:if>
                                </div>
                            </div>
                            <%
                                Order a = (Order)pageContext.getAttribute("order");
                                ArrayList<OrderDetail> listDetail = new OrderDAO().listOrderDetail(a);
                                pageContext.setAttribute("listDetail", listDetail);
                            %>
                            <c:forEach items="${listDetail}" var="detail">
                                <div class="profile-order-content">
                                    <div class="profile-order-content-col1">
                                        <a href="detail.html"><img src="../img/2.jpg" width="100%"/></a>
                                    </div>
                                    <div class="profile-order-content-col2">${detail.getProductName()}</div>
                                    <div class="profile-order-content-col3">Quantity: ${detail.getQuantity()}</div>
                                    <div class="profile-order-content-col4"><fmt:formatNumber value=
                                                      "${detail.getTotal()}" 
                                                      maxFractionDigits="2" />$</div>
                                </div>
                            </c:forEach>
                            <div style="text-align: right"><h3>Total price: ${order.getTotalPrice()}$</h3></div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</div>

<%@include file="template/footer.jsp" %>