<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="java.util.Enumeration"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <title>SalesOrderProcessingSystem - ViewProducts</title>
        <link rel="stylesheet" type="text/css" href="/Spring3MVCDemo/css/sopstyles.css"/>
        
    <link href="/Spring3MVCDemo/css/fonts.css" rel='stylesheet' type='text/css'>
		<meta charset="utf-8">
		
		<link href="/Spring3MVCDemo/css/homestyle.css" media="screen" rel="stylesheet" type="text/css" />
		<link href="/Spring3MVCDemo//css/iconic.css" media="screen" rel="stylesheet" type="text/css" />
		<script src="/Spring3MVCDemo/js/prefix-free.js"></script>
    
    
   
<style type="text/css">
<!--
body {
	font: 100%/1.4 Verdana, Arial, Helvetica, sans-serif;
	background-color: #eff3f6;
	margin: 0;
	padding: 0;
	color: #000;
	font-size: 100%;
}

/* ~~ Element/tag selectors ~~ */
ul, ol, dl { /* Due to variations between browsers, it's best practices to zero padding and margin on lists. For consistency, you can either specify the amounts you want here, or on the list items (LI, DT, DD) they contain. Remember that what you do here will cascade to the .nav list unless you write a more specific selector. */
	padding: 0;
	margin: 0;
}
h1, h2, h3, h4, h5, h6, p {
	margin-top: 0;	 /* removing the top margin gets around an issue where margins can escape from their containing div. The remaining bottom margin will hold it away from any elements that follow. */
	padding-right: 15px;
	padding-left: 15px; /* adding the padding to the sides of the elements within the divs, instead of the divs themselves, gets rid of any box model math. A nested div with side padding can also be used as an alternate method. */
}
a img { /* this selector removes the default blue border displayed in some browsers around an image when it is surrounded by a link */
	border: none;
}

/* ~~ Styling for your site's links must remain in this order - including the group of selectors that create the hover effect. ~~ */
a:link {
	color: #42413C;
	text-decoration: underline; /* unless you style your links to look extremely unique, it's best to provide underlines for quick visual identification */
}
a:visited {
	color: #6E6C64;
	text-decoration: underline;
}
a:hover, a:active, a:focus { /* this group of selectors will give a keyboard navigator the same hover experience as the person using a mouse. */
	text-decoration: none;
}

/* ~~ this fixed width container surrounds all other divs ~~ */
.container {
	width: 960px;
	background-color: #eff3f6;
	margin: 0 auto; /* the auto value on the sides, coupled with the width, centers the layout */
	overflow: hidden; /* this declaration makes the .container understand where the floated columns within ends and contain them */
}

/* ~~ These are the columns for the layout. ~~ 

1) Padding is only placed on the top and/or bottom of the divs. The elements within these divs have padding on their sides. This saves you from any "box model math". Keep in mind, if you add any side padding or border to the div itself, it will be added to the width you define to create the *total* width. You may also choose to remove the padding on the element in the div and place a second div within it with no width and the padding necessary for your design.

2) No margin has been given to the columns since they are all floated. If you must add margin, avoid placing it on the side you're floating toward (for example: a right margin on a div set to float right). Many times, padding can be used instead. For divs where this rule must be broken, you should add a "display:inline" declaration to the div's rule to tame a bug where some versions of Internet Explorer double the margin.

3) Since classes can be used multiple times in a document (and an element can also have multiple classes applied), the columns have been assigned class names instead of IDs. For example, two sidebar divs could be stacked if necessary. These can very easily be changed to IDs if that's your preference, as long as you'll only be using them once per document.

4) If you prefer your nav on the right instead of the left, simply float these columns the opposite direction (all right instead of all left) and they'll render in reverse order. There's no need to move the divs around in the HTML source.

*/
.sidebar1 {
	float: left;
	width: 180px;
	background-color: #eff3f6;
	padding-bottom: 10px;
}
.content {
	padding: 0px 0;
	width: 780px;
	float: left;
}

/* ~~ This grouped selector gives the lists in the .content area space ~~ */
.content ul, .content ol { 
	padding: 0 15px 15px 40px; /* this padding mirrors the right padding in the headings and paragraph rule above. Padding was placed on the bottom for space between other elements on the lists and on the left to create the indention. These may be adjusted as you wish. */
}

