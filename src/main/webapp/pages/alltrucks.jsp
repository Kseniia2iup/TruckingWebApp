<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %><html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>All trucks</title>

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
        <h2>List of Trucks</h2>
        <table class="table table-hover">
    <tr>
        <td>REG NUMBER</td><td>CONDITION</td><td>SHIFT PERIOD</td><td>CAPACITY (TON)</td><td>CITY</td><td></td><td></td>
    </tr>
    <c:forEach items="${trucks}" var="truck">
        <tr>
            <td>${truck.regNumber}</td>
            <td>${truck.condition}</td>
            <td>${truck.shiftPeriod}</td>
            <td>${truck.capacityTon}</td>
            <td>${truck.city.name}</td>
            <c:choose>
                <c:when test="${truck.order!=null}"></c:when>
                <c:otherwise>
                    <td><a href="<c:url value='/manager/edit-${truck.id}-truck' />" class="btn btn-success custom-width">
                        edit</a></td>
                    <td><a href="<c:url value='/manager/delete-${truck.regNumber}-truck' />" class="btn btn-danger custom-width">
                        delete</a></td>
                </c:otherwise>
            </c:choose>
        </tr>
    </c:forEach>
</table>
<br/>
<a href="<c:url value='/manager/newTruck' />" class="btn btn-success custom-width">Add New Truck</a>
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