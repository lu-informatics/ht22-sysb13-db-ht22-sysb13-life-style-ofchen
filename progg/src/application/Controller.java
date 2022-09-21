package application;


import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class Controller  {
	DAL dal = new DAL();

	
	
	
//	RadioButtonConsultant.setToggleGroup(tgConsultant)
	
	
	// TextField -- Consultant 
	@FXML
	private TextField TextFieldConsultantName = new TextField();
	@FXML
	private TextField TextFieldConsultantAddress = new TextField();
	@FXML
	private TextField TextFieldConsultantStartdate = new TextField();
	@FXML
	private TextField TextFieldSalary = new TextField();
	
	//TextField -- Log
	@FXML
	private TextField TextFieldLogHours = new TextField();
	
	// TextField -- Project 
	@FXML
	private TextField TextFieldProjectName = new TextField();
	@FXML
	private TextField TextFieldProjectStartDate = new TextField();
	@FXML
	private TextField TextFieldProjectBudget = new TextField();
	
	//TextField -- Milestones
	@FXML
	private TextField TextFieldMilestonesType = new TextField();
	
	@FXML
	private TextField TextFieldDateOfCompletition = new TextField(); 
	
	// RadioButton Consultants 
	@FXML
	private Button ButtonCreateEmployee = new Button();
	@FXML
	private RadioButton RadioButtonConsultant = new RadioButton();
	@FXML
	private RadioButton RadioButtonManager = new RadioButton();
	
	// RadioButton Log
	@FXML
	private Button ButtonAssignTime = new Button();
	
	// RadioButton Project
	@FXML
	private Button ButtonCreateProject = new Button();
	@FXML
	private RadioButton RadioButtonViewProject = new RadioButton();
	@FXML
	private RadioButton RadioButtonAssignConsultant = new RadioButton();
	@FXML
	private RadioButton RadioButtonActive = new RadioButton();
	@FXML
	private RadioButton RadioButtonNotActive = new RadioButton();
	
	// RadioButton Milestones
	@FXML
	private Button RunButtonCreateMileStone = new Button();

	
	// ComboBoxes Log
	@FXML
	private ComboBox ComboBoxLogConsultants = new ComboBox();
	@FXML
	private ComboBox ComboBoxLogProjects = new ComboBox();
	
	// ComboBoxes Project
	private ComboBox ComboBoxProjectManagers = new ComboBox();
	@FXML
	private ComboBox ComboBoxProjectProjects = new ComboBox();
	@FXML
	private ComboBox ComboBoxProjectConsultants = new ComboBox();
	
	// comboBoxes Milestone
	@FXML
	private ComboBox ComboBoxMilestoneManager = new ComboBox();
	@FXML
	private ComboBox ComboBoxMilestoneMilestone = new ComboBox();
	@FXML
	private ComboBox ComboBoxMilestoneProject = new ComboBox();
	
	// TabePane
	@FXML
	private TabPane tabpane = new TabPane();
	
	// All TextAreas
	@FXML
	private TextArea textarea = new TextArea();
	@FXML
	private TextArea TextAreaConsultant = new TextArea();
	@FXML
	private TextArea TextAreaLog = new TextArea();
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

		// Creating an employee 
	public void createEmployeeRunButton() throws SQLException {
		if(TextFieldConsultantName.getText().isEmpty()
				|| TextFieldConsultantAddress.getText().isEmpty()
				|| TextFieldConsultantStartdate.getText().isEmpty()
				|| TextFieldSalary.getText().isEmpty()) {
			
			TextAreaConsultant.setText("Oops something went wrong. Please make sure all requiered fields have been filled in before you press create employee again");
		}		
		//else {
	
	}
		// creating a project 
	public void projectRunButton() throws SQLException {
				if(TextFieldProjectName.getText().isEmpty()
				|| TextFieldProjectStartDate.getText().isEmpty()
				|| TextFieldProjectBudget.getText().isEmpty()) {
				
				TextAreaConsultant.setText("Something went wrong in the database. Make sure you have entered required fields");
				}
		}
				//else {		
			
		
// dal.createProject(Integer.valueOf(TextFieldProjectBudget.getText()), TextFieldProjectName.getText(), TextFieldProjectStartDate.getText());
//	if(check){
	
	 	// creating milestones
	public void milestoneRunButton() throws SQLException {
				if (!TextFieldMilestonesType.getText().isEmpty()
					||ComboBoxMilestoneManager.getSelectionModel().getSelectedItem() != null)
					TextFieldMilestonesType.clear();
				
				
				TextAreaMilestone.setText("You have run into an error. Please try again");
				}				
				
	
	
	// METHOD FOR REFRESHING/FILLING THE CONSULTANTCOMBOBOX 
	public void refreshComboBoxLogConsultants() throws SQLException {
		ObservableList<String> listConsultants = FXCollections.observableArrayList();
		
		ResultSet result = dal.getConsultantEmpID();
		while(result.next()){
			listConsultants.add(result.getString(1));
		}
		ComboBoxLogConsultants.setItems(listConsultants);
		ComboBoxProjectConsultants.setItems(listConsultants);
	}
	
	// METHOD FOR REFRESHING/FILLING PROJECT COMBOBOX 
	
	public void refreshComboBoxLogProjects() throws SQLException {
		ObservableList<String> listProjects = FXCollections.observableArrayList();
		
		ResultSet result = dal.getProjectID();
		while(result.next()) {
			listProjects.add(result.getString(1));
		}
		ComboBoxProjectProjects.setItems(listProjects);
		ComboBoxMilestoneProject.setItems(listProjects);
		ComboBoxLogProjects.setItems(listProjects);	
		}
		
// METHOD FOR FILLING MILESTONES COMBOBOX
	public void refreshComboBoxMilestoneMilestone() throws SQLException {
		ObservableList<String> listMilestones = FXCollections.observableArrayList();
		ResultSet result = dal.viewMilestones();
		while(result.next()) {
			listMilestones.add(result.getString(1));
		}
		ComboBoxMilestoneMilestone.setItems(listMilestones);
		}
	
	public Controller() throws IOException {
		   DAL dal = new DAL();
 }
	// METHOD FOR FILLING MANAGER COMBOBOXES 
	public void refreshComboBoxMilestoneManager()throws SQLException {
		ObservableList<String> listManagers = FXCollections.observableArrayList();
		ResultSet result = dal.getManagers();
		while(result.next()) {
			listManagers.add(result.getString(1));
		}
		ComboBoxProjectManagers.setItems(listManagers);
		ComboBoxMilestoneManager.setItems(listManagers);
	}
	
	/*public void viewConsultantsHasWorked) throws SQLException{
		ObservableList<String> listEmployeesHasWorked = FXCollections.observableArrayList();
		Res
	}
	*/
	
	private ToggleGroup tgConsultant = new ToggleGroup();
	
	@FXML
	private void initialize() {
		tgConsultant = new ToggleGroup();
		RadioButtonConsultant.setToggleGroup(tgConsultant);
		RadioButtonManager.setToggleGroup(tgConsultant);
		
		
		
		try {
			refreshComboBoxLogConsultants();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {refreshComboBoxLogProjects();
		} catch (SQLException e) {
			e.printStackTrace();	
		}
		try {
			refreshComboBoxMilestoneMilestone();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			refreshComboBoxMilestoneManager();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
