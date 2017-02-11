package jp.co.valtech.sudoku.web.controller.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by uratamanabu on 2017/01/20.
 */
public class BestScoreCompletePage {

	private static final String URL = "https://localhost:8081/Sudoku/bestScoreComplete";
	@FindBy(name = "content")
	public SelenideElement content;
	@FindBy(name = "post")
	public SelenideElement post;
	@FindBy(id = "echo")
	public SelenideElement echo;

	public static String title() {
		return Selenide.title();
	}

	public static BestScoreCompletePage open() {
		return Selenide.open(URL, BestScoreCompletePage.class);
	}
}
