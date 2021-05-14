package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Product {
	
	public Connection connect()
	{ 
	 Connection con = null; 
	 
	 try 
	 { 
	 Class.forName("com.mysql.cj.jdbc.Driver"); 
	 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf", 
	 "root", ""); 
	 //For testing
	 System.out.print("Successfully connected"); 
	 } 
	 catch(Exception e) 
	 { 
	 e.printStackTrace(); 
	 } 
	 
	 return con; 
		}
	
	
	public String insertproduct(String code, String name, String price, String desc) {
		
		 String output = "";
		 
		 try {
		
		Connection con = connect();
		if (con == null) 
		{ 
		return "Error while connecting to the database"; 
		}
		
		// create a prepared statement
		String query = "insert into product (ID, Code, Name, Price, Description)"
				 + " values (?, ?, ?, ?, ?)"; 
		PreparedStatement preparedStmt = con.prepareStatement(query); 
		// binding values
		preparedStmt.setInt(1, 0); 
		preparedStmt.setString(2, code); 
		preparedStmt.setString(3, name); 
		preparedStmt.setDouble(4, Double.parseDouble(price)); 
		preparedStmt.setString(5, desc);
		
		System.out.println(code);
		 
		 preparedStmt.execute(); 
		 con.close(); 
		 
		 String newItems = readProduct();
		 output =  "{\"status\":\"success\", \"data\": \"" + 
				 newItems + "\"}"; 
		 } 

		catch (Exception e) 
		 { 
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}";  
		 System.err.println(e.getMessage()); 
		 } 
		return output; 
		}

	public String readProduct()
	{ 
			 String output = ""; 
			try
			 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
			 return "Error while connecting to the database for reading."; 
			 } 
			 // Prepare the html table to be displayed
			 output = "<table border='1'><tr><th>Product Code</th>" 
			 +"<th>Product Name</th><th>Product Price</th>"
			 + "<th>Product Description</th>" 
			 + "<th>Update</th><th>Remove</th></tr>"; 
			 String query = "select * from product"; 
			 java.sql.Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 // iterate through the rows in the result set
			 while (rs.next()) 
			 { 
			 String id = Integer.toString(rs.getInt("id")); 
			 String code = rs.getString("Code"); 
			 String name = rs.getString("Name"); 
			 String price = Double.toString(rs.getDouble("Price")); 
			 String desc = rs.getString("Description"); 
			 // Add a row into the html table
			 output += "<tr><td><input id='hidItemIDUpdate' name='hidItemIDUpdate' type='hidden' value='" + id + "'>"
					 + code + "</td>";
			 output += "<td>" + name + "</td>"; 
			 output += "<td>" + price + "</td>"; 
			 
			 output += "<td>" + desc + "</td>";
			 // buttons
			 output += "<td><input name='btnUpdate' " 
			 + " type='button' value='Update' class =' btnUpdate btn btn-secondary'data-itemid='" + id + "'></td>"
			 + "<td><form method='post' action='Item.jsp'>"
			 + "<input name='btnRemove' " 
			 + " type='button' value='Remove' class='btnRemove btn btn-danger' data-itemid='" + id + "'>"
			 + "<input name='hidItemIDDelete' type='hidden' " 
			 + " value='" + id + "'>" + "</form></td></tr>"; 
			 } 
			 con.close(); 
			 // Complete the html table
			 output += "</table>"; 
			 } 
			catch (Exception e) 
			 { 
			 output = "Error while reading the items."; 
			 System.err.println(e.getMessage()); 
			 } 
			return output; 
		}
	
	public String updateItem(String ID, String code, String name, String price, String desc)
	//4
	{
	String output = "";
	try {
	Connection conn = connect();
	if (conn == null) {
	return "Error while connecting to the database for updating.";
	}

	// create a prepared statement
	String query = "UPDATE product SET Code=?,Name=?,Price=?,Description=? WHERE id=?";
	PreparedStatement preparedStmt = conn.prepareStatement(query);
	//binding values
	preparedStmt.setString(1, code);
	preparedStmt.setString(2, name);
	preparedStmt.setDouble(3, Double.parseDouble(price));
	preparedStmt.setString(4, desc);
	preparedStmt.setInt(5, Integer.parseInt(ID));
	//execute the statement
	preparedStmt.execute();
	conn.close();
	String newItems = readProduct();
	 output =  "{\"status\":\"success\", \"data\": \"" + 
			 newItems + "\"}"; 
	 } 

	catch (Exception e) 
	 { 
		output = "{\"status\":\"error\", \"data\": \"Error while Updating the item.\"}";  
	
	System.err.println(e.getMessage());
	}
	return output;
	}
	
	public String deleteItem(String id)
	{ 
	 String output = ""; 
	try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 { 
	 return "Error while connecting to the database for deleting."; 
	 } 
	 // create a prepared statement
	 String query = "delete from product where id=?"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(id)); 
	 
	 // execute the statement
	 preparedStmt.execute(); 
	 con.close(); 
	 String newItems = readProduct();
	 output =  "{\"status\":\"success\", \"data\": \"" + 
			 newItems + "\"}"; 
	 } 

	catch (Exception e) 
	 { 
		output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}";  
	 System.err.println(e.getMessage()); 
	 } 
	return output; 
		}
}


