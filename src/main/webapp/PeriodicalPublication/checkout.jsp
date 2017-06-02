<%--
  Created by IntelliJ IDEA.
  User: enginebreaksdown
  Date: 01/06/2017
  Time: 19:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Checkout</title>
</head>
<body>
<div class="container">

    <h1>Shopping Cart</h1><hr>
    <table class="table table-striped table-hover table-bordered">
        <tbody>
        <tr>
            <th>Item</th>
            <th>QTY</th>
            <th>Unit Price</th>
            <th>Total Price</th>
        </tr>
        <tr>
            <td><input type="hidden" name>${param.item}</td>
            <td>1 <a href="#">X</a></td>
            <td>£250.00</td>
            <td>£250.00</td>
        </tr>
        <tr>
            <th colspan="3"><span class="pull-right">Sub Total</span></th>
            <th>£250.00</th>
        </tr>
        <tr>
            <th colspan="3"><span class="pull-right">VAT 20%</span></th>
            <th>£50.00</th>
        </tr>
        <tr>
            <th colspan="3"><span class="pull-right">Total</span></th>
            <th>£300.00</th>
        </tr>
        <tr>
            <td><a href="#" class="btn btn-primary">Continue Shopping</a></td>
            <td colspan="3"><a href="#" class="pull-right btn btn-success">Checkout</a></td>
        </tr>
        </tbody>
    </table>
</div>
</body>
</html>
