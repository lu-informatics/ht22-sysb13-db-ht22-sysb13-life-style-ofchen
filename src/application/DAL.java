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
		 
		/*   System.out.println("DB server name: " +System.getenv("DATABASE_SERVER_NAME"));
		   System.out.println("port: " + System.getenv("DATABASE_SERVER_PORT"));
		   System.out.println("DB namn: " + System.getenv("DATABASE_NAME") );
		   System.out.println("DB User name: " + System.getenv("DATABASE_USER_NAME"));
		   System.out.println("DB User Password: " + System.getenv("DATABASE_USER_PASSWORD") );
		   
		   */
	   
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
	

	
	// #### Creating consultants 
	   public void createEmployee(String Name, String Address, String Startdate, int Salary) 
			   throws SQLException {
		   
		   String EmpID = generateConsultantID();
		   String query = "INSERT INTO CONSULTANT VALUES(?,?,?,?) SET NOCOUNT ON";
		   Connection connection = DriverManager.getConnection(connectionURL);
		   PreparedStatement ps = connection.prepareStatement(query);
		   ps.setString(1, Name);
		   ps.setString(2, Address);
		   ps.setString(3, Startdate);
		   ps.setInt(4, Salary);
		   ps.executeUpdate();
		   ps.close();
		   connection.close();
		  }
	  
	   
	   // create milestone 
	   
	   public void createMilestone(String Type, int ProjectID, int dateOfCompletion) 
			   throws SQLException {
		  
			   String query = "INSERT INTO MILESTONES VALUES (?,?,?)";
			   Connection connection = DriverManager.getConnection(connectionURL);
			   PreparedStatement ps = connection.prepareStatement(query);
			   ps.setString(1, Type);
			   ps.setInt(2,ProjectID);
			   ps.setInt(3, dateOfCompletion);
			   
			   ps.executeUpdate();
			   ps.close();
			   connection.close();
					   
		   }
	
	   
	   // create projects
	   public void createProject(int Budget, String ProjectName, String ProjectStartdate)
			   throws SQLException{
		   
			   int ProjectID = generateProjectID();
			   String query ="INSERT INTO PROJECT VALUES (?,?,?)";
			   Connection connection = DriverManager.getConnection(connectionURL);
			   PreparedStatement ps = connection.prepareStatement(query);
			   ps.setInt(1, Budget);
			   ps.setString(2, ProjectName);
			   ps.setString(3, ProjectStartdate);
			   
			   ps.executeUpdate();
			   ps.close();
			   connection.close();
			   
	   }
	   
	// View project 
	  public ResultSet getProject(int ProjectID) throws SQLException {
		String query = "SELECT ProjectID FROM PROJECT";
		Connection connection = DriverManager.getConnection(connectionURL);
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setInt(1, ProjectID);
		ResultSet projectResult = ps.executeQuery();
		return projectResult;
	}

	
	// Method for generating a project ID
	public static int generateProjectID() {
		Random rand = new Random();
		int id = 0;
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
	
	/*
	// #### Metadata 1

		public ResultSetMetaData metadata_1() throws SQLException {
			Connection con2 = login();
			String query = "SELECT  p.BusinessEntityID, e.Gender, e.NationalIDNumber, e.LoginID , p.ModifiedDate , p.rowguid "
					+ "FROM HumanResources.Employee e, Person.BusinessEntity p "
					+ "WHERE e.BusinessEntityID = p.BusinessEntityID";
			PreparedStatement ps = con2.prepareStatement(query);
			ResultSet resultSet = ps.executeQuery();
			ResultSetMetaData rsmd = resultSet.getMetaData();
			return rsmd;
		}

		// #### Metadata 2: All Keys

		public ResultSetMetaData metadata2_AllKeys() throws SQLException {
			Connection con2 = login();
			String query = "USE AdventureWorks2019 "
					+ "	SELECT  pk.CONSTRAINT_NAME AS 'KEYS' "
					+ "	FROM  INFORMATION_SCHEMA.KEY_COLUMN_USAGE pk "
					+ "	GROUP BY pk.CONSTRAINT_NAME";
			PreparedStatement ps = con2.prepareStatement(query);
			ResultSet resultSet = ps.executeQuery();
			ResultSetMetaData rsmd = resultSet.getMetaData();
			return rsmd;
		}
		
		// #### Metadata 2: All table_constraints

		public ResultSetMetaData metadata2_AllTableConstraints() throws SQLException {
			Connection con2 = login();
			String query = "SELECT * "
					+ "FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS";
			PreparedStatement ps = con2.prepareStatement(query);
			ResultSet resultSet = ps.executeQuery();
			ResultSetMetaData rsmd = resultSet.getMetaData();
			return rsmd;
		}
		
		// #### Metadata 2: 1 - All tables in AdventureWorks2019

		public ResultSetMetaData metadata2_AllTablesInDatabse1()throws SQLException {
			Connection con2 = login();
		String query = "SELECT name "
				+ "FROM AdventureWorks2019.sys.tables";
		PreparedStatement ps = con2.prepareStatement(query);
		ResultSet resultSet = ps.executeQuery();
		ResultSetMetaData rsmd = resultSet.getMetaData();
		return rsmd;
		}
		

		// #### Metadata 2: 2 - All tables in AdventureWorks2019

		public ResultSetMetaData metadata2_AllTablesInDatabse2()throws SQLException {
			Connection con2 = login();
		String query = "SELECT TABLE_NAME*TABLE_NAME "
				+ "FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS";
		PreparedStatement ps = con2.prepareStatement(query);
		ResultSet resultSet = ps.executeQuery();
		ResultSetMetaData rsmd = resultSet.getMetaData();
		return rsmd;
		}
		
		// #### Metadata 2: 1  All columns in the SalesLT.Customer table
		
		public ResultSetMetaData metadata2_AllColumnsInSalesCustomer1() throws SQLException {
			Connection con2 = login();
		String query = "SELECT * "
				+ "FROM Sales.Customer";
		PreparedStatement ps = con2.prepareStatement(query);
		ResultSet resultSet = ps.executeQuery();
		ResultSetMetaData rsmd = resultSet.getMetaData();
		return rsmd;
		}
		
		// #### Metadata 2: 2  All columns in the SalesLT.Customer table
		
		public ResultSetMetaData  metadata2_AllColumnsInSalesCustomer2() throws SQLException {
			Connection con2 = login();
		String query =  "SELECT name AS 'Columns' "
				+ "FROM syscolumns where id=object_id('Sales.Customer')";
		PreparedStatement ps = con2.prepareStatement(query);
		ResultSet resultSet = ps.executeQuery();
		ResultSetMetaData rsmd = resultSet.getMetaData();
		return rsmd;
			}

		
		// #### Metadata 2: The name of the table in the AdventureWorks2019 containing 
		// #### the highest number of rows 
		
		public ResultSetMetaData metadata2_NameOfTableWithHighestNumberOfRows() throws SQLException{
			Connection con2 = login();
			String query = "SELECT QUOTENAME(SCHEMA_NAME(sO.schema_id)) + '.' + QUOTENAME(sO.name) AS TableName, sdmvPTNS.row_count "
					+ "FROM sys.objects as sO"
					+ "JOIN sys.dm_db_partition_stats AS sdmvPTNS ON sO.object_id = sdmvPTNS.object_id "
					+ "WHERE sO.type = 'U' /* U = Table */// "
		//			+ "AND sO.is_ms_shipped = 0x0 /*Object is createed by an internal SQL Server component with a 0*0 bit*/ "
			//		+ "AND sdmvPTNS.index_id < 2 /* INDEX ID of sdmvPTRN, show less than 2*/"
//					+ ""
	//				+ "GROUP BY sO.schema_ID, sO.name , sdmvPTNS.row_count "
		//			+ "ORDER BY sdmvPTNS.row_count DESC";





			/*
			PreparedStatement ps = con2.prepareStatement(query);
			ResultSet resultSet = ps.executeQuery();
			ResultSetMetaData rsmd = resultSet.getMetaData();

			System.out.println("TableName: " + rsmd.getTableName(1));
			System.out.println("*******************");

			return rsmd;
		}
*/
		
	}

	
			//hej

