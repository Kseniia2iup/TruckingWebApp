<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Truck Registration Form</title>
    <link href="<c:url value="/static/css/bootstrap.css" />" rel="stylesheet" />
    <link href="<c:url value="/static/css/app.css" />" rel="stylesheet" />

    <style>

        .error {
            color: #ff0000;
        }
    </style>

</head>

<body>
<div class="page-header"></div>
<div class="container">
    <div class="success">
<h2>Add Truck Form</h2>

<form:form method="POST" modelAttribute="truck" >
    <form:input type="hidden" path="id" id="id"/>
    <table>
        <tr>
            <td><label for="reg_number">Reg Number: </label> </td>
            <td><form:input path="regNumber" id="reg_number"/></td>
            <td><form:errors path="regNumber" cssClass="error"/></td>
        </tr>

        <tr>
            <td><label for="shift_period">Shift Period: </label> </td>
            <td><form:input path="shiftPeriod" id="shift_period"/></td>
            <td><form:errors path="shiftPeriod" cssClass="error"/></td>
        </tr>

        <tr>
            <td><label for="capacity_ton">Capacity(ton): </label> </td>
            <td><form:input path="capacityTon" id="capacity_ton"/></td>
            <td><form:errors path="capacityTon" cssClass="error"/></td>
        </tr>

        <tr>
            <td><label for="condition">Condition: </label> </td>
            <td><form:input path="condition" id="condition"/></td>
            <td><form:errors path="condition" cssClass="error"/></td>
        </tr>

        <tr>
            <td colspan="3">
                <c:choose>
                    <c:when test="${edit}">
                        <input type="submit" value="Update"/>
                    </c:when>
                    <c:otherwise>
                        <input type="submit" value="Register"/>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </table>
</form:form>
<br/>
<br/>
Go back to <a href="<c:url value='/listTrucks' />">List of All Trucks</a>
</div>
</div>
</body>
</html>