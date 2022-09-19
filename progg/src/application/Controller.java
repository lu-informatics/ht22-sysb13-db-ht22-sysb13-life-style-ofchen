package application;

import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.awt.TextField;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;

public class Controller {
	
	DAL dal = new DAL();
	
	@FXML
	// TextField -- Consultant 
	private TextField TextFieldConsultantName = new TextField();
	private TextField TextFieldConsultantAddress = new TextField();
	private TextField TextFieldConsultantStartdate = new TextField();
	private TextField TextFieldSalary = new TextField();
	
	//TextField -- Log
	private TextField TextFieldLogHours = new TextField();
	
	// TextField -- Project 
	private TextField TextFieldProjectName = new TextField();
	private TextField TextFieldProjectStartDate = new TextField();
	private TextField TextFieldProjectBudget = new TextField();
	
	//TextField -- Milestones
	private TextField TextFieldMilestonesType = new TextField();
	
	// RadioButton Consultants 
	private RadioButton RadioButtonCreateEmployee = new RadioButton();
	private RadioButton RadioButtonConsultant = new RadioButton();
	private RadioButton RadioButtonManager = new RadioButton();
	
	// RadioButton Log
	private RadioButton RadioButtonAssignTime = new RadioButton();
	
	// RadioButton Project
	private RadioButton RadioButtonCreateProject = new RadioButton();
	private RadioButton RadioButtonViewProject = new RadioButton();
	private RadioButton RadioButtonAssignConsultant = new RadioButton();
	private RadioButton RadioButtonActive = new RadioButton();
	private RadioButton RadioButtonNotActive = new RadioButton();
	
	// RadioButton Milestones
	private RadioButton RadioButtonCreateMileStone = new RadioButton();
	private RadioButton RadioButtonAssignMilestone = new RadioButton();
	
	// ComboBoxes Log
	private ComboBox ComboBoxLogConsultants = new ComboBox();
	private ComboBox ComboBoxLogProjects = new ComboBox();
	
	// ComboBoxes Project
	private ComboBox ComboboxProjectManagers = new ComboBox();
	private ComboBox ComboBoxProjectProjects = new ComboBox();
	private ComboBox ComboBoxProjectConsultants = new ComboBox();
	
	// comboBoxes Milestone
	private ComboBox ComboBoxMilestoneManager = new ComboBox();
	private ComboBox ComboBoxMilestoneMilestone = new ComboBox();
	private ComboBox ComboBoxMilestoneProject = new ComboBox();
	
	// TabePane
	private TabPane tabpane = new TabPane();
	
	// All TextAreas
	private TextArea textarea = new TextArea();
	private TextArea TextAreaConsultant = new TextArea();
	private TextArea TextAreaLog = new TextArea();
	private TextArea TextAreaProject = new TextArea();
	private TextArea TextAreaMilestone = new TextArea();
	
	// All AnchorPanes
	private AnchorPane AnchorPaneConsultant = new AnchorPane();
	private AnchorPane AnchorPaneProject = new AnchorPane();
	private AnchorPane AnchorPaneLog = new AnchorPane();
	private AnchorPane AnchorPaneMilestone = new AnchorPane();
	
	// All tabs
	private Tab TabConsultant = new Tab();
	private Tab TabLog = new Tab();
	private Tab TabProject = new Tab();
	private Tab TabMilestone = new Tab();



	
	
	
	
	@FXML
	private void initialize() {
		
	}
	
	
}
