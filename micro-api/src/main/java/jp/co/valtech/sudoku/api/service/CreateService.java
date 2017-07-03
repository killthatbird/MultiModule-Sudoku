package jp.co.valtech.sudoku.api.service;

import java.util.List;
import java.util.Optional;
import jp.co.valtech.sudoku.core.bean.NumberPlaceBean;
import jp.co.valtech.sudoku.core.domain.AnswerInfoTbl;
import jp.co.valtech.sudoku.core.domain.ScoreInfoTbl;
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

		public ResponseEntity<NumberPlaceBean> generate(int selectType)
				throws SudokuApplicationException {
				Optional<NumberPlaceBean> numberPlaceBeanOpt = Optional
						.ofNullable(new Sudoku(selectType).generate());
				if (numberPlaceBeanOpt.isPresent()) {
						return new ResponseEntity<NumberPlaceBean>(numberPlaceBeanOpt.get(),
								HttpStatus.CREATED);
				} else {
						return new ResponseEntity<NumberPlaceBean>(new NumberPlaceBean(),
								HttpStatus.NO_CONTENT);
				}
		}

		public ResponseEntity<Boolean> isExist(String answerKey) {
				List<AnswerInfoTbl> list = answerInfoService.findByAnswerKey(answerKey);
				if (list.isEmpty()) {
						return new ResponseEntity<>(false, HttpStatus.OK);
				} else {
						return new ResponseEntity<>(true, HttpStatus.OK);
				}
		}

		@Transactional
		public ResponseEntity<String> insertAnswerAndScore(NumberPlaceBean numberPlaceBean) {
				try {
						AnswerInfoTbl answerInfoTbl = answerInfoService.insert(numberPlaceBean);
						Optional<ScoreInfoTbl> scoreInfoTblOpt = Optional
								.ofNullable(scoreInfoService.insert(numberPlaceBean));
						if (scoreInfoTblOpt.isPresent()) {
								return new ResponseEntity<>(answerInfoTbl.getKeyHash(), HttpStatus.OK);
						} else {
								return new ResponseEntity<>("", HttpStatus.OK);
						}
				} catch (Exception e) {
						return new ResponseEntity<>("", HttpStatus.OK);
				}
		}
}
