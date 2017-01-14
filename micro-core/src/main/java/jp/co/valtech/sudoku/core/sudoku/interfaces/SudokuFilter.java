package jp.co.valtech.sudoku.core.sudoku.interfaces;

import jp.co.valtech.sudoku.core.bean.NumberPlaceBean;
import jp.co.valtech.sudoku.core.config.CommonConstant;
import jp.co.valtech.sudoku.core.config.enums.Difficulty;
import jp.co.valtech.sudoku.core.exception.SudokuApplicationException;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
public interface SudokuFilter {

	/**
	 * @param level
	 * @param size
	 * @return
	 */
	default int getWarmEatenValue(@NonNull String level, int size) {
		if (level == null) {
			return 0;
		}
		String key = level.toUpperCase().concat(Integer.toString(size));
		Difficulty difficulty = Difficulty.getDifficulty(key);
		if (difficulty == null) {
			return 0;
		} else {
			return difficulty.getValue();
		}

	}

	/**
	 * @param numberPlaceBean
	 * @param key
	 * @throws
	 */
	default void filteredCell(@NonNull NumberPlaceBean numberPlaceBean, String key) throws SudokuApplicationException {
		try {
			PropertyDescriptor properties = new PropertyDescriptor(key, numberPlaceBean.getClass());
			Method setter = properties.getWriteMethod();
			if (setter != null) {
				setter.invoke(numberPlaceBean, CommonConstant.ZERO);
			}
		} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
			e.printStackTrace();
			throw new SudokuApplicationException();
		}
	}

}
