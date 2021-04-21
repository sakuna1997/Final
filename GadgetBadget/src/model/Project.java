package model;

import java.sql.*;

public class Project {
	
	public Connection connect() {
		
		 Connection con = null; 
		 
		 try { 
			 Class.forName("com.mysql.jdbc.Driver"); 
			 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gadgetbadget", "root", ""); 
			 
			 //For testing
			 System.out.print("Successfully connected"); 
		 } 
		 
		 catch(Exception e){ 
			 e.printStackTrace(); 
		 } 
		 
		 return con; 
		}
	
	public String insertProject(String code, String name, String price, String desc, String buy, String ctg){
		 
		 String output = ""; 
		 
		try{ 
		 
			 Connection con = connect(); 
			 
			 if (con == null){
				 return "Error while connecting to the database"; 
			 }
			 
			 // create a prepared statement
			 String query = " insert into projects (`projectID`,`projectCode`,`projectName`,`projectPrice`,`projectDesc`, `projectBuy`, `projectCtg`)"
					 + " values (?, ?, ?, ?, ?, ?, ?)"; 
			 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 
			 // binding values
			 preparedStmt.setInt(1, 0); 
			 preparedStmt.setString(2, code); 
			 preparedStmt.setString(3, name); 
			 preparedStmt.setDouble(4, Double.parseDouble(price)); 
			 preparedStmt.setString(5, desc); 
			 preparedStmt.setString(6, buy); 
			 preparedStmt.setString(7, ctg); 
			 
			 preparedStmt.execute(); 
			 con.close(); 
			 
			 output = "Inserted successfully"; 
		 } 
		
		catch (Exception e){ 
			 output = "Error while inserting"; 
			 System.err.println(e.getMessage()); 
		 } 
		
		return output; 
	}
	
	public String readProjects(){ 
		
		 String output = ""; 
		 
		 try{ 
			 
		 Connection con = connect(); 
		 if (con == null){ 
			 
		 return "Error while connecting to the database for reading."; 
		 } 
		 
		 // Prepare the html table to be displayed
		 output = "<table border='1'><tr><th>Project Code</th>" 
		 +"<th>Project Name</th><th>Project Price</th>"
		 + "<th>Project Description</th>" 
		 + "<th>Project Buyer</th>"
		 + "<th>Project Category</th>" 
		 + "<th>Update</th><th>Remove</th></tr>"; 
		 
		 String query = "select * from projects"; 
		 Statement stmt = con.createStatement(); 
		 ResultSet rs = stmt.executeQuery(query); 
		 
		 // iterate through the rows in the result set
		 while (rs.next()){ 
			 
		 String projectID = Integer.toString(rs.getInt("projectID")); 
		 String projectCode = rs.getString("projectCode"); 
		 String projectName = rs.getString("projectName"); 
		 String projectPrice = Double.toString(rs.getDouble("projectPrice")); 
		 String projectDesc = rs.getString("projectDesc"); 
		 String projectBuy = rs.getString("projectBuy"); 
		 String projectCtg = rs.getString("projectCtg"); 
		 
		 // Add a row into the html table
		 output += "<tr><td>" + projectCode + "</td>"; 
		 output += "<td>" + projectName + "</td>"; 
		 output += "<td>" + projectPrice + "</td>"; 
		 output += "<td>" + projectDesc + "</td>";
		 output += "<td>" + projectBuy + "</td>";
		 output += "<td>" + projectCtg + "</td>";
		 
		 // buttons
		 output += "<td><input name='btnUpdate' " 
				 + " type='button' value='Update'></td>"
				 + "<td><form method='post' action=''>"
				 + "<input name='btnRemove' " 
				 + " type='submit' value='Remove'>"
				 + "<input name='projectID' type='hidden' " 
				 + " value='" + projectID + "'>" + "</form></td></tr>"; 
		 } 
		 con.close(); 
		 // Complete the html table
		 output += "</table>"; 
		 } 
		 
		catch (Exception e) { 
			
		 output = "Error while reading the projects."; 
		 System.err.println(e.getMessage()); 
		 } 
		 
		 return output; 
	}
	
	public String updateProject(String ID, String code, String name, String price, String desc, String buy, String ctg) {
		 String output = ""; 
		 try {
		  
			 Connection con = connect(); 
			 if (con == null) {
				 return "Error while connecting to the database for updating."; 
			 } 
		 // create a prepared statement
				 String query = "UPDATE projects SET projectCode=?,projectName=?,projectPrice=?,projectDesc=?,projectBuy=?,projectCtg=? WHERE projectID=?"; 
				 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
				 // binding values
				 preparedStmt.setString(1, code); 
				 preparedStmt.setString(2, name); 
				 preparedStmt.setDouble(3, Double.parseDouble(price)); 
				 preparedStmt.setString(4, desc);
				 preparedStmt.setString(5, buy);
				 preparedStmt.setString(6, ctg);
				 preparedStmt.setInt(7, Integer.parseInt(ID)); 
				 
				 // execute the statement
				 preparedStmt.execute(); 
				 con.close(); 
				 
				 output = "Updated successfully"; 
		 } 
		 catch (Exception e) {
		  
			 output = "Error while updating the item."; 
			 System.err.println(e.getMessage()); 
		 } 
		 return output;
	}
	
	public String deleteProject(String projectID) { 
		 String output = ""; 
		 try {
		  
			 Connection con = connect(); 
			 if (con == null) {
				 return "Error while connecting to the database for deleting."; 
				 } 
				 // create a prepared statement
				 String query = "delete from projects where projectID=?"; 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
				 // binding values
				 preparedStmt.setInt(1, Integer.parseInt(projectID)); 
				 
				 // execute the statement
				 preparedStmt.execute(); 
				 con.close(); 
				 output = "Deleted successfully"; 
		 } 
		 catch (Exception e) {
		  
			 output = "Error while deleting the item."; 
			 System.err.println(e.getMessage()); 
		 } 
		 return output; 
	 } 

}
