package jp.co.valtech.sudoku.web.config.enums;

import lombok.Getter;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Created by uratamanabu on 2017/07/02.
 */
public class UrlConstants {

		public final static String URL_TOP = "top";
		public final static String URL_CHOICE_QUESTION = "choiceQuestion";
		public final static String URL_CREATE_ANSWER = "createAnswer";
		public final static String URL_COMPLETE_ANSWER = "completeAnswer";
		public final static String URL_CREATE_QUESTION = "createQuestion";
		public final static String URL_PLAY_NUMBER_PLACE = "playNumberPlace";
		public final static String URL_BEST_SCORE = "bestScore";
		public final static String URL_BEST_SCORE_COMPLETE = "bestScoreComplete";
		public final static String URL_COMPLETE_NUMBER_PLACE = "completeNumberPlace";
		public final static String URL_SEARCH_ANSWER = "searchAnswer";
		public final static String URL_DETAIL = "detail";
		public final static String URL_INTRODUCE = "introduce";
		public final static String URL_LINK_LIST = "linkList";

		/**
		 * ページを列挙型で定義します。
		 *
		 * @author uratamanabu
		 * @version 1.0
		 * @since 1.0
		 */
		public enum Forward {

				TOP(URL_TOP),
				CHOICE_QUESTION(URL_CHOICE_QUESTION),
				CREATE_ANSWER(URL_CREATE_ANSWER),
				COMPLETE_ANSWER(URL_COMPLETE_ANSWER),
				CREATE_QUESTION(URL_CREATE_QUESTION),
				PLAY_NUMBER_PLACE(URL_PLAY_NUMBER_PLACE),
				BEST_SCORE(URL_BEST_SCORE),
				BEST_SCORE_COMPLETE(URL_BEST_SCORE_COMPLETE),
				COMPLETE_NUMBER_PLACE(URL_COMPLETE_NUMBER_PLACE),
				SEARCH_ANSWER(URL_SEARCH_ANSWER),
				DETAIL(URL_DETAIL),
				INTRODUCE(URL_INTRODUCE),
				LINK_LIST(URL_LINK_LIST),;

				@Getter
				private String path;

				/**
				 * コンストラクタ
				 */
				Forward(final String value) {
						this.path = value;
				}

				/**
				 * 列挙型を返却します。
				 *
				 * @author uratamanabu
				 * @version 1.0
				 * @return　難易度
				 * @since 1.0
				 */
				public static Forward getForward(@Nullable final String key) {
						for (Forward forward : Forward
								.values()) {
								if (forward.toString().equals(key)) {
										return forward;
								}
						}
						return null;
				}
		}

}
