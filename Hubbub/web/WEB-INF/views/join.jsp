<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Hubbub&trade; Assimilation</title>
        <link rel="stylesheet" type="text/css" href="styles/hubbub.css"/>
    </head>
    <body>
        <div class="masthead">
            <img src="images/hubbub-logo.png"/>
        </div>        
        <h1>Join our Community!</h1>
        <h2 class="flash">${flash}</h2>
        <form method="POST" action="main">
            <input type="hidden" name="action" value="join"/>
            Choose a username: <input type="text" name="username" required value="${errors.username[0].offendingValue}"/><br/>
            <c:if test="${not empty errors.username}">
                <ul>
                <c:forEach var="error" items="${errors.username}">
                    <li><font color="red">${error.offense}</font></li>
                </c:forEach>
                </ul>
            </c:if>            
            Pick a strong password: <input type="password" name="password1" required/><br/>
            <c:if test="${not empty errors.password}">
                <ul>
                <c:forEach var="error" items="${errors.password}">
                    <li><font color="red">${error.offense}</font></li>
                </c:forEach>
                </ul>
            </c:if>
            Repeat that password: <input type="password" name="password2" required/><br/>
            <input type="submit" value="Sign me up!"/>
        </form>
    </body>
</html>
