package jp.co.valtech.sudoku.api.controller;

import jp.co.valtech.sudoku.api.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by uratamanabu on 2017/07/04.
 */
@Slf4j
@RestController
@RequestMapping(value = {"/"})
public class RestApiSearchController {

		@Autowired
		SearchService service;

		/**
		 * 数独の存在確認をします。
		 *
		 * @author uratamanabu
		 * @version 1.0
		 * @since 1.0
		 */
		@GetMapping(value = "/searchMaster/{answerKey}")
		public ResponseEntity<Boolean> isSudokuExist(@PathVariable String answerKey) {
				log.info("isSudokuExist");
				return service.isExist(answerKey);
		}

}
