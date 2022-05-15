package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;



public class Usage {
	
		
		private Connection connect() {
			  //database connection details
	        String dbDriver = "com.mysql.jdbc.Driver";
	        String dbURL = "jdbc:mysql://localhost:3306/";
	        String dbName = "pgms";
	        String dbUsername = "root";
	        String dbPassword = "";
	        
	        Connection conn = null;
	        
	        try {
	        	//connecting the database
	        	Class.forName(dbDriver);
	        	conn = DriverManager.getConnection(dbURL+dbName, dbUsername, dbPassword);
	        	
	        } catch(Exception e) {
	        	e.printStackTrace();
	        }
	        
	        return conn;
		}
		
		//read
		public String readUsage()  
		{   
			Connection conn = connect();
			String output = ""; 
		
			try   
			{    
						
				if (conn == null)    
				{
					return "Error while connecting to the database for reading."; 
				} 
				
				String query = "select * from usages"; 
				Statement stmt = conn.createStatement(); 
				ResultSet rs = stmt.executeQuery(query); 
		 
				// Prepare the html table to be displayed 
				output = "<table class='table'border='1'><tr><th>UsageId</th>" 
			        	+"<th>RefNo</th>"
			        	+"<th>Units</th><th>Month</th>"
			        	+"<th>Amount</th>"
			    		+ "<th>Update</th><th>Remove</th></tr>";
				
		 
				
		 
				// iterate through the rows in the result set    
				while (rs.next())    
				{     
					String UsageId = Integer.toString(rs.getInt("UsageId")); 
					String RefNo = rs.getString("RefNo");
					String Units = Integer.toString(rs.getInt("Units")); 
					String Month = rs.getString("Month");
					String Amount = rs.getString("Amount");
				

					// Add into the HTML table 
					output += "<tr><td>" + UsageId + "</td>"; 
	        		output += "<td>" + RefNo + "</td>"; 
	        		output += "<td>" + Units + "</td>"; 
	        		output += "<td>" + Month + "</td>";
	        		output += "<td>" + Amount + "</td>"; 
	        		
					// buttons     
//					output += "<td><input name='btnUpdate' type='button'"
//							+ "value='Update' class='btnUpdate btn btn-secondary'></td>"
//							+ "<td><form method='post' action='Payment.jsp'>"
//							+ "<input name='btnRemove' type='submit'"
//							+ "value='Remove' class='btnRemove btn btn-danger'>"
//							+ "<input name='hidPaymentIDDelete' type='hidden'"
//							+ "value='" + payId + "'>" + "</form></td></tr>"; 
					output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary' data-usageid='" + UsageId + "'></td>"       
							+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-usageid='" + UsageId + "'>" + "</td></tr>"; 
			
				}
				conn.close(); 
		 
				// Complete the HTML table    
				output += "</table>";   
			}   
			catch (Exception e)   
			{    
				output = "Error while reading the UsageId.";    
				System.err.println(e.getMessage());   
			} 
		 
			return output;  
		}
		
		//Add details about the usage
		public String insertUsage( String refNo, String units, String month, String amount)  
		{   
			Connection conn = connect(); 
			String output = ""; 
			//int UsageId = Integer.parseInt(usageId);
			String RefNo = refNo;
			int Units = Integer.parseInt(units);
			String Month = month;
			String Amount = amount;
		 
			try
			{    
				
		 
				if (conn == null)    
				{
					return "Error while connecting to the database for inserting.";
				} 
		 
				// create a prepared statement 
				String query = " insert into usages ( `RefNo` , `Units` , `Month` , `Amount`) values (?, ?, ?, ?)"; 
		 
		 
				PreparedStatement preparedStmt = conn.prepareStatement(query); 
		 
				// binding values    
				 //preparedStmt.setInt(1, 0);
				 //preparedStmt.setString(2, UsageId);
				 preparedStmt.setString(1, RefNo);
				 preparedStmt.setInt(2, Units);
				 preparedStmt.setString(3, Month);
				 preparedStmt.setString(4, Amount);
				
				
				// execute the statement    
				preparedStmt.execute();    
				conn.close(); 
		   
				String newUsage = readUsage(); 
				output =  "{\"status\":\"success\", \"data\": \"" + newUsage + "\"}";    
			}   
			catch (Exception e)   
			{     
				output =  "{\"status\":\"error\", \"data\": \"Error while inserting the Usage.\"}";  
				System.err.println(e.getMessage());   
			} 
			
		  return output;  
		}
		
		//update
		
		public String updateUsage(String usageId, String refNo, String units, String month, String amount)    
		{   
			Connection conn = connect(); 
			String output = ""; 
			
			int UsageId = Integer.parseInt(usageId);
			String RefNo = refNo;
			int Units = Integer.parseInt(units);
			String Month = month;
			String Amount = amount;
		 
			try   
			{    
				
		 
				if (conn == null)    
				{
					return "Error while connecting to the database for updating.";
				} 
		 
				// create a prepared statement    
				String query = "UPDATE usages SET RefNo=?,Units=?,Month=?,Amount=? WHERE UsageId=?"; 
		 
				PreparedStatement preparedStmt = conn.prepareStatement(query); 
		 
				// binding values    
				//preparedStmt.setString(1, UsageId);
				preparedStmt.setString(1, RefNo);
				preparedStmt.setInt(2, Units);
				preparedStmt.setString(3, Month);
				preparedStmt.setString(4, Amount);
				
				preparedStmt.setInt(5, UsageId); 
		 
				// execute the statement    
				preparedStmt.execute();    
				conn.close(); 
		 
				String newUsage = readUsage();    
				output = "{\"status\":\"success\", \"data\": \"" + newUsage + "\"}";    
			}   
			catch (Exception e)   
			{    
				output =  "{\"status\":\"error\", \"data\": \"Error while updating the Usage.\"}";   
				System.err.println(e.getMessage());   
			} 
		 
		  return output;  
		} 
		
		//delete
		public String deleteUsage(String UsageId)   
		{   
			String output = ""; 
			Connection conn = connect();
		 
			try   
			{    
				
		 
				if (conn == null)    
				{
					return "Error while connecting to the database for deleting."; 
				} 
		 
				// create a prepared statement    
				String query = "delete from usages where UsageId=?";  
		 
				PreparedStatement preparedStmt = conn.prepareStatement(query); 
		 
				// binding values    
				preparedStmt.setInt(1, Integer.parseInt(UsageId)); 
		 
				// execute the statement    
				preparedStmt.execute();    
				conn.close(); 
		 
				String newUsage = readUsage();  
				    
				output = "{\"status\":\"success\", \"data\": \"" +  newUsage + "\"}";    
			}   
			catch (Exception e)   
			{    
				output = "{\"status\":\"error\", \"data\":\"Error while deleting the usage.\"}";    
				System.err.println(e.getMessage());   
			} 
		 
			return output;  
		}
	}


