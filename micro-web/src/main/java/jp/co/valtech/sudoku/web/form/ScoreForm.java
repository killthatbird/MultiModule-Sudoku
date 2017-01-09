package jp.co.valtech.sudoku.web.form;

import jp.co.valtech.sudoku.core.form.BaseForm;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ScoreForm extends BaseForm implements Serializable {

	private static final long serialVersionUID = -8386170808653616445L;

	@Digits(integer = 7, fraction = 0)
	private int score;

	@NotNull
	private int count;

	@NotBlank
	@Size(max = 64)
	private String name;

	@Size(max = 64)
	private String memo;

	@NotBlank
	@Size(max = 64)
	private String keyHash;

	@NotNull
	@Digits(integer = 1, fraction = 0)
	private int type;
}
