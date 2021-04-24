package model;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Customer {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/gb_shop?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertCustomer(String name, String address, String pname, String email, String date, String gender, String pno) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into customerp(`cID`,`cName`,`cAddress`,`pName`,`cEmail`,`cDate`,`cGender`,`cPno`)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, address);
			preparedStmt.setString(4, pname);
			preparedStmt.setString(5, email);
			preparedStmt.setString(6, date);
			preparedStmt.setString(7, gender);
			preparedStmt.setString(8, pno);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the customer.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readCustomer() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Buyer ID</th><th>Buyer's Name</th><th>Buyer's Address</th><th>Product Name</th><th>Buyer's Email</th><th>Purchase Date</th><th>Gender</th><th>Phone No</th></tr>";
			String query = "select * from customerp";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String cID = Integer.toString(rs.getInt("cID"));
				String cName = rs.getString("cName");
				String cAddress = rs.getString("cAddress");
				String pName = rs.getString("pName");
				String cEmail = rs.getString("cEmail");
				String cDate = rs.getString("cDate");
				String cGender = rs.getString("cGender");
				String cPno = rs.getString("cPno");

				// Add into the html table
				output += "<tr><td>" + cID + "</td>";
				output += "<td>" + cName + "</td>";
				output += "<td>" + cAddress + "</td>";
				output += "<td>" + pName + "</td>";
				output += "<td>" + cEmail + "</td>";
				output += "<td>" + cDate + "</td>";
				output += "<td>" + cGender + "</td>";
				output += "<td>" + cPno + "</td>";
				
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the customer.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateCustomer(String ID, String name, String address, String pname, String email, String date, String gender,
			String pno) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE customerp SET cName=?,cAddress=?,pName=?,cEmail=?,cDate=?,cGender=?,cPno=?" + "WHERE cID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, address);
			preparedStmt.setString(3, pname);
			preparedStmt.setString(4, email);
			preparedStmt.setString(5, date);
			preparedStmt.setString(6, gender);
			preparedStmt.setString(7, pno);
			preparedStmt.setInt(8, Integer.parseInt(ID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the customer.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deleteCustomer(String cID) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from customerp where cID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(cID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the customer.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
