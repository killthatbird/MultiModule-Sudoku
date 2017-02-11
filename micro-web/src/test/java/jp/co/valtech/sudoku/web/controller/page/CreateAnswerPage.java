package jp.co.valtech.sudoku.web.controller.page;

import com.codeborne.selenide.Selenide;

/**
 * Created by uratamanabu on 2017/02/01.
 */
public class CreateAnswerPage {

	private static final String URL = "/createAnswer";


	public static String title() {
		return Selenide.title();
	}

	public static CreateAnswerPage open() {
		return Selenide.open(URL, CreateAnswerPage.class);
	}

}
