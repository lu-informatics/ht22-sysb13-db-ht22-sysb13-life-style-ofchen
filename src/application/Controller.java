package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.shape.Line;

public class Controller {
	DAL dal = new DAL();

	// CONSULTANT FXML

	// Text area

	@FXML
	private TextArea consultantTextAreaInformation = new TextArea();
	@FXML
	private TextArea TextAreaConsultant = new TextArea();

	// DatePickers
	@FXML
	private DatePicker DatePickerConsultant = new DatePicker();

	// TextField
	@FXML
	private TextField TextFieldConsultantName = new TextField();
	@FXML
	private TextField TextFieldEmpID = new TextField();
	@FXML
	private TextField TextFieldConsultantAddress = new TextField();
	@FXML
	private TextField TextFieldSalary = new TextField();

	// Button
	@FXML
	private Button ButtonCreateEmployee = new Button();

	@FXML
	private Button ConsultantViewButton = new Button();

	// Line and combobox
	@FXML
	private Line ConsultantLine = new Line();

	@FXML
	private ComboBox<String> ConsultantComboBox = new ComboBox<String>();

	// -------------------------------------------------------------------------------------------------------------------//

	// LOG

	// TextFields
	@FXML
	private TextField TextFieldLogHours = new TextField();

	// ComboBoxes
	@FXML
	private ComboBox ComboBoxLogConsultants = new ComboBox();
	@FXML
	private ComboBox ComboBoxLogProjects = new ComboBox();
	@FXML
	private ComboBox ComboboxLogViewTimeConsultant = new ComboBox();
	@FXML
	private ComboBox ComboBoxLogProjects1 = new ComboBox();

	// RunButton
	@FXML
	private Button ButtonAssignTime = new Button();

	@FXML
	private Button ButtonViewHours = new Button();

	// Text Area
	@FXML
	private TextArea TextAreaLog = new TextArea();

	@FXML
	private TextArea ViewTimeWorked = new TextArea();

	// Line
	@FXML
	private Line LineLog = new Line();

	// ------------------------------------------------------------------------------------------------------------------//

	// PROJECT
	@FXML
	private TextArea viewProjectInformationTextArea = new TextArea();

	// TextFields
	@FXML
	private TextField TextFieldProjectName = new TextField();
	@FXML
	private TextField TextFieldProjectID = new TextField();
	@FXML
	private TextField TextFieldProjectBudget = new TextField();

	@FXML
	private TextField TextFieldProjectHours = new TextField();

	// DatePicker
	@FXML
	private DatePicker DatePickerProject = new DatePicker();

	// Table Column + view
	@FXML
	private TableView ProjectTableView = new TableView();
	@FXML
	private TableColumn ProjectTableColumnName = new TableColumn();
	@FXML
	private TableColumn ProjectTableColumnProjectID = new TableColumn();
	@FXML
	private TableColumn ProjectTableColumnHours = new TableColumn();

	// RadioButton

	@FXML
	private RadioButton RadioButtonYes = new RadioButton();
	@FXML
	private RadioButton RadioButtonNo = new RadioButton();

	// Button
	@FXML
	private Button ButtonViewProject = new Button();
	@FXML
	private Button ButtonCreateProject = new Button();
	@FXML
	private Button ButtonAssignConsultant = new Button();

	@FXML
	private Button ButtonViewHasWork = new Button();

	// ComboBoxes
	@FXML
	private ComboBox ComboBoxProjectProjects = new ComboBox();
	@FXML
	private ComboBox ComboBoxProjectConsultants = new ComboBox();
	@FXML
	private ComboBox ComboBoxProjectAdd = new ComboBox();
	@FXML
	private ComboBox ComboBoxViewProject = new ComboBox();

	@FXML
	private ComboBox ComboBoxProjectAddConsultant = new ComboBox();

	// ---------------------------------------------------------------------------------------------------------------------//

	// MILESTONE

	// TextField
	@FXML
	private TextField TextFieldMilestonesType = new TextField();

