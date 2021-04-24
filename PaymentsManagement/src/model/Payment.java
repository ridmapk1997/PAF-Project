package model;
import java.sql.*;
public class Payment {
	
	//A common method to connect to the DB
		private Connection connect()
		 {
		 Connection con = null;
		 try
		 {
		 Class.forName("com.mysql.jdbc.Driver");

		 //Provide the correct details: DBServer/DBName, username, password
		 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf", "root", "");
		 }
		 catch (Exception e)
		 {e.printStackTrace();}
		 return con;
		 }
		public String insertPayment(String CustomerName, String AccountNumber, String ProductName, String PaymentAmount)
		 {
		 String output = "";
		 try
		 {
		 Connection con = connect();
		 if (con == null)
		 {return "Error while connecting to the database for inserting."; }
		 // create a prepared statement
		 String query = " insert into payment(`paymentID`,`customerName`,`accountNumber`,`productName`,`amount`)"
		 + " values (?, ?, ?, ?, ?)";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 // binding values
		 preparedStmt.setInt(1, 0);
		 preparedStmt.setString(2, CustomerName);
		 preparedStmt.setString(3, AccountNumber);
		 preparedStmt.setString(4, ProductName);
		 preparedStmt.setDouble(5, Double.parseDouble(PaymentAmount));
		
		// execute the statement
		 preparedStmt.execute();
		 con.close();
		 output = "Inserted successfully";
		 }
		 catch (Exception e)
		 {
		 output = "Error while inserting the payment.";
		 System.err.println(e.getMessage());
		 }
		 return output;
		 }
		public String readPayments()
		 {
		 String output = "";
		 try
		 {
		 Connection con = connect();
		 if (con == null)
		 {return "Error while connecting to the database for reading."; }
		 // Prepare the html table to be displayed
		 output = "<table border='1'><tr><th>Customer Name</th><th>Account Number</th>" +
		 "<th>Product Name</th>" +
		 "<th>Payment Amount</th>" +
		 "<th>Update</th><th>Remove</th></tr>";

		 String query = "select * from payment";
		 Statement stmt = con.createStatement();
		 ResultSet rs = stmt.executeQuery(query);
		 // iterate through the rows in the result set
		 while (rs.next())
		 {
		 String paymentID = Integer.toString(rs.getInt("paymentID"));
		 String CustomerName = rs.getString("customerName");
		 String AccountNumber = rs.getString("accountNumber");
		 String ProductName = rs.getString("productName");
		 String PaymentAmount = Double.toString(rs.getDouble("amount"));
		 
		 // Add into the html table
		 output += "<tr><td>" + CustomerName + "</td>";
		 output += "<td>" + AccountNumber + "</td>";
		 output += "<td>" + ProductName + "</td>";
		 output += "<td>" + PaymentAmount + "</td>";
		 // buttons
		 output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"+ "<td><form method='post' action='items.jsp'>"+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"+ "<input name='paymentID' type='hidden' value='" + paymentID+ "'>" + "</form></td></tr>";
		 }
		 con.close();
		 // Complete the html table
		 output += "</table>";
		 }
		 catch (Exception e)
		 {
		 output = "Error while reading the payments.";
		 System.err.println(e.getMessage());
		 }
		 return output;
		 }
		public String updatePayment(String paymentID, String CustomerName, String AccountNumber, String ProductName, String PaymentAmount)
		{
			 String output = "";
			 try
			 {
			 Connection con = connect();
			 if (con == null)
			 {return "Error while connecting to the database for updating."; }
			 // create a prepared statement
			 String query = "UPDATE payment SET customerName=?,accountNumber=?,productName=?,amount=?WHERE paymentID=?";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 // binding values
			 preparedStmt.setString(1, CustomerName);
			 preparedStmt.setString(2, AccountNumber);
			 preparedStmt.setString(3, ProductName);
			 preparedStmt.setDouble(4, Double.parseDouble(PaymentAmount));
			 preparedStmt.setInt(5, Integer.parseInt(paymentID));
			 // execute the statement
			 preparedStmt.execute();
			 con.close();
			 output = "Updated successfully";
			 }
			 catch (Exception e)
			 {
			 output = "Error while updating the payment.";
			 System.err.println(e.getMessage());
			 }
			 return output;
			 }
			public String deletePayment(String paymentID)
			 {
			 String output = "";
			 try
			 {
			 Connection con = connect();
			 if (con == null)
			 {return "Error while connecting to the database for deleting."; }
			 // create a prepared statement
			 String query = "delete from payment where paymentID=?";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 // binding values
			 preparedStmt.setInt(1, Integer.parseInt(paymentID));
			 // execute the statement
			 preparedStmt.execute();
			 con.close();
			 output = "Deleted successfully";
			 }
			 catch (Exception e)
			 {
			 output = "Error while deleting the Payment.";
			 System.err.println(e.getMessage());
			 }
			 return output;
			 } 

}
