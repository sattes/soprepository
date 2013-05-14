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
	<form:form method="POST" modelAttribute="registration"
		action="/Spring3MVCPerformance/register.htm">
		<table>
			<thead>
				<tr>
					<th>SalesOrderProcessingSystem - CustomerRegistration</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>First Name</td>
					<td><form:input path="fname" /></td>
				</tr>
				<tr>
					<td>Last Name</td>
					<td><form:input path="lname" /></td>
				</tr>
				<tr>
					<td>Gender</td>
					<td><form:radiobutton path="gender" value="male" />Male <form:radiobutton
							path="gender" value="female" />Female<br /></td>
				</tr>
				<tr>
					<td>Type of Address</td>
					<td><form:select path="typeOfAddress">
							<form:option value="delivery">Delivery</form:option>
							<form:option value="shipping">Shipping</form:option>
							<form:option value="billing">Billing</form:option>
						</form:select></td>
				</tr>
				<tr>
					<td>Address</td>
					<td><form:textarea path="address" /></td>
				</tr>
				<tr>
					<td>Email</td>
					<td><form:input path="email" /></td>
				</tr>
				<tr>
					<td>Country</td>
					<td>
						<form:select path="country" >
							<form:option value="India">India</form:option>
	                       <form:option value="USA">USA</form:option>
	                       <form:option value="Australia">Australia</form:option>
						</form:select>
					</td>
				</tr>
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
					<td align="center"><input type="submit" value="Register" name="register"/>&nbsp;<input type="submit" value="Back" name="register"/></td>
				</tr>
			</tbody>
		</table>
		<%--  User Name: <form:input path="username"/><font color="red">
               			<form:errors path="username"/></font><br />
           Password: <form:password path="password"/><font color="red">
           			<form:errors path="password"/></font><br />
           First Name: <form:input path="fname"/><br />
           Last Name: <form:input path="lname"/><br />
           Gender: <form:radiobutton path="gender" value="male"/>Male
               		<form:radiobutton path="gender" value="female"/>Female<br />
                   Country :<form:select path="country" >
                       <form:option value="india">India</form:option>
                       <form:option value="india">USA</form:option>
                       <form:option value="india">Australia</form:option>
                   </form:select><br />
                   Address: <form:textarea path="addr"/><br />
                       Select any :<form:checkbox path="cb" value="checkbox1"/>
                       Check Box1
                       <form:checkbox path="cb" value="checkbox2"/>
                       Check Box2<br /> 
 
            <input type="submit" value="Register" /> &nbsp;
            <input type="submit" value="Back" /> --%>
	</form:form>
</body>
</html>