package jp.co.valtech.sudoku.web.controller.page;

import com.codeborne.selenide.Selenide;

public class BestScorePage {

	private static final String URL = "/bestScore";

	public static String title() {
		return Selenide.title();
	}

	public static BestScorePage open() {
		return Selenide.open(URL, BestScorePage.class);
	}

}
