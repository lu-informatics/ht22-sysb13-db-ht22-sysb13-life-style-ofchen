package application;
import java.time.LocalDate;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Random; 


public class DAL {
	
	   private String connectionURL;
	   
	   public DAL() throws IOException { 
		 
	   
	   String databaseServerName = System.getenv("DATABASE_SERVER_NAME"); 
	   String databaseServerPort = System.getenv("DATABASE_SERVER_PORT");
	   String databaseName = System.getenv("DATABASE_NAME");
	   String databaseUserName = System.getenv("DATABASE_USER_NAME");
	   String databaseUserPassword = System.getenv("DATABASE_USER_PASSWORD"); 
	   //String connectionURL = "jdbc:sqlserver://vmdev001:1433;";

	   try {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

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
	

	
	// Creating consultants 
	   public boolean createEmployee(String EmpID, String Name, String Address, String Startdate, int Salary) 
			   throws SQLException {
		   String query = "INSERT INTO CONSULTANT OUTPUT INSERTED.EmpID VALUES(?,?,?,?,?,?) SET NOCOUNT ON";
		   Connection connection = DriverManager.getConnection(connectionURL);
		   PreparedStatement ps = connection.prepareStatement(query);
		   ps.setString(1, EmpID);
		   ps.setString(2, Name);
		   ps.setString(3, Address);
		   ps.setString(4, Startdate);
		   ps.setInt(5, Salary);
		   ps.setString(6, null);
		   return ps.execute();
		  }
	   
	   // create projects
	   public boolean createProject(int ProjectID, int Budget, String ProjectName, String ProjectStartdate)
			   throws SQLException{
		       String query ="INSERT INTO PROJECT VALUES (?,?,?,?)";
			   Connection connection = DriverManager.getConnection(connectionURL);
			   PreparedStatement ps = connection.prepareStatement(query);
			   ps.setInt(1, ProjectID);
			   ps.setInt(2, Budget);
			   ps.setString(3, ProjectName);
			   ps.setString(4, ProjectStartdate);
			  return ps.execute();
			  
	   }

	// Query for viewing staffing information for a project. I.e. all consultants currently working on the project and their hours
		public boolean viewStaffOnProject(int ProjectID) 
			throws SQLException {
			String query = "SELECT EmpID, ProjectID, Hours FROM WORK WHERE ProjectID = ?";
			Connection connection = DriverManager.getConnection(connectionURL);
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, ProjectID);
			return ps.execute();
			
		}
	// Query for Inserting hours in to TABLE "WORK" 
		
		public boolean AssignHours (String EmpID, int ProjectID, float Hours)
				throws SQLException { 
				String query = "INSERT INTO MILESTONES VALUES (?,?,?)";
				Connection connection = DriverManager.getConnection(connectionURL);
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setString(1, EmpID);
				ps.setInt(2, ProjectID);
				ps.setFloat(3, Hours);
				return ps.execute();
				
		
		}
		
	   
	   // create milestone 
	   
	   public boolean createMilestone(String Type, int ProjectID, int dateOfCompletion) 
			   throws SQLException {
			   String query = "INSERT INTO MILESTONES VALUES (?,?,?)";
			   Connection connection = DriverManager.getConnection(connectionURL);
			   PreparedStatement ps = connection.prepareStatement(query);
			   ps.setString(1, Type);
			   ps.setInt(2, ProjectID);
			   ps.setInt(3, dateOfCompletion);
			   return ps.execute();
		   }
	
	   
	  
	  
	
	// #### ALL METHODS FOR CONSULTANT 
	
	// Query for finding returning all EmpID´s
	public ResultSet getAllConsultantEmpID() throws SQLException {
		Connection connection = DriverManager.getConnection(connectionURL);
		String query = "SELECT EmpID FROM CONSULTANT";
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet projectResult = ps.executeQuery();
		return projectResult;
	}
	
	// query for attaining all information about a consultant  based on a given EmpID
	public ResultSet getAllConsultantInformation(String EmpID) throws SQLException {
		Connection connection= DriverManager.getConnection(connectionURL);
		String query = "SELECT * FROM CONSULTANT WHERE EmpID = '" + EmpID + "'";
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet IDResult = ps.executeQuery();
		return IDResult;
	}
	
 
    // method for getting managers
   public ResultSet getManagers() throws SQLException {
	   Connection connection = DriverManager.getConnection(connectionURL);
	   String query = "SELECT EmpID, ConsultantName FROM CONSULTANT WHERE ManagerID IS NULL";
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
	
	// View project 
		  public ResultSet getProject() throws SQLException {
			String query = "SELECT ProjectID FROM PROJECT";
			Connection connection = DriverManager.getConnection(connectionURL);
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet projectResult = ps.executeQuery();
			return projectResult;
		}
		  
	// View specific project 
		 public ResultSet getAllProjectInformation(int ProjectID) throws SQLException {
			 String query = "SELECT * FROM PROJECT WHERE ProjectID = " + ProjectID; 
			 Connection connection = DriverManager.getConnection(query);
			 PreparedStatement ps = connection.prepareStatement(query);
			 ResultSet IDResultProject = ps.executeQuery();
			 return IDResultProject;
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

