<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>All orders</title>

    <link href="<c:url value="/static/css/bootstrap.css" />" rel="stylesheet" />
    <link href="<c:url value="/static/css/app.css" />" rel="stylesheet" />
    <link href="<c:url value="/static/css/menu.css" />" rel="stylesheet" />
    <style>
        tr:first-child{
            font-weight: bold;
            background-color: #C6C9C4;
        }
    </style>
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
<div class="container">
    <div class="success">
        <h2>List of Orders</h2>
        <table class="table table-hover">
            <tr>
                <td>UNIQUE NUMBER</td><td>STATUS</td><td>TRUCK</td><td></td><td></td>
            </tr>
            <c:forEach items="${orders}" var="order">
                <tr>
                    <td>${order.id}</td>
                    <td>${order.orderStatus}</td>
                    <td>${order.truck.regNumber}</td>
                    <c:choose>
                        <c:when test="${order.orderStatus=='CREATED'}">
                            <td><a href="<c:url value='/manager/${order.id}/listOrderCargoes' />" class="btn btn-success custom-width">
                                Complete</a></td>
                            <td><a href="<c:url value='/manager/${order.id}/cancel' />" class="btn btn-danger custom-width">
                                Delete</a></td>
                        </c:when>
                        <c:otherwise>
                            <td><a href="<c:url value='/manager/${order.id}/listOrderCargoes' />" class="btn btn-success custom-width">
                                See Info</a></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
        </table>
        <br/>
        <a href="<c:url value='/manager/newOrder' />" class="btn btn-success custom-width">Add New Order</a>
    </div>
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