	// RadioButton
	@FXML
	private Button RunButtonCreateMileStone = new Button();
	@FXML
	private Button ViewMilestone = new Button();

	// ComboBoxes
	@FXML
	private ComboBox<String> ComboBoxMilestoneMilestone = new ComboBox();

	@FXML
	private ComboBox<String> ComboBoxMilestoneProject = new ComboBox();

	@FXML
	private DatePicker DatePickerMilestone = new DatePicker();

	// ----------------------------------------------------------------------------------------------------------------------//

	// TabePane
	@FXML
	private TabPane tabpane = new TabPane();

	// All TextAreas
	@FXML
	private TextArea textarea = new TextArea();

	@FXML
	private TextArea TextAreaProject = new TextArea();
	@FXML
	private TextArea TextAreaMilestone = new TextArea();

	// All tabs
	@FXML
	private Tab TabConsultant = new Tab();
	@FXML
	private Tab TabLog = new Tab();
	@FXML
	private Tab TabProject = new Tab();
	@FXML
	private Tab TabMilestone = new Tab();

	@FXML
	private ToggleGroup tgConsultant = new ToggleGroup();

	// END OF FXML

	// ----------------------------------------------------//----------------------------------------------------//----------------------------------------------------//

	// DATEPICKER METHODS
	public void getDateConsultant(ActionEvent event) {
		LocalDate myDate = DatePickerConsultant.getValue();
	}

	public void getDateMilestone(ActionEvent event) {
		LocalDate myDate = DatePickerMilestone.getValue();
	}

	public void getDateProject(ActionEvent event) {
		LocalDate myDate = DatePickerMilestone.getValue();
	}

	// ----------------------------------------------------//----------------------------------------------------//----------------------------------------------------//

	// CREAT METHODS

	// Creating an employee
	public void createEmployeeRunButton() throws SQLException {
		try {
			if (!TextFieldEmpID.getText().trim().isEmpty() && !TextFieldConsultantName.getText().isEmpty()
					&& !TextFieldConsultantAddress.getText().isEmpty() && DatePickerConsultant.getValue() != null
					&& !TextFieldSalary.getText().isEmpty()) {
				// only allowing numbers for the id and setting the lenght to 10 digits
				if (!Pattern.matches("[0-9]+", TextFieldEmpID.getText()) || TextFieldEmpID.getText().length() != 10) {
					TextAreaConsultant.setText("Only positive 10 digit numbers are allowed as ID." + "\n"
							+ " Please do not use any special charcaters like " + "-+%&" + "\n"
							+ "Try again using this information!");
				} else {
					// if everything is entered correctly, we first create the employee in our
					// database
					int check = dal.createEmployee(TextFieldEmpID.getText().trim(),
							TextFieldConsultantName.getText().trim(), TextFieldConsultantAddress.getText().trim(),
							DatePickerConsultant.getValue().toString(),
							Integer.valueOf(TextFieldSalary.getText().trim()));
					if (check == 1 || check == 2) {
						// if the creation of employee (consultant) was successfull, we let the user
						// know it was successfull
						TextAreaConsultant.setText("The employee: " + TextFieldConsultantName.getText() + " ("
								+ TextFieldEmpID.getText() + ") was created!" + "\n" + "Address: "
								+ TextFieldConsultantAddress.getText() + "\n" + "Start date: "
								+ DatePickerConsultant.getValue() + "\n" + "Salary: " + TextFieldSalary.getText());
						TextFieldConsultantName.clear();
						TextFieldConsultantAddress.clear();
						TextFieldSalary.clear();
						TextFieldEmpID.clear();

						this.refreshComboBoXConsultants();
					} else {
						TextAreaConsultant.setText(
								"This employee ID already exists! Try entering a new one before pressing create.");
					}
				}
			} else {
				TextAreaConsultant.setText(
						"Please make sure all requiered fields have been filled in before you press 'register employee' again");
			}
		}

		catch (SQLServerException e) {
			// table full error
			if (e.getErrorCode() == 114) {
				TextAreaConsultant.setText("The employee: " + TextFieldConsultantName.getText() + " ("
						+ TextFieldEmpID.getText() + ") was created!" + "\n" + "Address: "
						+ TextFieldConsultantAddress.getText() + "\n" + "Start date: " + DatePickerConsultant.getValue()
						+ "\n" + "Salary: " + TextFieldSalary.getText());
				TextFieldConsultantName.clear();
				TextFieldSalary.clear();
				TextFieldEmpID.clear();

				this.refreshComboBoXConsultants();
			}
			TextAreaConsultant.setText("There is already an employee with that ID. Please choose a different one!");
		} catch (NumberFormatException e) {
			TextAreaConsultant.setText("Please make sure that the amount in 'salary' is entered using numbers only");
		}
	}

