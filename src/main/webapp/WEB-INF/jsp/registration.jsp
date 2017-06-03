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
    <c:if test="${not empty sessionScope.user}">
        <c:redirect url="/PeriodicalPublications"/>
    </c:if>
    <title><fmt:message bundle="messages" key="REGISTRATION_PAGE"/></title>
</head>
<body>
<div class="container">
    <div class="row">
        <form action="PeriodicalPublications" method="POST" class="form-horizontal">
            <!-- Form Name -->
            <legend><fmt:message bundle="messages" key="REGISTRATION_PAGE"/></legend>
            <c:if test="${not empty requestScope.error}">
                <div class="form-group alert alert-danger fade in">
                    <a href="#" class="close" data-dismiss="alert">&times;</a>
                    <p><strong><fmt:message bundle="messages" key="ACTION_ERROR"/> </strong>${requestScope.error}</p>
                </div>
            </c:if>
            <c:if test="${not empty requestScope.success}">
                <div class="form-group alert alert-success fade in">
                    <a href="#" class="close" data-dismiss="alert">&times;</a>
                    <p><strong><fmt:message bundle="messages" key="ACTION_SUCCESS"/> </strong>${requestScope.success}</p>
                </div>
            </c:if>
            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label"><fmt:message bundle="messages" key="USER_LOGIN"/></label>
                <div class="col-md-4">
                    <input name="login" placeholder="<fmt:message bundle="messages" key="USER_LOGIN"/>" class="form-control input-md" required=""
                           type="text">
                    <span class="help-block"> </span>
                </div>
            </div>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label"><fmt:message bundle="messages" key="USER_EMAIL"/></label>
                <div class="col-md-4">
                    <input name="email" placeholder="<fmt:message bundle="messages" key="USER_EMAIL"/>" class="form-control input-md" required=""
                           type="email">
                    <span class="help-block"> </span>
                </div>
            </div>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label"><fmt:message bundle="messages" key="USER_PASSWORD"/></label>
                <div class="col-md-4">
                    <input name="password" placeholder="<fmt:message bundle="messages" key="USER_PASSWORD"/>"
                           class="form-control input-md" required="" type="password">
                    <span class="help-block"> </span>
                </div>
            </div>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label"><fmt:message bundle="messages" key="CONFIRM_PASSWORD"/></label>
                <div class="col-md-4">
                    <input name="confirmPassword" placeholder="<fmt:message bundle="messages" key="CONFIRM_PASSWORD"/>" class="form-control input-md"
                           required="" type="password">
                    <span class="help-block"> </span>
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
<script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</body>
</html>