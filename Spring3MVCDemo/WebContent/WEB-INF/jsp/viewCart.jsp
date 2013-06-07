<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="java.util.Enumeration"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
	<title>SalesOrderProcessingSystem - ViewCart</title>
    <link href="/Spring3MVCDemo/css/homestyle.css" media="screen" rel="stylesheet" type="text/css" />
		<link href="/Spring3MVCDemo//css/iconic.css" media="screen" rel="stylesheet" type="text/css" />
		<script src="/Spring3MVCDemo/js/prefix-free.js"></script>
    
    <link href="/Spring3MVCDemo/css/validationEngine.jquery.css" media="screen" rel="stylesheet" type="text/css" />
	<link href="/Spring3MVCDemo/css/template.css" media="screen" rel="stylesheet" type="text/css" />
	<script src="/Spring3MVCDemo/js/jquery-1.8.2.min.js"></script>
	<script src="/Spring3MVCDemo/js/jquery.validationEngine-en.js"></script>
	<script src="/Spring3MVCDemo/js/jquery.validationEngine.js"></script>
     <link rel="stylesheet" type="text/css" href="/Spring3MVCDemo/css/sopstyles.css"/>
     
     
        <script language="javascript">
			function adjust(obj) {
				if(obj.name == "SubmitOrder") {
					document.viewcartpage.action = "/Spring3MVCDemo/submitorder.htm";
					return true;
				}
				if(obj.name == "Back") {
					document.viewcartpage.action = "/Spring3MVCDemo/backtoproducts.htm";
					return true;
				}
			}
		</script>
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

   
<h1 align="left">&nbsp; Welcome <sec:authentication property="principal.username" /> </h1> 
<h1 align="center"> SalesOrderProcessingSystem - ViewCart
   </h1>
  

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
            <a href="#"><span class="iconic map-pin"></span>View Products</a>
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

  
 				<%-- <c:if test="${order.hasErros}">
					<div class="errorblock">
						<c:forEach var="errorMsg" items="${errorList}">
							<c:out value="${errorMsg}"/><br/>
						</c:forEach>
					</div>
				</c:if>--%>	
							
  			<table id="mytable" align="center">
					<tr>
						<th>ProductId</th><th>Category</th><th>UnitCost</th><th>Name</th><th>Description</th><th>Quantity</th><th>Remove</th>
					</tr>
					<c:forEach var="prod" items="${sessionScope.selectedProducts}">
						<tr>
					      <td>${prod.productId}</td>
					      <td>${prod.category}</td>
					      <td>${prod.unitCost}</td>
					      <td>${prod.name}</td>
					      <td>${prod.description}</td>
					      <td>${prod.quantity}</td>
					      <td>
					    <a href="/Spring3MVCDemo/removeproduct.htm?prodid=${prod.productId}" title="Remove from Cart">
					      		<img src="./images/remove-from-cart-icon.jpg" alt="Remove from Cart"/>
					      	</a>
					      </td>
					    </tr>
					</c:forEach>
				</table>

		<%--</fieldset> --%>
		<br/>	
  <form:form action="" modelAttribute="order" method="POST" name="viewcartpage" id="formID" class="formular">
	    	<legend>
				Shipping Address
			</legend>
        </br>
			<label>
				 Address1
                 <br />
