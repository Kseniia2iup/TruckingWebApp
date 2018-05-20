<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %><html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>All Cargoes Of Order</title>

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
        <h2>List of Cargoes For Order ${order.id}</h2>
        <table class="table table-hover">
            <tr>
                <td>NAME</td><td>WEIGHT</td><td>STATUS</td>
            </tr>
            <c:forEach items="${cargoes}" var="cargo">
                <tr>
                    <td>${cargo.name}</td>
                    <td>${cargo.weight}</td>
                    <td>${cargo.delivery_status}</td>
                </tr>
            </c:forEach>
        </table>
        <br/>
        <c:choose>
            <c:when test="${order.orderStatus=='CREATED'}">
                <c:choose>
                    <c:when test="${order.truck==null}">
                        <a href="<c:url value='/manager/${order.id}/newCargo' />" class="btn btn-success custom-width">
                            Add Cargo</a>
                        <c:choose>
                            <c:when test="${cargoes.size()!=0}">
                                <br/>
                                <br/>
                                <a href="<c:url value='/manager/${order.id}/setOrderTruck' />" class="btn btn-success custom-width">
                                    Add Truck</a>
                            </c:when>
                            <c:otherwise>
                                <br/>
                                <br/>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/manager/${order.id}/setOrderDrivers' />" class="btn btn-success custom-width">
                            Add Drivers</a>
                    </c:otherwise>
                </c:choose>
                <br/>
                <br/>
                <a href="<c:url value="/manager/${order.id}/cancel"/> " class="btn btn-danger custom-width">
                    Delete Order</a>
            </c:when>
            <c:otherwise/>
        </c:choose>
        <br/>
        <br/>
        <a href="<c:url value="/manager/listOrders"/> " class="btn btn-success custom-width">
            All Orders</a>
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