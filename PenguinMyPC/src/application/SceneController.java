package application;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SceneController {
	
	private SceneSwitcher sceneSwitcher = new SceneSwitcher();
	
	public void about() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About");
		alert.setHeaderText("PenguinMyPC");
		alert.setContentText("PenguinMyPC is a cross plaform JavaFX application developed to assist in the process of switching users to Linux."
		+ "\nCreated By Sam Thyen");

		alert.showAndWait();
	}
	
	public void backgroundInfoSceneSwitch (ActionEvent event) throws IOException {
		sceneSwitcher.setFXML("BackgroundInformation.fxml");
		sceneSwitcher.switchScene(event);
		
		
	
	}
	
	
	
	
	
}
