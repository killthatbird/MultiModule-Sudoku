package jp.co.valtech.sudoku.web.utils;

import jp.co.valtech.sudoku.core.bean.NumberPlaceBean;
import jp.co.valtech.sudoku.core.config.CommonConstant;
import jp.co.valtech.sudoku.core.exception.SudokuApplicationException;
import jp.co.valtech.sudoku.core.utils.ESListWrapUtil;
import jp.co.valtech.sudoku.web.form.PlayForm;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ListIterator;

import static lombok.AccessLevel.PRIVATE;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class CompareUtil {

	/**
	 * @param form
	 * @param bean
	 * @throws
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	public static void playFormCompareAnswer(PlayForm form, NumberPlaceBean bean) throws SudokuApplicationException {
		if (bean.getType() != form.getType()) {
			throw new SudokuApplicationException();
		} else if (!bean.getKeyHash().equals(form.getKeyHash())) {
			throw new SudokuApplicationException();
		}
		form.setCompareFlg(true);
		form.addCount();
		ListIterator<String> itr = ESListWrapUtil.createCells(bean.getType(), 0).listIterator();
		try {
			while (itr.hasNext()) {
				String property = itr.next();
				PropertyDescriptor formProperties = new PropertyDescriptor(property, form.getClass());
				PropertyDescriptor beanProperties = new PropertyDescriptor(property, bean.getClass());
				Method formGetter = formProperties.getReadMethod();
				Method beanGetter = beanProperties.getReadMethod();
				int formValue = (int) formGetter.invoke(form, (Object[]) null);
				int beanValue = (int) beanGetter.invoke(bean, (Object[]) null);
				if (formValue != beanValue) {
					Method formSetter = formProperties.getWriteMethod();
					formSetter.invoke(form, CommonConstant.ZERO);
					form.subtractionScore(50);
					form.setCompareFlg(false);
				}
			}
		} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			log.error("");
			throw new SudokuApplicationException();
		}
	}
}