/* ~~ The navigation list styles (can be removed if you choose to use a premade flyout menu like Spry) ~~ */
ul.nav {
	list-style: none; /* this removes the list marker */
	border-top: 1px solid #666; /* this creates the top border for the links - all others are placed using a bottom border on the LI */
	margin-bottom: 15px; /* this creates the space between the navigation on the content below */
}
ul.nav li {
	border-bottom: 1px solid #666; /* this creates the button separation */
}
ul.nav a, ul.nav a:visited { /* grouping these selectors makes sure that your links retain their button look even after being visited */
	padding: 5px 5px 5px 15px;
	display: block; /* this gives the link block properties causing it to fill the whole LI containing it. This causes the entire area to react to a mouse click. */
	width: 160px;  /*this width makes the entire button clickable for IE6. If you don't need to support IE6, it can be removed. Calculate the proper width by subtracting the padding on this link from the width of your sidebar container. */
	text-decoration: none;
	background-color: #C6D580;
}
ul.nav a:hover, ul.nav a:active, ul.nav a:focus { /* this changes the background and text color for both mouse and keyboard navigators */
	background-color: #ADB96E;
	color: #FFF;
}

/* ~~ miscellaneous float/clear classes ~~ */
.fltrt {  /* this class can be used to float an element right in your page. The floated element must precede the element it should be next to on the page. */
	float: right;
	margin-left: 8px;
}
.fltlft { /* this class can be used to float an element left in your page. The floated element must precede the element it should be next to on the page. */
	float: left;
	margin-right: 8px;
}
.clearfloat { /* this class can be placed on a <br /> or empty div as the final element following the last floated div (within the #container) if the overflow:hidden on the .container is removed */
	clear:both;
	height:0;
	font-size: 1px;
	line-height: 0px;
}
-->
</style></head>

<body>
<h1 align="left">&nbsp; Welcome <sec:authentication property="principal.username" /> </h1> 
<h1 align="center"> SalesOrderProcessingSystem - ViewProducts
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
 

<form:form action="/Spring3MVCDemo/gotocart.htm" modelAttribute="caps">
 <c:if test="${fn:length(caps.products) > 0}">
					<center>
                    <c:if test="${caps.hasErros}">
                    
					<div class="errorblock" align="center">
						<c:out value="${selectMessage}"/>
						<c:out value="${quantityMessage}"/>
					</div>
                    
					</c:if>
	</center>


<div class="container">
  <div class="sidebar1">
    <ul class="nav">
      <li><c:forEach var="category" items="${caps.categories}">
							
		<a href="/Spring3MVCDemo/getproducts.htm?category=${category.catId}">${category.name}</a>
	
						</c:forEach>
                        </li>
    </ul>
   
    <!-- end .sidebar1 --></div>
  <div class="content">
   				<table id="datatable">
						<tr>
							<th  align="center" style="text-align: center; vertical-align: middle;">Select</th><th  align="center" style="text-align: center; vertical-align: middle;">ProductId</th><th  align="center" style="text-align: center; vertical-align: middle;">UnitCost</th><th  align="center" style="text-align: center; vertical-align: middle;">Name</th><th align="center" style="text-align: center; vertical-align: middle;" >Description</th><th  align="center" style="text-align: center; vertical-align: middle;">Quantity</th>
						</tr>
						<c:forEach var="prod" items="${caps.products}" varStatus="status">
							<tr>
								<%-- <td><form:checkbox path="${prod.selected}" value=""/></td> --%>
								<td style="text-align: center; vertical-align: middle;"><input type = "checkbox" name = "selectedProdIds" value = "${prod.productId}" style="alignment-adjust:middle" align="middle"/></td>
						      <td>${prod.productId}</td>
						      
						      <td>${prod.unitCost}</td>
						      <td>${prod.name}</td>
						      <td>${prod.description}</td>
						      <td style="text-align: center; vertical-align: middle;"><input name="products[${status.index}].quantity" value="${prod.quantity}" size="2"/ align="middle" maxlength="2"></td>
						    </tr>
						</c:forEach>
					</table>
					<table align="center">
						<tr></tr>
						<tr>
							<td><input type="submit" class="button" value="Add To Cart" /></td>
						</tr> 
					</table>
					</c:if>
    <!-- end .content --></div>
  <!-- end .container --></div>
</form:form>
</body>

</html>
