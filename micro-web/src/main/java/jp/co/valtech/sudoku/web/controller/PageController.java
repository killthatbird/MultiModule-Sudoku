package jp.co.valtech.sudoku.web.controller;

import java.util.Optional;
import jp.co.valtech.sudoku.core.bean.LogicHandleBean;
import jp.co.valtech.sudoku.core.config.enums.Tables;
import jp.co.valtech.sudoku.core.controller.BaseController;
import jp.co.valtech.sudoku.core.exception.SudokuApplicationException;
import jp.co.valtech.sudoku.core.service.AnswerInfoService;
import jp.co.valtech.sudoku.core.service.ScoreInfoService;
import jp.co.valtech.sudoku.web.config.enums.UrlConstants;
import jp.co.valtech.sudoku.web.config.enums.UrlConstants.Forward;
import jp.co.valtech.sudoku.web.form.CreateForm;
import jp.co.valtech.sudoku.web.form.DetailForm;
import jp.co.valtech.sudoku.web.form.PlayForm;
import jp.co.valtech.sudoku.web.form.ScoreForm;
import jp.co.valtech.sudoku.web.form.SearchForm;
import jp.co.valtech.sudoku.web.form.validator.CreateFormValidator;
import jp.co.valtech.sudoku.web.form.validator.DetailFormValidator;
import jp.co.valtech.sudoku.web.form.validator.PlayFormValidator;
import jp.co.valtech.sudoku.web.form.validator.ScoreFormValidator;
import jp.co.valtech.sudoku.web.form.validator.SearchFromValidator;
import jp.co.valtech.sudoku.web.logic.CreateLogic;
import jp.co.valtech.sudoku.web.logic.PlayLogic;
import jp.co.valtech.sudoku.web.logic.SearchLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Controller
@RequestMapping(value = {"/"})
public class PageController extends BaseController {

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
		 * @author uratamanabu
		 * @version 1.0
		 * @since 1.0
		 */
		@InitBinder
		public void initBinder(WebDataBinder binder) {

				Optional<Object> object = Optional.ofNullable(binder.getTarget());
				object.filter((notNullBinder) -> CreateForm.class.equals(notNullBinder.getClass()))
						.ifPresent(o -> binder.addValidators(new CreateFormValidator()));
				object.filter((notNullBinder) -> DetailForm.class.equals(notNullBinder.getClass()))
						.ifPresent(o -> binder.addValidators(new DetailFormValidator()));
				object.filter((notNullBinder) -> PlayForm.class.equals(notNullBinder.getClass()))
						.ifPresent(o -> binder.addValidators(new PlayFormValidator()));
				object.filter((notNullBinder) -> ScoreForm.class.equals(notNullBinder.getClass()))
						.ifPresent(o -> binder.addValidators(new ScoreFormValidator()));
				object.filter((notNullBinder) -> SearchForm.class.equals(notNullBinder.getClass()))
						.ifPresent(o -> binder.addValidators(new SearchFromValidator()));

		}

		/**
		 * @author uratamanabu
		 * @version 1.0
		 * @since 1.0
		 */
		@GetMapping(value = {UrlConstants.URL_TOP})
		public String okTop() {
				return Forward.TOP.getPath();
		}

		/**
		 * @author uratamanabu
		 * @version 1.0
		 * @since 1.0
		 */
		@PostMapping(value = {UrlConstants.URL_TOP})
		public String notTop() {
				return "error";
		}

		/**
		 * @author uratamanabu
		 * @version 1.0
		 * @since 1.0
		 */
		@GetMapping(value = {UrlConstants.URL_CHOICE_QUESTION})
		public String okChoice() {
				return Forward.CHOICE_QUESTION.getPath();
		}

		/**
		 * @author uratamanabu
		 * @version 1.0
		 * @since 1.0
		 */
		@PostMapping(value = {UrlConstants.URL_CHOICE_QUESTION})
		public String notChoice() {
				return "error";
		}


		/**
		 * /createAnswerの初期ページへ遷移します。
		 *
		 * @author uratamanabu
		 * @version 1.0
		 * @since 1.0
		 */
		@GetMapping(value = {UrlConstants.URL_CREATE_ANSWER})
		public String okCreateAnswer(
				CreateForm form,
				Model model) {

				LogicHandleBean handleBean = new LogicHandleBean()
						.setModel(model);
				new CreateLogic().createAnswer(handleBean);
				return Forward.CREATE_ANSWER.getPath();

		}

