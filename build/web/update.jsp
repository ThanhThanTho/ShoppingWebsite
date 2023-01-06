<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="template/headerAdmin.jsp" %>
<a href="<%=path%>/admin/dashboard"><li>Dashboard</li></a>
<a href="<%=path%>/admin/orders"><li>Orders</li></a>
<a href="<%=path%>/admin/products"><li>Products</li></a>
<a href="#"><li>Customers</li></a>
</ul>
</div>
<div id="content-right">
    <div class="path-admin">UPDATE PRODUCT(wrong format or blank field will be remain)</div>
    <div class="content-main">
        <form action="manageProduct" method="post" id="content-main-product">
            <input type="hidden" name="update" value="${param.id}">
            <div class="content-main-1">
                <label>Product name (*):</label><br/>
                <input value="${pro.getProductName()}" type="text" name="txtProductName" id=""><br/>
                <span class="msg-error">Product name is required.</span><br/>
                <label>Unit price:</label><br/>
                <input value="${pro.getUnitPrice()}" type="text" name="txtUnitPrice" id=""><br/>
                <span class="msg-error">Unit price must be a positive number</span><br/>
                <label>Quantity per unit:</label><br/>
                <input value="${pro.getQuantityPerUnit()}" type="text" name="txtQuantityPerUnit" id=""><br/>
                <label>Units in stock (*):</label><br/>
                <input value="${pro.getUnitsInStock()}" type="text" name="txtUnitsInStock" id=""><br/>
                <span class="msg-error">Units in stock must be a positive number</span><br/>
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
                <input value="${pro.getReorderLevel()}" type="text" name="txtReorderLevel" id="" readonly><br/>
                <label>Units on order:</label><br/>
                <input value="${pro.getUnitsOnOrder()}" type="text" name="txtUnitsOnOrder" id="" readonly><br/>
                <label>Discontinued:</label><br/>
                <c:if test="${pro.isDiscontinued() eq true}">
                    <input value="${1}" checked="true" type="checkbox" name="chkDiscontinued" id=""><br/>
                </c:if>
                <c:if test="${pro.isDiscontinued() ne true}">
                    <input value="${0}" type="checkbox" name="chkDiscontinued" id=""><br/>
                </c:if>
                    <input onclick='return confirm("You sure you want to update this product?");' type="submit" value="Save"/>
            </div>
        </form>
    </div>
</div>
<%@include file="template/footerAdmin.jsp" %>