<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %><html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>List Of Cargoes</title>
    <meta charset="utf-8" />

    <!--[if lte IE 8]><script src="/static/js/ie/html5shiv.js"></script><![endif]-->
    <link href="<c:url value="/static/css/main.css"  />" rel="stylesheet" />
</head>
<body>
<div id="wrapper">
    <!-- Header -->
    <header id="header">
        <h1><a href="<c:url value="/manager/listOrders"/>">LogiWeb</a></h1>
        <nav class="links">
            <ul>
                <li><a href="<c:url value="/manager/listDrivers"/> ">Drivers</a></li>
                <li><a href="<c:url value="/manager/listTrucks"/>">Trucks</a></li>
                <li><a href="<c:url value="/manager/listOrders"/>">Orders</a></li>
                <li><a href="<c:url value="/logout" />">Logout</a></li>
            </ul>
        </nav>
    </header>

    <!-- Main -->
    <div id="main">
        <!-- Post -->
        <article class="post">
            <header>
                <div class="title">
                <h2>List of Cargoes <br/> For Order ${order.id}</h2>
                </div>
                    <div class="meta">
                        <time class="published" datetime="">${date}</time>
                        <a href="<c:url value="/manager/listOrders"/>" class="author"><span class="name">${user}</span></a>
                    </div>
            </header>
            <table class="table table-hover">
            <tr>
                <td>NAME</td><td>WEIGHT</td><td>STATUS</td>
            </tr>
            <c:forEach items="${cargoes}" var="cargo">
                <tr>
                    <td>${cargo.name}</td>
                    <td>${cargo.weight}</td>
                    <td>${cargo.delivery_status}</td>
                </tr>
            </c:forEach>
        </table>
        <br/>
        <c:choose>
            <c:when test="${order.orderStatus=='IN_PROCESS' && truck!=null}">
        <br/>
        <h2>TRUCK INFO</h2>
        <table class="table table-hover">
            <tr>
                <td>REG NUMBER</td><td>CURRENT CITY</td>
            </tr>
            <tr>
                <td>${truck.regNumber}</td>
                <td>${truck.city.name}</td>
            </tr>
        </table>
                </section>
                <section>
            </c:when>
            <c:otherwise/>
        </c:choose>
        <c:choose>
            <c:when test="${order.orderStatus=='CREATED' || order.orderStatus=='INTERRUPTED'}">
                <c:choose>
                    <c:when test="${order.truck==null}">
                        <c:choose>
                            <c:when test="${order.orderStatus!='INTERRUPTED'}">
                        <a href="<c:url value='/manager/${order.id}/newCargo' />" class="btn btn-success custom-width">
                            Add Cargo</a>
                            </c:when>
                        <c:otherwise/>
                        </c:choose>
                        <c:choose>
                            <c:when test="${cargoes.size()!=0}">
                                <br/>
                                <br/>
                                <a href="<c:url value='/manager/${order.id}/setOrderTruck' />" class="btn btn-success custom-width">
                                    Add Truck</a>
                            </c:when>
                            <c:otherwise>
                                <br/>
                                <br/>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/manager/${order.id}/setOrderDrivers' />" class="btn btn-success custom-width">
                            Add Drivers</a>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise/>
        </c:choose>
        <br/>
        <br/>
        <a href="<c:url value="/manager/listOrders"/> " class="btn btn-success custom-width">
            All Orders</a>
        </article>
    </div>

    <!-- Sidebar -->
    <section id="sidebar">

        <!-- Intro -->
        <section id="intro">
            <a href="<c:url value="/manager/listOrders"/>" class="logo">
                <img src="<c:url value="/static/images/logo.jpg"/>" alt="" /></a>
            <header>
                <h2>LogiWeb</h2>
                <p>Would you like to create <a href="<c:url value='/manager/newOrder' />">A NEW ORDER</a>
                    <br/> Or see <a href="<c:url value="/manager/listOrders"/> ">
                        All Orders</a>?</p>
            </header>
        </section>
    </section>
</div>
</body>
</html>