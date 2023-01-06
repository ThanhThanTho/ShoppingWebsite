<%@include file="template/header.jsp" %>
<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="form">
    <%
        String contextPath = request.getContextPath();
    %>
    <div id="form-title">
        <span><a href="<%=contextPath%>/account/signup" style="color: red;">SIGN UP</a></span>
        <span><a href="<%=contextPath%>/account/signin">SIGN IN</a></span>
    </div>
    <div id="form-content">
        <form action="<%=contextPath%>/account/signup" method="post">
            <label>Company name<span style="color: red;">*</span></label><br/>
            <input value="${requestScope.txtCompanyName}" name="CompanyName" type="text"/><br/>
            <c:if test="${requestScope.companyName ne null}">
                <span class="msg-error">${requestScope.companyName}</span><br/>
            </c:if>            
            <label>Contact name<span style="color: red;">*</span></label><br/>
            <input value="${requestScope.txtContactName}" name="ContactName" type="text"/><br/>            
            <label>Contact title<span style="color: red;">*</span></label><br/>
            <input value="${requestScope.txtContactTitle}" name="ContactTitle" type="text"/><br/>            
            <label>Address<span style="color: red;">*</span></label><br/>
            <input value="${requestScope.txtAddress}" name="Address" type="text"/><br/>            
            <label>Email<span style="color: red;">*</span></label><br/>
            <input value="${requestScope.txtEmail}" name="Email" type="text"/><br/>
            <c:if test="${requestScope.email ne null}">
                <span class="msg-error">${requestScope.email}</span><br/>
            </c:if>            
            <label>Password<span style="color: red;">*</span></label><br/>
            <input value="${requestScope.txtPass}" name="Pass" type="password"/><br/>
            <c:if test="${requestScope.pass ne null}">
                <span class="msg-error">${requestScope.pass}</span><br/>
            </c:if>
            <label>Re-Password<span style="color: red;">*</span></label><br/>
            <input value="${requestScope.txtRePass}" name="RePass" type="password"/><br/>
            <c:if test="${requestScope.rePass ne null}">
                <span class="msg-error">${requestScope.rePass}</span><br/>
            </c:if>
            <div style="color:green; text-align: left">
                <%
                if(request.getAttribute("success")!=null)
                    out.print(request.getAttribute("success"));
                %>
            </div>
            <div style="color:red; text-align: left">
                <%
                if(request.getAttribute("existedAccount")!=null)
                    out.print(request.getAttribute("existedAccount"));
                %>
            </div>
            <div></div>
            <input type="submit" value="SIGN UP" style="margin-bottom: 30px;"/>
        </form>
    </div>
</div>

<%@include file="template/footer.jsp" %>
