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
<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.24/themes/base/jquery-ui.css">
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.8.24/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="/Spring3MVCDemo/css/sopstyles.css"/>
<script type="text/javascript">
	function adjust(obj) {
		if(obj.name == "Close") {
			document.orderreportspage.action = "/Spring3MVCDemo/backtohome.htm";
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

	<form:form action="" modelAttribute="paymentReport" name="orderreportspage">
   		<table class="mainheader">
			<thead>
				<tr>
					<th>SalesOrderProcessingSystem - Payment Reports</th>
				</tr>
			</thead>
		</table>
		<table>
			<tr>
				<td align="left" width="10%">Welcome <sec:authentication property="principal.username"/>.</td>
				<td align="right" width="90%">
  						<a href="<c:url value="/j_spring_security_logout" />" >Logout</a>
  					</td>
			</tr>
		</table>
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