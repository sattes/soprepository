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
		</table>
		<fieldset>
			<legend>Customer Details</legend>
		<table>
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
					<td>Customer Type</td>
					<td><form:select path="custType">
							<form:option value="REGULAR">Regular</form:option>
							<form:option value="PREFERRED">Preferred</form:option>
							<form:option value="CORPORATE">Corporate</form:option>
						</form:select></td>
				</tr>
				<tr>
					<td>Email</td>
					<td><form:input path="email" /></td>
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
			</tbody>
		</table>
		</fieldset>
		<fieldset>
			<legend>Customer Address</legend>
		<table>
			<tr>
				<td>Address1</td>
				<td><form:textarea path="customerAddress.address1" /></td>
			</tr>
			<tr>
				<td>Address2</td>
				<td><form:textarea path="customerAddress.address2" /></td>
			</tr>
			<tr>
				<td>City</td>
				<td><form:input path="customerAddress.city" /></td>
			</tr>
			<tr>
				<td>State</td>
				<td><form:input path="customerAddress.state" /></td>
			</tr>
			<tr>
				<td>ZIP</td>
				<td><form:input path="customerAddress.zip" /></td>
			</tr>
			<tr>
				<td>Country</td>
				<td>
					<form:input path="customerAddress.country" />
				</td>
			</tr>
			<tr>
				<td>Phone</td>
				<td><form:input path="customerAddress.phone" /></td>
			</tr>
			<tr></tr>
			<tr align="center">
				<td align="center"><input type="submit" value="Back" name="register"/>&nbsp;<input type="submit" value="Register" name="register"/></td>
			</tr>
		</table>
		</fieldset>
		<%-- <fieldset>
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
			<tr></tr>
			<tr align="center">
				<td align="center"><input type="submit" value="Back" name="register"/>&nbsp;<input type="submit" value="Register" name="register"/></td>
			</tr>
		</table>
		</fieldset> --%>
		
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