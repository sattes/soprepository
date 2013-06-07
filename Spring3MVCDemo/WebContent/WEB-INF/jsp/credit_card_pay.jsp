<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
	<title>Credit Card Payment Page</title>
    <link rel="stylesheet" type="text/css" href="/Spring3MVCDemo/css/sopstyles.css"/>
	<link href="/Spring3MVCDemo/css/validationEngine.jquery.css" media="screen" rel="stylesheet" type="text/css" />
	<link href="/Spring3MVCDemo/css/payment.css" media="screen" rel="stylesheet" type="text/css" />
	<script src="/Spring3MVCDemo/js/jquery-1.8.2.min.js"></script>
	<script src="/Spring3MVCDemo/js/jquery.validationEngine-en.js"></script>
	<script src="/Spring3MVCDemo/js/jquery.validationEngine.js"></script>
	
	<script>
		jQuery(document).ready(function(){
			// binds form submission and fields to the validation engine
			jQuery("#formID").validationEngine();
		});

		/**
		*
		* @param {jqObject} the field where the validation applies
		* @param {Array[String]} validation rules for this field
		* @param {int} rule index
		* @param {Map} form options
		* @return an error string if validation failed
		*/
		function checkHELLO(field, rules, i, options){
			if (field.val() != "HELLO") {
				// this allows to use i18 for the error msgs
				return options.allrules.validate2fields.alertText;
			}
		}
	</script>

</head>
<body>
	<table class="mainheader">
			<thead>
				<tr>
					<th>SalesOrderProcessingSystem - Payment Gateway</th>
				</tr>
			</thead>
		</table>
		<table>
			<thead>
		
				<tr>
					<td width="25%">Merchant Name</td>
					<td>RELIANCE INFO</td>
					<td width="25%" >Payment Amount</td>
					<td >${payment.paymentAmount}</td>
			   </tr>
		
			</thead>
		</table>
		
        <form:form action="/Spring3MVCDemo/makepayment.htm" method="POST" modelAttribute="payment" 
          id="formID" class="formular"  >
	
		<fieldset>
		
		<legend>Payment Information (Please enter your card details to authorise this transaction)</legend>  
          <label>  Card Number:        
          <input value="" class="validate[required,creditCard] text-input" type="text" name="cardInfo.cardNumber" id="cardInfo.cardNumber" />
          </label>
     
            <label> <span>Name On Card: </span>
              <input value="" class="validate[required] text-input" type="text" name="cardInfo.nameOnCard" id="cardInfo.nameOnCard" />
            </label>
        
           <label><span>Expiry Date: </span><br/>
             <select name="cardInfo.expiryMonth" id="cardInfo.expiryMonth" class="validate[required]">
		               <option value="01">01</option>
                       <option value="02">02</option>
                       <option value="03">03</option>
                       <option value="04">04</option>
                       <option value="05">05</option>
                       <option value="06">06</option>
                       <option value="07">07</option>
                       <option value="08">08</option>
                       <option value="09">09</option>
                       <option value="10">10</option>
                       <option value="11">11</option>
                       <option value="12">12</option>
                   </select>
                   <select name="cardInfo.expiryYear"id="cardInfo.expiryYear" class="validate[required]">
                       <option value="2013">2013</option>
                       <option value="2014">2014</option>
                       <option value="2015">2015</option>
                       <option value="2016">2016</option>
                       <option value="2017">2017</option>
                       <option value="2018">2018</option>
                       <option value="2019">2019</option>
                       <option value="2020">2020</option>
                   </select>
                   </label>
        
            <label> <span>CCV Number: </span>
              <input value="" class="validate[required,custom[onlyNumberSp]] text-input" type="password" maxlength="4" min="3"
              name="cardInfo.cvvNumber" id="cardInfo.cvvNumber" />
            </label>
         
		</fieldset>
			
		
<input class="button" type="submit" value="Make Payment"/>

		</form:form>
</body>
</html>
