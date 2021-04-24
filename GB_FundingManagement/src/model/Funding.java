package model;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Funding {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/gb_shop?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertFunding(String ftype, String fsource, String famount, String fdate) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into fundingservice(`fid`,`ftype`,`fsource`,`famount`,`fdate`)"
					+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, ftype);
			preparedStmt.setString(3, fsource);
			preparedStmt.setString(4, famount);
			preparedStmt.setString(5, fdate);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the funding.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readFunding() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Funding ID</th><th>Funding Type</th><th>Funding Source</th><th>Amount</th><th>Date</th></tr>";
			String query = "select * from fundingservice";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String fid = Integer.toString(rs.getInt("fid"));
				String ftype = rs.getString("ftype");
				String fsource = rs.getString("fsource");
				String famount = Float.toString(rs.getFloat("famount"));
				String fdate = rs.getString("fdate");

				// Add into the html table
				output += "<tr><td>" + fid + "</td>";
				output += "<td>" + ftype + "</td>";
				output += "<td>" + fsource + "</td>";
				output += "<td>" + famount + "</td>";
				output += "<td>" + fdate + "</td>";
			}
			con.close();

			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the funding.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateFunding(String fundid, String fundtype, String fundsource, String fundamount, String funddate) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE fundingservice SET ftype=?,fsource=?,famount=?,fdate=? WHERE fid=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, fundtype);
			preparedStmt.setString(2, fundsource);
			preparedStmt.setFloat(3, Float.parseFloat(fundamount));
			preparedStmt.setString(4, funddate);
			preparedStmt.setInt(5, Integer.parseInt(fundid));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the funding.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deleteFunding(String fid) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from fundingservice where fid=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(fid));

			// execute the statement
			preparedStmt.execute();
			con.close();

			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the funding.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
