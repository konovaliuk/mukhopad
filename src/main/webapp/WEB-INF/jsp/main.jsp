<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel='stylesheet' href='webjars/bootstrap/3.2.0/css/bootstrap.min.css'>

</head>
<body>

<div class="container">
    <div class="row">
        <legend>Publications</legend>
        <c:if test="${not empty requestScope.error}">
            <div class="form-group alert alert-danger fade in">
                <a href="#" class="close" data-dismiss="alert">&times;</a>
                <p><strong>Error! </strong>${requestScope.error}</p>
            </div>
        </c:if>
        <c:if test="${not empty requestScope.success}">
            <div class="form-group alert alert-success fade in">
                <a href="#" class="close" data-dismiss="alert">&times;</a>
                <p><strong>Success! </strong>${requestScope.success}</p>
            </div>
        </c:if>
        <div class="btn-toolbar">
            <button class="btn btn-primary">Add periodicals</button>
        </div>
        <div class="well">
            <table class="table table-striped table-hover table-bordered">
                <thead>
                <tr>
                    <th>id</th>
                    <th>Name</th>
                    <th>Price per month</th>
                    <th>Subscription plan</th>
                    <th style="width: 36px;"></th>
                </tr>
                </thead>
                <tbody>
                <c:set var="formId" value="0" scope="page" />
                <c:forEach items="${requestScope.periodicals}" var="item">
                    <tr>
                        <td>${item.editionId}</td>
                        <td>${item.editionName}</td>
                        <td>${item.editionPrice}$</td>
                        <td>
                            <select class="form-control" name="plan" form="subscribe${formId}">
                                <option value="ONE_MONTH">1 Month</option>
                                <option value="THREE_MONTHS">3 Months - 10% OFF</option>
                                <option value="SIX_MONTHS">6 Months - 15% OFF</option>
                                <option value="YEAR">1 Year - 25% OFF</option>
                            </select>
                        </td>
                        <td>
                            <form method="POST" action="PeriodicalPublications" id="subscribe${formId}">
                                <input type="hidden" name="command" value="editionCheckout">
                                <input type="hidden" name="editionId" value="${item.editionId}">
                                <input type="submit" value="Subscribe" class="btn btn-success">
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