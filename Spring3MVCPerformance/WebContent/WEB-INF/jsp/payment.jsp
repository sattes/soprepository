<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>SalesOrderProcessingSystem - Payment</title>
	
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script type="text/javascript">
         $(document).ready(function () {
        	 $('#div1').hide('fast');
        	 $('#div2').hide('fast');
        	
            $('#pmcc').click(function () {
               $('#div2').hide('fast');
               $('#div1').show('fast');
        });
        $('#pmdc').click(function () {
              $('#div1').hide('fast');
              $('#div2').show('fast');
         });
       });
	</script>
</head>
<body>
	<form:form action="/Spring3MVCPerformance/gotopayment.htm" modelAttribute="payment" method="POST">
		<table align="right">
   			<tr align="right">
   				<td align="right">
   					<c:if test="${login != null}">
   						<a href="/Spring3MVCPerformance/logout.htm" >Logout</a>
   					</c:if>
   				</td>
   			</tr>
   		</table>
   		<table>
			<thead>
				<tr>
					<th>SalesOrderProcessingSystem - Payment</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Welcome <c:out value="${login.userName}"></c:out>.</td>
				</tr>
				<tr></tr>
				<tr></tr>
				<tr>
					<td>
						<c:out value="OrderId "></c:out>
					</td>
					<td>
						<c:out value="${payment.orderId}"></c:out>
					</td>
				</tr>
				<tr>
					<td>
						<c:out value="Payment Amount"></c:out>
					</td>
					<td>
						<c:out value="${payment.paymentAmount}"></c:out>
					</td>
				</tr>
				
			</tbody>
		</table>
          <div>
          	<table>
         		<tr>
					<td>Payment Mode:</td>
					<td>
						<form:radiobutton id="pmcc" name="name_radio1" path="paymentMode" value="Credit Cards"/>Credit Cards&nbsp;
              			<form:radiobutton id="pmdc" name="name_radio1" path="paymentMode" value="Debit Cards"/>Debit Cards&nbsp;
              			<form:radiobutton id="pmnb" name="name_radio1" path="paymentMode" value="Net Banking"/>Net Banking&nbsp;
              			<form:radiobutton id="pmecs" name="name_radio1" path="paymentMode" value="ECS"/>ECS
              		</td>
				</tr>
			</table>
          </div>
          <div id="div1"> 
	          <form:radiobutton id="div1visa" path="ccType" value="Visa"/>Visa&nbsp;
	          <form:radiobutton id="div1master" path="ccType" value="MasterCard"/>MasterCard&nbsp;
	          <form:radiobutton id="div1amex" path="ccType" value="American Express"/>American Express<br />
	          <input type="submit" value="Pay Now" name="payNow"/>
       	  </div>
          <div id="div2"> 
	          <form:radiobutton id="div2visa" path="ccType" value="Visa"/>Visa&nbsp;
	          <form:radiobutton id="div2master" path="ccType" value="MasterCard"/>MasterCard&nbsp;
	          <form:radiobutton id="div2amex" path="ccType" value="American Express"/>American Express<br />
	          <input type="submit" value="Pay Now" name="payNow"/>
          </div>
          
	</form:form>
</body>
</html>