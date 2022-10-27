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
	public int createEmployee(String empID, String name, String address, String startDate, int salary) // camelCasing
			throws SQLException {

		String query = "INSERT INTO CONSULTANT VALUES(?,?,?,?,?) SET NOCOUNT ON";
		Connection connection = DriverManager.getConnection(connectionURL);
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, empID);
		ps.setString(2, name);
		ps.setString(3, address);
		ps.setString(4, startDate);
		ps.setInt(5, salary);
		return ps.executeUpdate();
	}

	// Creating employees who are ACTIVE
	public int createWorkingConsultants(String empID, String projectID) throws SQLException {
		String query = "INSERT INTO WORK VALUES (?,?,?)";
		Connection connection = DriverManager.getConnection(connectionURL);
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, empID);
		ps.setString(2, projectID);
		ps.setString(3, null);
		return ps.executeUpdate();

	}

	// create employees who are INACTIVE
	public int createNotWorkingConsultants(String empID, String projectID) throws SQLException {
		String query = "INSERT INTO HASWORK VALUES (?,?)";
		Connection connection = DriverManager.getConnection(connectionURL);
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, empID);
		ps.setString(2, projectID);
		return ps.executeUpdate();
	}

	// query for attaining all information about a consultant based on a given
	// EmpID.
	public ResultSet getAllConsultantInformation(String empID) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionURL);
		String query = "SELECT * FROM CONSULTANT WHERE EmpID = '" + empID + "'";
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet idResult = ps.executeQuery();
		return idResult;
	}

	// View all consultants and their hours on a specific project
	public ResultSet getConsultantInfoForProject(String projectID, String empID) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionURL);
		String query = "SELECT p.ProjectID, EmpID, hours\r\n"
				+ "FROM Project p\r\n JOIN Work w ON p.ProjectID= w.ProjectID WHERE p.ProjectID = '" + projectID + "'"
				+ " AND w.EmpID = '" + empID + "'";
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet idResultProject = ps.executeQuery();
		System.out.print("hej");
		return idResultProject;
	}

	// Finding all consultants who have previously worked on a certain project
	public ResultSet getConsultantsFromHASWORK(String projectID) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionURL);
		String query = "SELECT ProjectName, p.ProjectID, EmpID\r\n" + "FROM Project p\r\n" + "join HASWORK w\r\n"
				+ "on p.ProjectID= w.ProjectID\r\n" + "WHERE p.ProjectID = '" + projectID + "'";
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet idResultProject = ps.executeQuery();
		return idResultProject;
	}

	// --------------------------------------------------- QUERIES FOR PROJECTS
	// ---------------------------------------------------//

	// create projects
	public int createProject(String projectID, int budget, String projectName, String projectStartdate)
			throws SQLException {
		String query = "INSERT INTO PROJECT VALUES (?,?,?,?) SET NOCOUNT ON";
		Connection connection = DriverManager.getConnection(connectionURL);
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, projectID);
		ps.setInt(2, budget);
		ps.setString(3, projectName);
		ps.setString(4, projectStartdate);
		return ps.executeUpdate();

	}

	// View specific project information ABOUT A PROJECT by giving a specific
	// projectID (only if there are employees)
	public ResultSet getAllProjectInformation(String projectID) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionURL);
		String queryTest = "SELECT ProjectName, w.ProjectID, EmpID, hours, Budget, ProjectStartDate FROM Project p, Work w WHERE w.ProjectID = '"
				+ projectID + "'";
		PreparedStatement ps = connection.prepareStatement(queryTest);
		ResultSet IDResultProject = ps.executeQuery();
		return IDResultProject;
	}

	// projectID (only if there isn´t employees)
	public ResultSet viewProject(String projectID) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionURL);
		String queryTest = "SELECT * FROM Project WHERE ProjectID = '" + projectID + "'";
		PreparedStatement ps = connection.prepareStatement(queryTest);
		ResultSet idResultProject = ps.executeQuery();
		return idResultProject;
	}

	// without employees

	public ResultSet projectInfo(String projectID) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionURL);
		String query = "SELECT ProjectID,hours FROM WORK WHERE ProjectID = '" + projectID + "'";
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet resultProject = ps.executeQuery();
		return resultProject;
	}

	// Query for viewing staffing information for a project. I.e. all consultants
	// currently working on the project and their hours
	public ResultSet viewStaffOnProject(String projectID) throws SQLException {
		String query = "SELECT EmpID, ProjectID FROM HASWORK WHERE ProjectID = '" + projectID + "'";
		Connection connection = DriverManager.getConnection(connectionURL);
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet idResultProject = ps.executeQuery();
		return idResultProject;
	}

	// --------------------------------------------------- QUERIES FOR MILESTONES
	// ---------------------------------------------------//

	// create milestone
	public int createMilestone(String type, String projectID, String dateOfCompletion) throws SQLException {
		String query = "INSERT INTO MILESTONE VALUES (?,?,?) SET NOCOUNT ON";
		Connection connection = DriverManager.getConnection(connectionURL);
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, type);
		ps.setString(2, projectID);
		ps.setString(3, dateOfCompletion);
		return ps.executeUpdate();
	}

	// View all information about one selected milestones

	public ResultSet viewSpecificMilestones(String milestoneType) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionURL);
		String query = "SELECT * FROM MILESTONE WHERE MilestoneType = '" + milestoneType + "'";
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet milestoneResult = ps.executeQuery();
		return milestoneResult;
	}

	// VIEW FKN HOURS
	public ResultSet viewTimeConsultant(String empID, String projectID) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionURL);
		String query = "SELECT hours,EmpID,ProjectID FROM WORK WHERE EmpID = '" + empID + "' AND ProjectID = '"
				+ projectID + "'";
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet milestoneResult = ps.executeQuery();
		return milestoneResult;
	}

	// Finding projects for a specific consultant

	public ResultSet viewSpecificProject(String empID) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionURL);
		String query = "SELECT ProjectID FROM WORK Where EmpID  = '" + empID + "'";
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet projectResult = ps.executeQuery();
		return projectResult;
	}

	// Query for Inserting hours in to TABLE "WORK"

	public int assignHours(String empID, String projectID, int hours) throws SQLException {
		String query = "UPDATE WORK SET hours = " + hours + " WHERE EmpID = '" + empID + "'" + " AND ProjectID = '"
				+ projectID + "'";
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
		ResultSet idResult = ps.executeQuery();
		return idResult;

		// SELECT DISTINCT w.EmpID FROM WORK w, CONSULTANT c WHERE w.EmpID = c.EmpID
		// (denna istället kanske)
	}

	// query for finding the specific projects of a certain consultant
	public ResultSet getProjectIDForSpecificConsultant(String empID) throws SQLException {
		Connection connection = DriverManager.getConnection(connectionURL);
		String query = "SELECT ProjectID FROM WORK WHERE EmpID = '" + empID + "'";
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet projectIDResullt = ps.executeQuery();
		return projectIDResullt;
	}

	// Query for finding and returning all Milestones (for comboBox)
	public ResultSet viewAllMilestones() throws SQLException {
		Connection connection = DriverManager.getConnection(connectionURL);
		String query = "SELECT MilestoneType FROM MILESTONE";
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet milestoneResult = ps.executeQuery();
		return milestoneResult;
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
