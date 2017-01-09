package jp.co.valtech.sudoku.web.form.validator;

import jp.co.valtech.sudoku.core.form.validator.BaseFormValidator;
import jp.co.valtech.sudoku.web.form.PlayForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Component
public class PlayFormValidator extends BaseFormValidator {

	/**
	 * @param paramClass
	 * @return
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	@Override
	public boolean supports(Class<?> paramClass) {
		return PlayForm.class.isAssignableFrom(paramClass);
	}

	/**
	 * @param errors
	 * @param paramObject
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	@Override
	public void validate(Object paramObject, Errors errors) {
		if (errors.hasErrors()) {
			return;
		}
		PlayForm form = (PlayForm) paramObject;

	}
}
