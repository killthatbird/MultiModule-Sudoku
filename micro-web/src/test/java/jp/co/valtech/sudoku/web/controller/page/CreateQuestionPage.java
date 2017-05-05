package jp.co.valtech.sudoku.web.controller.page;

import com.codeborne.selenide.Selenide;

/**
 * Created by uratamanabu on 2017/02/11.
 */
public class CreateQuestionPage {

		private static final String URL = "/createQuestion";

		public static String title() {
				return Selenide.title();
		}

		public static CreateQuestionPage open() {
				return Selenide.open(URL, CreateQuestionPage.class);
		}

}
