package application;


import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class Controller  {
	DAL dal = new DAL();

	
	
	
//	RadioButtonConsultant.setToggleGroup(tgConsultant)
	
	// CONSULTANT
	
	@FXML
	private TextArea consultantTextAreaInformation = new TextArea();
	
	// DatePickers
	@FXML
	private DatePicker DatePickerConsultant = new DatePicker(); 
	@FXML
	private DatePicker DatePickerMilestone = new DatePicker();
	
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
	private Button ConsultantViewButton = new Button();
	
	@FXML
	private Line ConsultantLine = new Line();
	
	@FXML
	private ComboBox ConsultantComboBox = new ComboBox();
	
	@FXML
	private TableView TableViewConsultant = new TableView();
	@FXML
	private TableColumn TableColumnEmpID = new TableColumn();
	@FXML
	private TableColumn TableColumnName = new TableColumn();
	@FXML
	private TableColumn TableColumnAddress = new TableColumn();
	@FXML
	private TableColumn TableColumnSalary = new TableColumn();
	@FXML
	private TableColumn TableColumnStartDate = new TableColumn();
	
	//----------------------------------------------------------//// ---------------------------------------------------------//
	//LOG
	
	//TextFields 
	@FXML
	private TextField TextFieldLogHours = new TextField();
	
	// ComboBoxes 
	@FXML
	private ComboBox ComboBoxLogConsultants = new ComboBox();
	@FXML
	private ComboBox ComboBoxLogProjects = new ComboBox();
	
	// RunButton 
	@FXML
	private Button ButtonAssignTime = new Button();
	
	@FXML
	private TextArea TextAreaLog = new TextArea();
	
	@FXML
	private Line LineLog = new Line();
	
	// ---------------------------------------------------------//// ---------------------------------------------------------//
	
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
	
	//DatePicker 
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
	private TableColumn ProjectTableColumnHours= new TableColumn();
		
	// RadioButton
	
	@FXML
	private RadioButton RadioButtonYes = new RadioButton();
	@FXML
	private RadioButton RadioButtonNo = new RadioButton();

	
	//Button
	@FXML
	private Button ButtonViewProject = new Button();
	@FXML
	private Button ButtonCreateProject = new Button();
	@FXML
	private Button ButtonAssignConsultant = new Button();
	
	// ComboBoxes 
	private ComboBox ComboBoxProjectManagers = new ComboBox();
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
	
	//------------------------------------------------------------//// ---------------------------------------------------------//
	
	//MILESTONE
	
	//TextField 
	@FXML
	private TextField TextFieldMilestonesType = new TextField();
	
	// RadioButton
	@FXML
	private Button RunButtonCreateMileStone = new Button();

	// ComboBoxes 
	@FXML
	private ComboBox ComboBoxMilestoneManager = new ComboBox();
	@FXML
	private ComboBox ComboBoxMilestoneMilestone = new ComboBox();
	@FXML
	private ComboBox ComboBoxMilestoneProject = new ComboBox();
	
	//--------------------------------------------------------------//// ---------------------------------------------------------//

	
	// TabePane
	@FXML
	private TabPane tabpane = new TabPane();
	
	// All TextAreas
	@FXML
	private TextArea textarea = new TextArea();
	@FXML
	private TextArea TextAreaConsultant = new TextArea();
	
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
	
	//----------------------------------------------------//----------------------------------------------------//----------------------------------------------------//
	
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
	
	 //----------------------------------------------------//----------------------------------------------------//----------------------------------------------------//
	 
	 // CREAT METHODS 
	 
	
		// Creating an employee 
	public void createEmployeeRunButton() throws SQLException {
		try {
		if(!TextFieldEmpID.getText().trim().isEmpty()
			&&!TextFieldConsultantName.getText().isEmpty()
			&& !TextFieldConsultantAddress.getText().isEmpty()
			&& DatePickerConsultant.getValue() != null
			&& !TextFieldSalary.getText().isEmpty()) {
				boolean check = dal.createEmployee(TextFieldEmpID.getText().trim(), TextFieldConsultantName.getText().trim(), TextFieldConsultantAddress.getText().trim(),
						DatePickerConsultant.getValue().toString(), Integer.valueOf(TextFieldSalary.getText().trim()));
				if(check) {
				TextAreaConsultant.setText("The employee: " + TextFieldConsultantName.getText() + " (" + TextFieldEmpID.getText() + ") was created!" + "\n" + "Address: " + TextFieldConsultantAddress.getText() + "\n"
					+ "Start date: " + DatePickerConsultant.getValue() + "\n" + "Salary: " + TextFieldSalary.getText()); // 
				}
			}		
		else {
			TextAreaConsultant.setText("Oops something went wrong. Please make sure all requiered fields have been filled in before you press create employee again");

		}
		else if (TextFieldEmpID.getText().isEmpty()){
			TextAreaConsultant.setText("Please Enter EmployeeID ");
		}
		else if (TextFieldConsultantAddress.getText().isEmpty()){
			TextAreaConsultant.setText("Please Enter Address");
		}
	}
	
	// --------------------------------------------------------------------------------------------------------//----------------------------------------------------//
	
	// View information about employee by selecting employee ID 
		@FXML
		public void viewEmployeeInformationButton() throws SQLException {
			try {
				//consultantTextAreaInformation.setText("HEJ " + ConsultantComboBox.getSelectionModel().getSelectedItem().toString());
				if(ConsultantComboBox.getSelectionModel().getSelectedItem().toString() != "") {
					ResultSet rs = dal.getSpecificConsultantID(ConsultantComboBox.getSelectionModel().getSelectedItem().toString());
					while(rs.next()) {
						consultantTextAreaInformation.setText("Choosen consultant with the employee id (" + rs.getString(1) +" ) has the following information" + "\n" + 
										"Name: " + rs.getString(2) + "\n" + "Address: " + rs.getString(3) + "\n"
										+ "Startdate: " + rs.getString(4) + "\n" + "Salary: " + rs.getString(5));
					}
				}
				else { consultantTextAreaInformation.setText("Something went wrong when fetching your information. Please try again");
				}
			} catch (Exception e)
			{ consultantTextAreaInformation.setText("Something went wrong when calling the database. Please try again or contact a developer");
			}
		}	
	// --------------------------------------------------------------------------------------------------------//----------------------------------------------------//
	
	// View information about project by selecting Project ID 
		@FXML
		public void ButtonViewProject () throws SQLException {
			try {
				if((ComboBoxViewProject.getSelectionModel().getSelectedItem().toString()!= "")) {  // kan inte göra det till int med integer value of för att den funktionen kan inte avgöra en boolean. 
					ResultSet rs = dal.getAllProjectInformation(Integer.valueOf((ComboBoxProjectProjects.getSelectionModel().getSelectedItem().toString())));
					while(rs.next()){
						viewProjectInformationTextArea.setText("Choosen project with the ProjectID" + rs.getString(2));
									}
					} else { viewProjectInformationTextArea.setText("Something went wrong");
				}
				}catch (Exception e) { 
					viewProjectInformationTextArea.setText("Something went wrong when calling the database");
					
				}
			{
			}
			}
		

		
	// --------------------------------------------------------------------------------------------------------//----------------------------------------------------//

		
	// Assigning Hours to an employee
	
	public void assignHoursButton() throws SQLException { // funkar ej 
		String f = TextFieldLogHours.getText(); // har en sträng som jag vill kasta till en float 
		try {
			if(!(ComboBoxLogConsultants.getSelectionModel().getSelectedItem() == null)  
				&&!(ComboBoxLogProjects.getSelectionModel().getSelectedItem() == null)
				&&!TextFieldLogHours.getText().isEmpty()){ 
				boolean check = dal.AssignHours(ComboBoxLogConsultants.getSelectionModel().getSelectedItem().toString(), Integer.valueOf((String) ComboBoxLogProjects.getSelectionModel().getSelectedItem()),
						Float.parseFloat(f));
			
			if(check == true ) {
				TextAreaLog.setText("You assigned " + TextFieldLogHours.getText() + " hours to employee" +
			ComboBoxLogConsultants.getSelectionModel().getSelectedItem() + "\n" + " working on project " + (ComboBoxLogProjects.getSelectionModel().getSelectedItem()));
			}
		}
			else { TextAreaLog.setText("Ooooopsie");
			
			}
			}
		catch(NumberFormatException e) {
			TextAreaProject.setText("Please make sure only numbers have been entered in the hours text field");
		}
		}

			
	
		// creating a project 
	public void projectRunButton() throws SQLException { // funkar ej
		try {
		if(!TextFieldProjectName.getText().trim().isEmpty()
			&& !TextFieldProjectID.getText().isEmpty()
			&& DatePickerProject.getValue() != null
			&& !TextFieldProjectBudget.getText().isEmpty()) {
			boolean check = dal.createProject(Integer.valueOf(TextFieldProjectID.getText().toString()), Integer.valueOf(TextFieldProjectBudget.getText().toString()),
					TextFieldProjectName.getText(), DatePickerProject.getValue().toString());
			if(check == true) {
				TextAreaProject.setText("You have successfully created the project (" + Integer.valueOf(TextFieldProjectID.getText()) +")" + "\n" + 
				"Startdate: " + DatePickerProject.getValue() + "\n" + "Budget: " + Integer.valueOf(TextFieldProjectBudget.getText()));
			
			}
			else {
				TextAreaProject.setText("Something went wrong when creating the project. Please try again or contact support");		
			}
		}
		else {
			TextAreaProject.setText("Something went wrong in the database. Make sure you have entered required fields");
		}
		}
		catch(NumberFormatException e) {
			TextAreaProject.setText("Please make sure to enter only digits for the projectID and budget");
		}
		
		
	}
	
	//----------------------------------------------------//----------------------------------------------------//----------------------------------------------------//----------------------------------------------------//
		// Creating a milestone 
	public void milestoneRunButton() throws SQLException {
		if (!TextFieldMilestonesType.getText().isEmpty()
			&& !(ComboBoxMilestoneProject.getSelectionModel().getSelectedItem() == null)
			&& DatePickerMilestone.getValue().toString().isEmpty()) {
				boolean check = dal.createMilestone(TextFieldMilestonesType.getText().trim(),
						Integer.valueOf(ComboBoxMilestoneProject.getSelectionModel().getSelectedItem().toString()),
						Integer.valueOf(DatePickerMilestone.getValue().toString()));
				if(check) {
					TextAreaMilestone.setText("Milestone " + TextFieldMilestonesType.getText() + " has been assigned to project " + 
							ComboBoxMilestoneProject.getSelectionModel().getSelectedItem().toString() + ".");	
				}
				else {
					TextAreaMilestone.setText("Something went wrong when creating the project. Please try again or contact support");	
				}
		}
		else {	
			TextAreaMilestone.setText("You have run into an error. Please try again");
		}
	}				
	
	//----------------------------------------------------//----------------------------------------------------//----------------------------------------------------//----------------------------------------------------//
	
	// VIEWING ALL INFORMATION ON AN EMPLOYEE
	
	
	// METHODS FOR FILLING COMBOBOXES 
	
	//CONSULTANTS 
	public void refreshComboBoXConsultants() throws SQLException {
		ObservableList<String> listConsultants = FXCollections.observableArrayList();
		
		ResultSet result = dal.getAllConsultantEmpID();
		while(result.next()){
			listConsultants.add(result.getString(1));
		}
		ComboBoxLogConsultants.setItems(listConsultants);
		ComboBoxProjectConsultants.setItems(listConsultants);
		ConsultantComboBox.setItems(listConsultants);
		ComboBoxProjectAddConsultant.setItems(listConsultants);
	}
	
	
	// MILESTONES  
	public void refreshComboBoxMilestone() throws SQLException {
		ObservableList<String> listMilestones = FXCollections.observableArrayList();
		ResultSet result = dal.viewMilestones();
		while(result.next()) {
			listMilestones.add(result.getString(1));
		}
		ComboBoxMilestoneMilestone.setItems(listMilestones);
		}
	
	//PROJECTS
	public void refreshComboBoxProject() throws SQLException {
		ObservableList<String> listProjects = FXCollections.observableArrayList();
		ResultSet result = dal.getProject();
		while(result.next()) {
			listProjects.add(result.getString(1));		
		}
		ComboBoxProjectProjects.setItems(listProjects);
		ComboBoxMilestoneProject.setItems(listProjects);
		ComboBoxLogProjects.setItems(listProjects);
		ComboBoxViewProject.setItems(listProjects);
		ComboBoxProjectAdd.setItems(listProjects);
	}

	//MANAGERS
	public void refreshComboBoxManager()throws SQLException {
		ObservableList<String> listManagers = FXCollections.observableArrayList();
		ResultSet result = dal.getManagers();
		while(result.next()) {
			listManagers.add(result.getString(2) + ", " + result.getString(1));
		}
		ComboBoxProjectManagers.setItems(listManagers);
		ComboBoxMilestoneManager.setItems(listManagers);
	}
	
	public Controller() throws IOException {
		   DAL dal = new DAL();
	}
	/*public void viewConsultantsHasWorked) throws SQLException{
		ObservableList<String> listEmployeesHasWorked = FXCollections.observableArrayList();
		Res
	}
	*/
		
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
			refreshComboBoxManager();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
