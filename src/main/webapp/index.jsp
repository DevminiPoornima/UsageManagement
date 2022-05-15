<%@page import="com.Usage"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>



<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Usage Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css"> 
<script src="Components/jquery.min.js"></script> 
<script src="Components/usage.js"></script> 
<title>Usage Management</title>
</head>
<body>
<div class="container"> 
	<div class="row">  
		<div class="col-6"> 
			<h1>Usage Details</h1>
				<form id="formUsage" name="formUsage" method="post" action="index.jsp">  
					Reference Number:  
 	 				<input id="refNo" name="refNo" type="text"  class="form-control form-control-sm">
					<br> Units:   
  					<input id="units" name="units" type="text" class="form-control form-control-sm">   
  					<br> Month:   
  					<input id="month" name="month" type="text"  class="form-control form-control-sm">
					<br>  
					<br> Amount:   
  					<input id="amount" name="amount" type="text"  class="form-control form-control-sm">
					<br>
					
					<input id="btnSave" name="btnSave" type="button" value="SAVE" class="btn btn-primary">  
					<input type="hidden" id="hidUsageIDSave" name="hidUsageIDSave" value=""> 
				</form>
				
				<div id="alertSuccess" class="alert alert-success"> </div>
				
				<div id="alertError" class="alert alert-danger"></div>
				
				<br>
		</div>
		</div>
		<div id="divUsageGrid">
					<%
						Usage usageObj = new Usage();
						out.print(usageObj.readUsage());
					%>
				</div>
</div>

</body>
</html>