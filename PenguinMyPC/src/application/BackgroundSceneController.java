package application;


import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.control.Alert.AlertType;

public class BackgroundSceneController {
	
	@FXML
	private WebView webView;
	
	@FXML
	private WebEngine engine;
	
	
	private SceneSwitcher sceneSwitcher = new SceneSwitcher();
	
	public void initialize() {
		engine = webView.getEngine();
		loadPage();
	}
	
	public void loadPage() {
		engine.load("https://www.linux.com/what-is-linux/");
	}
	
	public void about() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About");
		alert.setHeaderText("PenguinMyPC");
		alert.setContentText("PenguinMyPC is a cross plaform JavaFX application developed to assist in the process of switching users to Linux."
		+ "\nCreated By Sam Thyen");

		alert.showAndWait();
	}
	
	public void questionSceneSwitch(ActionEvent event) throws IOException {
		sceneSwitcher.setFXML("QuestionScene.fxml");
		sceneSwitcher.switchScene(event);
	
	}
}