		/**
		 * @author uratamanabu
		 * @version 1.0
		 * @since 1.0
		 */
		@PostMapping(value = {UrlConstants.URL_CREATE_ANSWER})
		public String notCreateAnswer() {
				return "error";
		}

		/**
		 * @author uratamanabu
		 * @version 1.0
		 * @since 1.0
		 */
		@GetMapping(value = {UrlConstants.URL_COMPLETE_ANSWER})
		public String notCompleteAnswer() {
				return "error";
		}

		/**
		 * ANSWER_INFO_TBLとSCORE_INFO_TBLにレコードを追加し、作成完了ページへ遷移します。
		 * 一意制約等が発生しレコードが追加できなかった時は、エラーメッセージを作成完了ページへ表示します。
		 *
		 * @author uratamanabu
		 * @version 1.0
		 * @since 1.0
		 */
		@PostMapping(value = {UrlConstants.URL_COMPLETE_ANSWER})
		public String okCompleteAnswer(
				@Validated @ModelAttribute CreateForm form,
				BindingResult bindingResult,
				Model model)
				throws SudokuApplicationException {

				if (bindingResult.hasErrors()) {
						model.addAttribute("validationError", "不正な値が入力されました。");
						return okCreateAnswer(form, model);
				} else {
						LogicHandleBean handleBean = new LogicHandleBean()
								.setForm(form)
								.setModel(model)
								.setService(Tables.ANSWER_INFO_TBL, answerInfoService)
								.setService(Tables.SCORE_INFO_TBL, scoreInfoService);
						new CreateLogic().completeAnswer(handleBean);
						return Forward.COMPLETE_ANSWER.getPath();
				}
		}

		/**
		 * /createQuestionの初期ページへ遷移します。
		 *
		 * @author uratamanabu
		 * @version 1.0
		 * @since 1.0
		 */
		@GetMapping(value = {UrlConstants.URL_CREATE_QUESTION})
		public String okCreateQuestion(
				CreateForm form,
				Model model) {

				LogicHandleBean handleBean = new LogicHandleBean()
						.setModel(model);
				new PlayLogic().createQuestion(handleBean);
				return Forward.CREATE_QUESTION.getPath();

		}

		/**
		 * @author uratamanabu
		 * @version 1.0
		 * @since 1.0
		 */
		@PostMapping(value = {UrlConstants.URL_CREATE_QUESTION})
		public String notCreateQuestion() {
				return "error";
		}

		/**
		 * @author uratamanabu
		 * @version 1.0
		 * @since 1.0
		 */
		@GetMapping(value = {UrlConstants.URL_PLAY_NUMBER_PLACE})
		public String notPlayNumberPlace() {
				return "error";
		}

		/**
		 * /playNumberPlaceの初期ページへ遷移します。
		 */
		@PostMapping(value = {UrlConstants.URL_PLAY_NUMBER_PLACE})
		public String okPlayNumberPlace(
				@Validated @ModelAttribute CreateForm form,
				BindingResult bindingResult,
				Model model)
				throws SudokuApplicationException {

				if (bindingResult.hasErrors()) {
						model.addAttribute("validationError", "不正な値が入力されました。");
						return okCreateQuestion(form, model);
				} else {
						LogicHandleBean handleBean = new LogicHandleBean()
								.setForm(form)
								.setModel(model)
								.setService(Tables.ANSWER_INFO_TBL, answerInfoService);
						new PlayLogic().playNumberPlace(handleBean);
						return Forward.PLAY_NUMBER_PLACE.getPath();
				}
		}

		/**
		 * @author uratamanabu
		 * @version 1.0
		 * @since 1.0
		 */
		@GetMapping(value = {"isCheck"})
		public String notIsCheck() {
				return "error";
		}

		/**
		 * /isCheckの初期ページへ遷移します。
		 */
		@PostMapping(value = {"isCheck"})
		public String okIsCheck(
				@Validated @ModelAttribute PlayForm form,
				BindingResult bindingResult,
				Model model)
				throws SudokuApplicationException {

				if (bindingResult.hasErrors()) {
						model.addAttribute("validationError", "不正な値が入力されました。");
						return Forward.PLAY_NUMBER_PLACE.getPath();
				} else {
						LogicHandleBean handleBean = new LogicHandleBean()
								.setForm(form)
								.setModel(model)
								.setService(Tables.ANSWER_INFO_TBL, answerInfoService)
								.setService(Tables.SCORE_INFO_TBL, scoreInfoService);
						switch (new PlayLogic().isCheck(handleBean)) {
								case 1:
										return Forward.BEST_SCORE.getPath();
								case 2:
										return Forward.COMPLETE_NUMBER_PLACE.getPath();
								case 3:
										return Forward.PLAY_NUMBER_PLACE.getPath();
								default:
										return "";
						}
				}
		}

