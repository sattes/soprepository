<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>SalesOrderProcessingSystem - Payment Gateway</title>
    <link href="/Spring3MVCDemo/css/validationEngine.jquery.css" media="screen" rel="stylesheet" type="text/css" />
	<link href="/Spring3MVCDemo/css/template.css" media="screen" rel="stylesheet" type="text/css" />
	<script src="/Spring3MVCDemo/js/jquery-1.8.2.min.js"></script>
	<script src="/Spring3MVCDemo/js/jquery.validationEngine-en.js"></script>
	<script src="/Spring3MVCDemo/js/jquery.validationEngine.js"></script>
	 <link rel="stylesheet" type="text/css" href="/Spring3MVCDemo/css/sopstyles.css"/>
	
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
					<th>SalesOrderProcessingSystem - Net Banking Payment</th>
				</tr>
			</thead>
		</table>
		<table>
			<thead>
				<tr>
					<td width="25%">Merchant Name</td>
					<td>RELIANCE INFO</td>
					<td width="25%">Payment Amount</td>
					<td>${payment.paymentAmount}</td>
				</tr>
			</thead>
		</table>
		
	<form:form action="/Spring3MVCDemo/makepayment.htm" modelAttribute="payment" method="POST" id="formID" class="formular">
		
   		<fieldset>
		<legend>Payment Information (Please enter your card details to authorise this transaction)</legend>
		 <label>Account Holder Name:
     <input value="" class="validate[required,custom[onlyLetterSp]] text-input" type="text" name="directDebit.accHolderName" 
        id="directDebit.accHolderName" />
          </label>

            <label> <span>Account Number:</span>
              <input value="" class="validate[required,custom[onlyNumberSp]] text-input" type="text" 
              name="directDebit.accNumber" id="directDebit.accNumber" />
            </label>
            <label><span>Account Type:</span>
				  <select name="directDebit.accType" id="directDebit.accType" class="validate[required]">
                    <option value="Saving">Saving</option>
				    <option value="Current">Current</option>
				    <option value="NRE">NRE</option>
				    <option value="NRO">NRO</option>
				    <option value="FCNR">FCNR</option>
			 </select>
                </label>
      <label>
				<span>Bank Name : </span>
				<input value="" class="validate[required] text-input" type="text" 
                name="directDebit.bankName" id="directDebit.bankName" />
			</label>
            
            <label>
				<span>Branch Name : </span>
				<input value="" class="validate[required] text-input" type="text" name="directDebit.branchName" 
                id="directDebit.branchName" />
			</label>
<label>
				<span>IFSC Code: </span>
				<input value="" class="validate[required,custom[onlyLetterNumber]] text-input" 
                type="text" name="directDebit.ifscCode" id="directDebit.ifscCode" />
			</label>
<label>
				<span>Debt Amount : </span>
				<input value="" class="validate[required,custom[number]] text-input" type="text" 
                name="directDebit.debtAmount" id="directDebit.debtAmount" />
			</label>
<label>
				
				<span> Debt Date: </span>
				<input value="2012-12-01" class="validate[required,custom[date]] text-input" type="text" 
                name="directDebit.debtDate" id="directDebit.debtDate" />
			</label>
     
          </fieldset>
        <input class="button" type="submit" value="Make Payment"/><hr/>
      
	</form:form>
</body>
</html>