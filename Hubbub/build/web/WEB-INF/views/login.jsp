<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Hubbub&trade; Login</title>
        <link rel="stylesheet" type="text/css" href="styles/hubbub.css"/>
    </head>
    <body>
        <div class="masthead">
            <img src="images/hubbub-logo.png"/>
        </div>
        <h1>Log In and Start Hackin'!</h1>
        <h2 class="flash">${flash}</h2>
        <form method="POST" action="main">
            <input type="hidden" name="action" value="login"/>
            Username: <input type="text" name="username" required value="${errors.username[0].offendingValue}"/><br/>
            <c:if test="${not empty errors.username}">
                <ul>
                <c:forEach var="error" items="${errors.username}">
                    <li class="flash">${error.offense}</li>
                </c:forEach>
                </ul>
            </c:if>
            Password: <input type="password" name="password" required /><br/>
            <c:if test="${not empty errors.password}">
                <ul>
                <c:forEach var="error" items="${errors.password}">
                    <li class="flash">${error.offense}</li>
                </c:forEach>
            </c:if>
            <input type="submit" value="Get Hackin'"/>
        </form>
    </body>
</html>