	// --------------------------------------------------------------------------------------------------------//----------------------------------------------------//

	// Method for adding existing consultants to a project
	@FXML
	public void addConsultantsToProjects() throws SQLException {
		try {
			if (ComboBoxProjectAdd.getSelectionModel().getSelectedItem().toString() != null
					&& ComboBoxProjectAddConsultant.getSelectionModel().getSelectedItem().toString() != null
					// making sure the "yes" button is selected indicating that we want to run our
					// method for "creating working consultants"
					&& RadioButtonYes.isSelected()) {
				// running method connected to our database thus creating the consultant.
				int check = dal.createWorkingConsultants(
						ComboBoxProjectAddConsultant.getSelectionModel().getSelectedItem().toString(),
						ComboBoxProjectAdd.getSelectionModel().getSelectedItem().toString());
				if (check == 1 || check == 2) {
					TextAreaProject.setText("You added employee "
							+ ComboBoxProjectAddConsultant.getSelectionModel().getSelectedItem().toString()
							+ " to the project with ProjectID: "
							+ ComboBoxProjectAdd.getSelectionModel().getSelectedItem().toString() + "\n" + "\n"
							+ "Current status: Active on project ");
					// refreshing the comboboxes that store working consultants
					this.refreshWorkingConsultants();
				}
			} else {
				// else here indicates that the user is wanting to assign an employee to a
				// project for which he/she is not active on
				if (ComboBoxProjectAdd.getSelectionModel().getSelectedItem().toString() != null
						&& ComboBoxProjectAddConsultant.getSelectionModel().getSelectedItem().toString() != null
						&& RadioButtonNo.isSelected()) {
					// radiobuttonNo = not active
					int check2 = dal.createNotWorkingConsultants(
							ComboBoxProjectAddConsultant.getSelectionModel().getSelectedItem().toString(),
							ComboBoxProjectAdd.getSelectionModel().getSelectedItem().toString());
					if (check2 == 1 || check2 == 2) {
						TextAreaProject.setText("You added employee "
								+ ComboBoxProjectAddConsultant.getSelectionModel().getSelectedItem().toString()
								+ " to project with projectID: "
								+ ComboBoxProjectAdd.getSelectionModel().getSelectedItem().toString() + "\n" + "\n"
								+ "Current status on project: Inactive on project");

					}
				}
			}
		} catch (SQLException e) { // exception indicating that when trying to add this employee to the database,
									// the structure does not allow it since EmpID is a primary key alongside with
									// ProjectID (there can never be two instances of this)
			TextAreaProject.setText("This consultant already works on the project you tried to assign it to!");
		} catch (RuntimeException a) {
			TextAreaProject.setText(
					"Please select consultant, project and chosen current working status before trying again!");

		}
	}

