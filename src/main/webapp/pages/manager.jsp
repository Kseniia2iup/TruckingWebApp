<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Admin page</title>
    <link href="<c:url value="/static/css/bootstrap.css" />" rel="stylesheet" />
    <link href="<c:url value="/static/css/app.css" />" rel="stylesheet" />
    <link href="<c:url value="/static/css/menu.css" />" rel="stylesheet" />
</head>
<body>
    <span style="font-size:30px;cursor:pointer;float: left" onclick="openNav()">&#9776;</span>

<div id="mySidenav" class="sidenav">
    <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
    <ul>
        <li><a href="<c:url value="/manager/"/> ">Home</a></li>
        <li><a href="<c:url value="/manager/listDrivers"/> ">Drivers</a></li>
        <li><a href="<c:url value="/manager/listTrucks"/>">Trucks</a></li>
        <li><a href="<c:url value="/manager/listOrders"/>">Orders</a></li>
        <li><a href="<c:url value="/logout" />">Logout</a></li>
    </ul>
</div>
<div class="success">
    Dear <strong>${user}</strong>, Welcome to Manager Page.
</div>
<script>
    function openNav() {
        document.getElementById("mySidenav").style.width = "250px";
    }

    function closeNav() {
        document.getElementById("mySidenav").style.width = "0";
    }
</script>
</body>
</html>