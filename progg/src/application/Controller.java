package application;


import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import javafx.scene.control.TextField;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class Controller {
	
	DAL dal = new DAL();
	
	/*
	// TextField -- Consultant 
	@FXML
	private TextField TextFieldConsultantName = new TextField();
	@FXML
	private TextField TextFieldConsultantAddress = new TextField();
	@FXML
	private TextField TextFieldConsultantStartdate = new TextField();
	@FXML
	private TextField TextFieldSalary = new TextField();
	*/
	//TextField -- Log
	//@FXML
	//private TextField TextFieldLogHours = new TextField();
	
	// TextField -- Project 
	/*@FXML
	private TextField TextFieldProjectName = new TextField();
	@FXML
	private TextField TextFieldProjectStartDate = new TextField();
	@FXML
	private TextField TextFieldProjectBudget = new TextField();
	
	//TextField -- Milestones
	@FXML
	private TextField TextFieldMilestonesType = new TextField();
	*/
	// RadioButton Consultants 
	@FXML
	private RadioButton RadioButtonCreateEmployee = new RadioButton();
	@FXML
	private RadioButton RadioButtonConsultant = new RadioButton();
	@FXML
	private RadioButton RadioButtonManager = new RadioButton();
	
	// RadioButton Log
	@FXML
	private RadioButton RadioButtonAssignTime = new RadioButton();
	
	// RadioButton Project
	@FXML
	private RadioButton RadioButtonCreateProject = new RadioButton();
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
	private RadioButton RadioButtonCreateMileStone = new RadioButton();
	@FXML
	private RadioButton RadioButtonAssignMilestone = new RadioButton();
	
	// ComboBoxes Log
	@FXML
	private ComboBox ComboBoxLogConsultants = new ComboBox();
	@FXML
	private ComboBox ComboBoxLogProjects = new ComboBox();
	
	// ComboBoxes Project
	private ComboBox ComboboxProjectManagers = new ComboBox();
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


	public void refreshComboBoxLogConsultants() throws SQLException {
		ObservableList<String> listConsultants = FXCollections.observableArrayList();
		
		ResultSet result = dal.getConsultantEmpID();
		while(result.next()){
			listConsultants.add(result.getString(1));
		}
		ComboBoxLogConsultants.setItems(listConsultants);
		ComboBoxProjectConsultants.setItems(listConsultants);
	}
	
	
		
	
	
	
	
	@FXML
	private void initialize() {
		try {
			refreshComboBoxLogConsultants();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
