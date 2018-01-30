<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Post to Hubbub&trade;</title>
        <link rel="stylesheet" type="text/css" href="styles/hubbub.css"/>
    </head>
    <body>
        <div class="masthead">
            <img src="images/hubbub-logo.png"/>
        </div>
        <h1>What are you hackin' on today?</h1>
        <c:if test="${not empty flash}">
        <h2 class="flash">${flash}</h2>
        </c:if>
        <form method="POST" action="main">
            <input type="hidden" name="action" value="post"/>
            <textarea cols="50" rows="4" name="content">
            <c:if test="${not empty errors.content}">
                ${errors.content[0].offendingValue}
            </c:if>    
            </textarea>
            <c:if test="${not empty errors.content}">
                <ul>
                <c:forEach var="error" items="${errors.content}">
                    <li><font color="red">${error.offense}</font></li>
                </c:forEach>
                </ul>
            </c:if>
            <input type="submit" value="Tell the world!"/>
        </form>
    </body>
</html>
