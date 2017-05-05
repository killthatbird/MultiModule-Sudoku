package jp.co.valtech.sudoku.web.form;

import java.io.Serializable;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import jp.co.valtech.sudoku.core.form.BaseForm;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DetailForm extends BaseForm implements Serializable {

		private static final long serialVersionUID = -2616500675152025057L;

		@Digits(integer = 1, fraction = 0, message = "選択肢から選択してください。")
		private int type;

		@NotBlank
		@Pattern(regexp = "easy|normal|hard", message = "選択肢から選択してください。")
		private String selectLevel;

		@NotBlank
		@Size(max = 64, message = "keyHashは64文字までです。")
		private String keyHash;
}
