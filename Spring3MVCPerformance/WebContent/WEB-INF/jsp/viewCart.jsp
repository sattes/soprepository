<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="java.util.Enumeration"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
    <head>
        <title>SalesOrderProcessingSystem - ViewProducts</title>
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
						<th>SalesOrderProcessingSystem - ViewCart</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>Welcome <c:out value="${login.userName}"></c:out>.</td>
					</tr>
				</tbody>
			</table>
			<fieldset>
				<legend>Order Details</legend>
				<table>
					<tr>
						<th>ProductId</th><th>Category</th><th>UnitCost</th><th>Name</th><th>Description</th><th>Quantity</th>
					</tr>
					<c:forEach var="prod" items="${sessionScope.selectedProducts}">
						<tr>
					      <td>${prod.productId}</td>
					      <td>${prod.category}</td>
					      <td>${prod.unitCost}</td>
					      <td>${prod.name}</td>
					      <td>${prod.description}</td>
					      <td>${prod.quantity}</td>
					    </tr>
					</c:forEach>
				</table>
			</fieldset>
			<fieldset>
				<legend>Shipping Address</legend>
				<table>
					<tr>
						<td>Address1</td>
						<td><form:textarea path="shippingAddress.address1" /></td>
					</tr>
					<tr>
						<td>Address2</td>
						<td><form:textarea path="shippingAddress.address2" /></td>
					</tr>
					<tr>
						<td>City</td>
						<td><form:input path="shippingAddress.city" /></td>
					</tr>
					<tr>
						<td>State</td>
						<td><form:input path="shippingAddress.state" /></td>
					</tr>
					<tr>
						<td>ZIP</td>
						<td><form:input path="shippingAddress.zip" /></td>
					</tr>
					<tr>
						<td>Country</td>
						<td>
							<form:input path="shippingAddress.country" />
						</td>
					</tr>
					<tr>
						<td>Phone</td>
						<td><form:input path="shippingAddress.phone" /></td>
					</tr>
				</table>
				</fieldset>
		 	<input type="submit" value="Back" name="viewcart"/>&nbsp;<input type="submit" value="SubmitOrder" name="viewcart"/>
 		</form:form>
    </body>
</html>