<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel='stylesheet' href='webjars/bootstrap/3.2.0/css/bootstrap.min.css'>
    <c:if test="${not empty sessionScope.user}">
        <c:redirect url="/PeriodicalPublications"/>
    </c:if>
    <title><fmt:message bundle="messages" key="LOGIN_PAGE"/></title>
</head>
<body>
<div class="container">
    <div class="row">
        <form action="PeriodicalPublications" method="POST" class="form-horizontal">
            <!-- Form Name -->
            <legend><fmt:message bundle="messages" key="LOGIN_PAGE"/></legend>
            <c:if test="${not empty requestScope.error}">
            <div class="form-group alert alert-danger fade in">
                <a href="#" class="close" data-dismiss="alert">&times;</a>
                <p><strong>Error! </strong>${requestScope.error}</p>
            </div>
            </c:if>
            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label"><fmt:message bundle="messages" key="USER_LOGIN"/></label>
                <div class="col-md-4">
                    <input name="login" placeholder="login" class="form-control input-md" required="" type="text">
                    <span class="help-block"> </span>
                </div>
            </div>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label"><fmt:message bundle="messages" key="USER_PASSWORD"/></label>
                <div class="col-md-4">
                    <input name="password" placeholder="password" class="form-control input-md" required=""
                           type="password">
                    <span class="help-block"> </span>
                </div>
            </div>

            <!-- Button -->
            <div class="form-group">
                <label class="col-md-4 control-label"> </label>
                <div class="col-md-4">
                    <input type="hidden" name="command" value="userLogin">
                    <input type="submit" class="btn btn-primary"
                           value="<fmt:message bundle="messages" key="ACTION_LOG_IN"/>">
                    <input type="submit" class="btn btn-link" form="register"
                           value="<fmt:message bundle="messages" key="ACTION_SIGN_IN"/>">
                </div>
            </div>
    </div>
    </form>
    <form method="post" action="PeriodicalPublications" id="register">
        <input type="hidden" name="command" value="redirectRegister">
    </form>
</div>
</div>
<script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</body>
</html>