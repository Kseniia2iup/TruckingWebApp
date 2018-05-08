<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %><html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>All trucks</title>

    <link href="<c:url value="/pages/css/bootstrap.css" />" rel="stylesheet" />
    <link href="<c:url value="/pages/css/app.css" />" rel="stylesheet" />
    <style>
        tr:first-child{
            font-weight: bold;
            background-color: #C6C9C4;
        }
    </style>

</head>


<body>
<div class="page-header"></div>
<div class="container">
    <div class="success">
<h2>List of Trucks</h2>
<table>
    <tr>
        <td>TRUCK CONDITION</td><td>SHIFT PERIOD</td><td>CAPACITY (TON)</td><td>CITY</td><td>REG NUMBER</td><td></td>
    </tr>
    <c:forEach items="${trucks}" var="truck">
        <tr>
            <td>${truck.condition}</td>
            <td>${truck.shiftPeriod}</td>
            <td>${truck.capacityTon}</td>
            <td>${truck.city.name}</td>
            <td><a href="<c:url value='/edit-${truck.regNumber}-truck' />">${truck.regNumber}</a></td>
            <td><a href="<c:url value='/delete-${truck.regNumber}-truck' />">delete</a></td>
        </tr>
    </c:forEach>
</table>
<br/>
<a href="<c:url value='//newTruck' />">Add New Truck</a>
</div>
</div>
</body>
</html>