package jp.co.valtech.sudoku.web.controller.page;


import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by uratamanabu on 2017/01/16.
 */
public class ChoiceQuestionPage {

	private static final String URL = "https://localhost:8081/choiceQuestion";
	@FindBy(name = "content")
	public SelenideElement content;
	@FindBy(name = "post")
	public SelenideElement post;
	@FindBy(id = "echo")
	public SelenideElement echo;

	public static ChoiceQuestionPage open() {
		return Selenide.open(URL, ChoiceQuestionPage.class);
	}

}
