package application;
	
import java.io.InputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//Creating Parent from FXML and creating scene from root.
			Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
			Scene scene = new Scene(root);
			
			//Window Setup
			primaryStage.setTitle("PenguinMyPC");
			InputStream iconStream = getClass().getResourceAsStream("Tux.png");
			Image image = new Image(iconStream);
			primaryStage.getIcons().add(image);
			
			primaryStage.setMinWidth(800);
			primaryStage.setMinHeight(600);
			
			
			//Setting and showing the scene
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
	}
	
	public static void main(String[] args) {
		launch();
	}
}
