<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>AccessDenied page</title>
    <link href="<c:url value="/static/css/bootstrap.css" />" rel="stylesheet" />
    <link href="<c:url value="/static/css/app.css" />" rel="stylesheet" />
</head>
<body>
<div class="page-header"></div>
Dear <strong>${user}</strong>, You are not authorized to access this page.
<br/>
<a href="<c:url value="/index" />">Go to home</a> OR <a href="<c:url value="/logout" />">Logout</a>
</body>
</html>