	// View information about employee by selecting employee ID
	@FXML
	public void viewEmployeeInformationButton() throws SQLException {
		try {
			if (ConsultantComboBox.getSelectionModel().getSelectedItem().toString() != "") {
				ResultSet rs = dal.getAllConsultantInformation(
						ConsultantComboBox.getSelectionModel().getSelectedItem().toString());
				while (rs.next()) {
					consultantTextAreaInformation.setText("Choosen consultant with the employee id (" + rs.getString(1)
							+ " ) has the following information" + "\n" + "Name: " + rs.getString(2) + "\n"
							+ "Address: " + rs.getString(3) + "\n" + "Startdate: " + rs.getString(4) + "\n" + "Salary: "
							+ rs.getString(5));
				}
			} else {
				consultantTextAreaInformation.setText("Please select the consultant you want to view!"); // same error
																											// message
																											// as in the
																											// catch but
																											// there is
																											// only one
																											// way to
																											// make a
																											// mistake
			}
		} catch (Exception e) {
			consultantTextAreaInformation.setText("Please select the consultant you want to view!");
		}
	}
	// --------------------------------------------------------------------------------------------------------//----------------------------------------------------//

	// view all milestone information
	@FXML
	public void viewMilestonesRunButton() throws SQLException {
		try {
			if (ComboBoxMilestoneMilestone.getSelectionModel().getSelectedItem().toString() == "") {
				TextAreaMilestone.appendText("Please select the milestone you want to view!");
			} else if (ComboBoxMilestoneMilestone.getSelectionModel().getSelectedItem().toString() != "") {
				ResultSet MilestoneResult = dal.viewSpecificMilestones(
						ComboBoxMilestoneMilestone.getSelectionModel().getSelectedItem().toString());
				while (MilestoneResult.next()) {
					TextAreaMilestone.setText("Selected milestone (" + MilestoneResult.getString(1) + ")"
							+ " is associated with project " + MilestoneResult.getString(2) + "." + "\n"
							+ "The milestone is set to be completed on " + MilestoneResult.getString(3));
				}

			}
		} catch (Exception e) {
			TextAreaMilestone.setText("Please select the milestone you want to view!");
		}
	}

	// View HOURS
	public void viewHoursRunButton() throws SQLException {
		try {
			if (!ComboboxLogViewTimeConsultant.getSelectionModel().getSelectedItem().toString().isEmpty()
					&& !ComboBoxLogProjects1.getSelectionModel().getSelectedItem().toString().isEmpty()) {
				ResultSet Result = dal.ViewTimeConsultant(
						ComboboxLogViewTimeConsultant.getSelectionModel().getSelectedItem().toString(),
						ComboBoxLogProjects1.getSelectionModel().getSelectedItem().toString());
				{
					// using the scanner method next() to view if the amount of hours worked is
					// equal to null, if it is I want to dispaly it as 0, else I want the string
					// hours to be equal to the value of first selected column (hours) result from
					// my dal method
					while (Result.next()) {
						String hours = "";
						if (Result.getString(1) == null) {
							hours = "0";
						} else {
							hours = Result.getString(1);
						}
						ViewTimeWorked.setText("Selected employee with ID (" + Result.getString(2) + " ) has worked "
								+ hours + " hours on project with ID ( " + Result.getString(3) + " )");

					}
				}
			} else {
				ViewTimeWorked.setText(
						"Please make sure you have selected both a project and a consultant before pressing view");
			}
		} catch (NullPointerException e) {
			ViewTimeWorked.setText("Please select the project and consultant of which you want to view!");
		}

	}

