<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Driver Registration Form</title>
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
<div class="container">
    <div class="success">
        <h2>Driver Registration Form</h2>

        <form:form method="POST" modelAttribute="driver">
            <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-3 control-lable" for="name">First Name</label>
                    <div class="col-md-7">
                        <form:input type="text" path="name" id="name" class="form-control input-sm"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-3 control-lable" for="surname">Last Name</label>
                    <div class="col-md-7">
                        <form:input type="text" path="surname" id="surname" class="form-control input-sm"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-3 control-lable" for="city">City</label>
                    <div class="col-md-7">
                        <form:select id ="city" path="city" items="${cities}" class="form-control input-sm"
                                     itemLabel="name" itemValue="id"/>
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
        <a href="<c:url value='/manager/listDrivers' />" class="btn btn-success custom-width">List of All Drivers</a>
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