package jp.co.valtech.sudoku.web.controller.page;

import com.codeborne.selenide.Selenide;


/**
 * Created by uratamanabu on 2017/01/20.
 */
public class BestScoreCompletePage {

	private static final String URL = "https://localhost:8081/Sudoku/bestScoreComplete";

	public static String title() {
		return Selenide.title();
	}

	public static BestScoreCompletePage open() {
		return Selenide.open(URL, BestScoreCompletePage.class);
	}
}
