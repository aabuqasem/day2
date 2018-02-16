<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Hubbub&trade; Profile for ${param.for}</title>
        <link rel="stylesheet" type="text/css" href="styles/hubbub.css"/>
    </head>
    <body>
        <div class="masthead">
            <img src="images/hubbub-logo.png"/>
        </div>        
        <h1>Check the Deets for ${param.for}</h1>
        <h2 class="flash">${flash}</h2>
        <h2 class="success">${success}</h2>
        <form method="POST" action="main">
            <c:set value="${param.for ne user.username ? 'disabled' : ''}" var="disabled"/>
            <input type="hidden" name="action" value="profile"/>
            <input type="hidden" name="for" value="${param.for}"/>
            <p>The following fields represent your Hubbub&trade; Profile.
                They are all optional and may be updated at any time.</p>
            Your first name: <input type="text" name="firstName" value="${profile.firstName}" ${disabled}/><br/>
            <c:if test="${not empty profileErrors.firstName}">
                <ul>
                    <c:forEach var="error" items="${profileErrors.firstName}">
                        <li class="flash">${error.offense}</li>
                    </c:forEach>
                </ul>
            </c:if>
            Your last name: <input type="text" name="lastName" value="${profile.lastName}" ${disabled}/><br/>
            <c:if test="${not empty profileErrors.lastName}">
                <ul>
                    <c:forEach var="error" items="${profileErrors.lastName}">
                        <li class="flash">${error.offense}</li>
                    </c:forEach>
                </ul>
            </c:if>            
            Your email address: <input type="type" name="email" value="${profile.email}" ${disabled}/><br/>
            <c:if test="${not empty profileErrors.email}">
                <ul>
                    <c:forEach var="error" items="${profileErrors.email}">
                        <li class="flash">${error.offense}</li>
                    </c:forEach>
                </ul>
            </c:if>  
            A short biography: 
            <textarea rows="10" cols="50" name="biography" ${disabled}>${profile.biography}</textarea><br/>
            <c:if test="${not empty profileErrors.biography}">
                <ul>
                    <c:forEach var="error" items="${profileErrors.biography}">
                        <li class="flash">${error.offense}</li>
                    </c:forEach>
                </ul>
            </c:if>
            <c:if test="${disabled ne 'disabled'}">
            <input type="submit" value="Save Changes"/>
            </c:if>
        </form>
    </body>
</html>
