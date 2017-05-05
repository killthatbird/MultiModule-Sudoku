package jp.co.valtech.sudoku.web.controller.page;

import com.codeborne.selenide.Selenide;

/**
 * Created by uratamanabu on 2017/02/11.
 */
public class PlayNumberPlacePage {

		private static final String URL = "/playNumberPlace";

		public static String title() {
				return Selenide.title();
		}

		public static PlayNumberPlacePage open() {
				return Selenide.open(URL, PlayNumberPlacePage.class);
		}
}
