package jp.co.valtech.sudoku.web.controller.page;


import com.codeborne.selenide.Selenide;

public class ChoiceQuestionPage {

	private static final String URL = "/choiceQuestion";

	public static String title() {
		return Selenide.title();
	}

	public static ChoiceQuestionPage open() {
		return Selenide.open(URL, ChoiceQuestionPage.class);
	}

}
