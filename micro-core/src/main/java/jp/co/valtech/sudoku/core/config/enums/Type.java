package jp.co.valtech.sudoku.core.config.enums;

import lombok.Getter;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * 数独のサイズを列挙型で定義します。
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
public enum Type {
	/**
	 * size:4
	 */
	SIZE4(4),

	/**
	 * size:9
	 */
	SIZE9(9);

	@Getter
	private int size;

	/**
	 * コンストラクタ
	 *
	 * @param size
	 */
	Type(int size) {
		this.size = size;
	}

	/**
	 * 列挙型を返却します。
	 *
	 * @param size
	 * @return 数独のサイズ
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	@Nullable
	public static Type getType(final int size) {
		for (Type type : Type.values()) {
			if (size == type.getSize()) {
				return type;
			}
		}
		return null;
	}
}
