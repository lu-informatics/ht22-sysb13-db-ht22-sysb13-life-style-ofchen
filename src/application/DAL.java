package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAL {

	private String connectionURL;

	public DAL() throws IOException {

		try {
			String databaseServerName = System.getenv("DATABASE_SERVER_NAME");
			String databaseServerPort = System.getenv("DATABASE_SERVER_PORT");
			String databaseName = System.getenv("DATABASE_NAME");
			String databaseUserName = System.getenv("DATABASE_USER_NAME");
			String databaseUserPassword = System.getenv("DATABASE_USER_PASSWORD");
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connectionURL = "jdbc:sqlserver://" + databaseServerName + ":" + databaseServerPort + ";" + "database="
					+ databaseName + ";" + "user=" + databaseUserName + ";" + "password=" + databaseUserPassword + ";"
					+ "encrypt=true;" + "trustServerCertificate=true";
		} catch (Exception e) {

		}
	}

	// --------------------------------------------------- QUERIES FOR CONSULTANTS
	// ---------------------------------------------------//
	// Creating consultants
	public int createEmployee(String EmpID, String Name, String Address, String Startdate, int Salary)
			throws SQLException {
		try {
			String query = "INSERT INTO CONSULTANT VALUES(?,?,?,?,?) SET NOCOUNT ON";
			Connection connection = DriverManager.getConnection(connectionURL);
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, EmpID);
			ps.setString(2, Name);
			ps.setString(3, Address);
			ps.setString(4, Startdate);
			ps.setInt(5, Salary);
			return ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("e: " + e.getMessage());
		}
		return 0;
	}

	// Creating employees who are ACTIVE
	public int createWorkingConsultants(String EmpID, String ProjectID) throws SQLException {
		String query = "INSERT INTO WORK VALUES (?,?,?)";
		Connection connection = DriverManager.getConnection(connectionURL);
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, EmpID);
		ps.setString(2, ProjectID);
		ps.setString(3, null);
		return ps.executeUpdate();

	}

	// create employees who are INACTIVE
	public int createNotWorkingConsultants(String EmpID, String ProjectID) throws SQLException {
		String query = "INSERT INTO HASWORK VALUES (?,?)";
		Connection connection = DriverManager.getConnection(connectionURL);
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, EmpID);
		ps.setString(2, ProjectID);
		return ps.executeUpdate();
	}

	// query for attaining all information about a consultant based on a given
	// EmpID.
	public ResultSet getAllConsultantInformation(String EmpID) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionURL);
		String query = "SELECT * FROM CONSULTANT WHERE EmpID = '" + EmpID + "'";
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet IDResult = ps.executeQuery();
		return IDResult;
	}

	// View all consultants and their hours on a specific project
	public ResultSet getConsultantInfoForProject(String ProjectID, String EmpID) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionURL);
		String query = "SELECT p.ProjectID, EmpID, hours\r\n"
				+ "FROM Project p\r\n JOIN Work w ON p.ProjectID= w.ProjectID WHERE p.ProjectID = '" + ProjectID + "'"
				+ " AND w.EmpID = '" + EmpID + "'";
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet IDResultProject = ps.executeQuery();
		System.out.print("hej");
		return IDResultProject;
	}

	// Finding all consultants who have previously worked on a certain project
	public ResultSet getConsultantsFromHASWORK(String ProjectID) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionURL);
		String query = "SELECT ProjectName, p.ProjectID, EmpID\r\n" + "FROM Project p\r\n" + "join HASWORK w\r\n"
				+ "on p.ProjectID= w.ProjectID\r\n" + "WHERE p.ProjectID = '" + ProjectID + "'";
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet IDResultProject = ps.executeQuery();
		return IDResultProject;
	}

	// --------------------------------------------------- QUERIES FOR PROJECTS
	// ---------------------------------------------------//

	// create projects
	public int createProject(String ProjectID, int Budget, String ProjectName, String ProjectStartdate)
			throws SQLException {
		try {
			String query = "INSERT INTO PROJECT VALUES (?,?,?,?) SET NOCOUNT ON";
			Connection connection = DriverManager.getConnection(connectionURL);
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, ProjectID);
			ps.setInt(2, Budget);
			ps.setString(3, ProjectName);
			ps.setString(4, ProjectStartdate);
			return ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("e: " + e.getMessage());
		}
		return 0;
	}

	// View specific project information ABOUT A PROJECT by giving a specific
	// projectID (only if there are employees)
	public ResultSet getAllProjectInformation(String ProjectID) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionURL);
		String queryTest = "SELECT ProjectName, w.ProjectID, EmpID, hours, Budget, ProjectStartDate FROM Project p, Work w WHERE w.ProjectID = '"
				+ ProjectID + "'";
		PreparedStatement ps = connection.prepareStatement(queryTest);
		ResultSet IDResultProject = ps.executeQuery();
		return IDResultProject;
	}

	// projectID (only if there isn´t employees)
	public ResultSet viewProject(String ProjectID) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionURL);
		String queryTest = "SELECT * FROM Project WHERE ProjectID = '" + ProjectID + "'";
		PreparedStatement ps = connection.prepareStatement(queryTest);
		ResultSet IDResultProject = ps.executeQuery();
		return IDResultProject;
	}

	// without employees

	public ResultSet projectInfo(String ProjectID) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionURL);
		String query = "SELECT ProjectID,hours FROM WORK WHERE ProjectID = '" + ProjectID + "'";
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet ResultProject = ps.executeQuery();
		return ResultProject;
	}

	// Query for viewing staffing information for a project. I.e. all consultants
	// currently working on the project and their hours
	public ResultSet viewStaffOnProject(String ProjectID) throws SQLException {
		String query = "SELECT EmpID, ProjectID FROM HASWORK WHERE ProjectID = '" + ProjectID + "'";
		Connection connection = DriverManager.getConnection(connectionURL);
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet IDResultProject = ps.executeQuery();
		return IDResultProject;
	}

	// --------------------------------------------------- QUERIES FOR MILESTONES
	// ---------------------------------------------------//

	// create milestone
	public int createMilestone(String Type, String ProjectID, String dateOfCompletion) throws SQLException {
		String query = "INSERT INTO MILESTONE VALUES (?,?,?) SET NOCOUNT ON";
		Connection connection = DriverManager.getConnection(connectionURL);
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, Type);
		ps.setString(2, ProjectID);
		ps.setString(3, dateOfCompletion);
		return ps.executeUpdate();
	}

	// View all information about one selected milestones

	public ResultSet viewSpecificMilestones(String MilestoneType) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionURL);
		String query = "SELECT * FROM MILESTONE WHERE MilestoneType = '" + MilestoneType + "'";
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet MilestoneResult = ps.executeQuery();
		return MilestoneResult;
	}

	// VIEW FKN HOURS
	public ResultSet ViewTimeConsultant(String EmpID, String ProjectID) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionURL);
		String query = "SELECT hours,EmpID,ProjectID FROM WORK WHERE EmpID = '" + EmpID + "' AND ProjectID = '"
				+ ProjectID + "'";
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet MilestoneResult = ps.executeQuery();
		return MilestoneResult;
	}

	// Finding projects for a specific consultant

	public ResultSet viewSpecificProject(String EmpID) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionURL);
		String query = "SELECT ProjectID FROM WORK Where EmpID  = '" + EmpID + "'";
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet ProjectResult = ps.executeQuery();
		return ProjectResult;
	}

	// Query for Inserting hours in to TABLE "WORK"

	public int AssignHours(String EmpID, String ProjectID, int hours) throws SQLException {
		String query = "UPDATE WORK SET hours = " + hours + " WHERE EmpID = '" + EmpID + "'" + " AND ProjectID = '"
				+ ProjectID + "'";
		Connection connection = DriverManager.getConnection(connectionURL);
		PreparedStatement ps = connection.prepareStatement(query);
		return ps.executeUpdate();
	}

	// -------------------------------------------------- QUERIES FOR COMBOBOXES
	// -------------------------------------------- //

	// Query for finding and returning all EmpID´s for comboBox)
	public ResultSet getAllConsultantEmpID() throws SQLException {
		Connection connection = DriverManager.getConnection(connectionURL);
		String query = "SELECT EmpID FROM CONSULTANT";
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet projectResult = ps.executeQuery();
		return projectResult;
	}

	// query for finding EmpID of those who work on a project for combBox
	public ResultSet getEmpIDWorkingConsultant() throws SQLException {
		Connection connection = DriverManager.getConnection(connectionURL);
		String query = "SELECT Distinct w.EmpID \r\n" + "FROM WORK w, CONSULTANT c \r\n" + "WHERE w.EmpID = c.EmpID";
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet IDResult = ps.executeQuery();
		return IDResult;

		// SELECT DISTINCT w.EmpID FROM WORK w, CONSULTANT c WHERE w.EmpID = c.EmpID
		// (denna istället kanske)
	}

	// query for finding the specific projects of a certain consultant
	public ResultSet getProjectIDForSpecificConsultant(String EmpID) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionURL);
		String query = "SELECT ProjectID FROM WORK WHERE EmpID = '" + EmpID + "'";
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet ProjectIDResullt = ps.executeQuery();
		return ProjectIDResullt;
	}

	// Query for finding and returning all Milestones (for comboBox)
	public ResultSet viewAllMilestones() throws SQLException {
		Connection connection = DriverManager.getConnection(connectionURL);
		String query = "SELECT MilestoneType FROM MILESTONE";
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet MilestoneResult = ps.executeQuery();
		return MilestoneResult;
	}

	// View project
	public ResultSet getProject() throws SQLException {
		String query = "SELECT ProjectID FROM PROJECT";
		Connection connection = DriverManager.getConnection(connectionURL);
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet projectResult = ps.executeQuery();
		return projectResult;
	}

}
