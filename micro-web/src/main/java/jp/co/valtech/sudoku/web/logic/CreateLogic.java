package jp.co.valtech.sudoku.web.logic;

import jp.co.valtech.sudoku.core.bean.LogicHandleBean;
import jp.co.valtech.sudoku.core.bean.NumberPlaceBean;
import jp.co.valtech.sudoku.core.config.enums.Tables;
import jp.co.valtech.sudoku.core.domain.AnswerInfoTbl;
import jp.co.valtech.sudoku.core.domain.ScoreInfoTbl;
import jp.co.valtech.sudoku.core.exception.SudokuApplicationException;
import jp.co.valtech.sudoku.core.service.AnswerInfoService;
import jp.co.valtech.sudoku.core.service.ScoreInfoService;
import jp.co.valtech.sudoku.core.sudoku.Sudoku;
import jp.co.valtech.sudoku.core.utils.ESMapWrapUtil;
import jp.co.valtech.sudoku.web.form.CreateForm;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class CreateLogic {

	/**
	 * @param handleBean
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	public void createAnswer(LogicHandleBean handleBean) {
		Model model = handleBean.getModel();
		model.addAttribute("selectTypes", ESMapWrapUtil.getSelectTypes());
	}

	/**
	 * @param handleBean
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	public void completeAnswer(LogicHandleBean handleBean) {
		CreateForm form = (CreateForm) handleBean.getForm();
		Model model = handleBean.getModel();
		AnswerInfoService answerInfoService = (AnswerInfoService) handleBean.getService(Tables.ANSWER_INFO_TBL);
		try {
			// create部
			Optional<NumberPlaceBean> numberPlaceBeanOpt = Optional.ofNullable(new Sudoku(form.getSelectType()).generate());
			if (numberPlaceBeanOpt.isPresent()) {
				List<AnswerInfoTbl> list = answerInfoService.select(numberPlaceBeanOpt.get());
				if (list.isEmpty()) {
					Optional<String> keyHashOpt = Optional.ofNullable(
									insertAnswerAndScore(handleBean, numberPlaceBeanOpt.get())
					);
					if (keyHashOpt.isPresent()) {
						model.addAttribute("message", "登録完了です。");
						model.addAttribute("keyHash", keyHashOpt.get());
					} else {
						model.addAttribute("message", "新規追加失敗です。");
						log.debug("insertAnswerAndScore()でロールバックしています。");
					}
				} else {
					// 一意制約になるので何もしない。
					model.addAttribute("message", "新規追加失敗です。");
					log.debug("completeAnswer()で一意制約違反です。");
				}
			}
		} catch (SudokuApplicationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			model.addAttribute("message", "新規追加失敗です。");
			log.debug("completeAnswer()で一意制約違反です。");
		}
	}

	/**
	 * @param handleBean
	 * @param numberPlaceBean
	 * @return
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	@Nullable
	@Transactional
	public String insertAnswerAndScore(LogicHandleBean handleBean, NumberPlaceBean numberPlaceBean) {
		AnswerInfoService answerInfoService = (AnswerInfoService) handleBean.getService(Tables.ANSWER_INFO_TBL);
		ScoreInfoService scoreInfoService = (ScoreInfoService) handleBean.getService(Tables.SCORE_INFO_TBL);
		try {
			AnswerInfoTbl answerInfoTbl = answerInfoService.insert(numberPlaceBean);
			Optional<ScoreInfoTbl> scoreInfoTblOpt = Optional.ofNullable(scoreInfoService.insert(numberPlaceBean));
			if (scoreInfoTblOpt.isPresent()) {
				return answerInfoTbl.getKeyHash();
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
}
