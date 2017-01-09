package jp.co.valtech.sudoku.web.config.enums;

import lombok.Getter;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * ページ繊維を列挙型で定義します。
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
public enum Forward {

	TOP("common/top"),
	CHOICE_QUESTION("choiceQuestion"),
	CREATE_ANSWER("createAnswer"),
	COMPLETE_ANSWER("completeAnswer"),
	CREATE_QUESTION("createQuestion"),
	PLAY_NUMBER_PLACE("playNumberPlace"),
	BEST_SCORE("bestScore"),
	BEST_SCORE_COMPLETE("bestScoreComplete"),
	COMPLETE_NUMBER_PLACE("completeNumberPlace"),
	SEARCH_ANSWER("searchAnswer"),
	DETAIL("detail"),
	INTRODUCE("introduce"),
	LINK_LIST("linkList");


	@Getter
	private String path;


	/**
	 * コンストラクタ
	 *
	 * @param value
	 */
	Forward(final String value) {
		this.path = value;
	}

	/**
	 * 列挙型を返却します。
	 *
	 * @param key
	 * @author uratamanabu
	 * @version 1.0
	 * @return　難易度
	 * @since 1.0
	 */
	@Nullable
	public static Forward getForward(@Nullable final String key) {
		for (Forward forward : Forward.values()) {
			if (forward.toString().equals(key)) {
				return forward;
			}
		}
		return null;
	}
}
