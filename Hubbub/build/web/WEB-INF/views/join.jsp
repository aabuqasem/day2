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
            Choose a username: <input type="text" name="username" required value="${user.username}"/><br/>
            <c:if test="${not empty userErrors.username}">
                <ul>
                <c:forEach var="error" items="${userErrors.username}">
                    <li><font color="red">${error.offense}</font></li>
                </c:forEach>
                </ul>
            </c:if>            
            Pick a strong password: <input type="password" name="password1" required/><br/>
            <c:if test="${not empty userErrors.password}">
                <ul>
                <c:forEach var="error" items="${userErrors.password}">
                    <li class="flash">${error.offense}</li>
                </c:forEach>
                </ul>
            </c:if>
            Repeat that password: <input type="password" name="password2" required/><br/>
            <p>The following fields represent your Hubbub&trade; Profile.
                They are all optional at this time and may be updated later.</p>
            Your first name: <input type="text" name="firstName" value="${profile.firstName}"/><br/>
            <c:if test="${not empty profileErrors.firstName}">
                <ul>
                    <c:forEach var="error" items="${profileErrors.firstName}">
                        <li class="flash">${error.offense}</li>
                    </c:forEach>
                </ul>
            </c:if>
            Your last name: <input type="text" name="lastName" value="${profile.lastName}"/><br/>
            <c:if test="${not empty profileErrors.lastName}">
                <ul>
                    <c:forEach var="error" items="${profileErrors.lastName}">
                        <li class="flash">${error.offense}</li>
                    </c:forEach>
                </ul>
            </c:if>            
            Your email address: <input type="type" name="email" value="${profile.email}"/><br/>
            <c:if test="${not empty profileErrors.email}">
                <ul>
                    <c:forEach var="error" items="${profileErrors.email}">
                        <li class="flash">${error.offense}</li>
                    </c:forEach>
                </ul>
            </c:if>  
            A short biography: 
            <textarea rows="10" cols="50" name="biography">${profile.biography}</textarea><br/>
            <c:if test="${not empty profileErrors.biography}">
                <ul>
                    <c:forEach var="error" items="${profileErrors.biography}">
                        <li class="flash">${error.offense}</li>
                    </c:forEach>
                </ul>
            </c:if> 
            <input type="submit" value="Sign me up!"/>
        </form>
    </body>
</html>
