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
	private TextArea textAreaConsultant = new TextArea();

	// DatePickers
	@FXML
	private DatePicker datePickerConsultant = new DatePicker();

	// TextField
	@FXML
	private TextField textFieldConsultantName = new TextField();
	@FXML
	private TextField textFieldEmpID = new TextField();
	@FXML
	private TextField textFieldConsultantAddress = new TextField();
	@FXML
	private TextField textFieldSalary = new TextField();

	// Button
	@FXML
	private Button buttonCreateEmployee = new Button();

	@FXML
	private Button consultantViewButton = new Button();

	// Line and combobox
	@FXML
	private Line consultantLine = new Line();

	@FXML
	private ComboBox<String> consultantComboBox = new ComboBox<String>();

	// -------------------------------------------------------------------------------------------------------------------//

	// LOG

	// TextFields
	@FXML
	private TextField textFieldLogHours = new TextField();

	// ComboBoxes
	@FXML
	private ComboBox comboBoxLogConsultants = new ComboBox();
	@FXML
	private ComboBox comboBoxLogProjects = new ComboBox();
	@FXML
	private ComboBox comboboxLogViewTimeConsultant = new ComboBox();
	@FXML
	private ComboBox comboBoxLogProjects1 = new ComboBox();

	// RunButton
	@FXML
	private Button buttonAssignTime = new Button();

	@FXML
	private Button buttonViewHours = new Button();

	// Text Area
	@FXML
	private TextArea textAreaLog = new TextArea();

	@FXML
	private TextArea viewTimeWorked = new TextArea();

	// Line
	@FXML
	private Line lineLog = new Line();

	// ------------------------------------------------------------------------------------------------------------------//

	// PROJECT
	@FXML
	private TextArea viewProjectInformationTextArea = new TextArea();

	// TextFields
	@FXML
	private TextField textFieldProjectName = new TextField();
	@FXML
	private TextField textFieldProjectID = new TextField();
	@FXML
	private TextField textFieldProjectBudget = new TextField();

	@FXML
	private TextField textFieldProjectHours = new TextField();

	// DatePicker
	@FXML
	private DatePicker DatePickerProject = new DatePicker();

	// Table Column + view
	@FXML
	private TableView projectTableView = new TableView();
	@FXML
	private TableColumn projectTableColumnName = new TableColumn();
	@FXML
	private TableColumn projectTableColumnProjectID = new TableColumn();
	@FXML
	private TableColumn projectTableColumnHours = new TableColumn();

	// RadioButton

	@FXML
	private RadioButton radioButtonYes = new RadioButton();
	@FXML
	private RadioButton radioButtonNo = new RadioButton();

	// Button
	@FXML
	private Button buttonViewProject = new Button();
	@FXML
	private Button buttonCreateProject = new Button();
	@FXML
	private Button buttonAssignConsultant = new Button();

	@FXML
	private Button buttonViewHasWork = new Button();

	// ComboBoxes
	@FXML
	private ComboBox comboBoxProjectProjects = new ComboBox();
	@FXML
	private ComboBox comboBoxProjectConsultants = new ComboBox();
	@FXML
	private ComboBox comboBoxProjectAdd = new ComboBox();
	@FXML
	private ComboBox comboBoxViewProject = new ComboBox();

	@FXML
	private ComboBox ComboBoxProjectAddConsultant = new ComboBox();

	// ---------------------------------------------------------------------------------------------------------------------//

	// MILESTONE

	// TextField
	@FXML
	private TextField textFieldMilestonesType = new TextField();

	// RadioButton
	@FXML
	private Button runButtonCreateMileStone = new Button();
	@FXML
	private Button viewMilestone = new Button();

	// ComboBoxes
	@FXML
	private ComboBox<String> comboBoxMilestoneMilestone = new ComboBox();

	@FXML
	private ComboBox<String> comboBoxMilestoneProject = new ComboBox();

	@FXML
	private DatePicker datePickerMilestone = new DatePicker();

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
		LocalDate myDate = datePickerConsultant.getValue();
	}

	public void getDateMilestone(ActionEvent event) {
		LocalDate myDate = datePickerMilestone.getValue();
	}

	public void getDateProject(ActionEvent event) {
		LocalDate myDate = datePickerMilestone.getValue();
	}

	// ----------------------------------------------------//----------------------------------------------------//----------------------------------------------------//

	// CREAT METHODS

	// Creating an employee
	public void createEmployeeRunButton() throws SQLException {
		try {
			if (!textFieldEmpID.getText().trim().isEmpty() && !textFieldConsultantName.getText().isEmpty()
					&& !textFieldConsultantAddress.getText().isEmpty() && datePickerConsultant.getValue() != null
					&& !textFieldSalary.getText().isEmpty()) {
				// only allowing numbers for the id and setting the lenght to 10 digits
				if (!Pattern.matches("[0-9]+", textFieldEmpID.getText()) || textFieldEmpID.getText().length() != 10) {
					textAreaConsultant.setText("Only positive 10 digit numbers are allowed as ID." + "\n"
							+ " Please do not use any special charcaters like " + "-+%&" + "\n"
							+ "Try again using this information!");
				} else {
					// if everything is entered correctly, we first create the employee in our
					// database
					int check = dal.createEmployee(textFieldEmpID.getText().trim(),
							textFieldConsultantName.getText().trim(), textFieldConsultantAddress.getText().trim(),
							datePickerConsultant.getValue().toString(),
							Integer.valueOf(textFieldSalary.getText().trim()));
					if (check == 1 || check == 2) {
						// if the creation of employee (consultant) was successfull, we let the user
						// know it was successfull
						textAreaConsultant.setText("The employee: " + textFieldConsultantName.getText() + " ("
								+ textFieldEmpID.getText() + ") was created!" + "\n" + "Address: "
								+ textFieldConsultantAddress.getText() + "\n" + "Start date: "
								+ datePickerConsultant.getValue() + "\n" + "Salary: " + textFieldSalary.getText());
						textFieldConsultantName.clear();
						textFieldConsultantAddress.clear();
						textFieldSalary.clear();
						textFieldEmpID.clear();
						this.refreshComboBoXConsultants();
					} else {
						textAreaConsultant.setText(
								"This employee ID already exists! Try entering a new one before pressing create.");
					}
				}
			} else {
				textAreaConsultant.setText(
						"Please make sure all requiered fields have been filled in before you press 'register employee' again");
			}
		}

		catch (SQLServerException e) {
			// table full error
			if (e.getErrorCode() == 114) {
				textAreaConsultant.setText("The employee: " + textFieldConsultantName.getText() + " ("
						+ textFieldEmpID.getText() + ") was created!" + "\n" + "Address: "
						+ textFieldConsultantAddress.getText() + "\n" + "Start date: " + datePickerConsultant.getValue()
						+ "\n" + "Salary: " + textFieldSalary.getText());
				textFieldConsultantName.clear();
				textFieldSalary.clear();
				textFieldEmpID.clear();

				this.refreshComboBoXConsultants();
			}
			textAreaConsultant.setText("There is already an employee with that ID. Please choose a different one!");
		} catch (NumberFormatException e) {
			textAreaConsultant.setText("Please make sure that the amount in 'salary' is entered using numbers only");
		}
	}

	// --------------------------------------------------------------------------------------------------------//----------------------------------------------------//

	// Method for adding existing consultants to a project
	@FXML
	public void addConsultantsToProjects() throws SQLException {
		try {
			if (comboBoxProjectAdd.getSelectionModel().getSelectedItem().toString() != null
					&& ComboBoxProjectAddConsultant.getSelectionModel().getSelectedItem().toString() != null
					// making sure the "yes" button is selected indicating that we want to run our
					// method for "creating working consultants"
					&& radioButtonYes.isSelected()) {
				// running method connected to our database thus creating the consultant
				int check = dal.createWorkingConsultants(
						ComboBoxProjectAddConsultant.getSelectionModel().getSelectedItem().toString(),
						comboBoxProjectAdd.getSelectionModel().getSelectedItem().toString());
				if (check == 1) {
					TextAreaProject.setText("You added employee "
							+ ComboBoxProjectAddConsultant.getSelectionModel().getSelectedItem().toString()
							+ " to the project with ProjectID: "
							+ comboBoxProjectAdd.getSelectionModel().getSelectedItem().toString() + "\n" + "\n"
							+ "Current status: Active on project ");
					// refreshing the comboboxes that store working consultants
					this.refreshWorkingConsultants();
				}
			} else {
				// else here indicates that the user is wanting to assign an employee to a
				// project for which he/she is not active on
				if (comboBoxProjectAdd.getSelectionModel().getSelectedItem().toString() != null
						&& ComboBoxProjectAddConsultant.getSelectionModel().getSelectedItem().toString() != null
						&& radioButtonNo.isSelected()) {
					// radiobuttonNo = not active
					int check2 = dal.createNotWorkingConsultants(
							ComboBoxProjectAddConsultant.getSelectionModel().getSelectedItem().toString(),
							comboBoxProjectAdd.getSelectionModel().getSelectedItem().toString());
					if (check2 == 1) {
						TextAreaProject.setText("You added employee "
								+ ComboBoxProjectAddConsultant.getSelectionModel().getSelectedItem().toString()
								+ " to project with projectID: "
								+ comboBoxProjectAdd.getSelectionModel().getSelectedItem().toString() + "\n" + "\n"
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
			if (consultantComboBox.getSelectionModel().getSelectedItem().toString() != "") {
				ResultSet rs = dal.getAllConsultantInformation(
						consultantComboBox.getSelectionModel().getSelectedItem().toString());
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
			if (comboBoxMilestoneMilestone.getSelectionModel().getSelectedItem().toString() == "") {
				TextAreaMilestone.appendText("Please select the milestone you want to view!");
			} else if (comboBoxMilestoneMilestone.getSelectionModel().getSelectedItem().toString() != "") {
				ResultSet MilestoneResult = dal.viewSpecificMilestones(
						comboBoxMilestoneMilestone.getSelectionModel().getSelectedItem().toString());
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
			if (!comboboxLogViewTimeConsultant.getSelectionModel().getSelectedItem().toString().isEmpty()
					&& !comboBoxLogProjects1.getSelectionModel().getSelectedItem().toString().isEmpty()) {
				ResultSet Result = dal.viewTimeConsultant(
						comboboxLogViewTimeConsultant.getSelectionModel().getSelectedItem().toString(),
						comboBoxLogProjects1.getSelectionModel().getSelectedItem().toString());
				{
					// using the scanner method next() to view if the amount of hours worked is
					// equal to null, if it is I want to display it as 0, else I want the string
					// hours to be equal to the value of first selected column (hours) result from
					// my dal method
					while (Result.next()) {
						String hours = "";
						if (Result.getString(1) == null) {
							hours = "0";
						} else {
							hours = Result.getString(1);
						}
						viewTimeWorked.setText("Selected employee with ID (" + Result.getString(2) + " ) has worked "
								+ hours + " hours on project with ID ( " + Result.getString(3) + " )");

					}
				}
			} else {
				viewTimeWorked.setText(
						"Please make sure you have selected both a project and a consultant before pressing view");
			}
		} catch (NullPointerException e) {
			viewTimeWorked.setText("Please select the project and consultant of which you want to view!");
		}

	}

	// View information about project by selecting Project ID
	@FXML
	public void ButtonViewProject() {
		try {
			if (comboBoxViewProject.getSelectionModel().getSelectedItem().toString() != "") {
				ResultSet rs = dal
						.getAllProjectInformation(comboBoxViewProject.getSelectionModel().getSelectedItem().toString());
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
							.viewProject(comboBoxViewProject.getSelectionModel().getSelectedItem().toString());
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
			if (comboBoxViewProject.getSelectionModel().getSelectedItem().toString() != "") {
				ResultSet rs = dal.getConsultantsFromHASWORK(
						comboBoxViewProject.getSelectionModel().getSelectedItem().toString());
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
			if (!textFieldProjectName.getText().trim().isEmpty() && !textFieldProjectID.getText().isEmpty()
					&& DatePickerProject.getValue() != null && !textFieldProjectBudget.getText().toString().isEmpty())
				if (!Pattern.matches("[0-9]+", textFieldProjectID.getText())
						|| textFieldProjectID.getText().length() != 8) {
					TextAreaProject.setText("Make sure you have entered a eight digit ID only consisting of numbers");
				} else {

					int check = dal.createProject(textFieldProjectID.getText(),
							Integer.parseInt(textFieldProjectBudget.getText()), textFieldProjectName.getText(),
							DatePickerProject.getValue().toString());
					if (check == 1) {
						TextAreaProject.setText("You have successfully created project "
								+ textFieldProjectName.getText() + " with ProjectID ( "
								+ (textFieldProjectID.getText() + " )" + "\n" + "Startdate: "
										+ DatePickerProject.getValue() + "\n" + "Budget: "
										+ Integer.parseInt(textFieldProjectBudget.getText())));
						textFieldProjectID.clear();
						textFieldProjectName.clear();
						textFieldProjectBudget.clear();
						this.refreshComboBoxProject();

					} else {
						TextAreaProject.setText(
								"The use of " + textFieldProjectID.getText().toString() + " as ID is already in use."
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
			if (!textFieldMilestonesType.getText().trim().isEmpty()
					&& !comboBoxMilestoneProject.getSelectionModel().getSelectedItem().toString().isEmpty()
					&& datePickerMilestone.getValue().toString() != null) {
				if (!Pattern.matches("[a-zA-Z_]*", textFieldMilestonesType.getText().toString())) {
					TextAreaMilestone.setText("The name of the milestone can only be entered using letters!");
				} else {
					int check = dal.createMilestone(textFieldMilestonesType.getText().trim(),
							(comboBoxMilestoneProject.getSelectionModel().getSelectedItem().toString()),
							(datePickerMilestone.getValue().toString()));
					if (check == 1) {
						TextAreaMilestone.setText("Milestone: " + textFieldMilestonesType.getText() + "\n"
								+ "has been assigned to project "
								+ comboBoxMilestoneProject.getSelectionModel().getSelectedItem().toString() + ".");
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
			if (!comboBoxLogConsultants.getSelectionModel().getSelectedItem().toString().isEmpty()
					&& !comboBoxLogProjects.getSelectionModel().getSelectedItem().toString().isEmpty()
					&& !textFieldLogHours.getText().trim().isEmpty()) {
				int check = dal.assignHours(comboBoxLogConsultants.getSelectionModel().getSelectedItem().toString(),
						comboBoxLogProjects.getSelectionModel().getSelectedItem().toString(),
						Integer.valueOf(textFieldLogHours.getText()));
				if (check == 1) {
					textAreaLog.setText("You updated amount of hours for consultant with ID ("
							+ comboBoxLogConsultants.getSelectionModel().getSelectedItem().toString() + ")" + "\n"
							+ " to have worked " + textFieldLogHours.getText().toString()
							+ " hours on project with ID ("
							+ comboBoxLogProjects.getSelectionModel().getSelectedItem().toString() + ")");
					comboBoxLogConsultants.setItems(null);
					comboBoxLogProjects.setItems(null);
					textFieldLogHours.clear();
				}
			} else {
				textAreaLog.setText("Please enter amount of hours and try again");
			}
		} catch (SQLException e) {
			textAreaLog.setText(
					"Something went wrong when calling the database. Make sure your internet connection is valid before trying again!");
		} catch (RuntimeException e) {
			textAreaLog.setText("Please make sure you have selected a project and " + "\n" + " an consultant"
					+ " and used digits when entering amount of hours!");
		}
	}

	// Right side LOG

	// METHODS FOR FILLING COMBOBOXES
// Left side Log
	public void refreshLogProjectsComboBox() throws SQLException {
		ObservableList<String> listSpecificProjects = FXCollections.observableArrayList();
		if (comboBoxLogConsultants.getSelectionModel().getSelectedItem() != null) {
			ResultSet result = dal
					.viewSpecificProject(comboBoxLogConsultants.getSelectionModel().getSelectedItem().toString());
			while (result.next()) {
				listSpecificProjects.add(result.getString(1));
			}
			comboBoxLogProjects.getItems().clear();
			comboBoxLogProjects.setItems(listSpecificProjects);
		}
	}

// right side Log
	public void refreshLogProjectsViewComboBox() throws SQLException {
		ObservableList<String> listSpecificProjects = FXCollections.observableArrayList();
		if (comboboxLogViewTimeConsultant.getSelectionModel().getSelectedItem() != null) {
			ResultSet result = dal.viewSpecificProject(
					comboboxLogViewTimeConsultant.getSelectionModel().getSelectedItem().toString());
			while (result.next()) {
				listSpecificProjects.add(result.getString(1));
			}
			comboBoxLogProjects1.getItems().clear();
			comboBoxLogProjects1.setItems(listSpecificProjects);
		}
	}

	// CONSULTANTS (ALL)
	public void refreshComboBoXConsultants() throws SQLException {
		ObservableList<String> listConsultants = FXCollections.observableArrayList();
		ResultSet result = dal.getAllConsultantEmpID();
		while (result.next()) {
			listConsultants.add(result.getString(1));
		}
		consultantComboBox.setItems(listConsultants);
		ComboBoxProjectAddConsultant.setItems(listConsultants);
	}

	// CONSULTANTS (WORKING)
	public void refreshWorkingConsultants() throws SQLException {
		ObservableList<String> listWorkingConsultants = FXCollections.observableArrayList();
		ResultSet IDResult = dal.getEmpIDWorkingConsultant();
		while (IDResult.next()) {
			listWorkingConsultants.add(IDResult.getString(1));
		}
		comboBoxLogConsultants.setItems(listWorkingConsultants);
		comboboxLogViewTimeConsultant.setItems(listWorkingConsultants);

	}

	// PROJECTS THAT BELONG TO A CERTAIN EMPLOYEE
	public void refreshWorkingProjects() throws SQLException {
		ObservableList<String> listWorkingProjects = FXCollections.observableArrayList();
		ResultSet rs = dal.getProjectIDForSpecificConsultant(
				comboBoxLogConsultants.getSelectionModel().getSelectedItem().toString());
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
		comboBoxMilestoneMilestone.setItems(listMilestones);
	}

	// PROJECTS
	public void refreshComboBoxProject() throws SQLException {
		ObservableList<String> listProjects = FXCollections.observableArrayList();
		ResultSet result = dal.getProject();
		while (result.next()) {
			listProjects.add(result.getString(1));
		}
		comboBoxProjectAdd.setItems(listProjects);
		comboBoxViewProject.setItems(listProjects);
		comboBoxMilestoneProject.setItems(listProjects);
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
