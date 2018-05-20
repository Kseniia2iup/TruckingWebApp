<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>User Registration Form</title>
	<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet" />
	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet" />
	<link href="<c:url value="/static/css/menu.css" />" rel="stylesheet" />
</head>
<body>
<span style="font-size:30px;cursor:pointer;float: left" onclick="openNav()">&#9776;</span>
<div id="mySidenav" class="sidenav">
	<a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
	<ul>
		<li><a href="<c:url value="/admin"/> ">Home</a></li>
		<li><a href="<c:url value='/admin/listUsers' />">Users</a></li>
		<li><a href="<c:url value='/admin/newUser' />">Register User</a></li>
		<li><a href="<c:url value="/logout" />">Logout</a></li>
	</ul>
</div>
<div class="generic-container">
	<div class="alert alert-success lead">
		${success}
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