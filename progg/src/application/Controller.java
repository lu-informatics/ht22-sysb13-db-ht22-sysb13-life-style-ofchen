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
		try {
			System.out.print("HEJSAN");
			ResultSet rs = dal.getProjects();
			
			System.out.println(dal.getProjects());
			while(rs.next()) {
				textarea.setText(rs.getString(1));
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
