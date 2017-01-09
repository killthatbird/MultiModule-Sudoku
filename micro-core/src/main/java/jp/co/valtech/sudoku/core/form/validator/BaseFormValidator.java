package jp.co.valtech.sudoku.core.form.validator;

import jp.co.valtech.sudoku.core.form.BaseForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * フォームバリデーションクラスのベースクラスです。
 * このクラスを継承して実装してください。
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
public abstract class BaseFormValidator implements Validator {

	/**
	 * @param paramClass
	 * @return
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	@Override
	public boolean supports(Class<?> paramClass) {
		return BaseForm.class.isAssignableFrom(paramClass);
	}

	/**
	 * @param paramObject
	 * @param errors
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	@Override
	public void validate(Object paramObject, Errors errors) {
	}
}
