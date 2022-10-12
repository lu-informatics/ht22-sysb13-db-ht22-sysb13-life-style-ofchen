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
import javafx.scene.shape.Line;

public class Controller {
	DAL dal = new DAL();

//	RadioButtonConsultant.setToggleGroup(tgConsultant)

	// CONSULTANT

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

	// RadioButton
	@FXML
	private Button ButtonCreateEmployee = new Button();

	@FXML
	private Button ButtonViewStaff = new Button();

	@FXML
	private Button ConsultantViewButton = new Button();

	@FXML
	private Line ConsultantLine = new Line();

	@FXML
	private ComboBox<String> ConsultantComboBox = new ComboBox<String>();

	// ----------------------------------------------------------////
	// ---------------------------------------------------------//
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
	private Button ButtonViewHours11 = new Button();

	@FXML
	private TextArea TextAreaLog = new TextArea();

	@FXML
	private Line LineLog = new Line();

	@FXML
	private TextArea ViewTimeWorked = new TextArea();

	// ---------------------------------------------------------////
	// ---------------------------------------------------------//

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

	// ------------------------------------------------------------////
	// ---------------------------------------------------------//

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

	// --------------------------------------------------------------////
	// ---------------------------------------------------------//

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
				if (!Pattern.matches("[0-9]+", TextFieldEmpID.getText()) || TextFieldEmpID.getText().length() != 5) {
					TextAreaConsultant.setText("Only five digit numbers are allowed as ID. " + "\n"
							+ "Please try again using this information!");
				} else {

					int check = dal.createEmployee(TextFieldEmpID.getText().trim(),
							TextFieldConsultantName.getText().trim(), TextFieldConsultantAddress.getText().trim(),
							DatePickerConsultant.getValue().toString(),
							Integer.valueOf(TextFieldSalary.getText().trim()));
					if (check == 1 || check == 2) {
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
						TextAreaConsultant.setText("Something went wrong, check if Employee ID already exists!");
					}
				}
			} else {
				TextAreaConsultant.setText(
						"Oops something went wrong. Please make sure all requiered fields have been filled in before you press create employee again");
			}
		}

		catch (SQLServerException e) {
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
			TextAreaConsultant.setText("There is already an employee with that ID. Please choose a different one!"
					+ "\n " + e.getMessage());
		} catch (NumberFormatException e) {
			TextAreaConsultant
					.setText("Salary WARNING!" + "\n" + "Please make sure that the HQ has approved this salary");
		}
	}

	// --------------------------------------------------------------------------------------------------------//----------------------------------------------------//

	// create working employees
	@FXML
	public void addConsultantsToProjects() throws SQLException {
		try {
			if (ComboBoxProjectAdd.getSelectionModel().getSelectedItem().toString() != null
					&& ComboBoxProjectAddConsultant.getSelectionModel().getSelectedItem().toString() != null
					&& RadioButtonYes.isSelected()) {
				int check = dal.createWorkingConsultants(
						ComboBoxProjectAddConsultant.getSelectionModel().getSelectedItem().toString(),
						ComboBoxProjectAdd.getSelectionModel().getSelectedItem().toString());
				if (check == 1 || check == 2) {
					TextAreaProject.setText("You added employee "
							+ ComboBoxProjectAddConsultant.getSelectionModel().getSelectedItem().toString()
							+ " to the project with ProjectID: "
							+ ComboBoxProjectAdd.getSelectionModel().getSelectedItem().toString() + "\n" + "\n"
							+ "Current status: Active on project ");
					this.refreshWorkingConsultants();
				}
			} else {
				if (ComboBoxProjectAdd.getSelectionModel().getSelectedItem().toString() != null
						&& ComboBoxProjectAddConsultant.getSelectionModel().getSelectedItem().toString() != null
						&& RadioButtonNo.isSelected()) {
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
		} catch (SQLException e) {
			TextAreaProject.setText("This consultant already works on the project you tried to assign it to!");
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
				consultantTextAreaInformation
						.setText("Something went wrong when fetching your information. Please try again");
			}
		} catch (Exception e) {
			consultantTextAreaInformation
					.setText("Something went wrong when calling the database. Please try again or contact a developer");
		}
	}
	// --------------------------------------------------------------------------------------------------------//----------------------------------------------------//

	// view all milestone information
	@FXML
	public void viewMilestonesRunButton() throws SQLException {
		try {
			if (ComboBoxMilestoneMilestone.getSelectionModel().getSelectedItem().toString() != "") {
				ResultSet MilestoneResult = dal.viewSpecificMilestones(
						ComboBoxMilestoneMilestone.getSelectionModel().getSelectedItem().toString());
				while (MilestoneResult.next()) {
					TextAreaMilestone.setText("Selected milestone (" + MilestoneResult.getString(1) + ")"
							+ " is associated with project " + MilestoneResult.getString(2) + "." + "\n"
							+ "The milestone is set to be completed on " + MilestoneResult.getString(3));
				}
			}
		} catch (Exception e) {
			consultantTextAreaInformation
					.setText("Something went wrong when calling the database. Please try again or contact a developer");
		}
	}

	// View FKN HOURS
	public void viewFKNHoursRunButton() throws SQLException {
		try {
			if (!ComboboxLogViewTimeConsultant.getSelectionModel().getSelectedItem().toString().isEmpty()
					&& !ComboBoxLogProjects1.getSelectionModel().getSelectedItem().toString().isEmpty()) {
				ResultSet Result = dal.ViewTimeConsultant(
						ComboboxLogViewTimeConsultant.getSelectionModel().getSelectedItem().toString(),
						ComboBoxLogProjects1.getSelectionModel().getSelectedItem().toString());
				{
					while (Result.next()) {
						ViewTimeWorked.setText("Selected employee with ID (" + Result.getString(2) + " ) has worked "
								+ Result.getString(1) + " hours on project with ID ( " + Result.getString(3) + " )");
					}
				}
			}
		} catch (NullPointerException e) {
			consultantTextAreaInformation
					.setText("Something went wrong when calling the database. Please try again or contact a developer");
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
				while (rs.next()) {
					viewProjectInformationTextArea.appendText("Errohiuguhuir ");
					viewProjectInformationTextArea.setText("You choose project " + rs.getString(1) + " with ID ("
							+ rs.getString(2) + ")" + "\n" + "--------------------------------------" + "\n"
							+ "Project information for " + rs.getString(1) + ":" + "\n" + "\n" + "Budget: "
							+ rs.getInt(5) + "\n" + "Start date: " + rs.getString(6) + "\n"
							+ "--------------------------------------" + "\n" + "Current staff on project:\n ");
					consultantWorks.put(rs.getString(3), rs.getString(4));
				}
				for (Map.Entry<String, String> set : consultantWorks.entrySet()) {
					viewProjectInformationTextArea.appendText(
							"Consultant with ID ( " + set.getKey() + " ) has worked: " + set.getValue() + " hours \n");
				}
				if (rs.getRow() <= 0) {
					viewProjectInformationTextArea.appendText(
							"Error " + ComboBoxViewProject.getSelectionModel().getSelectedItem().toString());
					ResultSet rs2 = dal
							.projectInfo(ComboBoxViewProject.getSelectionModel().getSelectedItem().toString());
					viewProjectInformationTextArea.setText("You choose project " + rs2.getString(1) + " with ID ("
							+ rs2.getInt(2) + ")" + "\n" + "--------------------------------------" + "\n"
							+ "Project information for " + rs2.getString(1) + ":" + "\n" + "\n" + "Budget: "
							+ rs2.getString(3) + "\n" + "Start date: " + rs2.getString(4) + "\n"
							+ "--------------------------------------" + "\n"
							+ "There is no current staff on the project ");
				}
			} else {
				viewProjectInformationTextArea
						.appendText("Please make sure you have selected a project before pressing view");
			}

		} catch (Exception e) {
			viewProjectInformationTextArea.appendText(e.getMessage());
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
			viewProjectInformationTextArea
					.appendText("Please make sure you have selected a project before pressing view." + "\n"
							+ "Your action yielded no result!");

		}
	}

	// creating a project
	public void projectRunButton() throws SQLException {
		try {
			if (!TextFieldProjectName.getText().trim().isEmpty() && !TextFieldProjectID.getText().isEmpty()
					&& DatePickerProject.getValue() != null && !TextFieldProjectBudget.getText().toString().isEmpty())
				if (!Pattern.matches("[0-9]+", TextFieldProjectID.getText())
						|| TextFieldProjectID.getText().length() != 5) {
					TextAreaProject.setText("Make sure you have entered a five digit ID only consisting of numbers");
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
				TextAreaProject
						.setText("Something went wrong in the database. Make sure you have entered required fields");
			}
		} catch (NumberFormatException e) {
			TextAreaProject.setText("Please make sure to enter only digits for the projectID and budget");
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
				if (!Pattern.matches("[a-zA-Z]", TextFieldMilestonesType.getText().toString())) {
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
				TextAreaMilestone.setText("You have run into an error. Please try again");

			}
		} catch (SQLException e) {
			TextAreaMilestone.setText("There appears to have been a problem");
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
				} else {
					TextAreaLog.setText("Did not work");
				}
			}
		} catch (SQLException e) {
			TextAreaLog.setText("we are here " + e.getMessage());
		} catch (RuntimeException e) {
			TextAreaLog.setText("Please make sure you have filled in all textfields");
		}
	}

	// Right side LOG

	// METHODS FOR FILLING COMBOBOXES
