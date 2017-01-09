package jp.co.valtech.sudoku.core.utils;

import jp.co.valtech.sudoku.core.bean.NumberPlaceBean;
import jp.co.valtech.sudoku.core.config.enums.Difficulty;
import jp.co.valtech.sudoku.core.config.enums.Type;
import jp.co.valtech.sudoku.core.domain.AnswerInfoTbl;
import jp.co.valtech.sudoku.core.exception.SudokuApplicationException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.modelmapper.ModelMapper;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ListIterator;

import static lombok.AccessLevel.PRIVATE;

/**
 * 数独に使用するユーティル群をまとめたクラスです。
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor(access = PRIVATE)
@Slf4j
public class SudokuUtils {

	/**
	 * @param num
	 * @return 引数で与えられた整数の平方根を返します。
	 */
	public static int convertSquareRoot(int num) {
		return (int) Math.sqrt(num);
	}

	/**
	 * @param selectType
	 * @param selectLevel
	 * @return
	 * @throws SudokuApplicationException
	 */
	public static int calculationScore(int selectType, String selectLevel) throws SudokuApplicationException {
		String key = selectLevel.toUpperCase().concat(Integer.toString(selectType));
		Difficulty difficulty = Difficulty.getDifficulty(key);
		switch (difficulty) {
			case EASY4:
				return 2000;
			case EASY9:
				return 2000;
			case NORMAL4:
				return 5000;
			case NORMAL9:
				return 5000;
			case HARD4:
				return 10000;
			case HARD9:
				return 15000;
			default:
				throw new SudokuApplicationException();
		}
	}

	/**
	 * @param answerInfoTbl
	 * @return
	 */
	@Nullable
	public static NumberPlaceBean answerInfoTblConvertBean(AnswerInfoTbl answerInfoTbl) {
		NumberPlaceBean numberPlaceBean = new ModelMapper().map(answerInfoTbl, NumberPlaceBean.class);
		String answerKey = numberPlaceBean.getAnswerKey();
		String[] valueArray = answerKey.split("");
		Type type = Type.getType(numberPlaceBean.getType());
		ListIterator<String> itr = ESListWrapUtil.createCells(type.getSize(), 0).listIterator();
		try {
			for (String value : valueArray) {
				setCell(numberPlaceBean, itr.next(), Integer.parseInt(value));
			}
			return numberPlaceBean;
		} catch (SudokuApplicationException e) {
			e.printStackTrace();
			log.error("やらかしています。");
			return null;
		}
	}

	/**
	 * @param numberPlaceBean
	 * @param key
	 * @param value
	 */
	public static void setCell(NumberPlaceBean numberPlaceBean, String key, int value) throws SudokuApplicationException {
		try {
			PropertyDescriptor properties = new PropertyDescriptor(key, numberPlaceBean.getClass());
			Method setter = properties.getWriteMethod();
			setter.invoke(numberPlaceBean, value);
		} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
			e.printStackTrace();
			throw new SudokuApplicationException();
		}
	}
}
