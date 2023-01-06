<!DOCTYPE html>
<html lang="en">
    <head>
        <%
                String path = request.getContextPath();
        %>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Index</title>
        <link href="../css/style.css" rel="stylesheet" type="text/css"/>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>
        <style>
            mark{
                background-color: sienna;
                color: white;
            }
        </style>
    </head>
    <body>
        <div id="container">
            <div id="header">
                <div id="logo-admin">
                    Ecommerce Admin
                </div>
                <div id="banner-admin">
                    <ul>
                        <li><a href="<%=path%>/index">Home</a></li>
                        <li><a href="<%=path%>/account/signin">SignOut</a></li>
                    </ul>
                </div>
            </div>
            <div id="content">
                <div id="content-left">
                    <ul>

