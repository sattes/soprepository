<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Login Page</title>
<link rel="stylesheet" type="text/css" href="/Spring3MVCDemo/css/sopstyles.css"/>
</head>
<body onload="document.loginform.username.focus();">
	<form name="loginform" action="<c:url value='j_spring_security_check' />"
		method="POST">
 		<table class="mainheader">
			<thead>
				<tr>
					<th width="100%" align="center">SalesOrderProcessingSystem - Login</th>
				</tr>
			</thead>
		</table>
		<c:if test="${not empty param.login_error}">
			<div class="errorblock">
				Your login attempt was not successful, try again.<br /> Caused : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
			</div>
		</c:if>
		<fieldset style="width: 375px;">
		<legend>Login</legend>
			<table>
				<tr>
					<td>User:</td>
					<td><input type="text" name="username" value="">
					</td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type="password" name="password" />
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input name="submit" type="submit"	value="Submit" class="button"/>&nbsp;
						<input name="reset" type="reset" value="Reset" class="button"/>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>