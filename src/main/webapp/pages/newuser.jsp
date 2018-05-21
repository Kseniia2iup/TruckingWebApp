<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
<div class="container">
	<div class="success">
<div class="generic-container">
	<h2>User Registration Form</h2>
	<br />
	<form:form method="POST" modelAttribute="user" class="form-horizontal">
		<form:input type="hidden" path="id" id="id"/>

		<div class="row">
			<div class="form-group col-md-12">
				<label class="col-md-3 control-lable" for="login">LOGIN</label>
				<div class="col-md-7">
							<form:input type="text" path="login" id="login" placeholder="min 3 symbols" class="form-control input-sm" />
							<div class="has-error">
								<form:errors path="login" class="help-inline"/>
							</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="form-group col-md-12">
				<label class="col-md-3 control-lable" for="password">PASSWORD</label>
				<div class="col-md-7">
					<form:input type="password" path="password" id="password" placeholder="min 5 symbols" class="form-control input-sm"/>
					<div class="has-error">
						<form:errors path="password" class="help-inline"/>
					</div>
				</div>
			</div>
		</div>
<!--
		<div class="row">
			<div class="form-group col-md-12">
				<label class="col-md-3 control-lable" for="email">Email</label>
				<div class="col-md-7">
					<form//:input type="text" path="email" id="email" class="form-control input-sm" />
					<div class="has-error">
						<form//:errors path="email" class="help-inline"/>
					</div>
				</div>
			</div>
		</div>
-->
		<div class="row">
			<div class="form-group col-md-12">
				<label class="col-md-3 control-lable" for="role">ROLE</label>
				<div class="col-md-7">
					<form:select path="role" id="role" type="role" items="${adminRegistrationRoles}" class="form-control input-sm" />
					<div class="has-error">
						<form:errors path="role" class="help-inline"/>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="form-actions floatRight">
						<input type="submit" value="Register" class="btn btn-primary btn-sm"/> or <a
							href="<c:url value='/admin/listUsers' />" class="btn btn-success custom-width">Cancel</a>
			</div>
		</div>
	</form:form>
</div>
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