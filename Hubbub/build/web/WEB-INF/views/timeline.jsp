<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Hubbub&trade; Timeline</title>
        <link rel="stylesheet" type="text/css" href="styles/hubbub.css"/>
    </head>
    <body>
        <div class="masthead">
            <img src="images/hubbub-logo.png"/>
        </div>
        <h2 class="flash">${flash}</h2>
        <h1>Welcome to Hubbub&trade;
            <c:if test="${not empty user}">, ${user.username}</c:if>
        </h1>
        <c:choose>
            <c:when test="${not empty user}">
                <a href="main?action=post">Post Yer Feelin's</a> |
                <a href="main?action=logout">Log out of Hubbub</a>
                <p>
                    <form method="GET" action="main">
                        <input type="hidden" name="action" value="search"/>
                        Search: <input type="text" name="search" required/>
                        <input type="submit" value="Find Terms"/>
                    </form>
                </p>
            </c:when>
            <c:otherwise>
                <a href="main?action=login">
                    Hubbub&trade; User Login
                </a> |
                <a href="main?action=join">
                    Join Hubbub&trade; Now
                </a>
            </c:otherwise>
        </c:choose>
        <h2>Here's what our users are hackin' on:</h2>
        <ul>
            <c:forEach var="post" items="${posts}">
                <li class="post">
                    Posted by
                    <span class="author" title="${post.author.joined}">
                        <c:choose>
                            <c:when test="${not empty user}">
                                <a href="main?action=ticker&for=${post.author.username}">
                                ${post.author.username}
                                </a>
                            </c:when>
                            <c:otherwise>
                                <strong>${post.author.username}</strong>
                            </c:otherwise>
                        </c:choose>
                    </span>
                    <span class="postdate">${post.posted}</span>
                    <p class="content">${post.content}</p>
                </li>
            </c:forEach>
        </ul>
    </body>
</html>
