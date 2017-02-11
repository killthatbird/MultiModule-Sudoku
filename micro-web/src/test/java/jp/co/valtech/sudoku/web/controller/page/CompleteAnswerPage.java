package jp.co.valtech.sudoku.web.controller.page;

import com.codeborne.selenide.Selenide;

/**
 * Created by uratamanabu on 2017/01/20.
 */
public class CompleteAnswerPage {

	private static final String URL = "/completeAnswer";

	public static String title() {
		return Selenide.title();
	}

	public static CompleteAnswerPage open() {
		return Selenide.open(URL, CompleteAnswerPage.class);
	}
}
