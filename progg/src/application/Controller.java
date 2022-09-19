package application;

import javafx.scene.control.TextArea;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;

public class Controller {
	
	DAL dal = new DAL();
	
	@FXML
	private TextArea textarea = new TextArea();
	
	@FXML
	private void initialize() {
		
	}
	
	
}
