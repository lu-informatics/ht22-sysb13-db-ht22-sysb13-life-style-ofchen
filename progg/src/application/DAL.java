package application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.Random;


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
	
	// Query for finding a project by project ID
	public ResultSet getProjectID() throws SQLException {
		Connection con = login();
		String query = "SELECT ProjectID FROM PROJECT";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet projectResult = ps.executeQuery();
		return projectResult;
	}
	
	// #### ALL METHODS FOR CONSULTANT 
	
	// Query for finding a consultant by employee ID
	public ResultSet getEmpID() throws SQLException {
		Connection con = login();
		String query = "SELECT EmpID FROM CONSULTANT";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet projectResult = ps.executeQuery();
		return projectResult;
	}
	
	// method for generating a consultant ID
    public static String generateConsultantID() {
        Random rand = new Random();
        String id = "C";
        for (int i = 0; i < 5; i++) {
            id += rand.nextInt(10);
        }
        return id;
    }
 
    // method for generating a manager ID
    public static String generateManagerID() {
    	Random rand = new Random();
    	String id ="M";
    	for(int i = 0; i < 5; i++) {
    		id+= rand.nextInt(10);
    	}
    	return id;
    }
	
	// #### ALL METHODS FOR MILESTONES 
	
	// Query for viewing milestones
	public ResultSet viewMilestones() throws SQLException {
		Connection con = login();
		String query = "SELECT MilestoneType AS Milestones FROM Milestones";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet projectResult = ps.executeQuery();
		return projectResult;
	}

	
		
	// Query for viewing staffing information for a project. I.e. all consultants currently working on the project and their hours
	public ResultSet viewStaffOnProject() throws SQLException {
		Connection con = login();
		String query = "SELECT c.EmpID, p.ProjectID, c.ConsultantName, Hours  FROM WORK w, CONSULTANT c, PROJECT p WHERE w.EmpID = c.EmpID AND w.ProjectID = p.ProjectID AND w.IsActive = 'true'\r\n"
				+ "";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet projectResult = ps.executeQuery();
		return projectResult;
	}
	
	// Query for finding all consultants who have previously worked on a certain project
	public ResultSet hasWorked() throws SQLException {
		Connection con = login();
		String query = "SELECT c.EmpID, p.ProjectID, c.ConsultantName FROM WORK w, CONSULTANT c, PROJECT p WHERE w.EmpID = c.EmpID AND w.ProjectID = p.ProjectID AND w.IsActive = 'false'";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet projectResult = ps.executeQuery();
		return projectResult;
	}
	
	// Show number of hours for a certain consultant on a certain project
	public ResultSet hoursOnProject() throws SQLException{
		Connection con = login();
		String query = "SELECT w.Hours, p.ProjectID, c.EmpID FROM CONSULTANT c,PROJECT p, WORK w WHERE c.EmpID = w.EmpID AND p.ProjectID = w.EmpID";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet projectResult = ps.executeQuery(query);
		return projectResult;
	}
	
	
			
}
