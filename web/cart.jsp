<%@include file="template/header.jsp" %>
<%
    String contextPath = request.getContextPath();
%>

<div id="cart">
    <div id="cart-title">
        <h3>SHOPPING CART</h3>
    </div>
    <div id="cart-content" style="overflow-x: scroll; height:400px;">
        <c:choose>
            <c:when test="${fn:length(cartListPro) == 0}">
                <p style="text-align: center;">You dont't have any product in your cart</p>
            </c:when>
            <c:otherwise>
                <c:set value="${0}" var="total"/>
                <c:forEach items="${cartListPro}" var="pro">
                    <div class="cart-item">
                        <div class="cart-item-infor">
                            <div class="cart-item-img">
                                <img src="<%=contextPath%>/img/1.jpg"/>
                            </div>
                            <div class="cart-item-name">
                                <a href="<%=contextPath%>/detail?id=${pro.getProductID()}">${pro.getProductName()}</a>
                            </div>
                            <div class="cart-item-price">
                                ${pro.getUnitPrice() * pro.getQuantity()-((pro.getUnitPrice() * pro.getQuantity())*pro.getDiscount()/100)}$
                            </div>
                            <div class="cart-item-button">
                                <a href="<%=contextPath%>/cart?remove=${pro.getProductID()}">Remove</a>
                            </div>
                        </div>
                        <div class="cart-item-function">
                            <a href="<%=contextPath%>/cart?subID=${pro.getProductID()}">-</a>  
                            <a href="<%=contextPath%>/cart?addID=${pro.getProductID()}">+</a>
                            <input type="text" value="${pro.getQuantity()}" disabled/>
                        </div>
                    </div>
                    <c:set value="${total + pro.getTotal()}" var="total"/>  
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
    <div id="cart-summary">
        <div id="cart-summary-content">Total amount: <span style="color:red">${total}$</span></div>
    </div>
    <form id="form1" method="post" action="order">
        <div id="customer-info">
            <div id="customer-info-content">
                <h3>CUSTOMER INFORMATION:</h3>
                <div id="customer-info-detail">
                    <div id="customer-info-left">
                        Company Name: <input name="companyName" value="${custInfor.getCompanyName()}" type="text" placeholder="Company name *"/><br/>
                        Contact Name: <input name="contactName" value="${custInfor.getContactName()}" type="text" placeholder="Contact name *"/><br/>
                    </div>
                    <div id="customer-info-right">
                        Contact Title: <input name="contactTitle" value="${custInfor.getContactTitle()}" type="text" placeholder="Contact title *"/><br/>
                        Address: <input name="address" value="${custInfor.getAddress()}" type="text" placeholder="Address *"/><br/>
                    </div>
                </div>
            </div>
        </div>
        <div id="customer-info">
            <div id="customer-info-content">
                <h3>PAYMENT METHODS: </h3>
                <div id="customer-info-payment">
                    <div>
                        <input value="cod" id="cod" type="radio" name="rbPaymentMethod" checked/>
                        <label for="cod">Payment C.O.D - Payment on delivery</label>
                    </div>
                    <div>
                        <input value="onl" id="onl" type="radio" name="rbPaymentMethod" />
                        <label for="onl">Payment via online payment gateway</label>
                    </div>
                </div>
            </div>
        </div>
        <c:if test="${fn:length(cartListPro) != 0}">
            <div id="cart-order">
                <input onclick="submitForm1()" type="button" value="ORDER"/>
            </div>
        </c:if>
        <c:if test="${notEnoughtInfor ne null}">
            <p style="color: red">${notEnoughtInfor}</p>
        </c:if>
            <c:if test="${noOnlPayment ne null}">
                <p style="color: red">${noOnlPayment}</p>
        </c:if>
        <c:if test="${success ne null}">
            <p style="color: green">${success}</p>
        </c:if>
    </form>
    <script>
        function submitForm1() {
            document.getElementById("form1").submit();
        }
    </script>
</div>

<%@include file="template/footer.jsp" %>
