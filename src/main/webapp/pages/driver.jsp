<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Driver page</title>
    <link href="<c:url value="/static/css/bootstrap.css" />" rel="stylesheet" />
    <link href="<c:url value="/static/css/app.css" />" rel="stylesheet" />
    <link href="<c:url value="/static/css/menu.css" />" rel="stylesheet" />
</head>
<body>
    <span style="font-size:30px;cursor:pointer" onclick="openNav()">&#9776;</span>

<div id="mySidenav" class="sidenav">
    <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
    <ul>
        <li><h4 style="color: #2b669a; text-align: center"><b>${user}</b></h4></li>
        <br/>
        <li><a href="<c:url value="/driver"/> ">Home</a></li>
        <li><a href="<c:url value="/logout" />">Logout</a></li>
    </ul>
</div>
<div class="container">
    <div class="success">
        <br/>
        <h2>INFO</h2>
        <table class="table table-hover">
            <tr>
                <td>PERSONAL NUMBER</td><td>TRUCK</td><td></td><td>CURRENT CITY</td><td>ORDER ID</td><td>CURRENT STATUS</td>
            </tr>
            <tr>
                <td>${driver.id}</td>
                <c:choose>
                    <c:when test="${order!=null&&truck!=null}">
                        <td>${truck.regNumber}</td>
                        <td><a href="<c:url value='/driver/${driver.id}/truckIsBroken' />" class="btn btn-danger custom-width">
                            Mark As Broken</a></td>
                        <td>${truck.city.name}</td>
                        <td>${order.id}</td>
                        <td><a href="<c:url value='/driver/${driver.id}/setStatus' />" class="btn btn-success custom-width">
                            ${driver.status}</a></td>
                    </c:when>
                    <c:otherwise>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td>${driver.status}</td>
                    </c:otherwise>
                </c:choose>
            </tr>
        </table>
        <br/>
        <c:choose>
            <c:when test="${drivers.size()!=0}">
        <h3>CO-WORKERS</h3>
        <table class="table table-hover">
            <tr>
                <td>PERSONAL NUMBER</td><td>NAME</td><td>SURNAME</td>
            </tr>
            <c:forEach items="${drivers}" var="coworker">
            <tr>
                <td>${coworker.id}</td>
                <td>${coworker.name}</td>
                <td>${coworker.surname}</td>
            </tr>
            </c:forEach>
        </table>
        <br/>
            </c:when>
            <c:otherwise/>
        </c:choose>
        <c:choose>
            <c:when test="${waypoints.size()!=0}">
        <h3>WAYPOINTS</h3>
        <table class="table table-hover">
            <tr>
                <td>CARGO</td><td>DEPARTURE CITY</td><td></td><td>DESTINATION CITY</td><td></td>
            </tr>
            <c:forEach items="${waypoints}" var="waypoint">
                <tr>
                    <td>${waypoint.cargo.name}</td>
                    <td>${waypoint.cityDep.name}</td>
                    <c:choose>
                        <c:when test="${waypoint.cargo.delivery_status=='PREPARED'}">
                            <td><a href="<c:url value='/driver/${waypoint.cargo.id}/loaded' />" class="btn btn-success custom-width">
                                    SHIPPED</a></td>
                        </c:when>
                        <c:otherwise>
                            <td>SHIPPED</td>
                        </c:otherwise>
                    </c:choose>
                    <td>${waypoint.cityDest.name}</td>
                    <c:choose>
                    <c:when test="${waypoint.cargo.delivery_status=='SHIPPED'}">
                    <td><a href="<c:url value='/driver/${waypoint.cargo.id}/unloaded' />" class="btn btn-success custom-width">
                        DELIVERED</a></td>
                    </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${waypoint.cargo.delivery_status=='PREPARED'}">
                                </c:when>
                                <c:otherwise>
                                    <td>DELIVERED</td>
                                </c:otherwise>
                            </c:choose>

                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
        </table>
            </c:when>
            <c:otherwise/>
        </c:choose>
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