	// View information about project by selecting Project ID
	@FXML
	public void ButtonViewProject() {
		try {
			if (ComboBoxViewProject.getSelectionModel().getSelectedItem().toString() != "") {
				ResultSet rs = dal
						.getAllProjectInformation(ComboBoxViewProject.getSelectionModel().getSelectedItem().toString());
				HashMap<String, String> consultantWorks = new HashMap<String, String>();
				if (rs.next()) {
					while (rs.next()) {
						if (rs.getString(3) != null) {
							// Displaying information only on projects who have working employees
							viewProjectInformationTextArea.setText("You choose project " + rs.getString(1)
									+ " with ID (" + rs.getString(2) + ")" + "\n"
									+ "--------------------------------------------------------------------" + "\n"
									+ "Project information for " + rs.getString(1) + ":" + "\n" + "\n" + "Budget: "
									+ rs.getInt(5) + "\n" + "Start date: " + rs.getString(6) + "\n"
									+ "--------------------------------------------------------------------" + "\n"
									+ "Current staff on project:\n ");
							String hours = "";
							if (rs.getString(4) == null) {
								hours = "0";
							} else {
								hours = rs.getString(4);
							}
							consultantWorks.put(rs.getString(3), hours);
							for (Map.Entry<String, String> set : consultantWorks.entrySet()) {
								viewProjectInformationTextArea.appendText("Consultant with ID ( " + set.getKey()
										+ " ) has worked: " + set.getValue() + " hours \n");
							}
						}
					}
				} else {
					// another method calling the database for when a project does not have
					// employees
					ResultSet rs2 = dal
							.viewProject(ComboBoxViewProject.getSelectionModel().getSelectedItem().toString());
					while (rs2.next()) {
						viewProjectInformationTextArea.setText("You choose project " + rs2.getString(3) + " with ID ("
								+ rs2.getString(1) + ")" + "\n"
								+ "------------------------------------------------------------------" + "\n"
								+ "Project information for " + rs2.getString(3) + ":" + "\n" + "\n" + "Budget: "
								+ rs2.getInt(2) + "\n" + "Start date: " + rs2.getString(4) + "\n"
								+ "--------------------------------------" + "\n" + "No current staff on project:\n ");
					}
				}
			} else {
				viewProjectInformationTextArea
						.appendText("Please make sure you have selected a project before pressing view");
			}

		} catch (Exception e) {
			viewProjectInformationTextArea
					.appendText("Please select the project you want to view from the drop down menu!");
		}
	}

	// --------------------------------------------------------------------------------------------------------//----------------------------------------------------//

// view all consultants who have previously worked on a certain project
	@FXML
	public void ButtonViewHasWorked() throws SQLException {
		try {
			if (ComboBoxViewProject.getSelectionModel().getSelectedItem().toString() != "") {
				ResultSet rs = dal.getConsultantsFromHASWORK(
						ComboBoxViewProject.getSelectionModel().getSelectedItem().toString());
				HashMap<String, String> consultantHasWorks = new HashMap<String, String>();

				while (rs.next()) {
					viewProjectInformationTextArea
							.setText("You choose project " + rs.getString(1) + " with ID (" + rs.getString(2) + ")"
									+ "\n" + "-------------------------------------------------------------" + "\n");
					consultantHasWorks.put(rs.getString(3), rs.getString(2));
					for (Map.Entry<String, String> set : consultantHasWorks.entrySet()) {
						viewProjectInformationTextArea.appendText("Consultant with ID (" + set.getKey()
								+ ") used to work at project (" + set.getValue() + ") \n");
					}
				}
			} else {
				viewProjectInformationTextArea.appendText("Something went wrong");
			}
		} catch (Exception e) {

			viewProjectInformationTextArea.setText("Please make sure you have selected a project before pressing view."
					+ "\n" + "Your action yielded no result!");
		}

	}

	// creating a project
	public void projectRunButton() throws SQLException {
		try {
			if (!TextFieldProjectName.getText().trim().isEmpty() && !TextFieldProjectID.getText().isEmpty()
					&& DatePickerProject.getValue() != null && !TextFieldProjectBudget.getText().toString().isEmpty())
				if (!Pattern.matches("[0-9]+", TextFieldProjectID.getText())
						|| TextFieldProjectID.getText().length() != 8) {
					TextAreaProject.setText("Make sure you have entered a eight digit ID only consisting of numbers");
				} else {

					int check = dal.createProject(TextFieldProjectID.getText(),
							Integer.parseInt(TextFieldProjectBudget.getText()), TextFieldProjectName.getText(),
							DatePickerProject.getValue().toString());
					if (check == 1 || check == 2) {
						TextAreaProject.setText("You have successfully created project "
								+ TextFieldProjectName.getText() + " with ProjectID ( "
								+ (TextFieldProjectID.getText() + " )" + "\n" + "Startdate: "
										+ DatePickerProject.getValue() + "\n" + "Budget: "
										+ Integer.parseInt(TextFieldProjectBudget.getText())));
						TextFieldProjectID.clear();
						TextFieldProjectName.clear();
						TextFieldProjectBudget.clear();
						this.refreshComboBoxProject();

					} else {
						TextAreaProject.setText(
								"The use of " + TextFieldProjectID.getText().toString() + " as ID is already in use."
										+ "\n" + "Please try entering a different ProjectID and try again");
					}
				}
			else {
				TextAreaProject.setText("Please make sure you have entered information in to all required fields");
			}
		} catch (NumberFormatException e) {
			TextAreaProject.setText("Please make sure you have entered the budget correctly using numbers.");
		} catch (SQLServerException e) {
			TextAreaProject
					.setText("There is already a project with that ID. Please choose a different one and try again!");
		} catch (java.lang.RuntimeException j) {
			TextAreaProject.setText(
					"There is already a project with this ID. Please try setting a different ID for your project");
		}
	}

