<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: alexsey
  Date: 30/01/2025
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <form action="${pageContext.request.contextPath}/login" method="post">
    <label for="email">Email:
      <input type="text" name="email" id="email" value="${param.email}" required>
    </label><br/>
    <label for="password">Password:
      <input type="password" name="password" id="password" required>
    </label><br/>
    <button type="submit">Login</button>
    <a href="${pageContext.request.contextPath}/registration">
      <button type="button">Register</button>
    </a>

    <c:if test="${param.error != null }">
      <div style="color:red">
          <span>Email or password is not correct</span>
      </div>
    </c:if>

  </form>

  <c:if test="${not empty requestScope.errors}">
    <div style="color:red">
      <c:forEach var="error" items="${requestScope.errors}">
        <span>${error.message}</span>
        <br>
      </c:forEach>
    </div>
  </c:if>

</body>
</html>
