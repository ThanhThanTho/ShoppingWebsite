<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="template/headerAdmin.jsp" %>
<a href="<%=path%>/admin/dashboard"><li>Dashboard</li></a>
<a href="<%=path%>/admin/orders"><li>Orders</li></a>
<a href="<%=path%>/admin/products"><li>Products</li></a>
<a href="#"><li>Customers</li></a>
</ul>
</div>
<div id="content-right">
    <div class="path-admin">CREATE A NEW PRODUCT</b></div>
    <div class="content-main">
        <form action="manageProduct" method="post" id="content-main-product">
            <input type="hidden" name="create" value="${true}">
            <div class="content-main-1">
                <label>Product name (*):</label><br/>
                <input value="${productName}" type="text" name="txtProductName" id=""><br/>
                <c:if test="${invalidName ne null}">
                    <span class="msg-error">${invalidName}</span><br/>
                </c:if>
                <label>Unit price:</label><br/>
                <input value="${unitPrice}" type="text" name="txtUnitPrice" id=""><br/>
                <c:if test="${invalidUnitPrice ne null}">
                    <span class="msg-error">${invalidUnitPrice}</span><br/>
                </c:if>
                <label>Quantity per unit:</label><br/>
                <input value="${quantityPerUnit}" type="text" name="txtQuantityPerUnit" id=""><br/>
                <c:if test="${invalidQuantityPerUnit ne null}">
                    <span class="msg-error">${invalidQuantityPerUnit}</span><br/>
                </c:if>
                <label>Units in stock (*):</label><br/>
                <input value="${unitsInStock}" type="text" name="txtUnitsInStock" id=""><br/>
                <c:if test="${invalidUnitInStock ne null}">
                    <span class="msg-error">${invalidUnitInStock}</span><br/>
                </c:if>
            </div>
            <div class="content-main-1">
                <label>Category (*):</label><br/>
                <select name="ddlCategory">
                    <c:forEach items="${cateList}" var="cate">
                        <c:choose>
                            <c:when test="${cate.getCategoryID() eq pro.getCategoryID()}">
                                <option selected="true" value="${cate.getCategoryID()}">${cate.getCategoryName()}</option>
                            </c:when>
                            <c:otherwise>
                                <option value="${cate.getCategoryID()}">${cate.getCategoryName()}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
                <br/>
                <label>Reorder level:</label><br/>
                <input type="text" name="txtReorderLevel" id="" disabled><br/>
                <label>Units on order:</label><br/>
                <input type="text" name="txtUnitsOnOrder" id="" disabled><br/>
                <label>Discontinued:</label><br/>
                <input type="checkbox" name="chkDiscontinued" id=""><br/>
                <input type="submit" value="Save"/>
            </div>
        </form>
    </div>
</div>
<%@include file="template/footerAdmin.jsp" %>
