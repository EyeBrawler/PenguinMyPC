package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

public class FinalResultsSceneController {
	
	@FXML 
	private Label scoreLabel;
	
	@FXML
	private Label resultsMessageLabel;
	
	public void about() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About");
		alert.setHeaderText("PenguinMyPC");
		alert.setContentText("PenguinMyPC is a cross plaform JavaFX application developed to assist in the process of switching users to Linux."
		+ "\nCreated By Sam Thyen");

		alert.showAndWait();
	}
	
	public void initialize() {
		scoreLabel.setText("Score: " + QuestionInterfaceController.score);
		
		if(QuestionInterfaceController.score < -3) {
			resultsMessageLabel.setText("Linux is probably not the best fit for you.");
		}
		else if(QuestionInterfaceController.score >= -3 && QuestionInterfaceController.score < 3) {
			resultsMessageLabel.setText("It is unclear whether Linux would work for you.");
		}
		else {
			resultsMessageLabel.setText("Linux is probably a great operating system for you!");
		}
	}
}
