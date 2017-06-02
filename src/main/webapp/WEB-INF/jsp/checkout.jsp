<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel='stylesheet' href='webjars/bootstrap/3.2.0/css/bootstrap.min.css'>

</head>
<body>
<div class="container">

    <h1></h1><hr>
    <table class="table table-striped table-hover table-bordered">
        <tbody>
        <tr>
            <th>Name of publication</th>
            <th>Subscription plan</th>
            <th>Price per month</th>
            <th>Total Price</th>
        </tr>
        <tr>
            <td>${requestScope.edition.editionName}</td>
            <td>${requestScope.plan.description}</td>
            <td>$<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${requestScope.edition.editionPrice}"/></td>
            <td>$<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${requestScope.plan.amountOfMonths * requestScope.edition.editionPrice}"/></td>
        </tr>
        <tr>
            <th colspan="3"><span class="pull-right">Sub Total</span></th>
            <th>$<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${requestScope.plan.amountOfMonths * requestScope.edition.editionPrice}"/></th>
        </tr>
        <tr>
            <th colspan="3"><span class="pull-right">Discount</span></th>
            <th>$<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${requestScope.plan.amountOfMonths * requestScope.edition.editionPrice - requestScope.totalPrice}"/></th>
        </tr>
        <tr>
            <th colspan="3"><span class="pull-right">Total</span></th>
            <th>$<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${requestScope.totalPrice}"/></th>
        </tr>
        <tr>
            <td colspan="3">
                <form method="POST" action="PeriodicalPublications">
                    <input type="hidden" name="command" value="userSubscribe">
                    <input type="hidden" name="editionId" value="${requestScope.edition.editionId}">
                    <input type="hidden" name="plan" value="${requestScope.plan.name}">
                    <input type="submit" value="Subscribe" class="pull-right btn btn-success">
                </form>
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