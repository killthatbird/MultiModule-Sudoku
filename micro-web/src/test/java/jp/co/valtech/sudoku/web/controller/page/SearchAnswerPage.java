package jp.co.valtech.sudoku.web.controller.page;

import com.codeborne.selenide.Selenide;

/**
 * Created by uratamanabu on 2017/02/11.
 */
public class SearchAnswerPage {

		private static final String URL = "/searchAnswer";

		public static String title() {
				return Selenide.title();
		}

		public static SearchAnswerPage open() {
				return Selenide.open(URL, SearchAnswerPage.class);
		}
}
