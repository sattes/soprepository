<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/Spring3MVCDemo/css/sopstyles.css"/>
<script type="text/javascript">
	function adjust(obj) {
		if(obj.name == "Back") {
			document.txnpage.action = "/Spring3MVCDemo/backtopaymentreport.htm";
			return true;
		}
		
	}
</script>
</head>
<body>
	<form:form action="" name="txnpage">
	<table class="mainheader">
		<thead>
			<tr>
				<th>SalesOrderProcessingSystem - Payment Reports</th>
			</tr>
		</thead>
	</table>
	<table>
		<tr>
			<td align="left" width="0%">Welcome <sec:authentication property="principal.username"/>.</td>
			<td align="right" width="90%">
				<a href="<c:url value="/j_spring_security_logout" />" >Logout</a>
			</td>
		</tr>
	</table>
	<c:if test="${fn:length(txnReports) > 0}">
	<table id="datatable">
		<tr>
			<th>TransactionId</th>
			<th>TransactionType</th>
			<th>TransactionAmount</th>
			<th>TransactionDate</th>
			<th>Details</th>
		</tr>
		<c:forEach var="report" items="${txnReports}">
			<tr>
				<td>${report.transactionId}</td>
		      	<td>${report.transType}</td>
		     	<td>${report.transAmount}</td>
		      	<td>${report.transDate}</td>
		      	<td><a href="/Spring3MVCDemo/gettxntypeinfo.htm?txntype=${report.transType}&&txnid=${report.transactionId}" title="Show Transaction Type Details">Show Transaction Type Details</a></td>
		    </tr>
		</c:forEach>  
	</table>
	</c:if>
	<table>
		<tr>
			<td>
				<input type="submit" class="button" value="Back" name="Back" onClick="adjust(this);"/>
			</td>
		</tr>
	</table>
	</form:form>
</body>
</html>