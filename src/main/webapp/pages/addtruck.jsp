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
</head>

<body>
<div class="page-header"></div>
<div class="container">
    <div class="success">
<h2>Add Truck Form</h2>

<form:form method="POST" modelAttribute="truck" >
    <div class="row">
        <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="reg_number">Registration number</label>
            <div class="col-md-7">
                <form:input type="text" path="regNumber" id="reg_number" placeholder="AA00000" class="form-control input-sm"/>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="shift_period">Shift Period</label>
            <div class="col-md-7">
                <form:input type="text" path="shiftPeriod" id="shift_period" class="form-control input-sm"/>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="capacity">Capacity</label>
            <div class="col-md-7">
                <form:input type="text" path="capacityTon" id="capacity" class="form-control input-sm"/>
            </div>
        </div>
    </div>


    <div class="row">
        <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="city">City</label>
            <div class="col-md-7">
                <form:select id ="city" path="city.id" items="${cities}" class="form-control input-sm"
                             itemLabel="name" itemValue="id"/>
            </div>
        </div>
    </div>


    <div class="row">
        <div class="form-group col-md-12">
            <label class="col-md-3 control-lable" for="condition">Condition</label>
            <div class="col-md-7">
                <form:select id ="status" path="condition" items="${truckConditions}" class="form-control input-sm"/>
            </div>
        </div>
    </div>


    <div class="row">
        <div class="form-actions floatRight">
            <c:choose>
                <c:when test="${edit}">
                    <input type="submit" class="btn btn-primary btn-sm" value="Update"/>
                </c:when>
                <c:otherwise>
                    <input type="submit" class="btn btn-primary btn-sm" value="Register"/>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

</form:form>
<br/>
<br/>
Go back to <a href="<c:url value='/listTrucks' />">List of All Trucks</a>
</div>
</div>
</body>
</html>