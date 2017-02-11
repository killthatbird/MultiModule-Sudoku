package jp.co.valtech.sudoku.web.controller.page;


import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

public class ChoiceQuestionPage {

	private static final String URL = "/choiceQuestion";
	@FindBy(name = "content")
	public SelenideElement content;
	@FindBy(name = "post")
	public SelenideElement post;
	@FindBy(id = "echo")
	public SelenideElement echo;

	public static String title() {
		return Selenide.title();
	}

	public static ChoiceQuestionPage open() {
		return Selenide.open(URL, ChoiceQuestionPage.class);
	}

}
