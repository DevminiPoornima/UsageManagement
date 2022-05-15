$(document).ready(function() {
	if ($('#alertSuccess').text().trim() == "") {
		$('#alertSuccess').hide();
	}

	$('#alertError').hide();
})
 
// SAVE  
$(document).on("click", "#btnSave", function(event) 
{  
	// Clear alerts
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 
 
	// Form validation 
	var status = validateUsageForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 
 
	// If valid
	var type = ($("#hidUsageIDSave").val() == "") ? "POST" : "PUT"; 

	$.ajax( 
	{  
		url : "UsageAPI",  
		type : type,  
		data : $("#formUsage").serialize(),  
		dataType : "text",  
		complete : function(response, status)  
		{   
			onUsageSaveComplete(response.responseText, status);  
		} 
	}); 
});

//after completing save request
function onUsageSaveComplete(response, status) 
{  
	if (status == "success")  //if the response status is success
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")    //if the json status is success
		{    
			//display success alert
			$("#alertSuccess").text("Successfully saved.");    
			$("#alertSuccess").show(); 

			//load data in json to html
			$("#divUsageGrid").html(resultSet.data);   
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		} 

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while saving.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while saving..");   
		$("#alertError").show();  
	} 

	//resetting the form
	$("#hidUsageIDSave").val("");  
	$("#formUsage")[0].reset(); 
}


//UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
{     
	$("#hidUsageIDSave").val($(this).data("usageid"));     
	$("#RefNo").val($(this).closest("tr").find('td:eq(1)').text());     
	$("#Units").val($(this).closest("tr").find('td:eq(2)').text());     
	$("#Month").val($(this).closest("tr").find('td:eq(3)').text());  
	$("#Amount").val($(this).closest("tr").find('td:eq(4)').text());
	
	
});


//REMOVE===========================================
$(document).on("click", ".btnRemove", function(event) 
{  //ajax communication
	$.ajax(  
	{   
		url : "UsageAPI",
		type : "DELETE",
		data : "UsageId=" + $(this).data("usageid"),
		dataType : "text",
		complete : function(response, status)
		{
			onUsageDeleteComplete(response.responseText, status);   
		}
	}); 
});

//after completing delete request
function onUsageDeleteComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			
			$("#alertSuccess").text("Successfully deleted.");    
			$("#alertSuccess").show(); 
		
			$("#divUsageGrid").html(resultSet.data); 
			
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		}
		

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while deleting.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while deleting..");   
		$("#alertError").show();  
	}
}

//validation
function validateUsageForm()
{
	// 	ref no
	if ($("#RefNo").val().trim() == "")
	{
		return "Insert Reference Number.";
	}
	
	// units
	if ($("#Units").val().trim() == "")
	{
		return "Insert Number of Units.";
	}
	
	// month
	if ($("#Month").val().trim() == "")
	{
		return "Insert Month.";
	}
	
	// amount
	if ($("#Amount").val().trim() == "")
	{
		return "Insert Amount.";
	}
	
	// is numerical value
	//var tmpUnits = $("#Units").val().trim();
//	if (!$.isNumeric(tmpUnits))
	//{
		//return "Insert a numerical value for Units.";
	//}
	
	// convert to decimal price
	//$("#itemPrice").val(parseFloat(tmpAmount).toFixed(2));
	
	// CARD NO------------------------
	/*if ($("#cardNo").val().trim() == "")
	{
		return "Insert Item Card No.";
	}
	
	// POSTAL NO------------------------
	if ($("#postalNo").val().trim() == "")
	{
		return "Insert Item Postal No.";
	}
	*/
	return true;
}
