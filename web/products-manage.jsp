<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="template/headerAdmin.jsp" %>
<a href="<%=path%>/admin/dashboard"><li>Dashboard</li></a>
<a href="<%=path%>/admin/orders"><li>Orders</li></a>
<a href="<%=path%>/admin/products"><li><mark>Products</mark></li></a>
<a href="#"><li>Customers</li></a>
</ul>
</div>
<div id="content-right">
    <div class="path-admin">PRODUCTS LIST</b></div>
    <div class="content-main">
        <div id="content-main-dashboard">
            <c:if test="${sessionScope.deleteFail ne null}">
                <div>
                    <span class="msg-error">${deleteFail}</span><br/>
                </div>
            </c:if>
            
            <form id="product-title-header" action="products" method="get">
                <div id="product-title-1" style="width: 25%;">
                    <b>Filter by Catetory:</b> 
                    <select name="ddlCategory">
                        <option value="${all}">--- All ---</option>
                        <c:forEach items="${cateList}" var="cate">
                            <c:choose>
                                <c:when test="${cate.getCategoryID() eq param.ddlCategory}">
                                    <option selected="true" value="${cate.getCategoryID()}">${cate.getCategoryName()}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${cate.getCategoryID()}">${cate.getCategoryName()}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
                <div id="product-title-2" style="width: 55%;">
                    <input value="${param.txtSearch}" type="text" name="txtSearch" placeholder="Enter product name to search"/>
                    <input type="submit" value="Filter">
                </div>
                <div id="product-title-3" style="width: 20%;">
                    <a href="<%=path%>/admin/create">Create a new Product</a>
                    <label for="upload-file">Import .xls or .xlsx file</label>
                    <input type="file" name="file" id="upload-file">
                </div>
            </form>
            
            <div id="order-table-admin">
                <table id="orders">
                    <tr>
                        <th>ProductID</th>
                        <th>ProductName</th>
                        <th>UnitPrice</th>
                        <th>Unit</th>
                        <th>UnitsInStock</th>
                        <th>Category</th>
                        <th>Discontinued</th>
                        <th></th>
                    </tr>
                    <c:forEach items="${proList}" var="product">
                        <tr>
                            <td><a href="order-detail.html?id=5">${product.getProductID()}</a></td>
                            <td>${product.getProductName()}</td>
                            <td>${product.getUnitPrice()}</td>
                            <td>${product.getQuantityPerUnit()}</td>
                            <td>${product.getUnitsInStock()}</td>
                            <td>${product.getCategoryName()}</td>
                            <td>${product.isDiscontinued()}</td>
                            <td>
                                <a href="update?id=${product.getProductID()}">Edit</a> &nbsp; | &nbsp; 
                                <a onclick='return confirm("You sure want to delete this product?");' href="manageProduct?delete=${product.getProductID()}">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <div id="paging">
                <div class="pagination">
                    <div class="pagination" style="display: block; margin-left: 10px">
                        <c:forEach begin="${1}" end="${num}" var="i">
                            <a class="${i==page?"active":""}" href="products?page=${i}&txtSearch=${param.txtSearch}&ddlCategory=${param.ddlCategory}">${i}</a>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="template/footerAdmin.jsp" %>