	// ----------------------------------------------------//----------------------------------------------------//----------------------------------------------------//----------------------------------------------------//
	// Creating a milestone
	public void milestoneRunButton() throws SQLException {
		try {
			if (!TextFieldMilestonesType.getText().trim().isEmpty()
					&& !ComboBoxMilestoneProject.getSelectionModel().getSelectedItem().toString().isEmpty()
					&& DatePickerMilestone.getValue().toString() != null) {
				if (!Pattern.matches("[a-zA-Z_]*", TextFieldMilestonesType.getText().toString())) {
					TextAreaMilestone.setText("The name of the milestone can only be entered using letters!");
				} else {
					int check = dal.createMilestone(TextFieldMilestonesType.getText().trim(),
							(ComboBoxMilestoneProject.getSelectionModel().getSelectedItem().toString()),
							(DatePickerMilestone.getValue().toString()));
					if (check == 1 || check == 2) {
						TextAreaMilestone.setText("Milestone: " + TextFieldMilestonesType.getText() + "\n"
								+ "has been assigned to project "
								+ ComboBoxMilestoneProject.getSelectionModel().getSelectedItem().toString() + ".");
						this.refreshComboBoxMilestone();
					}
				}
			} else {
				TextAreaMilestone.setText("Please enter a name for your milestone!");
			}
		} catch (SQLException e) {
			TextAreaMilestone.setText("This milestone already exists for the project you tried assigning it to!" + "\n"
					+ "If you want to create a new milestone for this project " + "please use a different name");
		} catch (RuntimeException a) {
			TextAreaMilestone.setText("Please select a project and date of completion for your milestone");
		}
	}
	// ----------------------------------------------------//----------------------------------------------------//----------------------------------------------------//----------------------------------------------------//

	// Method for assigning hours
	public void assignHoursRunButton() throws SQLException {
		try {
			if (!ComboBoxLogConsultants.getSelectionModel().getSelectedItem().toString().isEmpty()
					&& !ComboBoxLogProjects.getSelectionModel().getSelectedItem().toString().isEmpty()
					&& !TextFieldLogHours.getText().trim().isEmpty()) {
				int check = dal.AssignHours(ComboBoxLogConsultants.getSelectionModel().getSelectedItem().toString(),
						ComboBoxLogProjects.getSelectionModel().getSelectedItem().toString(),
						Integer.valueOf(TextFieldLogHours.getText()));
				if (check == 1) {
					TextAreaLog.setText("You updated amount of hours for consultant with ID ("
							+ ComboBoxLogConsultants.getSelectionModel().getSelectedItem().toString() + ")" + "\n"
							+ " to have worked " + TextFieldLogHours.getText().toString()
							+ " hours on project with ID ("
							+ ComboBoxLogProjects.getSelectionModel().getSelectedItem().toString() + ")");
					ComboBoxLogConsultants.setItems(null);
					ComboBoxLogProjects.setItems(null);
					TextFieldLogHours.clear();
				}
			} else {
				TextAreaLog.setText("Please enter amount of hours and try again");
			}
		} catch (SQLException e) {
			TextAreaLog.setText(
					"Something went wrong when calling the database. Make sure your internet connection is valid before trying again!");
		} catch (RuntimeException e) {
			TextAreaLog.setText("Please make sure you have selected a project and " + "\n" + " an consultant"
					+ " and used digits when entering amount of hours!");
		}
	}

