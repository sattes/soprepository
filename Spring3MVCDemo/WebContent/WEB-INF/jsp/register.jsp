<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>


<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
	<title>SalesOrderProcessingSystem - CustomerRegistration</title>
	
    <link href="/Spring3MVCDemo/css/fonts.css" rel='stylesheet' type='text/css'>
		<meta charset="utf-8">
		
		<link href="/Spring3MVCDemo/css/homestyle.css" media="screen" rel="stylesheet" type="text/css" />
		<link href="/Spring3MVCDemo//css/iconic.css" media="screen" rel="stylesheet" type="text/css" />
		<script src="/Spring3MVCDemo/js/prefix-free.js"></script>
    
    
	<link href="/Spring3MVCDemo/css/validationEngine.jquery.css" media="screen" rel="stylesheet" type="text/css" />
	<link href="/Spring3MVCDemo/css/template.css" media="screen" rel="stylesheet" type="text/css" />
	<script src="/Spring3MVCDemo/js/jquery-1.8.2.min.js"></script>
	<script src="/Spring3MVCDemo/js/jquery.validationEngine-en.js"></script>
	<script src="/Spring3MVCDemo/js/jquery.validationEngine.js"></script>

    <script type="text/javascript">
	function adjust(obj) {
		if(obj.name == "Register") {
			document.registerpage.action = "/Spring3MVCDemo/register.htm";
			return true;
		}
		if(obj.name == "Back") {
			document.registerpage.action = "/Spring3MVCDemo/backtoindex.htm";
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

	<form:form method="POST" modelAttribute="customer"
		 name="registerpage" id="formID" class="formular">
    	<fieldset>
			<legend>
				Customer Details!
			</legend>
			<label> <span>FirstName : </span>
			  <input value="" class="validate[required] text-input" type="text" name="fname" id="fname" />
            </label>
			<label>
			<span>Last Name    : </span>
			<input value="" class="validate[required] text-input" type="text" name="lname" id="lname" />
			</label>
		  <div> <span>Select Gender : <br/>
			  </span> <span>Male:
			  <input name="gender" type="radio" class="validate[required] radio" id="gender1" value="Male" checked/>
			  </span><span>Female: </span>
			  <input class="validate[required] radio" type="radio" name="gender" id="gender2" value="female"/>
		  </div>
          <br/>
			<label>
				<span>Customer Type :</span>
				<select name="custType" id="custType" class="validate[required]">
					<option value="REGULAR" selected>Regular</option>
					<option value="PREFFRRED">Preferred</option>
					<option value="CORPORATE">Corporate</option>
				</select>
			</label>
			<br/>
            <label>
				<span>Email address : </span>
<input value="" class="validate[required,custom[email]] text-input" type="text" name="email" id="email" />
			</label>
            <label> <span>UserName : </span>
			  <input value="" class="validate[required] text-input" type="text" name="userName" id="userName" />
            </label>
            <label>
				<span>Password : </span>
				<input value="" class="validate[required] text-input" type="password" name="password" id="password" />
			</label>
	<label>
				<span>Confirm password : </span>
<input value="" class="validate[required,equals[password]] text-input" type="password" 
name="password2" id="password2" />
</label>
				<br/>         
            
            		</fieldset>
			
		<fieldset>
			<legend>
				Customer Address
			</legend>
        </br>
			<label>
				 Address1
                 <br />
<textarea class="validate[required] text-input" rows="2" cols="20" name="customerAddress.address1" id="customerAddress.address1" ></textarea>
			</label>
            </br>
			<label>
            Address2
				<br />
				<textarea class="validate[required] text-input" rows="2" cols="20" name="customerAddress.address2" id="customerAddress.address2" ></textarea>
			</label>
            
			<label>
				<p>Country</p>
				<p>
				  <select name="customerAddress.country" id="customerAddress.country" class="validate[required]">
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
				    <option value="British Indian Ocean Territory">British Indian Ocean Territory</option>
				    <option value="Brunei Darussalam">Brunei Darussalam</option>
				    <option value="Bulgaria">Bulgaria</option>
				    <option value="Burkina Faso">Burkina Faso</option>
				    <option value="Burundi">Burundi</option>
				    <option value="Cambodia">Cambodia</option>
				    <option value="Cameroon">Cameroon</option>
				    <option value="Canada">Canada</option>
				    <option value="Cape Verde">Cape Verde</option>
				    <option value="Cayman Islands">Cayman Islands</option>
				    <option value="Chad">Chad</option>
				    <option value="Chile">Chile</option>
				    <option value="China">China</option>
				    <option value="Christmas Island">Christmas Island</option>
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
				    <option value="Kuwait">Kuwait</option>
				    <option value="Kyrgyzstan">Kyrgyzstan</option>
				   
				    <option value="Latvia">Latvia</option>
				    <option value="Lebanon">Lebanon</option>
				    <option value="Lesotho">Lesotho</option>
				    <option value="Liberia">Liberia</option>
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
				   
				    <option value="Martinique">Martinique</option>
				    <option value="Mauritania">Mauritania</option>
				    <option value="Mauritius">Mauritius</option>
				    <option value="Mayotte">Mayotte</option>
				    <option value="Mexico">Mexico</option>
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
				    <option value="Saint Helena">Saint Helena</option>
				    <option value="Saint Kitts and Nevis">Saint Kitts and Nevis</option>
				    <option value="Saint Lucia">Saint Lucia</option>
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
				    
				    <option value="Swaziland">Swaziland</option>
				    <option value="Sweden">Sweden</option>
				    <option value="Switzerland">Switzerland</option>
				    <option value="Syrian Arab Republic">Syrian Arab Republic</option>
				    <option value="Taiwan, Republic of China">Taiwan, Republic of China</option>
				    <option value="Thailand">Thailand</option>
				    <option value="Timor-leste">Timor-leste</option>
				    <option value="Togo">Togo</option>
				    <option value="Tokelau">Tokelau</option>
				    <option value="Tonga">Tonga</option>
				    <option value="Tunisia">Tunisia</option>
				    <option value="Turkey">Turkey</option>
				    <option value="Turkmenistan">Turkmenistan</option>
				    <option value="Tuvalu">Tuvalu</option>
				    <option value="Uganda">Uganda</option>
				    <option value="Ukraine">Ukraine</option>
				    <option value="United Kingdom">United Kingdom</option>
				    <option value="United States">United States</option>
				    <option value="Uruguay">Uruguay</option>
				    <option value="Uzbekistan">Uzbekistan</option>
				    <option value="Vanuatu">Vanuatu</option>
				    <option value="Venezuela">Venezuela</option>
				    <option value="Viet Nam">Viet Nam</option>
				    <option value="Yemen">Yemen</option>
				    <option value="Zambia">Zambia</option>
				    <option value="Zimbabwe">Zimbabwe</option>
			      </select>
				</p>
                </br>
                </label>
	 <label> 
     <span> State </span>
 <input value="" class="validate[required,custom[onlyLetterSp]] text-input" type="text" name="customerAddress.state" id="customerAddress.state" />
	 </label>
     </br>
			<label>	
		  <span>City</span>
            <input value="" class="validate[required,custom[onlyLetterSp]] text-input" type="text" name="customerAddress.city" id="customerAddress.city" />
		   </label>
                </br>
          <label>   <span>Zip</span>
                       
      <input value="" class="validate[optional,maxSize[6]] text-input" type="text" name="customerAddress.zip" 
      id="customerAddress.zip" />
                  <br/>
                </label>
		 
            <label> 
              <span>Phone:</span>
  <input value="" class="validate[custom[phone]] text-input" type="text" name="customerAddress.phone" id="customerAddress.phone" />
            </label>
		</fieldset>
		<input class="submit" type="submit" value="Register" name="Register" onClick="adjust(this);"/><hr/>
        
			</form:form>
</body>
</html>
