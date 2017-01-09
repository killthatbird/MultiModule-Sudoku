package jp.co.valtech.sudoku.web.form.validator;

import jp.co.valtech.sudoku.core.form.validator.BaseFormValidator;
import jp.co.valtech.sudoku.web.form.SearchForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Component
public class SearchFromValidator extends BaseFormValidator {

	/**
	 * @param paramClass
	 * @return
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	@Override
	public boolean supports(Class<?> paramClass) {
		return SearchForm.class.isAssignableFrom(paramClass);
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
		SearchForm form = (SearchForm) paramObject;
		Long no = form.getNo();
		int selectType = form.getSelectType();
		int score = form.getScore();
		String keyHash = form.getKeyHash();
		String name = form.getName();
	}
}
