<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SalesOrderProcessingSystem - OrderReports</title>
<link href="/Spring3MVCDemo/css/homestyle.css" media="screen" rel="stylesheet" type="text/css" />
		<link href="/Spring3MVCDemo//css/iconic.css" media="screen" rel="stylesheet" type="text/css" />
		<script src="/Spring3MVCDemo/js/prefix-free.js"></script>

<link rel="stylesheet" type="text/css" href="/Spring3MVCDemo/css/jquery-ui.css"/>
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.8.24/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="/Spring3MVCDemo/css/sopstyles.css"/>
<script type="text/javascript">
	function adjust(obj) {
		if(obj.name == "Close") {
			document.orderreportspage.action = "/Spring3MVCDemo/index.htm";
			return true;
		}
		if(obj.name == "Show") {
			document.orderreportspage.action = "/Spring3MVCDemo/getpayments.htm";
			return true;
		}
	}
</script>
<script type="text/javascript">
	$(document).ready(function() {			
		$("#fromDate").datepicker( {
			dateFormat: 'yy-mm-dd',
			showOn: "button",
			showAnim: "slideDown",
			buttonImage: "./images/datepicker_icon.gif",
			buttonImageOnly: true
		});
		$("img[class='ui-datepicker-trigger']").each(function()
		{ 
			$(this).attr('style', 'position:relative; top:5px; left:3px;');
		});
		$("#fromDate").focus(function() {
			$("#fromDate").datepicker("show");
	    });
	    
		$("#toDate").datepicker( {
			dateFormat: 'yy-mm-dd',
			showOn: "button",
			showAnim: "slideDown",
			buttonImage: "./images/datepicker_icon.gif",
			buttonImageOnly: true
		});
		$("img[class='ui-datepicker-trigger']").each(function()
		{ 
			$(this).attr('style', 'position:relative; top:5px; left:3px;');
		});
		$("#toDate").focus(function() {
			$("#toDate").datepicker("show");
	    });
	});
</script>
</head>
<body>
<h1 align="left">&nbsp; Welcome <sec:authentication property="principal.username" /> </h1> 
<h1 align="center"> SalesOrderProcessingSystem - Payment Reports </h1>
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
					<li><a href="/Spring3MVCDemo/gotoregister.htm?userType=admin">Admin</a></li>
				</ul>
				</sec:authorize>
			</li>
			
			<li>
			<sec:authorize access="not isAuthenticated()" >
			<a href="/Spring3MVCDemo/login.htm"><span class="iconic unlocked"></span>Login</a>
			</sec:authorize>
			</li>
		
			<li>
            <sec:authorize access="hasRole('ROLE_USER')">
            <a href="/Spring3MVCDemo/viewproduct.htm"><span class="iconic map-pin"></span>View Products</a>
            <ul>
            <c:forEach var="category" items="${caps.categories}">
            <li><a href="/Spring3MVCDemo/getproducts.htm?category=${category.catId}">${category.name}</a></li>
            </c:forEach>
            </ul> 
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
    
	<form:form action="" modelAttribute="paymentReport" name="orderreportspage">
   		
		<table>
			<tr>
				<td>Order Id</td>
				<td><form:input path="orderId"/></td>
				<td>Payment Id</td>
				<td><form:input path="paymentId"/></td>
				<td>Payment Status</td>
				<td>
					<form:select path="paymentStatus">
						<form:option value="Select">Select</form:option>
						<form:option value="SUCCESS">Success</form:option>
						<form:option value="FAILURE">Failure</form:option>
						<form:option value="PENDING">Pending</form:option>
					</form:select>
				</td>
			</tr>
			<tr>
				<td>From Date</td>
				<td><form:input path="fromDate" id="fromDate" readonly="true"/></td>
				<td>To Date</td>
				<td><form:input path="toDate" id="toDate" readonly="true"/></td>
			</tr>
		</table>
		<table>
			<tr>
				<td>
					<input type="submit" class="button" value="Close" name="Close" onClick="adjust(this);"/>&nbsp;
					<input type="submit" class="button" value="Show Reports" name="Show" onClick="adjust(this);"/>
				</td>
			</tr>
		</table>
		<c:if test="${fn:length(paymentReports) > 0}">
		<table id="datatable">
			<tr>
				<th>OrderId</th>
				<th>PaymentId</th>
				<th>PaymentStatus</th>
				<th>PaymentDate</th>
				<th>PaymentAmount</th>
				<th>TransactionId</th>
			</tr>
			<c:forEach var="report" items="${paymentReports}">
				<tr>
			      <td>${report.orderId}</td>
			      <td>${report.paymentId}</td>
			      <td>${report.paymentStatus}</td>
			      <td>${report.paymentDate}</td>
			      <td>${report.paymentAmount}</td>
			      <td><a href="/Spring3MVCDemo/gettransactions.htm?paymentid=${report.paymentId}" title="Show Transaction Information">Show Transaction Information</a></td>
			    </tr>
			</c:forEach>  
		</table>
		</c:if>
	</form:form>
</body>
</html>