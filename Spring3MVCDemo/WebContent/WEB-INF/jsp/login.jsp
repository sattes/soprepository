<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<META name="Author" content="Swapnil Gangrade" >
<META name="Date" content="2013-05-05">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>Sales Order Processing System with SQLFire In Memory Database - LoginPage</title>

<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.7.2.min.js"></script>
<link rel="stylesheet" href="/Spring3MVCDemo/css/style.css" type="text/css">

		<link href="/Spring3MVCDemo/css/fonts.css" rel='stylesheet' type='text/css'>
		<meta charset="utf-8">
		
		<link href="/Spring3MVCDemo/css/homestyle.css" media="screen" rel="stylesheet" type="text/css" />
		<link href="/Spring3MVCDemo/css/iconic.css" media="screen" rel="stylesheet" type="text/css" />
		<script src="/Spring3MVCDemo/js/prefix-free.js"></script>



<script type="text/javascript">
$(function(){
	$("#cerceve").hide().fadeIn(500);
	$(".show").hide();
	$(".close").click(function(){
		$("#cerceve").hide(500);
		$(".show").fadeIn(500);
	});
	$(".show").click(function(){
		$("#cerceve").fadeIn(500);
		$(".show").hide(500);
	});
});
</script>
</head>
<body onload='document.f.username.focus();'>
<h1 align="center">&nbsp;</h1>
<h1 align="left">&nbsp; Welcome <sec:authorize access="isAuthenticated()">  <sec:authentication property="principal.username"/> </sec:authorize> to Sales Order Processing System</h1>
<div class="wrap">
	
	<nav>
		<ul class="menu">
			<li><a href="/Spring3MVCDemo/index.htm"><span class="iconic home"></span> Home</a></li>
			<li><a href="#"><span class="iconic info"></span>About</a>
				<ul>
					<li><a href="#">Company History</a></li>
					<li><a href="#">Meet the team</a></li>
				</ul>
			</li>
            <li>
             <sec:authorize access="not isAuthenticated()">
            <a href="#"><span class="iconic user"></span>Register</a>
				<ul>
					<li><a href="/Spring3MVCDemo/gotoregister.htm?userType=customer">Customer</a></li>
					<li><a href="/Spring3MVCDemo/gotoregister.htm?userType=customer">Admin</a></li>
				</ul>
				</sec:authorize>
			</li>
		
			<li>
            <sec:authorize access="hasRole('ROLE_USER')">
            <a href="/Spring3MVCDemo/viewproduct.htm"><span class="iconic map-pin"></span>View Products</a>
			</sec:authorize>
			</li>
			
			<li>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
            <a href="/Spring3MVCDemo/gotoupdatepayments.htm"><span class="iconic cog-alt"></span>Payment Tracking</a> 
            </sec:authorize>
            </li>
			
            <li>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
            <a href="/Spring3MVCDemo/order.htm"><span class="iconic check-alt"></span>Order Tracking</a> 
            </sec:authorize>
            </li>
            
			<li>
            <sec:authorize access="hasAnyRole('ROLE_USER','ROLE_ADMIN')">
            <a href="/Spring3MVCDemo/gotoorderreports.htm"><span class="iconic pencil"></span>Order Reports</a> 
            </sec:authorize>
            </li>
			
            <li>
            <sec:authorize access="hasAnyRole('ROLE_USER','ROLE_ADMIN')">
            <a href="/Spring3MVCDemo/gotopaymentreports.htm"><span class="iconic article"></span>Payment Reports</a> 
            </sec:authorize>
            </li>
			
            <li>
            <sec:authorize access="hasRole('ROLE_SYSADMIN')">
            <a href="/Spring3MVCDemo/getDisabledCustomers.htm"><span class="iconic key"></span>Enable Customer</a> 
            </sec:authorize>
            </li>
			
		    <li>
            <sec:authorize access="isAuthenticated()">
            <a href="<c:url value="/j_spring_security_logout" />" ><span class="iconic locked"></span>Logout</a>
            </sec:authorize>
            </li>
            
			
            <li><a href="#"><span class="iconic chat"></span>Contact</a> </li>
		</ul>
		<div class="clearfix"></div>
	</nav>
	</div>


<div class="show"></div>
<div id="cerceve">
<div class="header">
  <div class="text" style="float:left">Login Form Online Processing</div><div class="close" style="float:right;margin-right:20px;cursor:pointer;"></div></div>
<div class="formbody">
<form class="box login" name='f' action="<c:url value='j_spring_security_check' />"
		method="post" >
<input type="text" name="username" required placeholder="Username" class="text" style="background:url(/Spring3MVCDemo/images/username.png) no-repeat;" />
<input type="password" name="password" required placeholder="Password" class="text" style="background:url(/Spring3MVCDemo/images/password.png) no-repeat;" />
<input type="submit" value="Sign In" class="submit" style="background:url(/Spring3MVCDemo/images/login.png) no-repeat;" />
<a href="#">Lost your password?</a>
<br /><br />
<c:if test="${not empty param.login_error}">
<label style="text-align:center;color:#F00"> 
&nbsp;&nbsp;&nbsp; 
<c:if test="${sessionScope[\"SPRING_SECURITY_LAST_EXCEPTION\"].message eq 'Bad credentials'}">
Username/Password entered is incorrect.
</c:if>
<c:if test="${sessionScope[\"SPRING_SECURITY_LAST_EXCEPTION\"].message eq 'User is disabled'}">
Your account is diabled, please contact administrator.
</c:if>
<c:if test="${fn:containsIgnoreCase(sessionScope[\"SPRING_SECURITY_LAST_EXCEPTION\"].message,'A communications error has been detected')}">
Database connection is down, try after sometime.
</c:if>

</label>
</c:if>

</form>
</div>
</div>
</body>
</html>
