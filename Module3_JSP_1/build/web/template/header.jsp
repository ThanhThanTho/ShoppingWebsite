<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList" %>
<%@page import="dal.Product" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<script>
    document.addEventListener("DOMContentLoaded", function (event) {
        var scrollpos = localStorage.getItem('scrollpos');
        if (scrollpos)
            window.scrollTo(0, scrollpos);
    });
    window.onbeforeunload = function (e) {
        localStorage.setItem('scrollpos', window.scrollY);
    };
</script>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Index</title>
        <%
            String path = request.getContextPath();
        %>
        <link href="<%=path%>/css/style.css" rel="stylesheet"/>
    </head>
    <body >
        <div id="container">
            <div id="header">
                <div id="logo">
                    <a href="<%=path%>/index"><img src="<%=path%>/img/logo.png"/></a>
                </div>
                <div id="banner">
                    <ul>
                        <form action="${from ne "account"?"search":"../search"}" method="get">
                            <input type="text" value="${searchKey}" name="searchKey">
                            <input name="cateId" type="hidden" value="${param.cateId}">
                            <input name="page" type="hidden" value="${param.page}"> 
                            <input type="submit" value="Search">
                        </form>
                        <c:if test="${sessionScope.AdmSession ne null}">
                            <li><a href="<%=path%>/admin/dashboard">Manage(for admin)</a></li>
                        </c:if>
                        <li><a href="<%=path%>/cart">Cart: ${fn:length(cartList)}</a></li>
                            <%
                            if(session.getAttribute("AccSession")==null && session.getAttribute("AdmSession")==null){
                            %>
                        <li><a href="<%=path%>/account/signin">SignIn</a></li>
                        <li><a href="<%=path%>/account/signup">SignUp</a></li>
                            <%
                                }else{
                            %>
                        <li><a href="<%=path%>/account/profile">Profile</a></li>
                        <li><a href="<%=path%>/account/signin">SignOut</a></li>
                            <%
                                }
                            %>

                    </ul>
                </div>
            </div>
            <div id="content">