		/**
		 * /bestScoreの初期ページへ遷移します。
		 */
		@PostMapping(value = {UrlConstants.URL_BEST_SCORE})
		public String bestScore(
				@Validated @ModelAttribute ScoreForm form,
				BindingResult bindingResult,
				Model model) {

				if (bindingResult.hasErrors()) {
						model.addAttribute("validationError", "不正な値が入力されました。");
						return Forward.BEST_SCORE.getPath();
				} else {
						LogicHandleBean handleBean = new LogicHandleBean()
								.setForm(form)
								.setModel(model)
								.setService(Tables.SCORE_INFO_TBL, scoreInfoService);
						new PlayLogic().bestScore(handleBean);
						return Forward.BEST_SCORE_COMPLETE.getPath();
				}
		}

		/**
		 * /completeNumberPlaceの初期ページへ遷移します。
		 */
		@PostMapping(value = {UrlConstants.URL_BEST_SCORE_COMPLETE})
		public String bestScoreComplete(
				@Validated @ModelAttribute ScoreForm form,
				BindingResult bindingResult,
				Model model) {

				return Forward.BEST_SCORE_COMPLETE.getPath();
		}

		/**
		 * /completeNumberPlaceの初期ページへ遷移します。
		 */
		@PostMapping(value = {UrlConstants.URL_COMPLETE_NUMBER_PLACE})
		public String completeNumberPlace(
				@Validated @ModelAttribute ScoreForm form,
				BindingResult bindingResult,
				Model model) {
				return Forward.COMPLETE_NUMBER_PLACE.getPath();
		}

		/**
		 * /searchAnswerの初期ページへ遷移します。
		 */
		@GetMapping(value = {UrlConstants.URL_SEARCH_ANSWER})
		public String searchAnswer(
				SearchForm form,
				Model model) {
				LogicHandleBean handleBean = new LogicHandleBean()
						.setModel(model);
				new SearchLogic().searchAnswer(handleBean);
				return Forward.SEARCH_ANSWER.getPath();
		}

		/**
		 * /isSearchの初期ページへ遷移します。
		 */
		@PostMapping(value = {"isSearch"})
		public String isSearch(
				@Validated @ModelAttribute SearchForm form,
				BindingResult bindingResult,
				Model model) {

				if (bindingResult.hasErrors()) {
						model.addAttribute("validationError", "不正な値が入力されました。");
				} else {
						LogicHandleBean handleBean = new LogicHandleBean()
								.setForm(form)
								.setModel(model)
								.setService(Tables.ANSWER_INFO_TBL, answerInfoService);
						new SearchLogic().isSearch(handleBean);
				}
				return searchAnswer(form, model);
		}

		/**
		 * /detailの初期ページへ遷移します。
		 */
		@GetMapping(value = {UrlConstants.URL_DETAIL}, params = {"type", "keyHash"})
		public String detail(
				@RequestParam("type") final int type,
				@RequestParam("keyHash") final String keyHash,
				Model model) {

				if (keyHash.length() == 64) {
						DetailForm form = new DetailForm();
						form.setType(type);
						form.setKeyHash(keyHash);
						LogicHandleBean handleBean = new LogicHandleBean()
								.setForm(form)
								.setModel(model);
						new SearchLogic().detail(handleBean);
						return Forward.DETAIL.getPath();
				} else {
						return "error";
				}
		}

		/**
		 * /playNumberPlaceDetailの初期ページへ遷移します。
		 */
		@PostMapping(value = {"playNumberPlaceDetail"})
		public String playNumberPlaceDetail(
				@Validated @ModelAttribute DetailForm form,
				Model model)
				throws SudokuApplicationException {

				LogicHandleBean handleBean = new LogicHandleBean()
						.setForm(form)
						.setModel(model)
						.setService(Tables.ANSWER_INFO_TBL, answerInfoService);
				new SearchLogic().playNumberPlaceDetail(handleBean);
				return Forward.PLAY_NUMBER_PLACE.getPath();

		}

		/**
		 * /introduceの初期ページへ遷移します。
		 */
		@GetMapping(value = {UrlConstants.URL_INTRODUCE})
		public String introduce() {
				return Forward.INTRODUCE.getPath();
		}

		/**
		 * /linkListの初期ページへ遷移します。
		 */
		@GetMapping(value = {UrlConstants.URL_LINK_LIST})
		public String linkList() {
				return Forward.LINK_LIST.getPath();
		}
}
