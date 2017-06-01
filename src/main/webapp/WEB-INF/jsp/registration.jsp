<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel='stylesheet' href='webjars/bootstrap/3.2.0/css/bootstrap.min.css'>
    <c:if test="${not empty sessionScope.user}">
        <c:redirect url="/PeriodicalPublications"/>
    </c:if>
</head>
<body>
<div class="container">
    <div class="row">
        <form action="PeriodicalPublications" method="POST" class="form-horizontal">
            <!-- Form Name -->
            <legend>Registration form</legend>
            <c:if test="${not empty requestScope.error}">
                <div class="form-group alert alert-danger fade in">
                    <a href="#" class="close" data-dismiss="alert">&times;</a>
                    <p><strong>Error! </strong>${requestScope.error}</p>
                </div>
            </c:if>
            <c:if test="${not empty requestScope['success']}">
                <div class="form-group alert alert-success fade in">
                    <a href="#" class="close" data-dismiss="alert">&times;</a>
                    <p><strong>Error! </strong>${requestScope['success']}</p>
                </div>
            </c:if>
            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label">First Name</label>
                <div class="col-md-4">
                    <input name="login" placeholder="Enter your username" class="form-control input-md" required=""
                           type="text">
                    <span class="help-block"> </span>
                </div>
            </div>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label">Email</label>
                <div class="col-md-4">
                    <input name="email" placeholder="Enter your Email" class="form-control input-md" required=""
                           type="email">
                    <span class="help-block"> </span>
                </div>
            </div>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label">Password</label>
                <div class="col-md-4">
                    <input name="password" placeholder="Insert your Password"
                           class="form-control input-md" required="" type="password">
                    <span class="help-block"> </span>
                </div>
            </div>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label">Confirm Password</label>
                <div class="col-md-4">
                    <input name="confirmPassword" placeholder="Confirm your Password" class="form-control input-md"
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