<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Admin page</title>
    <link href="<c:url value="/pages/css/bootstrap.css" />" rel="stylesheet" />
    <link href="<c:url value="/pages/css/app.css" />" rel="stylesheet" />
</head>
<body>
<div class="page-header"></div>
<div class="success">
    Dear <strong>${user}</strong>, Welcome to Admin Page.
    <br/>
    Would you like to <a href="<c:url value='/newUser' />">Add Some Users</a> to keep yourself busy?
    <br/>
    <a href="<c:url value="/logout" />">Logout</a>
</div>
</body>
</html>