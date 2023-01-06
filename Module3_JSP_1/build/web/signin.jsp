<%@include file="template/header.jsp" %>

<div id="form">
    <%
    String contextPath = request.getContextPath();
    %>
    <div id="form-title">
        <span><a href="<%=contextPath%>/account/signup">SIGN UP</a></span>
        <span><a href="<%=contextPath%>/account/signin" style="color: red;">SIGN IN</a></span>
    </div>
    <div id="form-content">
        <div style="color:red; text-align: left">
            <%!String tempEmail = "", tempPass = "";%>
            <%
            if(request.getAttribute("txtEmail")!=null){
                tempEmail = (String)request.getParameter("txtEmail");
            }
            else tempEmail = "";
            if(request.getAttribute("txtPass")!=null){
                tempPass = (String)request.getParameter("txtPass");
            }
            else tempPass = "";
            if(request.getAttribute("msg")!=null)
                out.print(request.getAttribute("msg"));
            %>
        </div>
        <form action="signin" method="post">
            <label>Email<span style="color: red;">*</span></label><br/>
            <input name="txtEmail" type="text" value="<%=tempEmail%>"/><br/>
            <div style="color:red; text-align: left">
                <%
                if(request.getAttribute("msgEmail")!=null)
                    out.print(request.getAttribute("msgEmail"));
                %>
            </div>
            <label>Password<span style="color: red;">*</span></label><br/>
            <input name="txtPass" type="password" value="<%=tempPass%>"/><br/>
            <div style="color:red; text-align: left">
                <%
                if(request.getAttribute("msgPass")!=null)
                    out.print(request.getAttribute("msgPass"));
                %>
            </div>
            <div><a href="forgot">Forgot password?</a></div>
            <input type="submit" value="SIGN IN"/><br/>
            <input type="button" value="FACEBOOK LOGIN" style="background-color: #3b5998;"/><br/>
            <input type="button" value="ZALO LOGIN" style="background-color: #009dff;margin-bottom: 30px;"/>
        </form>
    </div>
</div>

<%@include file="template/footer.jsp" %>