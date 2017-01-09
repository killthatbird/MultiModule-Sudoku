package jp.co.valtech.sudoku.web.form;

import jp.co.valtech.sudoku.core.form.BaseForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SearchForm extends BaseForm implements Serializable {

	private static final long serialVersionUID = 8711960650043503815L;

	@Digits(integer = 1, fraction = 0)
	private int selectType;

	@Digits(integer = 10, fraction = 0, message = "数値10桁を入力しましょう。")
	private long no;

	@Size(max = 64, message = "数値64桁を入力しましょう。")
	private String keyHash;

	@Digits(integer = 7, fraction = 0, message = "数値7桁を入力しましょう。")
	private int score;

	@Size(max = 64, message = "64文字までの入力です。")
	private String name;

	private LocalDate dateStart;
	private LocalDate dateEnd;

	@Digits(integer = 1, fraction = 0, message = "改竄値を入力しないでください。")
	private int selectorNo;

	@Digits(integer = 1, fraction = 0, message = "改竄値を入力しないでください。")
	private int selectorKeyHash;

	@Digits(integer = 1, fraction = 0, message = "改竄値を入力しないでください。")
	private int selectorScore;

	@Digits(integer = 1, fraction = 0, message = "改竄値を入力しないでください。")
	private int selectorName;

	@Digits(integer = 7, fraction = 0)
	private int pageNumber;// ページ番号を取得

	@Digits(integer = 7, fraction = 0)
	private int pageSize;

}
