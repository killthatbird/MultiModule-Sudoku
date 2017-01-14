package jp.co.valtech.sudoku.core.utils;

import jp.co.valtech.sudoku.core.config.CommonConstant;
import jp.co.valtech.sudoku.core.config.enums.Type;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.api.list.primitive.MutableIntList;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.primitive.IntLists;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static lombok.AccessLevel.PRIVATE;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class ESListWrapUtil {

	/**
	 * @param size
	 * @param warmEaten
	 * @return
	 */
	public static MutableList<String> createCells(int size, int warmEaten) {
		MutableList<String> result = Lists.mutable.empty();
		for (int count = 0; count < size; count++) {
			MutableList<String> list = getCellList(size, count, CommonConstant.UPPER);
			if (warmEaten == CommonConstant.ZERO) {
				list.forEach(result::add);
			} else {
				list.shuffleThis().take(warmEaten).toSortedList().each(result::add);
			}
		}
		return result;
	}

	/**
	 * @param selectType
	 * @return
	 */
	public static MutableList<MutableList<String>> createCells(int selectType) {
		MutableList<MutableList<String>> resultList = Lists.mutable.empty();
		@Nullable Type type = Type.getType(selectType);
		if (type == null) {
			return resultList;
		}
		int size = type.getSize();
		for (int count = 0; count < size; count++) {
			MutableList<String> list = getCellList(size, count, CommonConstant.LOWER);
			resultList.add(list);
		}
		return resultList;
	}

	/**
	 * @param size
	 * 				リストのサイズ
	 * @param count
	 * @param convert
	 * 				アルファベットのアッパー/ロアー
	 * @return セルの連番のListを返却します
	 */
	public static MutableList<String> getCellList(int size, int count, String convert) {
		MutableList<String> list = Lists.mutable.empty();
		for (int i = 1; i < size + 1; i++) {
			int alphabet = CommonConstant.ALPHABET_START + count;
			String cell = String.valueOf((char) alphabet);
			switch (convert) {
				case CommonConstant.UPPER:
					cell = cell.toUpperCase();
					break;
				case CommonConstant.LOWER:
					cell = cell.toLowerCase();
					break;
				default:
					break;
			}
			list.add(cell.concat(Integer.toString(i)));
		}
		return list;
	}

	/**
	 * リストボックスに表示する数独の値を保持するListを返却します。
	 *
	 * @param selectType
	 * @return 連番のListを返却します
	 */
	public static List<Integer> getSelectNum(int selectType) {
		Type type = Type.getType(selectType);
		if (type == null) {
			return Collections.EMPTY_LIST;
		} else {
			switch (type) {
				case SIZE4:
					return getLinkedNum(type.getSize());
				case SIZE9:
					return getLinkedNum(type.getSize());
				default:
					return Collections.EMPTY_LIST;
			}
		}
	}

	/**
	 * 数独の値で使用する連番を保持するListを返却します。
	 *
	 * @param size
	 * @return 変更不能のListを返却します
	 */
	public static List<Integer> getLinkedNum(int size) {
		List<Integer> list = new LinkedList<>();
		for (int i = 1; i < size + 1; i++) {
			list.add(i);
		}
		return Collections.unmodifiableList(list);
	}

	/**
	 * ランダムなListを生成し、返却します。
	 *
	 * @param size
	 * @return 変更不能のListを返却します
	 */
	public static ImmutableIntList getRandList(int size) {
		Random rnd = new Random();
		MutableIntList shuffleList = IntLists.mutable.empty();
		for (int i = 1; i < size + 1; i++) {
			// ランダムなindexに要素を順に追加する
			shuffleList.addAtIndex(rnd.nextInt(i), i);
		}
		// 不要なindexを削ぎ落とし、Immutable化し返却する
		return shuffleList.distinct().toImmutable();
	}

}
