<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="java.util.Enumeration"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html lang="en">
	<head>
	
<META name="Author" content="Swapnil Gangrade" >
<META name="Date" content="2013-05-10">
	
		<link href="/Spring3MVCDemo/css/fonts.css" rel='stylesheet' type='text/css'>
		<meta charset="utf-8">
		<title>SalesOrderProcessingSystem - CustomerHomePage</title>
		<link href="/Spring3MVCDemo/css/homestyle.css" media="screen" rel="stylesheet" type="text/css" />
		<link href="/Spring3MVCDemo//css/iconic.css" media="screen" rel="stylesheet" type="text/css" />
		<script src="/Spring3MVCDemo/js/prefix-free.js"></script>
	</head>

<body>
<h1 align="center">&nbsp;</h1>
<h1 align="left">&nbsp; Welcome <sec:authentication property="principal.username"/> to Sales Order Processing System</h1>
<form:form action="/Spring3MVCDemo/gotocart.htm" modelAttribute="caps">
<div class="wrap">
	
	<nav>
		<ul class="menu">
			<li><a href="#"><span class="iconic home"></span> Home</a></li>
			
            <li>
            <sec:authorize access="hasRole('ROLE_USER')">
            <a href="/Spring3MVCDemo/viewproduct.htm"><span class="iconic map-pin"></span>View Products</a>
			</sec:authorize>
			</li>
			
			<li>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
            <a href="/Spring3MVCDemo/gotoupdatepayments.htm"><span class="iconic cog"></span>Payment Tracking</a> 
            </sec:authorize>
            </li>
			
            <li>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
            <a href="/Spring3MVCDemo/order.htm"><span class="iconic check"></span>Order Tracking</a> 
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
            <a href="<c:url value="/j_spring_security_logout" />" ><span class="iconic locked"></span> Logout</a>
            </sec:authorize>
            </li>
            
			
            <li>
            <sec:authorize access="hasRole('ROLE_USER')">
            <a href="#"><span class="iconic chat"></span>Contact</a>
            </sec:authorize> 
            </li>
		</ul>
		<div class="clearfix"></div>
	</nav>
	</div>
      



   	</form:form>
	</body>
</html>