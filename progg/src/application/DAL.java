package application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

public class DAL {
	public Connection login() throws SQLException {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String connectionURL = "jdbc:sqlserver://vmdev001:1433;"
					+ "database=MainDatabase;"
					+ "user=Beddan;"
					+ "password=Madmaxfuryroad9811;"
					+ "encrypt=true;"
					+ "trustServerCertificate=true";
			
			Connection con = DriverManager.getConnection(connectionURL);
			return con;

		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// #### ALL METHODS FOR TABLE PERSONS
	
	public ResultSet getPersons() throws SQLException {
		Connection con = login();
		String query = "SELECT * FROM Person";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet personResult = ps.executeQuery();
		return personResult;
	}

	
	// #### ALL METHODS FOR PROJECTS
	public ResultSet getProjects() throws SQLException {
		Connection con = login();
		String query = "SELECT * FROM PROJECT";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet projectResult = ps.executeQuery();
		return projectResult;
	}
	
			
}
