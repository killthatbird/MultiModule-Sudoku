package jp.co.valtech.sudoku.core.sudoku;

import jp.co.valtech.sudoku.core.bean.NumberPlaceBean;
import jp.co.valtech.sudoku.core.config.CommonConstant;
import jp.co.valtech.sudoku.core.exception.SudokuApplicationException;
import jp.co.valtech.sudoku.core.sudoku.interfaces.SudokuFilter;
import jp.co.valtech.sudoku.core.sudoku.interfaces.SudokuGenerator;
import jp.co.valtech.sudoku.core.sudoku.interfaces.SudokuUtil;
import jp.co.valtech.sudoku.core.utils.ESListWrapUtil;
import jp.co.valtech.sudoku.core.utils.SudokuUtils;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ListIterator;

import static lombok.AccessLevel.PRIVATE;

/**
 * 数独を生成するクラスです。
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@NoArgsConstructor(access = PRIVATE)
@Slf4j
public class Sudoku implements SudokuFilter, SudokuGenerator, SudokuUtil, AutoCloseable {

	private int size;

	private int[][] numberPlaceArray;

	private NumberPlaceBean numberPlaceBean;

	/**
	 * コンストラクタです。
	 *
	 * @param type
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	public Sudoku(int type) {
		this.size = type;
		this.numberPlaceArray = new int[this.size][this.size];
		this.numberPlaceBean = new NumberPlaceBean();
	}

	/**
	 * 終了時に呼び出されるクローズメソッドです。
	 *
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	@Override
	public void close() {
		destroy();
	}

	private void destroy() {
		this.size = 0;
		this.numberPlaceArray = new int[0][0];
		this.numberPlaceBean = new NumberPlaceBean();
	}

	/**
	 * 数独を0から生成し返却します。
	 *
	 * @return
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	public NumberPlaceBean generate() {
		createAnswerArray();
		arrayConvertBean();
		return this.numberPlaceBean;
	}

	/**
	 * 数独をマスキングして返却します。
	 *
	 * @param bean
	 * @param level
	 * @return
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	public NumberPlaceBean filteredOfLevel(NumberPlaceBean bean, String level) {
		this.numberPlaceBean = bean;
		filteredOfLevel(level);
		return this.numberPlaceBean;
	}

	private void createAnswerArray() {
		for (int y = 0; y < this.size; y++) {
			// 1行毎配列の要素を組み立てる
			// 行によって処理を分岐する
			if (y == 0) {
				// １行目
				createFirstRow(this.numberPlaceArray, this.size);
			} else if (y > 0 && y < this.size - 1) {
				// ２行目〜
				createMiddleRows(this.numberPlaceArray, y, this.size);
			} else if (y == size - 1) {
				// 最後
				createLastRow(this.numberPlaceArray, this.size);
			}
		}
	}

	private void arrayConvertBean() {
		String answerKey = createAnswerKey(this.numberPlaceArray);
		String keyHash = convertToSha256(answerKey);
		if (keyHash == null) {
			this.numberPlaceBean = new NumberPlaceBean();
		} else {
			this.numberPlaceBean.setType(this.size);
			this.numberPlaceBean.setNo(CommonConstant.ZERO);
			this.numberPlaceBean.setAnswerKey(answerKey);
			this.numberPlaceBean.setKeyHash(keyHash);
			setCells();
		}
	}

	private void setCells() {

		ListIterator<String> itr = ESListWrapUtil.createCells(this.size, 0).listIterator();
		int x = 0;
		int y = 0;

		try {
			while (itr.hasNext()) {
				SudokuUtils.setCell(this.numberPlaceBean, itr.next(), this.numberPlaceArray[y][x++]);
				if (x == this.size) {
					x = 0;
					y++;
				}
			}
		} catch (SudokuApplicationException e) {
			e.printStackTrace();
			log.error("やらかしています。");
			this.numberPlaceBean = new NumberPlaceBean();
		}
	}

	private void filteredOfLevel(String level) {
		int type = this.size;
		ListIterator<String> itr = ESListWrapUtil.createCells(size, getWarmEatenValue(level, size)).listIterator();
		try {
			while (itr.hasNext()) {
				filteredCell(this.numberPlaceBean, itr.next());
			}
		} catch (SudokuApplicationException e) {
			e.printStackTrace();
			log.error("やらかしています。");
			this.numberPlaceBean = new NumberPlaceBean();
		}

	}
}
