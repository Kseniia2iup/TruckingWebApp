<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %><html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Users List</title>
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
        <h2>List of Trucks</h2>
        <table class="table table-hover">
            <thead>
            <tr>
                <!--<th>Email</th>-->
                <th>LOGIN</th>
                <th>ROLE</th>
                <th width="100"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <!--<td>$//{user.email}</td>-->
                    <td>${user.login}</td>
                    <td>${user.role}</td>
                    <c:choose>
                        <c:when test="${user.driver!=null}"></c:when>
                        <c:otherwise>
                        <td><a href="<c:url value='/admin/delete-user-${user.login}' />" class="btn btn-danger custom-width">
                            delete</a></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    <div class="well">
        <a href="<c:url value='/admin/newUser' />" class="btn btn-success custom-width"> Add New User</a>
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