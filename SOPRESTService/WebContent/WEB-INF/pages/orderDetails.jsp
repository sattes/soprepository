<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Employees</title>
</head>
<body>

<table>
	<thead><tr>
		<th>OrderId</th>
		<th>UserId</th>
		<th>AddressId</th>
		<th>OrderDate</th>
		<th>TotalPrice</th>
		<th>Status</th>
	</tr></thead>
	<tr>
		<td>${order.orderId}</td>
		<td>${order.userId}</td>
		<td>${order.addressId}</td>
		<td>${order.orderDate}</td>
		<td>${order.totalPrice}</td>
		<td>${order.status}</td>
	</tr>
</table>
</body>
</html>