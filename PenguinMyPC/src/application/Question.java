package application;

import java.util.ArrayList;

public class Question {
	private int numberOfChoices;
	private String questionText;
	private ArrayList<String> choicesTextList = new ArrayList<>();
	private ArrayList<Integer> choicesValueList = new ArrayList<>();
	
	public Question () {
		
	}
	
	public void addChoiceText(String choiceText) {
		choicesTextList.add(choiceText);
	}
	
	
	public void addChoiceValue(int choiceValue) {
		choicesValueList.add(choiceValue);
	}
	
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	
	public int getChoiceValue(int choiceIndex) {
		return choicesValueList.get(choiceIndex);
	}
	
	public String getChoiceText(int choiceNumber) {
		return choicesTextList.get(choiceNumber);
	}
	
	public int getNumberOfChoices() {
		return choicesTextList.size();
	}

	public String getQuestionText() {
		return questionText;
	}
	
}
