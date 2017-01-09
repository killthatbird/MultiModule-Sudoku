package jp.co.valtech.sudoku.core.sudoku.interfaces;

import jp.co.valtech.sudoku.core.bean.NumberPlaceBean;
import jp.co.valtech.sudoku.core.config.CommonConstant;
import jp.co.valtech.sudoku.core.config.enums.Difficulty;
import jp.co.valtech.sudoku.core.exception.SudokuApplicationException;

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
	default int getWarmEatenValue(String level, int size) {
		String key = level.toUpperCase().concat(Integer.toString(size));
		return Difficulty.getDifficulty(key).getValue();
	}

	/**
	 * @param numberPlaceBean
	 * @param key
	 * @throws
	 */
	default void filteredCell(NumberPlaceBean numberPlaceBean, String key) throws SudokuApplicationException {
		try {
			PropertyDescriptor properties = new PropertyDescriptor(key, numberPlaceBean.getClass());
			Method setter = properties.getWriteMethod();
			setter.invoke(numberPlaceBean, CommonConstant.ZERO);
		} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
			e.printStackTrace();
			throw new SudokuApplicationException();
		}
	}

}
