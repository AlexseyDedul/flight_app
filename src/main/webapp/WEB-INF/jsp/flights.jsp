<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Flights</title>
</head>
<body>
    <%@ include file="header.jsp"%>
    <h1>Flights list:</h1>
    <ul>
        <c:if test="${not empty requestScope.flights}">
            <c:forEach var="flight" items="${requestScope.flights}">
                <li><a href='${pageContext.request.contextPath}/tickets?flightId=${flight.id()}'>${flight.description()}</a></li>
            </c:forEach>
        </c:if>
    </ul>
</body>
</html>
