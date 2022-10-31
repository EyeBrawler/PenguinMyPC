package application;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class FinalResultsSceneController {
	public void about() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About");
		alert.setHeaderText("PenguinMyPC");
		alert.setContentText("PenguinMyPC is a cross plaform JavaFX application developed to assist in the process of switching users to Linux."
		+ "\nCreated By Sam Thyen");

		alert.showAndWait();
	}
}
