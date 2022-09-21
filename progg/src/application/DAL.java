package application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Random;


public class DAL {
	
	   private String connectionURL;
	   
	   public DAL() throws IOException { 
		 
		   System.out.println("DB server name: " +System.getenv("DATABASE_SERVER_NAME"));
		   System.out.println("port: " + System.getenv("DATABASE_SERVER_PORT"));
		   System.out.println("DB namn: " + System.getenv("DATABASE_NAME") );
		   System.out.println("DB User name: " + System.getenv("DATABASE_USER_NAME"));
		   System.out.println("DB User Password: " + System.getenv("DATABASE_USER_PASSWORD") );
		   
	   
	   String databaseServerName = System.getenv("DATABASE_SERVER_NAME"); 
	   String databaseServerPort = System.getenv("DATABASE_SERVER_PORT");
	   String databaseName = System.getenv("DATABASE_NAME");
	   String databaseUserName = System.getenv("DATABASE_USER_NAME");
	   String databaseUserPassword = System.getenv("DATABASE_USER_PASSWORD"); 
	   //String connectionURL = "jdbc:sqlserver://vmdev001:1433;";

	//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

	           connectionURL = "jdbc:sqlserver://"
	                   + databaseServerName 
	                   + ":"
	                   + databaseServerPort + ";"
	                   + "database=" + databaseName + ";"
	                   + "user=" + databaseUserName + ";"
	                   + "password=" + databaseUserPassword + ";"
	                   + "encrypt=true;" 
	                   + "trustServerCertificate=true"; 
	   }       
	
	   /*
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
	    */
	
	// #### ALL METHODS FOR INSERTING INTO TABLE 
	   public boolean createEmployee(String Name, String Address, String Startdate, int Salary) throws SQLException{
		  try {
		   String EmpID = generateConsultantID();
		   String query = "INSERT INTO CONSULTANT VALUES('"
				   + EmpID + "', '" + Name + "', '" + Address + "', '" + Startdate + "', " + Salary + ", " + null + ") SET NOCOUNT ON";
		   //String query = "INSERT INTO CONSULTANT VALUES(""'" + EmpID + "'," + "'" + Name + "'," + "'" + Address + "'," + "'" Startdate+ +Salary+)";
		   Connection connection = DriverManager.getConnection(connectionURL);
		   PreparedStatement ps = connection.prepareStatement(query);
		   return ps.execute();	
		  }
	       catch(SQLException e) {
	    	System.out.print(e.getMessage());   
	       }
		  return false;
	   }
	
	   

	
	// #### ALL METHODS FOR PROJECTS
	
	// Query for finding a project by project ID
	public ResultSet getProjectID() throws SQLException {
		String query = "SELECT ProjectID FROM dbo.PROJECT";
		Connection connection = DriverManager.getConnection(connectionURL);
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet projectResult = ps.executeQuery();
		return projectResult;
	}


	
	// Method for generating a project ID
	public static String generateProjectID() {
		Random rand = new Random();
		String id = "P";
		for(int i = 0; i < 5; i++) {
			id += rand.nextInt(10);
		}
		return id;
	}
	
	// #### ALL METHODS FOR CONSULTANT 
	
	// Query for finding a consultant by employee ID
	public ResultSet getConsultantEmpID() throws SQLException {
		Connection connection = DriverManager.getConnection(connectionURL);
		String query = "SELECT EmpID FROM CONSULTANT";
		PreparedStatement ps = connection.prepareStatement(query);
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
 
    // method for getting managers
   public ResultSet getManagers() throws SQLException {
	   Connection connection = DriverManager.getConnection(connectionURL);
	   String query = "SELECT ConsultantName FROM CONSULTANT WHERE ManagerID IS NULL";
	   PreparedStatement ps = connection.prepareStatement(query);
		ResultSet projectResult = ps.executeQuery();
		return projectResult;
   }
	
	// #### ALL METHODS FOR MILESTONES 
	
	// Query for viewing milestones
	public ResultSet viewMilestones() throws SQLException {
		String query = "SELECT MilestoneType AS Milestones FROM Milestones";
		Connection connection = DriverManager.getConnection(connectionURL);
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet projectResult = ps.executeQuery();
		return projectResult;
	}
	
	
	// Query for viewing staffing information for a project. I.e. all consultants currently working on the project and their hours
	public ResultSet viewStaffOnProject() throws SQLException {
		String query = "SELECT c.EmpID, p.ProjectID, c.ConsultantName, Hours  FROM WORK w, CONSULTANT c, PROJECT p WHERE w.EmpID = c.EmpID AND w.ProjectID = p.ProjectID AND w.IsActive = 'true'\r\n"
				+ "";
		Connection connection = DriverManager.getConnection(connectionURL);
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet projectResult = ps.executeQuery();
		return projectResult;
	}
	
	// Query for finding all consultants who have previously worked on a certain project
	public ResultSet hasWorked() throws SQLException {
		String query = "SELECT c.EmpID, p.ProjectID, c.ConsultantName FROM WORK w, CONSULTANT c, PROJECT p WHERE w.EmpID = c.EmpID AND w.ProjectID = p.ProjectID AND w.IsActive = 'false'";
		Connection connection = DriverManager.getConnection(connectionURL);
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet projectResult = ps.executeQuery();
		return projectResult;
	}
	
	// Show number of hours for a certain consultant on a certain project
	public ResultSet hoursOnProject() throws SQLException{
		String query = "SELECT w.Hours, p.ProjectID, c.EmpID FROM CONSULTANT c,PROJECT p, WORK w WHERE c.EmpID = w.EmpID AND p.ProjectID = w.EmpID";
		Connection connection = DriverManager.getConnection(connectionURL);
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet projectResult = ps.executeQuery(query);
		return projectResult;
	}
	
	
			
}
