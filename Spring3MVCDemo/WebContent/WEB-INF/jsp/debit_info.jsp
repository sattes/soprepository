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
			document.ddpage.action = "/Spring3MVCDemo/backtotxn.htm";
			return true;
		}
		
	}
</script>
</head>
<body>
	<form:form action="" name="ddpage">
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
	<c:if test="${fn:length(txnDebitReports) > 0}">
	<table id="datatable">
		<tr>
			<th>AccHolderName</th>
			<th>AccNumber</th>
			<th>AccType</th>
			<th>BankName</th>
			<th>BranchName</th>
			<th>IFSC Code</th>
			<th>DebtAmount</th>
			<th>DebtDate</th>
			<th>DebFrequency</th>
			<th>DebtStatus</th>
		</tr>
		<c:forEach var="report" items="${txnDebitReports}">
			<tr>
				<td>${report.accHolderName}</td>
		      	<td>${report.accNumber}</td>
		     	<td>${report.accType}</td>
		      	<td>${report.bankName}</td>
		      	<td>${report.branchName}</td>
		      	<td>${report.ifscCode}</td>
		     	<td>${report.debtAmount}</td>
		      	<td>${report.debtDate}</td>
		      	<td>${report.debtFrequency}</td>
		      	<td>${report.debtStatus}</td>
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