package jp.co.valtech.sudoku.core.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * SCORE_INFO_TBLのEntityクラスです。
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Entity
@Data
@ToString(exclude = {"answerInfoTbl"})
@Table(name = "SCORE_INFO_TBL", catalog = "SUDOKU")
public class ScoreInfoTbl implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -5665962434247119049L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "NO", unique = true, length = 10, nullable = false, columnDefinition = "BIGINT")
	private long no;
	@Column(name = "SCORE", length = 7, nullable = false, columnDefinition = "MEDIUMINT")
	private int score;
	@Column(name = "NAME", length = 64, nullable = false, columnDefinition = "VARCHAR")
	private String name;
	@Column(name = "MEMO", length = 64, nullable = false, columnDefinition = "VARCHAR")
	private String memo;

	@Column(name = "UPDATE_DATE", nullable = true, columnDefinition = "DATETIME")
	private LocalDateTime updateDate;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = AnswerInfoTbl.class)
	@JoinColumn(name = "NO", unique = true, nullable = false, table = "ANSWER_INFO_TBL")
	private AnswerInfoTbl answerInfoTbl;
}
