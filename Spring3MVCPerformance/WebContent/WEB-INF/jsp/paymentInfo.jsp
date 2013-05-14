<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>SalesOrderProcessingSystem - PaymentInfo</title>
</head>
<body>
	<form:form action="/Spring3MVCPerformance/submitcart.htm" modelAttribute="order" method="POST">
		<table align="right">
   			<tr align="right">
   				<td align="right">
   					<c:if test="${login != null}">
   						<a href="/Spring3MVCPerformance/logout.htm" >Logout</a>
   					</c:if>
   				</td>
   			</tr>
   		</table>
   		<table>
			<thead>
				<tr>
					<th>SalesOrderProcessingSystem - PaymentInfo</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Welcome <c:out value="${login.userName}"></c:out>.</td>
				</tr>
				<tr></tr>
				<tr></tr>
				<tr>
					<td>
						<c:out value="Order "></c:out>
						<c:out value="${order.orderId}"></c:out>
						<c:out value=" will be processed soon"></c:out>
					</td>
				</tr>
			</tbody>
		</table>
	</form:form>
</body>
</html>