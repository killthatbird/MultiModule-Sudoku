package jp.co.valtech.sudoku.core.config.enums;

import lombok.Getter;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * セレクトボックスを列挙型で定義します。
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
public enum Selector {

	PERFECT_MATCH("完全一致", 1),
	FORWARD_MATCH("前方一致", 2),
	BACKWARD_MATCH("後方一致", 3),
	PARTIAL_MATCH("部分一致", 4),
	AROUND5("前後５件", 5),
	MORE_BIG("より大きい", 6),
	MORE_SMALL("より小さい", 7);

	@Getter
	private String label;

	@Getter
	private int value;

	/**
	 * コンストラクタ
	 *
	 * @param label
	 * @param value
	 */
	Selector(final String label, final int value) {
		this.label = label;
		this.value = value;
	}

	/**
	 * 列挙型を返却します。
	 *
	 * @param value
	 * @author uratamanabu
	 * @version 1.0
	 * @return　セレクトボックス
	 * @since 1.0
	 */
	@Nullable
	public static Selector getSelector(final int value) {
		for (Selector selector : Selector.values()) {
			if (selector.getValue() == value) {
				return selector;
			}
		}
		return null;
	}
}
