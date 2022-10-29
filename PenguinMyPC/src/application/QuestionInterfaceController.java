package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class QuestionInterfaceController {
	
	@FXML
	private Label questionLabel;
	
	@FXML
	private VBox questionVBox;
	
	@FXML 
	private Button nextButton;
	
	private SceneSwitcher sceneSwitcher = new SceneSwitcher();
	private ArrayList<RadioButton> radioButtonList = new ArrayList<>();
	private ArrayList<Question> questionList = new ArrayList<>();
	
	private int currentQuestionNumber;
	private int numberOfQuestions;
	private int score = 0;
	private ToggleGroup radioButtonGroup;
	
	
	
	
	public void about() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About");
		alert.setHeaderText("PenguinMyPC");
		alert.setContentText("PenguinMyPC is a cross plaform JavaFX application developed to assist in the process of switching users to Linux."
		+ "\nCreated By Sam Thyen");

		alert.showAndWait();
	}
	
	public void initialize() {
		for(int i = 0; i < 4; i++) {
			radioButtonList.add(new RadioButton("Radio Button"));
		}
		
		questionVBox.getChildren().addAll(radioButtonList);
		radioButtonGroup = new ToggleGroup();
		
		for(RadioButton radioButton : radioButtonList) {
			radioButton.setToggleGroup(radioButtonGroup);
		}
		
		//Code to make button changes on selection
		radioButtonGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
		     public void changed(ObservableValue<? extends Toggle> changed,
		                         Toggle oldVal, Toggle newVal) {
		    	 if(radioButtonGroup.getSelectedToggle() == null) {
		 			nextButton.setDisable(true);
		 		} else {
		 			nextButton.setDisable(false);
		 		}
		     }
		 });
	
		
		
		loadQuestionData();
		
		//Loading First Question
		loadNextQuestion();
		
		
		
	}
	
	
	public void loadQuestionData() {
		
		ArrayList<String> choiceLinesList = new ArrayList<>();
		ArrayList<String> questionLinesList = new ArrayList<>();
 		
		//Try Catch For Questions
		try {
			File questionsFile = new File("./src/application/Questions.txt");
			Scanner questionsScanner = new Scanner(questionsFile);			
			
			while(questionsScanner.hasNextLine()) {
				questionLinesList.add(questionsScanner.nextLine());
				
			}
			
			questionsScanner.close();
			
		} catch (Exception e){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("File Not Found Error");
			alert.setContentText("The file \"Questions.txt\" was not found.");
			
			alert.showAndWait();
		}
		
		
		//Try Catch For Choices
		try {
			File choicesFile = new File("./src/application/AnswerChoices.txt");
			Scanner choicesScanner = new Scanner(choicesFile);
			
			while(choicesScanner.hasNextLine()) {
				choiceLinesList.add(choicesScanner.nextLine());
			}
			
			choicesScanner.close();
			
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("File Not Found Error");
			alert.setContentText("The file \"AnswerChoices.txt\" was not found.");
			
			alert.showAndWait();
		}
		
		if(questionLinesList.size() == choiceLinesList.size()) { 
			numberOfQuestions = questionLinesList.size();
			convertListsToQuestions(questionLinesList, choiceLinesList);
			currentQuestionNumber = 1;
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("File Mismatch Error");
			alert.setContentText("The two files \"Questions.txt\" \"AnswerChoices.txt\" have a mismatch that is preventing the proper loading of data");
		}
		
	}

	
	public void loadNextQuestion() {
		
		//Ensuring Button is False when new question is loaded
		nextButton.setDisable(true);
		
		if(currentQuestionNumber == numberOfQuestions) {
			nextButton.setText("See Final Results");
		}
		
		if(currentQuestionNumber < numberOfQuestions) {
			if(radioButtonGroup.getSelectedToggle() != null) {
				saveAnswerChoice();
			}
			
			radioButtonGroup.selectToggle(null);
			
			if(questionList.get(currentQuestionNumber).getNumberOfChoices() != radioButtonList.size()) {
				for(int i = questionList.get(currentQuestionNumber - 1).getNumberOfChoices(); i < radioButtonList.size(); i++) {
					radioButtonList.get(i).setVisible(false);
				}
			} 
			
			questionLabel.setText(questionList.get(currentQuestionNumber - 1).getQuestionText());
			
			for(int i = 0; i < questionList.get(currentQuestionNumber - 1).getNumberOfChoices(); i++) {
				radioButtonList.get(i).setText(questionList.get(currentQuestionNumber - 1).getChoiceText(i));
				
			}
			
			if(currentQuestionNumber < numberOfQuestions) {
				currentQuestionNumber++;
			}
			
			
			
		} else {
			finalResultsSceneSwitch();
		}
		
		
		
	}
	
	public void saveAnswerChoice() {
		//Not Saving Choice For Last Question Seen
		//Not Loading Last Question
		
		for(int i = 0; i < questionList.get(currentQuestionNumber - 1).getNumberOfChoices(); i++) {
			if(radioButtonList.get(i).isSelected()) {
				
				System.out.println("inside if statement");
				score += questionList.get(currentQuestionNumber - 1).getChoiceValue(i);
				break;
			}
		}
		
	}
	
	public void convertListsToQuestions(ArrayList<String> questionLinesList, ArrayList<String> choiceLinesList) {
		//Populating Array List
		for(int i = 0; i < questionLinesList.size(); i++) {
			questionList.add(new Question());
			
			//Transferring Question From QuestionLinesList
			questionList.get(i).setQuestionText(questionLinesList.get(i));
			
			//Splitting Choices Line
			String [] lineArray = choiceLinesList.get(i).split("~");
			
			
			//Taking Split Array and Adding it to current question
			for(int j = 0; j < lineArray.length; j++) {
				
				
				//Checking if index in array is even or odd to alternate
				if(j % 2 == 0) {
					questionList.get(i).addChoiceText(lineArray[j].trim());
				}
				else {
					questionList.get(i).addChoiceValue(Integer.parseInt(lineArray[j].trim()));

				}
				
			}
			
		}
		
	}
	
	public void finalResultsSceneSwitch() {
		System.out.println("Final Scene");
		System.out.println(score);
		System.out.println(questionList.size());
		sceneSwitcher.setFXML("FinalResultsScene.fxml");
		//sceneSwitcher.switchScene(event);
	
	}
	


}
