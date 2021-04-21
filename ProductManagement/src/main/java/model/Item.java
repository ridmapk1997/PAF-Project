package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Item {
	
	
	private Connection connect()
    {
    Connection con = null;
    try
    {
    Class.forName("com.mysql.cj.jdbc.Driver");
   
    //Provide the correct details: DBServer/DBName, username, password
    con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf", "root", "");
    }
    catch (Exception e)
    {e.printStackTrace();}
    return con;
    }


	
	
	public String readItems()
    {
    String output = "";
    try
    {
    Connection con = connect();
    if (con == null)
    {return "Error while connecting to the database for reading."; }
    
    // Prepare the html table to be displayed
    output = "<table border='1'><tr><th>Product Code</th><th>Product Name</th>" +
    "<th>Product Price</th>" +
    "<th>Product Description</th>" +
    "<th>Update</th><th>Remove</th></tr>";
   
    String query = "select * from product";
    Statement stmt = con.createStatement();
    ResultSet rs = stmt.executeQuery(query);
    // iterate through the rows in the result set
    
    while (rs.next())
    {
    String Code = rs.getString("Code");
    String Name = rs.getString("Name");
    String Price = Double.toString(rs.getDouble("Price"));
    String Description = rs.getString("Description");
    
    // Add into the html table
    output += "<tr><td>" + Code + "</td>";
    output += "<td>" + Name + "</td>";
    output += "<td>" + Price + "</td>";
    output += "<td>" + Description + "</td>";
    
    // buttons
    output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
    + "<td><form method='post' action='items.jsp'>"
    + "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
    + "<input name='itemID' type='hidden' value='" + Code
    + "'>" + "</form></td></tr>";
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
	
	 //Insert Product
	public String insertProduct(String code, String name, String price, String des)
    {
    String output = "";
    try
    {
    Connection con = connect();
    if (con == null)
    {return "Error while connecting to the database for inserting."; }
    
    // create a prepared statement
    String query = " insert into product(Code,Name,Price,Description)"
    + " values (?, ?, ?, ?)";
    PreparedStatement preparedStmt = con.prepareStatement(query);
    
    // binding values
    preparedStmt.setString(1, code);
    preparedStmt.setString(2, name);
    preparedStmt.setDouble(3, Double.parseDouble(price));
    preparedStmt.setString(4, des);
    
   // execute the statement
    preparedStmt.execute();
    con.close();
    output = "Inserted successfully";
    }
    catch (Exception e)
    {
    output = "Error while inserting the item.";
    System.err.println(e.getMessage());
    }
    return output;
    }

	//Update
	public String updateProduct(String code, String name, String price, String des)
    {
         String output = "";
         try
         {
         Connection con = connect();
         if (con == null)
         {return "Error while connecting to the database for updating."; }
         // create a prepared statement
         String query = "UPDATE product SET Name=?,Price=?,Description=? WHERE Code=?";
         PreparedStatement preparedStmt = con.prepareStatement(query);

         // binding values
         preparedStmt.setString(1, name);
         preparedStmt.setDouble(2, Double.parseDouble(price));
         preparedStmt.setString(3, des);
         preparedStmt.setString(4,code);

         System.out.println(price);
         
         // execute the statement
         preparedStmt.execute();
         con.close();
         output = "Updated successfully";
         }
         catch (Exception e)
         {
         output = "Error while updating the item.";
         System.err.println(e.getMessage());
         }
         return output;
         }
	
	//delete
	public String deleteProduct(String code)
    {
    String output = "";
    try
    {
    Connection con = connect();
    if (con == null)
    {return "Error while connecting to the database for deleting."; }
    // create a prepared statement
    String query = "delete from product where Code=?";
    PreparedStatement preparedStmt = con.prepareStatement(query);
    // binding values
    preparedStmt.setString(1,code);
    // execute the statement
    preparedStmt.execute();
    con.close();
    output = "Deleted successfully";
    }
    catch (Exception e)
    {
    output = "Error while deleting the item.";
    System.err.println(e.getMessage());
    }
    return output;
    }
}
