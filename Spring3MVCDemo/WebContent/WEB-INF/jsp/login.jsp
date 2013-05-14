<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<META name="Author" content="Swapnil Gangrade" >
<META name="Date" content="2013-05-05">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>Login Form</title>

<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.7.2.min.js"></script>
<link rel="stylesheet" href="/Spring3MVCDemo/css/style.css" type="text/css">
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
<div class="show"></div>
<div id="cerceve">
<div class="header">
  <div class="text" style="float:left">Login Form Online Processing</div><div class="close" style="float:right;margin-right:20px;cursor:pointer;">x</div></div>
<div class="formbody">
<form class="box login" name='f' action="<c:url value='j_spring_security_check' />"
		method="post" >
<input type="text" name="username" required="required" placeholder="Username" class="text" style="background:url(/Spring3MVCDemo/images/username.png) no-repeat;" />
<input type="password" name="password" required="required" placeholder="Password" class="text" style="background:url(/Spring3MVCDemo/images/password.png) no-repeat;" />
<input type="submit" value="Sign In" class="submit" style="background:url(/Spring3MVCDemo/images/login.png) no-repeat;" />
<a href="#">Lost your password?</a>&nbsp;&nbsp;<a href="/Spring3MVCDemo/gotoregister.htm?userType=customer">Singn Up</a>
</form>
<c:if test="${not empty param.login_error}">
<label style="text-align:center;color:#F00"> 
Login Failed because ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
</label>
</c:if>
</div>
</div>
</body>
</html>
