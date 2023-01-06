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
<style>
    input[type="date"]::-webkit-datetime-edit, input[type="date"]::-webkit-inner-spin-button, input[type="date"]::-webkit-clear-button {
        color: #fff;
        position: relative;
    }

    input[type="date"]::-webkit-datetime-edit-year-field{
        position: absolute !important;
        border-left:1px solid #8c8c8c;
        padding: 2px;
        color:#000;
        left: 56px;
    }

    input[type="date"]::-webkit-datetime-edit-month-field{
        position: absolute !important;
        border-left:1px solid #8c8c8c;
        padding: 2px;
        color:#000;
        left: 26px;
    }


    input[type="date"]::-webkit-datetime-edit-day-field{
        position: absolute !important;
        color:#000;
        padding: 2px;
        left: 4px;

    }
</style>
<div id="content-right">
    <div class="path-admin">ORDERS LIST</div>
    <div class="content-main">
        <div id="content-main-dashboard">
            <div id="order-title">
                <b>Filter by Order date:</b>
                <form style="display: inline-block" name="form1" action="orders" method="get">
                    From <input value="${start}" type="date" name="txtStartOrderDate"/>
                    To <input value="${end}" type="date" name="txtEndOrderDate"/>
                    <input type="submit" value="Filter">
                </form>
                <input style="display: inline-block" onclick='location.href = "<%=path%>/admin/orders?clear=${true}"' type="submit" value="Clear">
                <c:if test="${invalid ne null}">
                    <p style="color: red">${invalid}</p>
                </c:if>
            </div>
            <div id="order-table">
                <table id="orders">
                    <tr>
                        <th>OrderID</th>
                        <th>OrderDate</th>
                        <th>RequiredDate</th>
                        <th>ShippedDate</th>
                        <th>Employee</th>
                        <th>Customer</th>
                        <th>Freight($)</th>
                        <th>Status</th>
                    </tr>
                    <c:choose>
                        <c:when test="${page > num || fn:length(cartList)==0}">
                            <p style="text-align: center">There are no product in this page</p>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${orderList}" var="order">
                                <tr>
                                    <td><a href="<%=path%>/admin/view_detail?id=${order.getOrderID()}">${order.getOrderID()}</a></td>
                                    <td><fmt:formatDate value="${order.getOrderDate()}" pattern="dd/MM/yyyy"></fmt:formatDate></td>
                                    <td><fmt:formatDate value="${order.getRequiredDate() ne null?order.getRequiredDate():null}" pattern="dd/MM/yyyy"></fmt:formatDate></td>
                                    <td><fmt:formatDate value="${order.getShippedDate() ne null?order.getShippedDate():null}" pattern="dd/MM/yyyy"></fmt:formatDate></td>
                                    <td>${order.getEmployeeName()}</td>
                                    <td>${order.getContactName()}</td>
                                    <td>${order.getFreight()}</td>
                                    <c:if test="${order.getRequiredDate() eq null && order.getShippedDate() eq null}">
                                        <td style="color: red;">Order canceled</td>
                                    </c:if>
                                    <c:if test="${order.getRequiredDate() eq null && order.getShippedDate() ne null}">
                                        <td style="color: green;">Completed</td>
                                    </c:if>
                                    <c:if test="${order.getRequiredDate() ne null && order.getShippedDate() eq null}"> 
                                        <td style="color: blue;">Pending | <a onclick="return confirm('You sure you want to cancel this order?')" href="orders?cancel=${order.getOrderID()}&page=${param.page}&txtStartOrderDate=${param.txtStartOrderDate}&txtEndOrderDate=${param.txtEndOrderDate}">Cancel</a></td>
                                    </c:if>
                                    <c:if test="${order.getRequiredDate() ne null && order.getShippedDate() ne null}">
                                        <td style="color: green;">Completed</td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>                    
                </table>
            </div>
            <div id="paging">
                <div class="pagination">
                    <c:forEach begin="${1}" end="${num}" var="i">
                        <a class="${i==page?"active":""}" href="orders?page=${i}&txtStartOrderDate=${param.txtStartOrderDate}&txtEndOrderDate=${param.txtEndOrderDate}">${i}</a>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="template/footerAdmin.jsp" %>