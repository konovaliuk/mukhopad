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
            <legend>Register Here</legend>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="textinput">First Name</label>
                <div class="col-md-4">
                    <input id="textinput" name="textinput" placeholder="Enter your username" class="form-control input-md" required="" type="text">
                    <span class="help-block"> </span>
                </div>
            </div>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="textinput">Email</label>
                <div class="col-md-4">
                    <input id="textinput" name="textinput" placeholder="Enter your Email" class="form-control input-md" required="" type="email">
                    <span class="help-block"> </span>
                </div>
            </div>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="textinput">Password</label>
                <div class="col-md-4">
                    <input id="textinput" name="textinput" placeholder="Insert your Password" class="form-control input-md" required="" type="text">
                    <span class="help-block"> </span>
                </div>
            </div>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="textinput">Confirm Password</label>
                <div class="col-md-4">
                    <input id="textinput" name="textinput" placeholder="Confirm your Password" class="form-control input-md" required="" type="text">
                    <span class="help-block"> </span>
                </div>
            </div>

            <!-- Button -->
            <div class="form-group">
                <label class="col-md-4 control-label" for="singlebutton"> </label>
                <div class="col-md-4">
                    <button id="singlebutton" name="singlebutton" class="btn btn-primary">Submit</button>
                </div>
            </div>

            </fieldset>
        </form>

</div>
</div>
<script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</body>
</html>