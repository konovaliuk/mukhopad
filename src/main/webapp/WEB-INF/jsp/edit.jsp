<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/custom_tag.tld" prefix="mytag" %>
<fmt:requestEncoding value="UTF-8" />
<fmt:setBundle basename="messages" var="messages"/>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title>
        <mytag:title>
        <c:choose>
            <c:when test="${requestScope.action eq 'insert'}">
                <fmt:message bundle="${messages}" key="ACTION_ADD"/>
            </c:when>
            <c:when test="${requestScope.action eq 'update'}">
                <fmt:message bundle="${messages}" key="ACTION_EDIT"/>
            </c:when>
        </c:choose>
        </mytag:title>
    </title>
    <c:if test="${empty sessionScope.user}">
        <c:redirect url="/publications"/>
    </c:if>
</head>
<body>
<div class="container">
    <div class="row">
        <form action="publications" method="POST" class="form-horizontal">
            <!-- Form Name -->
            <legend>
                <c:choose>
                    <c:when test="${requestScope.action eq 'insert'}">
                        <fmt:message bundle="${messages}" key="ACTION_ADD"/>
                    </c:when>
                    <c:when test="${requestScope.action eq 'update'}">
                        <fmt:message bundle="${messages}" key="ACTION_EDIT"/>
                    </c:when>
                </c:choose>
                <div class="btn-group pull-right" style="padding-bottom: 25px">
                </div>
            </legend>
            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label"><fmt:message bundle="${messages}" key="EDITION_NAME"/></label>
                <div class="col-md-4">
                    <input name="editionName" placeholder="<fmt:message bundle="${messages}" key="EDITION_NAME"/>"
                           class="form-control input-md"
                           required="" type="text"
                           value="<c:if test="${requestScope.action eq 'update'}">${requestScope.edition.editionName}</c:if>">
                    <span class="help-block"> </span>
                </div>
            </div>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label"><fmt:message bundle="${messages}" key="EDITION_PRICE"/></label>
                <div class="col-md-4">
                    <input name="editionPrice" placeholder="<fmt:message bundle="${messages}" key="EDITION_PRICE"/>"
                           class="form-control input-md" required="" type="number" step="0.01" min="0" max="50"
                           value="<c:if test="${requestScope.action eq 'update'}"> ${requestScope.edition.editionPrice}</c:if>">
                    <span class="help-block"> </span>
                </div>
            </div>

            <!-- Button -->
            <div class="form-group">
                <label class="col-md-4 control-label"> </label>
                <div class="col-md-4">
                    <c:choose>
                        <c:when test="${requestScope.action eq 'insert'}">
                            <input type="hidden" name="command" value="addPeriodical">
                        </c:when>
                        <c:when test="${requestScope.action eq 'update'}">
                            <input type="hidden" name="editionId" value="${requestScope.edition.editionId}">
                            <input type="hidden" name="command" value="updatePeriodical">
                        </c:when>
                    </c:choose>
                    <div class="btn-group pull-right">
                        <input type="submit" value="<fmt:message bundle="${messages}" key="ACTION_BACK"/>" class="btn btn-primary" form="back">
                    <input type="submit" class="btn btn-success" value="<fmt:message bundle="${messages}" key="ACTION_SUBMIT"/>">
                    </div>
                </div>
            </div>
        </form>
    </div>
    <form method="POST" action="publications" id="back">
        <input type="hidden" name="command" value="redirectMain">
    </form>
</div>
<script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</body>
</html>