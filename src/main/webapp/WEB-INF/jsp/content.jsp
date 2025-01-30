<%--
  Created by IntelliJ IDEA.
  User: alexsey
  Date: 30/01/2025
  Time: 11:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Content</title>
</head>
<body>
<div>
  <span>Content english:</span>
  <p>Size: ${requestScope.flights.size()}</p>
  <p>description: ${requestScope.flights.get(0).description()}</p>
  <p>id: ${requestScope.flights[1].id()}</p>
  <p>JSESSIONID: ${cookie.get("JSESSIONID")}</p>
  <p>PARAM id: ${param.id}</p>
  <p>HEADER id: ${header["cookie"]}</p>
  <p>NOT EMPTY: ${not empty requestScope.flights}</p>
</div>
</body>
</html>
