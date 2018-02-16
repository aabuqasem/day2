<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Hubbub&trade; Search Results</title>
        <link rel="stylesheet" type="text/css" href="styles/hubbub.css"/>
    </head>
    <body>
        <div class="masthead">
            <img src="images/hubbub-logo.png"/>
        </div>
        <a href="main?action=post">Post Yer Feelin's</a> |
        <a href="main?action=profile&for=${user.username}">View or Edit My Profile</a> |
        <a href="main?action=timeline">Go back to the Timeline</a> |
        <a href="main?action=logout">Log out of Hubbub</a>
        <p>
            <form method="GET" action="main">
                <input type="hidden" name="action" value="search"/>
                Search: <input type="text" name="search" required/>
                <input type="submit" value="Find Terms"/>
            </form>
        </p>
        <h2>
            Here are the posts matching your search: 
            <span class="terms">${search}</span>
        </h2>
        <ul>
            <c:forEach var="post" items="${results}">
                <li class="post">
                    Posted by
                    <span class="author" title="${post.author.joined}">
                    <a href="main?action=ticker&for=${post.author.username}">
                        ${post.author.username}
                    </a>
                    </span>
                    <span class="postdate">${post.posted}</span>
                    <p class="content">${post.content}</p>
                </li>
            </c:forEach>
        </ul>
    </body>
</html>
