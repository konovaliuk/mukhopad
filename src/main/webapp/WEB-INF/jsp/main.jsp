<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="messages" var="messages"/>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title><fmt:message bundle="messages" key="PERIODICALS_PAGE"/></title>
</head>
<body>

<div class="container">
    <div class="row">
        <legend><fmt:message bundle="messages" key="PERIODICALS_PAGE"/></legend>
        <c:if test="${not empty requestScope.error}">
            <div class="form-group alert alert-danger fade in">
                <a href="#" class="close" data-dismiss="alert">&times;</a>
                <p><strong>Ooops! </strong>${requestScope.error}</p>
            </div>
        </c:if>
        <c:if test="${not empty requestScope.success}">
            <div class="form-group alert alert-success fade in">
                <a href="#" class="close" data-dismiss="alert">&times;</a>
                <p><strong>Yaaay! </strong>${requestScope.success}</p>
            </div>
        </c:if>
        <div class="btn-toolbar">
            <button class="btn btn-primary">Add periodicals</button>
        </div>
        <div class="well">
            <table class="table table-striped table-hover table-bordered">
                <thead>
                <tr>
                    <th>#</th>
                    <th><fmt:message bundle="messages" key="EDITION_NAME"/></th>
                    <th><fmt:message bundle="messages" key="EDITION_PRICE"/></th>
                    <th><fmt:message bundle="messages" key="EDITION_PLAN"/></th>
                    <th style="width: 36px;"></th>
                </tr>
                </thead>
                <tbody>
                <c:set var="formId" value="0" scope="page"/>
                <c:forEach items="${requestScope.periodicals}" var="item">
                    <tr>
                        <td>${item.editionId}</td>
                        <td>${item.editionName}</td>
                        <td>$${item.editionPrice}</td>
                        <td>
                            <select class="form-control" name="plan" form="subscribe${formId}">
                                <option value="ONE_MONTH"><fmt:message bundle="messages" key="EDITION_PLAN_ONE"/></option>
                                <option value="THREE_MONTHS"><fmt:message bundle="messages" key="EDITION_PLAN_THREE"/></option>
                                <option value="SIX_MONTHS"><fmt:message bundle="messages" key="EDITION_PLAN_SIX"/></option>
                                <option value="YEAR"><fmt:message bundle="messages" key="EDITION_PLAN_YEAR"/></option>
                            </select>
                        </td>
                        <td>
                            <form method="POST" action="PeriodicalPublications" id="subscribe${formId}">
                                <input type="hidden" name="command" value="editionCheckout">
                                <input type="hidden" name="edition" value="${item.editionId}">
                                <input type="submit" value="<fmt:message bundle="messages" key="ACTION_SUBSCRIBE"/>" class="btn btn-success">
                            </form>
                        </td>
                    </tr>
                    <c:set var="formId" value="${formId + 1}" scope="page"/>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script>
</script>
</body>
</html>