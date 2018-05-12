<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>List Of Drivers</title>

    <!-- Bootstrap core CSS -->
    <link href="<c:url value="/static/css/bootstrap.css" />" rel="stylesheet" />
    <link href="<c:url value="/static/css/app.css" />" rel="stylesheet" />

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js" />
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js" />
    <![endif]-->
</head>
<body>
<div class="page-header"></div>
<div class="container">
    <div class="success">
        <h2>List of Drivers</h2>
        <table>
            <tr>
                <td>NAME</td><td>SURNAME</td><td>WORKED THIS MONTH</td><td>STATUS</td><td>CITY</td><td></td><td></td>
            </tr>
            <c:forEach items="${drivers}" var="driver">
                <tr>
                    <td>${driver.name}</td>
                    <td>${driver.surname}</td>
                    <td>${driver.workedThisMonth}</td>
                    <td>${driver.status}</td>
                    <td>${driver.city.name}</td>
                    <td></td>
                    <td><a href="<c:url value='/edit-${driver.id}-driver' />">edit</a></td>
                    <td><a href="<c:url value='/delete-${driver.id}-driver'/>">delete</a></td>
                </tr>
            </c:forEach>
        </table>
        <br/>
        <a href="<c:url value='/newDriver' />">Add New Driver</a>
    </div>
</div>
</body>
</body>
</html>