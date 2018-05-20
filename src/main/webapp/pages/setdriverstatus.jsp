<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Admin page</title>
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
        <h2>SET NEW STATUS</h2>

        <form:form method="POST" modelAttribute="driver">

            <div class="row">
                <div class="form-group col-md-12">
                    <label class="col-md-3 control-lable" for="status">Choose truck</label>
                    <div class="col-md-7">
                        <form:select path="status" items="${driverStatuses}" class="form-control input-sm"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-actions floatRight">
                    <input type="submit" class="btn btn-primary btn-sm" value="Set new Status"/>
                    <br/>
                    <br/>
                    <a href="<c:url value="/driver"/> " class="btn btn-danger custom-width">
                        Return</a>
                </div>
            </div>
        </form:form>
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