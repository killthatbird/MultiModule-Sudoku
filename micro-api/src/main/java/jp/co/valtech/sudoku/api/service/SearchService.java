package jp.co.valtech.sudoku.api.service;

import java.util.List;
import jp.co.valtech.sudoku.core.domain.AnswerInfoTbl;
import jp.co.valtech.sudoku.core.service.AnswerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Created by uratamanabu on 2017/07/04.
 */
@Service
public class SearchService {

		/**
		 * AnswerInfoServiceを配備します。
		 */
		@Autowired
		AnswerInfoService answerInfoService;

		/**
		 * answerKeyで数独の存在確認をします。
		 *
		 * @author uratamanabu
		 * @version 1.0
		 * @since 1.0
		 */
		public ResponseEntity<Boolean> isExist(String answerKey) {
				List<AnswerInfoTbl> list = answerInfoService.findByAnswerKey(answerKey);
				if (list.isEmpty()) {
						return new ResponseEntity<>(false, HttpStatus.OK);
				} else {
						return new ResponseEntity<>(true, HttpStatus.OK);
				}
		}

}
