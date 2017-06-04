<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="messages" var="messages"/>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link href="http://getbootstrap.com/dist/css/bootstrap.css" rel="stylesheet" type="text/css" />
    <c:if test="${not empty sessionScope.user}">
        <c:redirect url="/PeriodicalPublications"/>
    </c:if>
    <title><fmt:message bundle="${messages}" key="REGISTRATION_PAGE"/></title>
</head>
<body>
<div class="container">
    <div class="row">
        <form action="PeriodicalPublications" method="POST" class="form-horizontal" data-toggle="validator">
            <!-- Form Name -->
            <legend>
                <fmt:message bundle="${messages}" key="REGISTRATION_PAGE"/>
                <div class="btn-group pull-right" style="padding-bottom: 25px">
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
            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="login"><fmt:message bundle="${messages}" key="USER_LOGIN"/></label>
                <div class="col-md-4">
                    <input id="login" name="login" placeholder="<fmt:message bundle="${messages}" key="USER_LOGIN"/>" class="form-control input-md" required=""
                           type="text">
                    <span class="glyphicon form-control-feedback"></span>
                    <span class="help-block with-errors"></span>
                </div>
            </div>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="email"><fmt:message bundle="${messages}" key="USER_EMAIL"/></label>
                <div class="col-md-4">
                    <input id="email" name="email" placeholder="<fmt:message bundle="${messages}" key="USER_EMAIL"/>" class="form-control input-md" required=""
                           type="email">
                    <span class="glyphicon form-control-feedback"></span>
                    <span class="help-block with-errors"></span>
                </div>
            </div>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="inputPassword"><fmt:message bundle="${messages}" key="USER_PASSWORD"/></label>
                <div class="col-md-4">
                    <input name="password" id="inputPassword" data-minlength="6" placeholder="<fmt:message bundle="${messages}" key="USER_PASSWORD"/>"
                           class="form-control input-md" required="" type="password">
                    <span class="glyphicon form-control-feedback"></span>
                    <span class="help-block with-errors"></span>
                </div>
            </div>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="confirmPassword"><fmt:message bundle="${messages}" key="CONFIRM_PASSWORD"/></label>
                <div class="col-md-4">
                    <input id="confirmPassword" name="confirmPassword" data-match="#inputPassword"
                           data-match-error="<fmt:message bundle="${messages}" key="PASSWORD_MISMATCH_ERROR"/>"
                           data-minlength="6" placeholder="<fmt:message bundle="${messages}"
                           key="CONFIRM_PASSWORD"/>" class="form-control input-md"
                           required="" type="password">
                    <span class="glyphicon form-control-feedback"></span>
                    <span class="help-block with-errors"></span>
                </div>
            </div>

            <!-- Button -->
            <div class="form-group">
                <label class="col-md-4 control-label"> </label>
                <div class="col-md-4">
                    <input type="hidden" name="command" value="userRegister"/>
                    <input type="submit" value="Register" class="btn btn-primary"/>
                </div>
            </div>
        </form>
    </div>
</div>
<script src="http://getbootstrap.com/dist/js/bootstrap.js"></script>
<script src="http://1000hz.github.io/bootstrap-validator/dist/validator.min.js"></script>
<script src="http://code.jquery.com/jquery.min.js"></script>
</body>Ω
</html>