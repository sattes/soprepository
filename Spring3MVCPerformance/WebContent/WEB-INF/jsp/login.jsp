<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SalesOrderProcessingSystem</title>
</head>
<body>
	<form:form modelAttribute="login" action="/Spring3MVCPerformance/login.htm">
		<table>
			<thead>
				<tr>
					<th>SalesOrderProcessingSystem - Login</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>User Name</td>
					<td><form:input path="userName" /><font color="red"> <form:errors
								path="userName" /></font><br /></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><form:password path="password" /><font color="red">
							<form:errors path="password" />
					</font><br /></td>
				</tr>
				<tr></tr>
				<tr align="center">
					<td align="center"><input type="submit" value="Login" /></td>
				</tr>
			</tbody>
		</table>
	</form:form>
</body>
</html>