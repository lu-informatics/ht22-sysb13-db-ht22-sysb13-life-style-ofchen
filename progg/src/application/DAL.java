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
	
	public ResultSet getProjectID() throws SQLException {
		Connection con = login();
		String query = "SELECT ProjectID FROM PROJECT";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet projectResult = ps.executeQuery();
		return projectResult;
	}
	
	// #### ALL METHODS FOR CONSULTANT 
	
	public ResultSet getEmpID() throws SQLException {
		Connection con = login();
		String query = "SELECT EmpID FROM CONSULTANT";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet projectResult = ps.executeQuery();
		return projectResult;
	}
	
	public ResultSet viewMilestones() throws SQLException {
		Connection con = login();
		String query = "SELECT MilestoneType AS Milestones FROM Milestones";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet projectResult = ps.executeQuery();
		return projectResult;
	}
		
	public ResultSet viewStaffOnProject() throws SQLException {
		Connection con = login();
		String query = "SELECT c.EmpID, p.ProjectID, c.ConsultantName, Hours  FROM WORK w, CONSULTANT c, PROJECT p WHERE w.EmpID = c.EmpID AND w.ProjectID = p.ProjectID AND w.IsActive = 'true'\r\n"
				+ "";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet projectResult = ps.executeQuery();
		return projectResult;
	}
	public ResultSet hasWorked() throws SQLException {
		Connection con = login();
		String query = "SELECT c.EmpID, p.ProjectID, c.ConsultantName FROM WORK w, CONSULTANT c, PROJECT p WHERE w.EmpID = c.EmpID AND w.ProjectID = p.ProjectID AND w.IsActive = 'false'";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet projectResult = ps.executeQuery();
		return projectResult;
	}
	
	
	
	
	
			
}
