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
    <title><mytag:title><fmt:message bundle="${messages}" key="CHECKOUT_PAGE"/></mytag:title></title>
    <c:if test="${empty sessionScope.user}">
        <c:redirect url="/publications"/>
    </c:if>
</head>
<body>
<div class="container">
    <legend><fmt:message bundle="${messages}" key="ACTION_SUBSCRIBE"/></legend>
    <c:if test="${not empty requestScope.alreadySubscribed}">
        <div class="form-group alert alert-warning fade in">
            <a href="#" class="close" data-dismiss="alert">&times;</a>
            <p><fmt:message bundle="${messages}" key="ALREADY_SUBSCRIBED"/></p>
            <fmt:message bundle="${messages}" key="SUBSCRIPTION_EXPIRES"/><strong>${requestScope.alreadySubscribed}</strong></p>
        </div>
    </c:if>
    <table class="table table-striped table-hover table-bordered">
        <tbody>
        <tr>
            <th><fmt:message bundle="${messages}" key="EDITION_NAME"/></th>
            <th><fmt:message bundle="${messages}" key="EDITION_PLAN"/></th>
            <th><fmt:message bundle="${messages}" key="EDITION_PRICE"/></th>
            <th><fmt:message bundle="${messages}" key="PURCHASE_TOTAL_PRICE"/></th>
        </tr>
        <tr>
            <td>${requestScope.edition.editionName}</td>
            <td><fmt:message bundle="${messages}" key="${requestScope.plan.description}"/></td>
            <td>$<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${requestScope.edition.editionPrice}"/></td>
            <td>$<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${requestScope.plan.amountOfMonths * requestScope.edition.editionPrice}"/></td>
        </tr>
        <tr>
            <th colspan="3"><span class="pull-right"><fmt:message bundle="${messages}" key="PURCHASE_SUBTOTAL"/></span></th>
            <th>$<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${requestScope.plan.amountOfMonths * requestScope.edition.editionPrice}"/></th>
        </tr>
        <tr>
            <th colspan="3"><span class="pull-right"><fmt:message bundle="${messages}" key="PURCHASE_DISCOUNT"/></span></th>
            <th>$<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${requestScope.plan.amountOfMonths * requestScope.edition.editionPrice - requestScope.totalPrice}"/></th>
        </tr>
        <tr>
            <th colspan="3"><span class="pull-right"><fmt:message bundle="${messages}" key="PURCHASE_TOTAL"/></span></th>
            <th>$<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${requestScope.totalPrice}"/></th>
        </tr>
        <tr>
            <td colspan="3">
                <form method="POST" action="publications">
                    <input type="hidden" name="command" value="redirectMain">
                    <input type="submit" value="<fmt:message bundle="${messages}" key="ACTION_BACK"/>" class="btn btn-primary">
                </form>
            </td>
            <td>
                <c:if test="${empty requestScope.alreadySubscribed}">
                <form method="POST" action="publications">
                    <input type="hidden" name="command" value="userSubscribe">
                    <input type="hidden" name="edition" value="${requestScope.edition.editionId}">
                    <input type="hidden" name="plan" value="${requestScope.plan.name}">
                    <input type="submit" value="<fmt:message bundle="${messages}" key="ACTION_SUBSCRIBE"/>" class="btn btn-success">
                </form>
                </c:if>
                </td>
        </tr>
        </tbody>
    </table>

</div>
<script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script>
</script>
</body>
</html>