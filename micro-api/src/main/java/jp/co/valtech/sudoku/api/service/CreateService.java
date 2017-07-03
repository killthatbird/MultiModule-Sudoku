package jp.co.valtech.sudoku.api.service;

import java.util.Optional;
import jp.co.valtech.sudoku.core.bean.NumberPlaceBean;
import jp.co.valtech.sudoku.core.domain.AnswerInfoTbl;
import jp.co.valtech.sudoku.core.exception.SudokuApplicationException;
import jp.co.valtech.sudoku.core.service.AnswerInfoService;
import jp.co.valtech.sudoku.core.service.ScoreInfoService;
import jp.co.valtech.sudoku.core.sudoku.Sudoku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by uratamanabu on 2017/07/03.
 */
@Service
public class CreateService {

		/**
		 * AnswerInfoServiceを配備します。
		 */
		@Autowired
		AnswerInfoService answerInfoService;

		/**
		 * ScoreInfoServiceを配備します。
		 */
		@Autowired
		ScoreInfoService scoreInfoService;

		/**
		 * typeから数独を生成します。
		 *
		 * @author uratamanabu
		 * @version 1.0
		 * @since 1.0
		 */
		public ResponseEntity<NumberPlaceBean> generate(int type)
				throws SudokuApplicationException {
				Optional<NumberPlaceBean> numberPlaceBeanOpt = Optional
						.ofNullable(new Sudoku(type).generate());
				if (numberPlaceBeanOpt.isPresent()) {
						return new ResponseEntity<>(numberPlaceBeanOpt.get(),
								HttpStatus.CREATED);
				} else {
						return new ResponseEntity<>(new NumberPlaceBean(),
								HttpStatus.NO_CONTENT);
				}
		}

		/**
		 * 数独をRDBに保存します。
		 *
		 * @param numberPlaceBean
		 *
		 * @author uratamanabu
		 * @version 1.0
		 * @since 1.0
		 */
		@Transactional
		public ResponseEntity<String> insertAnswerAndScore(NumberPlaceBean numberPlaceBean) {
				try {
						AnswerInfoTbl answerInfoTbl = answerInfoService.insert(numberPlaceBean);
						scoreInfoService.insert(numberPlaceBean);
						return new ResponseEntity<>(answerInfoTbl.getKeyHash(), HttpStatus.OK);
				} catch (Exception e) {
						return new ResponseEntity<>("", HttpStatus.CONFLICT);
				}
		}
}
