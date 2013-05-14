<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link rel="stylesheet" type="text/css" href="/Spring3MVCDemo/css/sopstyles.css"/>
<form:form action="/Spring3MVCPerformance/xmlRead.htm">
<table class="mainheader">
			<thead>
				<tr>
					<th width="100%" align="center">Welcome to SalesOrderProcessingSystem</th>
				</tr>
			</thead>
		</table>
<fieldset style="width: 375px;">
		
		<legend style="">Performance Test</legend>
	<table>
		<thead>
			<tr>
				<th><c:out value="Performance Test on Middleware"></c:out></th>
			</tr>
		</thead>
		<tbody>
			
			<tr align="center">

				<td align="center"><hr width="300" color="green" /></td>
			</tr>
			
			

			<tr>
				<td>Number of XML Messages: <input name="xmlMsgCount"/></td>
				
			</tr>
			
			<tr align="center">
			
				<td align="center"><input type="submit" value="XML2Queue" /></td>
			</tr>
		</tbody>
	</table>
	</fieldset>

</form:form>