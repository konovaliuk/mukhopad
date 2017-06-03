<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="messages" var="messages"/>
<html>
<head>
    <head>
        <meta name="viewport" content="initial-scale=1, maximum-scale=1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <title>${sessionScope.user.username}</title>
    </head>
<body>
<div class="container">
    <legend><fmt:message bundle="${messages}" key="MY_PAGE"/></legend>
    <div class="well">
    <table class="table table-striped table-hover table-bordered">
        <tbody>
        <tr>
            <th><span class="pull-right"><fmt:message bundle="${messages}" key="USER_LOGIN"/></span></th>
            <th colspan="3">${sessionScope.user.username}</th>
        </tr>
        <tr>
            <th><span class="pull-right"><fmt:message bundle="${messages}" key="USER_EMAIL"/></span></th>
            <th colspan="3">${sessionScope.user.email}</th>
        </tr>
        <tr>
            <th><span class="pull-right"><fmt:message bundle="${messages}" key="USER_GROUP"/></span></th>
            <th colspan="3">${sessionScope.user.group.groupName}</th>
        </tr>
        <tr>
            <th><span class="pull-right">Session ID</span></th>
            <th colspan="3">${pageContext.session.id}</th>
        </tr>
        </tbody>
    </table>
    </div>
    <div class="well">
    <table class="table table-striped table-hover table-bordered">
        <thead>
        <tr>
            <th><fmt:message bundle="${messages}" key="EDITION_NAME"/></th>
            <th><fmt:message bundle="${messages}" key="EXPIRATION_DATE"/></th>
        </tr>
        </thead>
        <c:choose>
            <c:when test="${not empty sessionScope.subscriptions}">
                <c:forEach items="${sessionScope.subscriptions}" var="item">
                    <tbody>
                    <tr>
                        <td>${item.edition.editionName}</td>
                        <td>${item.expirationDate}</td>
                    </tr>
                    </tbody>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <fmt:message bundle="${messages}" key="NO_SUBSCRIPTIONS"/>
                </tr>
            </c:otherwise>
        </c:choose>

    </table>
</div>
</div>
</body>
</html>
