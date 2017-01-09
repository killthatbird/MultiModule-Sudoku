package jp.co.valtech.sudoku.core.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * 数独の検索結果を保持するBeanです。
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class RecordBean {
	private long no;
	private int type;
	@Size(max = 64)
	private String keyHash;
	private String name;
	private int score;
	private String memo;
	private LocalDate updateDate;
}
