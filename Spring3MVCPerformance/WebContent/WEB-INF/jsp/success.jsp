<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.Enumeration"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
    <head>
        <title>SalesOrderProcessingSystem - Success</title>
    </head>
    <body>
    	<form:form action="/Spring3MVCPerformance/register.htm">
	    	<table>
				<thead>
					<tr>
						<th>SalesOrderProcessingSystem - Success</th>
					</tr>
				</thead>
				<tbody>
					<tr align="center"><td align="center">Customer registered successfully with the following details</td></tr>
					<tr>
						<td>First Name</td>
						<td><c:out value="${customer.fname}"></c:out></td>
					</tr>
					<tr>
						<td>Last Name</td>
						<td><c:out value="${customer.lname}"></c:out></td>
					</tr>
					<tr>
						<td>Gender</td>
						<td><c:out value="${customer.gender}"></c:out></td>
					</tr>
					<tr>
						<td>Address</td>
						<td><c:out value="${customer.email}"></c:out></td>
					</tr>
					<tr>
						<td>User Name</td>
						<td><c:out value="${customer.userName}"></c:out></td>
					</tr>
					<tr></tr>
					<tr align="center">
						<td align="center">
							<a href="/Spring3MVCPerformance/login.htm" >Go to Login Page</a>
						</td>
					</tr>
				</tbody>
			</table>
		       
		 	<input type="submit" value="Back" name="register">
 		</form:form>
    </body>
</html>