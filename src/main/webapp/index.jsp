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
        <form action = "index.jsp" method = "POST" class="form-horizontal">
                <!-- Form Name -->
                <legend>Please, log in</legend>

                <!-- Text input-->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="username">Username</label>
                    <div class="col-md-4">
                        <input id="username" name="username" placeholder="login" class="form-control input-md" required="" type="text">
                        <span class="help-block"> </span>
                    </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="username">Password</label>
                    <div class="col-md-4">
                        <input name="password" placeholder="password" class="form-control input-md" required="" type="password">
                        <span class="help-block"> </span>
                    </div>
                </div>

                <!-- Button -->
                <div class="form-group">
                    <label class="col-md-4 control-label"> </label>
                    <div class="col-md-4">
                        <button class="btn btn-primary">Submit</button>
                        <a href="registration.jsp">Register</a>
                    </div>
                </div>
            </div>
        </form>


    </div>
</div>
<script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</body>
</html>