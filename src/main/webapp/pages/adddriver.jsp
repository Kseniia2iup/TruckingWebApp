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

        <form:form method="POST" modelAttribute="driver" >

        </form:form>
        <br/>
        <br/>
        Go back to <a href="<c:url value='/listDrivers' />">List of All Drivers</a>
    </div>
</div>
</body>
</html>