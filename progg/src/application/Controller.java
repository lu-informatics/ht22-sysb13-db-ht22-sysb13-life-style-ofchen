package application;

import javafx.scene.control.RadioButton;
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
	// Consultant 
	private TabPane tabpane = new TabPane();
	private TextArea textarea = new TextArea();
	private TextField TextFieldConsultantName = new TextField();
	private TextField TextFieldConsultantAddress = new TextField();
	private TextField TextFieldConsultantStartdate = new TextField();
	private TextField TextFieldSalary = new TextField();
	private RadioButton RadioButtonConsultant = new RadioButton();
	private RadioButton RadioButtonManager = new RadioButton();
	private AnchorPane AnchorPaneConsultant = new AnchorPane();
	
	
	@FXML
	private void initialize() {
		
	}
	
	
}
