<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:requestEncoding value="UTF-8" />
<fmt:setBundle basename="messages" var="messages"/>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title><fmt:message bundle="${messages}" key="PERIODICALS_PAGE"/></title>
    <c:if test="${empty sessionScope.user}">
        <c:redirect url="/publications"/>
    </c:if>
</head>
<body>
<div class="container">
        <legend>
            <fmt:message bundle="${messages}" key="PERIODICALS_PAGE"/>
            <div class="btn-group pull-right">
                <input type="submit" class="btn btn-default btn-sm" form="localeEn" value="EN">
                <input type="submit" class="btn btn-default btn-sm" form="localeUa" value="UA"/>
            </div>
        </legend>
        <c:if test="${not empty requestScope.error}">
            <div class="form-group alert alert-danger fade in">
                <a href="#" class="close" data-dismiss="alert">&times;</a>
                <p><strong><fmt:message bundle="${messages}" key="ACTION_ERROR"/> </strong>${requestScope.error}</p>
            </div>
        </c:if>
        <c:if test="${not empty requestScope.success}">
            <div class="form-group alert alert-success fade in">
                <a href="#" class="close" data-dismiss="alert">&times;</a>
                <p><strong><fmt:message bundle="${messages}" key="ACTION_SUCCESS"/> </strong>${requestScope.success}</p>
            </div>
        </c:if>
    <div class="well">
        <div class="btn-group" style="padding-bottom: 25px">
        <input type="submit" class="btn btn-primary btn-sm" form="userPage"
               value="<fmt:message bundle="${messages}" key="MY_PAGE"/>">
        <input type="submit" class="btn btn-primary btn-sm" form="logout"
               value="<fmt:message bundle="${messages}" key="ACTION_LOGOUT"/>">
    </div>
        <c:set var="groupName" value="${sessionScope.user.group.groupName}"/>
        <c:if test="${groupName eq 'ADMIN'}">
            <input type="submit" class="btn btn-success pull-right" form="addPeriodical"
                   value="<fmt:message bundle="${messages}" key="ACTION_ADD"/>">
        </c:if>
        <br>
            <table class="table table-striped table-hover table-bordered">
                <thead>
                <tr>
                    <th>#</th>
                    <th><fmt:message bundle="${messages}" key="EDITION_NAME"/></th>
                    <th><fmt:message bundle="${messages}" key="EDITION_PRICE"/></th>
                    <c:if test="${groupName eq 'USER'}">
                        <th><fmt:message bundle="${messages}" key="EDITION_PLAN"/></th>
                    </c:if>
                    <th style="width:auto;"></th>
                </tr>
                </thead>
                <tbody>
                <c:set var="formId" value="0" scope="page"/>
                <c:forEach items="${requestScope.periodicals}" var="item">
                    <tr>
                        <td>${item.editionId}</td>
                        <td>${item.editionName}</td>
                        <td>$${item.editionPrice}</td>
                        <c:if test="${groupName eq 'USER'}">
                            <td>
                                <select class="form-control" name="plan" form="subscribe${formId}">
                                    <option value="ONE_MONTH"><fmt:message bundle="${messages}"
                                                                           key="EDITION_PLAN_ONE"/></option>
                                    <option value="THREE_MONTHS"><fmt:message bundle="${messages}"
                                                                              key="EDITION_PLAN_THREE"/></option>
                                    <option value="SIX_MONTHS"><fmt:message bundle="${messages}"
                                                                            key="EDITION_PLAN_SIX"/></option>
                                    <option value="YEAR"><fmt:message bundle="${messages}"
                                                                      key="EDITION_PLAN_YEAR"/></option>
                                </select>
                            </td>
                        </c:if>
                        <td>
                            <c:choose>
                                <c:when test="${groupName eq 'USER'}">
                                    <form method="POST" action="publications" id="subscribe${formId}">
                                        <input type="hidden" name="command" value="redirectCheckout">
                                        <input type="hidden" name="edition" value="${item.editionId}">
                                        <input type="submit"
                                               value="<fmt:message bundle="${messages}" key="ACTION_SUBSCRIBE"/>"
                                               class="btn btn-success">
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <div class="btn-group pull-right">
                                    <input type="submit"
                                           value="<fmt:message bundle="${messages}" key="ACTION_EDIT"/>"
                                           class="btn btn-primary" form="update${formId}">
                                    <input type="submit"
                                           value="<fmt:message bundle="${messages}" key="ACTION_DELETE"/>"
                                           class="btn btn-danger" form="delete${formId}">
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <form method="POST" action="publications" id="update${formId}">
                        <input type="hidden" name="command" value="redirectEditionUpdate">
                        <input type="hidden" name="edition" value="${item.editionId}">
                    </form>
                    <form method="POST" action="publications" id="delete${formId}">
                        <input type="hidden" name="command" value="deletePeriodical">
                        <input type="hidden" name="edition" value="${item.editionId}">
                    </form>
                    <c:set var="formId" value="${formId + 1}" scope="page"/>
                </c:forEach>
                </tbody>
            </table>
        </div>
</div>


<form method="post" action="publications" id="userPage">
    <c:if test="${sessionScope.user.group.groupName eq 'USER'}">
        <input type="hidden" name="command" value="redirectUserPage">
    </c:if>
    <c:if test="${sessionScope.user.group.groupName eq 'ADMIN'}">
        <input type="hidden" name="command" value="redirectAdminPage">
    </c:if>
</form>

<form method="post" action="publications" id="localeUa"><input type="hidden" name="command" value="localeUa"></form>
<form method="post" action="publications" id="localeEn"><input type="hidden" name="command" value="localeEn"></form>
<form method="post" action="publications" id="logout"><input type="hidden" name="command" value="userLogout"></form>
<form method="post" action="publications" id="addPeriodical"><input type="hidden" name="command" value="redirectEditionAdd"></form>
<script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script>
</script>
</body>
</html>