<textarea class="validate[required] text-input" rows="2" cols="20" name="shippingAddress.address1" id="shippingAddress.address1" ></textarea>
			</label>
            </br>
			<label>
            Address2
				<br />
				<textarea class="validate[required] text-input" rows="2" cols="20" name="shippingAddress.address2" id="shippingAddress.address22" ></textarea>
			</label>
            
			<label>
				<p>Country</p>
				<p>
				  <select name="shippingAddress.country" id="shippingAddress.country" class="validate[required]">
				    <option value=""></option>
				    <option value="United States">United States</option>
				    <option value="United Kingdom">United Kingdom</option>
				    <option value="Afghanistan">Afghanistan</option>
				    <option value="Albania">Albania</option>
				    <option value="Algeria">Algeria</option>
				    <option value="American Samoa">American Samoa</option>
				    <option value="Andorra">Andorra</option>
				    <option value="Angola">Angola</option>
				    <option value="Anguilla">Anguilla</option>
				    <option value="Antarctica">Antarctica</option>
				    <option value="Antigua and Barbuda">Antigua and Barbuda</option>
				    <option value="Argentina">Argentina</option>
				    <option value="Armenia">Armenia</option>
				    <option value="Aruba">Aruba</option>
				    <option value="Australia">Australia</option>
				    <option value="Austria">Austria</option>
				    <option value="Azerbaijan">Azerbaijan</option>
				    <option value="Bahamas">Bahamas</option>
				    <option value="Bahrain">Bahrain</option>
				    <option value="Bangladesh">Bangladesh</option>
				    <option value="Barbados">Barbados</option>
				    <option value="Belarus">Belarus</option>
				    <option value="Belgium">Belgium</option>
				    <option value="Belize">Belize</option>
				    <option value="Benin">Benin</option>
				    <option value="Bermuda">Bermuda</option>
				    <option value="Bhutan">Bhutan</option>
				    <option value="Bolivia">Bolivia</option>
				    <option value="Bosnia and Herzegovina">Bosnia and Herzegovina</option>
				    <option value="Botswana">Botswana</option>
				    <option value="Bouvet Island">Bouvet Island</option>
				    <option value="Brazil">Brazil</option>
				    <option value="Brunei Darussalam">Brunei Darussalam</option>
				    <option value="Bulgaria">Bulgaria</option>
				    <option value="Burkina Faso">Burkina Faso</option>
				    <option value="Burundi">Burundi</option>
				    <option value="Cambodia">Cambodia</option>
				    <option value="Cameroon">Cameroon</option>
				    <option value="Canada">Canada</option>
				    <option value="Cape Verde">Cape Verde</option>
				    <option value="Cayman Islands">Cayman Islands</option>
				    <option value="Central African Republic">Central African Republic</option>
				    <option value="Chad">Chad</option>
				    <option value="Chile">Chile</option>
				    <option value="China">China</option>
				    <option value="Christmas Island">Christmas Island</option>
				    <option value="Cocos (Keeling) Islands">Cocos (Keeling) Islands</option>
				    <option value="Colombia">Colombia</option>
				    <option value="Comoros">Comoros</option>
				    <option value="Congo">Congo</option>
				    <option value="Cook Islands">Cook Islands</option>
				    <option value="Costa Rica">Costa Rica</option>
				    <option value="Cote D'ivoire">Cote D'ivoire</option>
				    <option value="Croatia">Croatia</option>
				    <option value="Cuba">Cuba</option>
				    <option value="Cyprus">Cyprus</option>
				    <option value="Czech Republic">Czech Republic</option>
				    <option value="Denmark">Denmark</option>
				    <option value="Djibouti">Djibouti</option>
				    <option value="Dominica">Dominica</option>
				    <option value="Dominican Republic">Dominican Republic</option>
				    <option value="Ecuador">Ecuador</option>
				    <option value="Egypt">Egypt</option>
				    <option value="El Salvador">El Salvador</option>
				    <option value="Equatorial Guinea">Equatorial Guinea</option>
				    <option value="Eritrea">Eritrea</option>
				    <option value="Estonia">Estonia</option>
				    <option value="Ethiopia">Ethiopia</option>	    
				    <option value="Faroe Islands">Faroe Islands</option>
				    <option value="Fiji">Fiji</option>
				    <option value="Finland">Finland</option>
				    <option value="France">France</option>
				    <option value="French Guiana">French Guiana</option>
				    <option value="French Polynesia">French Polynesia</option>
				    <option value="Gabon">Gabon</option>
				    <option value="Gambia">Gambia</option>
				    <option value="Georgia">Georgia</option>
				    <option value="Germany">Germany</option>
				    <option value="Ghana">Ghana</option>
				    <option value="Gibraltar">Gibraltar</option>
				    <option value="Greece">Greece</option>
				    <option value="Greenland">Greenland</option>
				    <option value="Grenada">Grenada</option>
				    <option value="Guadeloupe">Guadeloupe</option>
				    <option value="Guam">Guam</option>
				    <option value="Guatemala">Guatemala</option>
				    <option value="Guinea">Guinea</option>
				    <option value="Guinea-bissau">Guinea-bissau</option>
				    <option value="Guyana">Guyana</option>
				    <option value="Haiti">Haiti</option>
				    <option value="Honduras">Honduras</option>
				    <option value="Hong Kong">Hong Kong</option>
				    <option value="Hungary">Hungary</option>
				    <option value="Iceland">Iceland</option>
				    <option value="India">India</option>
				    <option value="Indonesia">Indonesia</option>
				    <option value="Iraq">Iraq</option>
				    <option value="Ireland">Ireland</option>
				    <option value="Israel">Israel</option>
				    <option value="Italy">Italy</option>
				    <option value="Jamaica">Jamaica</option>
				    <option value="Japan">Japan</option>
				    <option value="Jordan">Jordan</option>
				    <option value="Kazakhstan">Kazakhstan</option>
				    <option value="Kenya">Kenya</option>
				    <option value="Kiribati">Kiribati</option>
				    <option value="Korea, Republic of">Korea, Republic of</option>
				    <option value="Kuwait">Kuwait</option>
				    <option value="Kyrgyzstan">Kyrgyzstan</option>			    
				    <option value="Latvia">Latvia</option>
				    <option value="Lebanon">Lebanon</option>
				    <option value="Lesotho">Lesotho</option>
				    <option value="Liberia">Liberia</option>
				    <option value="Libyan Arab Jamahiriya">Libyan Arab Jamahiriya</option>
				    <option value="Liechtenstein">Liechtenstein</option>
				    <option value="Lithuania">Lithuania</option>
				    <option value="Luxembourg">Luxembourg</option>
				    <option value="Macao">Macao</option>	
				    <option value="Madagascar">Madagascar</option>
				    <option value="Malawi">Malawi</option>
				    <option value="Malaysia">Malaysia</option>
				    <option value="Maldives">Maldives</option>
				    <option value="Mali">Mali</option>
				    <option value="Malta">Malta</option>
				    <option value="Marshall Islands">Marshall Islands</option>
				    <option value="Martinique">Martinique</option>
				    <option value="Mauritania">Mauritania</option>
				    <option value="Mauritius">Mauritius</option>
				    <option value="Mayotte">Mayotte</option>
				    <option value="Mexico">Mexico</option>				    
				    <option value="Moldova, Republic of">Moldova, Republic of</option>
				    <option value="Monaco">Monaco</option>
				    <option value="Mongolia">Mongolia</option>
				    <option value="Montenegro">Montenegro</option>
				    <option value="Montserrat">Montserrat</option>
				    <option value="Morocco">Morocco</option>
				    <option value="Mozambique">Mozambique</option>
				    <option value="Myanmar">Myanmar</option>
				    <option value="Namibia">Namibia</option>
				    <option value="Nauru">Nauru</option>
				    <option value="Nepal">Nepal</option>
				    <option value="Netherlands">Netherlands</option>
				    <option value="Netherlands Antilles">Netherlands Antilles</option>
				    <option value="New Caledonia">New Caledonia</option>
				    <option value="New Zealand">New Zealand</option>
				    <option value="Nicaragua">Nicaragua</option>
				    <option value="Niger">Niger</option>
				    <option value="Nigeria">Nigeria</option>
				    <option value="Niue">Niue</option>
				    <option value="Norfolk Island">Norfolk Island</option>
				    <option value="Northern Mariana Islands">Northern Mariana Islands</option>
				    <option value="Norway">Norway</option>
				    <option value="Oman">Oman</option>
				    <option value="Pakistan">Pakistan</option>
				    <option value="Palau">Palau</option>				  
				    <option value="Panama">Panama</option>
				    <option value="Papua New Guinea">Papua New Guinea</option>
				    <option value="Paraguay">Paraguay</option>
				    <option value="Peru">Peru</option>
				    <option value="Philippines">Philippines</option>
				    <option value="Pitcairn">Pitcairn</option>
				    <option value="Poland">Poland</option>
				    <option value="Portugal">Portugal</option>
				    <option value="Puerto Rico">Puerto Rico</option>
				    <option value="Qatar">Qatar</option>
				    <option value="Reunion">Reunion</option>
				    <option value="Romania">Romania</option>
				    <option value="Russian Federation">Russian Federation</option>
				    <option value="Rwanda">Rwanda</option>
				    <option value="Saint Helena">Saint Helena</option>
				    <option value="Saint Kitts and Nevis">Saint Kitts and Nevis</option>
				    <option value="Saint Lucia">Saint Lucia</option>
				    <option value="Saint Pierre and Miquelon">Saint Pierre and Miquelon</option>
				    <option value="Samoa">Samoa</option>
				    <option value="San Marino">San Marino</option>
				    <option value="Sao Tome and Principe">Sao Tome and Principe</option>
				    <option value="Saudi Arabia">Saudi Arabia</option>
				    <option value="Senegal">Senegal</option>
				    <option value="Serbia">Serbia</option>
				    <option value="Seychelles">Seychelles</option>
				    <option value="Sierra Leone">Sierra Leone</option>
				    <option value="Singapore">Singapore</option>
				    <option value="Slovakia">Slovakia</option>
				    <option value="Slovenia">Slovenia</option>
				    <option value="Solomon Islands">Solomon Islands</option>
				    <option value="Somalia">Somalia</option>
				    <option value="South Africa">South Africa</option>
	                <option value="South Sudan">South Sudan</option>
				    <option value="Spain">Spain</option>
				    <option value="Sri Lanka">Sri Lanka</option>
				    <option value="Sudan">Sudan</option>
				    <option value="Suriname">Suriname</option>
				    <option value="Svalbard and Jan Mayen">Svalbard and Jan Mayen</option>
				    <option value="Swaziland">Swaziland</option>
				    <option value="Sweden">Sweden</option>
				    <option value="Switzerland">Switzerland</option>
				    <option value="Syrian Arab Republic">Syrian Arab Republic</option>
				    <option value="Taiwan, Republic of China">Taiwan, Republic of China</option>
				    <option value="Tajikistan">Tajikistan</option>
				    <option value="Tanzania, United Republic of">Tanzania, United Republic of</option>
				    <option value="Thailand">Thailand</option>
				    <option value="Timor-leste">Timor-leste</option>
				    <option value="Togo">Togo</option>
				    <option value="Tokelau">Tokelau</option>
				    <option value="Tonga">Tonga</option>
				    <option value="Trinidad and Tobago">Trinidad and Tobago</option>
				    <option value="Tunisia">Tunisia</option>
				    <option value="Turkey">Turkey</option>
				    <option value="Turkmenistan">Turkmenistan</option>
				    <option value="Turks and Caicos Islands">Turks and Caicos Islands</option>
				    <option value="Tuvalu">Tuvalu</option>
				    <option value="Uganda">Uganda</option>
				    <option value="Ukraine">Ukraine</option>
				    <option value="United Arab Emirates">United Arab Emirates</option>
				    <option value="United Kingdom">United Kingdom</option>
				    <option value="United States">United States</option>				    
				    <option value="Uruguay">Uruguay</option>
				    <option value="Uzbekistan">Uzbekistan</option>
				    <option value="Vanuatu">Vanuatu</option>
				    <option value="Venezuela">Venezuela</option>
				    <option value="Viet Nam">Viet Nam</option>
				    <option value="Virgin Islands, British">Virgin Islands, British</option>
				    <option value="Virgin Islands, U.S.">Virgin Islands, U.S.</option>
				    <option value="Wallis and Futuna">Wallis and Futuna</option>
				    <option value="Western Sahara">Western Sahara</option>
				    <option value="Yemen">Yemen</option>
				    <option value="Zambia">Zambia</option>
				    <option value="Zimbabwe">Zimbabwe</option>
			      </select>
				</p>
                </br>
                </label>
	 <label> 
     <span> State </span>
 <input value="" class="validate[required,custom[onlyLetterSp]] text-input" type="text" name="shippingAddress.state" id="shippingAddress.state" />
	 </label>
     </br>
			<label>	
		  <span>City</span>
            <input value="" class="validate[required,custom[onlyLetterSp]] text-input" type="text" name="shippingAddress.city" id="shippingAddress.city" />
		   </label>
                </br>
          <label>   <span>Zip</span>
                       
      <input value="" class="validate[optional,maxSize[6]] text-input" type="text" name="shippingAddress.zip" 
      id="shippingAddress.zip" />
                  <br/>
                </label>
		 
            <label> 
              <span>Phone:</span>
  <input value="" class="validate[custom[phone]] text-input" type="text" name="shippingAddress.phone" id="shippingAddress.phone" />
        
            </label>
		
        
       
          <!--    <input type="submit" class="button" value="Back" name="Back" onClick="adjust(Back);"/>&nbsp;	-->	
              <input type="submit" class="button" value="SubmitOrder" name="SubmitOrder" onClick="adjust(this);"/>
       
	 </form:form>
</body>
</html>


