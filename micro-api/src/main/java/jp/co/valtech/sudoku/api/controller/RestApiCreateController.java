package jp.co.valtech.sudoku.api.controller;

import jp.co.valtech.sudoku.api.service.CreateService;
import jp.co.valtech.sudoku.core.bean.NumberPlaceBean;
import jp.co.valtech.sudoku.core.bean.request.ResisterSudokuRecordRequestBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by uratamanabu on 2017/07/04.
 */
@Slf4j
@RestController
@RequestMapping(value = {"/"})
public class RestApiCreateController {

		@Autowired
		CreateService service;

		/**
		 * タイプに従い数独を作成します。
		 *
		 * @author uratamanabu
		 * @version 1.0
		 * @since 1.0
		 */
		@GetMapping(value = "/createMaster/generate/{type}")
		public ResponseEntity<NumberPlaceBean> generateSudoku(@PathVariable int type) {
				log.info("generateSudoku");
				return service.generate(type);
		}

		/**
		 * 数独をRDBに保存します。
		 *
		 * @author uratamanabu
		 * @version 1.0
		 * @since 1.0
		 */
		@PostMapping(value = "/createMaster/generate")
		public ResponseEntity<String> resisterSudoku(
				@RequestBody ResisterSudokuRecordRequestBean request) {
				log.info("resisterSudoku");
				return service.insertAnswerAndScore(request.getNumberPlaceBean());
		}

}
