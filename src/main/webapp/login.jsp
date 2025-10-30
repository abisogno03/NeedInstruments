<%--@elvariable id="loginError" type="controller"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - NeedInstruments</title>
    <link href="./css/login.css"
        rel="stylesheet" type="text/css"/>

</head>
<body>
<div class="login-container">
    <a href="homepage"><img src="${pageContext.request.contextPath}/icons/logoBlack.png" alt="NeedInstruments Logo"></a>
    <h2>Accedi</h2>
    <c:if test="${not empty sessionScope.loginError}">
        <h4 style="color: darkred">${sessionScope.loginError}</h4>
        <c:remove var="loginError" scope="session" />
    </c:if>
    <form action="login-servlet" method="post">
        <label for="username">Nome utente</label>
        <input type="text" id="username" name="username" required>

        <label for="password">Password</label>
        <input type="password" id="password" name="password" required>

        <input type="submit" value="ACCEDI">
    </form>
    <div class="register-link">
        <p>Non sei ancora registrato? <a href="register.jsp">Registrati</a> </p>
    </div>
</div>
</body>
</html>
