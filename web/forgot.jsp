<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.io.*,java.util.*,javax.mail.*"%>
<%@ page import="javax.mail.internet.*,javax.activation.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@include file="template/header.jsp" %>

<div id="form">
    <h3 style="padding: 20px;">Forgot your account password?</h3>
    <div style="padding: 0px 20px 10px;">
        Please enter the email address registered with us to create a new password. We will send an email to the email address provided and require verification before we can generate a new password
    </div>
    <div id="form-content">
        <form action="forgot" method="post">
            <label>Enter your registered email address<span style="color: red;">*</span></label><br/>
            <input name="email" type="text"/><br/>
            <c:if test="${emptyEmail ne null}">
                <span class="msg-error">${emptyEmail}</span><br/>
            </c:if>
            <c:if test="${notExisted ne null}">
                <span class="msg-error">${notExisted}</span><br/>
            </c:if>
            <c:if test="${emailSent ne null}">
                    <span style="color: green; text-align: left">${emailSent}</span><br/>
            </c:if>
            <c:if test="${emailSentFail ne null}">
                    <span style="color: red; text-align: left">${emailSentFail}</span><br/>
            </c:if>
            <br>
            <input type="submit" value="GET PASSWORD" style="margin-bottom: 30px;"/><br/>
        </form>
    </div>
</div>

<%@include file="template/footer.jsp" %>