	// Right side LOG

	// METHODS FOR FILLING COMBOBOXES
// Left side Log
	public void refreshLogProjectsComboBox() throws SQLException {
		ObservableList<String> listSpecificProjects = FXCollections.observableArrayList();
		if (ComboBoxLogConsultants.getSelectionModel().getSelectedItem() != null) {
			ResultSet result = dal
					.viewSpecificProject(ComboBoxLogConsultants.getSelectionModel().getSelectedItem().toString());
			while (result.next()) {
				listSpecificProjects.add(result.getString(1));
			}
			ComboBoxLogProjects.getItems().clear();
			ComboBoxLogProjects.setItems(listSpecificProjects);
		}
	}

// right side Log
	public void refreshLogProjectsViewComboBox() throws SQLException {
		ObservableList<String> listSpecificProjects = FXCollections.observableArrayList();
		if (ComboboxLogViewTimeConsultant.getSelectionModel().getSelectedItem() != null) {
			ResultSet result = dal.viewSpecificProject(
					ComboboxLogViewTimeConsultant.getSelectionModel().getSelectedItem().toString());
			while (result.next()) {
				listSpecificProjects.add(result.getString(1));
			}
			ComboBoxLogProjects1.getItems().clear();
			ComboBoxLogProjects1.setItems(listSpecificProjects);
		}
	}

	// CONSULTANTS (ALL)
	public void refreshComboBoXConsultants() throws SQLException {
		ObservableList<String> listConsultants = FXCollections.observableArrayList();
		ResultSet result = dal.getAllConsultantEmpID();
		while (result.next()) {
			listConsultants.add(result.getString(1));
		}
		ConsultantComboBox.setItems(listConsultants);
		ComboBoxProjectAddConsultant.setItems(listConsultants);
	}

	// CONSULTANTS (WORKING)
	public void refreshWorkingConsultants() throws SQLException {
		ObservableList<String> listWorkingConsultants = FXCollections.observableArrayList();
		ResultSet IDResult = dal.getEmpIDWorkingConsultant();
		while (IDResult.next()) {
			listWorkingConsultants.add(IDResult.getString(1));
		}
		ComboBoxLogConsultants.setItems(listWorkingConsultants);
		ComboboxLogViewTimeConsultant.setItems(listWorkingConsultants);

	}

	// PROJECTS THAT BELONG TO A CERTAIN EMPLOYEE
	public void refreshWorkingProjects() throws SQLException {
		ObservableList<String> listWorkingProjects = FXCollections.observableArrayList();
		ResultSet rs = dal.getProjectIDForSpecificConsultant(
				ComboBoxLogConsultants.getSelectionModel().getSelectedItem().toString());
		while (rs.next()) {
			listWorkingProjects.add(rs.getString(1));
		}

	}

	// MILESTONES
	public void refreshComboBoxMilestone() throws SQLException {
		ObservableList<String> listMilestones = FXCollections.observableArrayList();
		ResultSet result = dal.viewAllMilestones();
		while (result.next()) {
			listMilestones.add(result.getString(1));
		}
		ComboBoxMilestoneMilestone.setItems(listMilestones);
	}

	// PROJECTS
	public void refreshComboBoxProject() throws SQLException {
		ObservableList<String> listProjects = FXCollections.observableArrayList();
		ResultSet result = dal.getProject();
		while (result.next()) {
			listProjects.add(result.getString(1));
		}
		ComboBoxProjectAdd.setItems(listProjects);
		ComboBoxViewProject.setItems(listProjects);
		ComboBoxMilestoneProject.setItems(listProjects);
	}

	public Controller() throws IOException {
		DAL dal = new DAL();
	}

	@FXML
	private void initialize() {

		try {
			refreshComboBoXConsultants();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			refreshComboBoxProject();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			refreshComboBoxMilestone();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			refreshWorkingConsultants();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
