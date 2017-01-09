package jp.co.valtech.sudoku.web.form.validator;

import jp.co.valtech.sudoku.core.form.validator.BaseFormValidator;
import jp.co.valtech.sudoku.web.form.ScoreForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Component
public class ScoreFormValidator extends BaseFormValidator {

	/**
	 * @param paramClass
	 * @return
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	@Override
	public boolean supports(Class<?> paramClass) {
		return ScoreForm.class.isAssignableFrom(paramClass);
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
		ScoreForm form = (ScoreForm) paramObject;
		int score = form.getScore();
		String keyHash = form.getKeyHash();
		String name = form.getName();
	}
}