// Left side Log
	public void refreshLogProjectsComboBox() throws SQLException {
		ObservableList<String> listSpecificProjects = FXCollections.observableArrayList();
		if (ComboBoxLogConsultants.getSelectionModel().getSelectedItem() != null
				&& ComboboxLogViewTimeConsultant.getSelectionModel().getSelectedItem() != null) {
			ResultSet result = dal
					.viewSpecificProject(ComboBoxLogConsultants.getSelectionModel().getSelectedItem().toString());
			while (result.next()) {
				listSpecificProjects.add(result.getString(1));
			}
			ComboBoxLogProjects.getItems().clear();
			ComboBoxLogProjects.setItems(listSpecificProjects);

		} else if (ComboboxLogViewTimeConsultant.getSelectionModel().getSelectedItem() != null) {
			ResultSet result = dal.viewSpecificProject(
					ComboboxLogViewTimeConsultant.getSelectionModel().getSelectedItem().toString());
			while (result.next()) {
				listSpecificProjects.add(result.getString(1));
			}
			ComboBoxLogProjects1.getItems().clear();
			ComboBoxLogProjects1.setItems(listSpecificProjects);

		} else if (ComboBoxLogConsultants.getSelectionModel().getSelectedItem() != null) {
			ResultSet result = dal
					.viewSpecificProject(ComboBoxLogConsultants.getSelectionModel().getSelectedItem().toString());
			while (result.next()) {
				listSpecificProjects.add(result.getString(1));
			}
			ComboBoxLogProjects.getItems().clear();
			ComboBoxLogProjects.setItems(listSpecificProjects);
		}
	}

	// CONSULTANTS (ALL)
	public void refreshComboBoXConsultants() throws SQLException {
		ObservableList<String> listConsultants = FXCollections.observableArrayList();
		ResultSet result = dal.getAllConsultantEmpID();
		while (result.next()) {
			listConsultants.add(result.getString(1));
		}
		ComboBoxProjectConsultants.setItems(listConsultants);
		ConsultantComboBox.setItems(listConsultants);
		ComboBoxProjectAddConsultant.setItems(listConsultants);
		ComboBoxLogConsultants.setItems(listConsultants);
		ComboboxLogViewTimeConsultant.setItems(listConsultants);
	}

	// CONSULTANTS (WORKING)
	public void refreshWorkingConsultants() throws SQLException {
		ObservableList<String> listWorkingConsultants = FXCollections.observableArrayList();
		ResultSet IDResult = dal.getEmpIDWorkingConsultant();
		while (IDResult.next()) {
			listWorkingConsultants.add(IDResult.getString(1));
		}

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
		ComboBoxProjectProjects.setItems(listProjects);
		ComboBoxMilestoneProject.setItems(listProjects);
		ComboBoxViewProject.setItems(listProjects);
		ComboBoxProjectAdd.setItems(listProjects);
		ComboBoxLogProjects.setItems(listProjects);
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
