<%@ page import="by.alexdedul.jdbc.service.TicketService" %>
<%@ page import="by.alexdedul.jdbc.dto.TicketDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Tickets</title>
</head>
<body>
    <%@ include file="header.jsp"%>
    <h1>Purchased tickets:</h1>
    <ul>
        <c:forEach var="ticket" items="${requestScope.tickets}">
            <li>${ticket.seatNo()}</li>
        </c:forEach>
    </ul>
</body>
</html>
