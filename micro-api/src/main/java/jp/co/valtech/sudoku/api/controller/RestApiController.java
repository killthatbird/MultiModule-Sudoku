package jp.co.valtech.sudoku.api.controller;

import java.net.URI;
import jp.co.valtech.sudoku.api.service.CreateService;
import jp.co.valtech.sudoku.core.bean.NumberPlaceBean;
import jp.co.valtech.sudoku.core.bean.request.ResisterSudokuRecordRequestBean;
import jp.co.valtech.sudoku.core.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = {"/"})
public class RestApiController extends BaseController {

		@Autowired
		CreateService service;

		@Autowired
		RestTemplate restTemplate;

		/**
		 * Web側から呼び出されます。
		 *
		 * @author uratamanabu
		 * @version 1.0
		 * @since 1.0
		 */
		@GetMapping(value = "/createMaster/{type}")
		public ResponseEntity<String> createFromWeb(
				@PathVariable int type,
				UriComponentsBuilder uriComponentsBuilder) {

				log.info("Creating From Web");
				URI uri;
				RequestEntity requestEntity;

				// generate
				// generateSudoku
				uri = uriComponentsBuilder.cloneBuilder().path("/createMaster/generate/{type}")
						.buildAndExpand(type)
						.toUri();
				requestEntity = RequestEntity.get(uri).build();

				System.out.println(uri.toString());
				ResponseEntity<NumberPlaceBean> generateEntity = restTemplate
						.exchange(requestEntity, NumberPlaceBean.class);

				NumberPlaceBean numberPlaceBean = generateEntity.getBody();
				String answerKey = numberPlaceBean.getAnswerKey();
				System.out.println(answerKey);

				// answerKey
				// isSudokuExist
				uri = uriComponentsBuilder.cloneBuilder().path("/searchMaster/{answerKey}")
						.buildAndExpand(answerKey).toUri();
				System.out.println(uri.toString());
				requestEntity = RequestEntity.get(uri).build();
				ResponseEntity<Boolean> isSudokuExistEntity = restTemplate
						.exchange(requestEntity, Boolean.class);

				if (isSudokuExistEntity.getBody().booleanValue()) {
						log.error("");
						return new ResponseEntity<>("", HttpStatus.CONFLICT);
				} else {
						// answerKey
						// resisterSudoku
						ResisterSudokuRecordRequestBean request = new ResisterSudokuRecordRequestBean();
						request.setNumberPlaceBean(numberPlaceBean);
						uri = uriComponentsBuilder.cloneBuilder().path("/createMaster/generate")
								.build().toUri();
						requestEntity = RequestEntity.post(uri).body(request);
						System.out.println(uri.toString());
						return restTemplate.exchange(requestEntity, String.class);
				}

		}

		/**
		 * タイプに従い数独を作成します。
		 *
		 * @author uratamanabu
		 * @version 1.0
		 * @since 1.0
		 */
		@GetMapping(value = "/createMaster/generate/{type}")
		public ResponseEntity<NumberPlaceBean> generateSudoku(@PathVariable int type) {
				return service.generate(type);
		}

		/**
		 * 数独の存在確認をします。
		 *
		 * @author uratamanabu
		 * @version 1.0
		 * @since 1.0
		 */
		@GetMapping(value = "/searchMaster/{answerKey}")
		public ResponseEntity<Boolean> isSudokuExist(@PathVariable String answerKey) {
				return service.isExist(answerKey);
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
				System.out.println("a");
				System.out.println(request.toString());
				return service.insertAnswerAndScore(request.getNumberPlaceBean());
		}

}
