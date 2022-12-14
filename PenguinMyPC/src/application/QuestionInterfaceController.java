package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
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
	private Button finalResultsButton;

	@FXML
	private Button nextButton;
	
	
	private SceneSwitcher sceneSwitcher = new SceneSwitcher();
	private ArrayList<RadioButton> radioButtonList = new ArrayList<>();
	private ArrayList<Question> questionList = new ArrayList<>();
	
	private int currentQuestionNumber;
	private int numberOfQuestions;
	public static int score = 0;
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
		
		//Adding all Radio Buttons to Toggle Group and adding some formatting
		for(RadioButton radioButton : radioButtonList) {
			radioButton.setToggleGroup(radioButtonGroup);
			VBox.setMargin(radioButton, new Insets(0,0,20,0));
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
			nextButton.setText("Submit All Answers");
			
		}
		
		//Saving response from previous question if possible
		if(radioButtonGroup.getSelectedToggle() != null) {
			saveAnswerChoice();
		}
		
		if(currentQuestionNumber <= numberOfQuestions) {
			
			//Clearing Selection from Previous Question
			radioButtonGroup.selectToggle(null);
			
			//Hiding Unused Buttons for Question
			if(questionList.get(currentQuestionNumber - 1).getNumberOfChoices() != radioButtonList.size()) {
				for(int i = questionList.get(currentQuestionNumber - 1).getNumberOfChoices(); i < radioButtonList.size(); i++) {
					radioButtonList.get(i).setVisible(false);
				}
			} 
			
			//Setting Question Text
			questionLabel.setText(questionList.get(currentQuestionNumber - 1).getQuestionText());
			
			//Setting Text for Choices
			for(int i = 0; i < questionList.get(currentQuestionNumber - 1).getNumberOfChoices(); i++) {
				radioButtonList.get(i).setText(questionList.get(currentQuestionNumber - 1).getChoiceText(i));
				
			}
			
			//Incrementing Question
			currentQuestionNumber++;
			
			
			
			
		} else {
			finalResultsButton.setVisible(true);
			nextButton.setDisable(true);
			
		}
		
		
		
	}
	
	public void saveAnswerChoice() {
		
		//Loop that looks through the choices and finds selected choice to add to the score
		for(int i = 0; i < questionList.get(currentQuestionNumber - 2).getNumberOfChoices(); i++) {
			
			if(radioButtonList.get(i).isSelected()) {
				
				score += questionList.get(currentQuestionNumber - 2).getChoiceValue(i);
				break;
			}
			
		}
		
		//Test Code for Printing Avaible Answer Choices and Questions
		/*
		System.out.println("Question " + (currentQuestionNumber - 1));
		for(int j = 0; j < questionList.get(currentQuestionNumber - 2).getNumberOfChoices(); j++) {
			System.out.println("Choice " + j + " = " + questionList.get(currentQuestionNumber - 2).getChoiceValue(j));
		}
		*/
			
		
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
	
	public void finalResultsSceneSwitch(ActionEvent event) throws IOException {
		sceneSwitcher.setFXML("FinalResultsScene.fxml");
		sceneSwitcher.switchScene(event);
	
	}
	


}
