//Class to Switch Scenes 

package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneSwitcher {
	
	private String fxmlName;
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void switchScene(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(this.fxmlName));
		root = loader.load();
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void setFXML(String fxmlName) {
		this.fxmlName = fxmlName;
		
	}
	
	
	

	
}
