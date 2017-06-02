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
            <table class="table">
                <thead>
                <tr>
                    <th>id</th>
                    <th>Name</th>
                    <th>Price per month</th>
                    <th style="width: 36px;"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.periodicals}" var="item">
                    <tr class='clickable-row' data-href='/PeriodicalPublication/checkout.jsp?item=${item.editionId}'>
                        <td>${item.editionId}</td>
                        <td>${item.editionName}</td>
                        <td>${item.editionPrice}$</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="pagination">
            <ul>
                <li><a href="#">Prev</a></li>
                <li><a href="#">1</a></li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">Next</a></li>
            </ul>
        </div>
    </div>
</div>
<script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script>
    $(function(){
        $('tr').click( function(){
                    document.location = $(this).attr('data-href');
                }
            );
        });
</script>
</body>
</html>