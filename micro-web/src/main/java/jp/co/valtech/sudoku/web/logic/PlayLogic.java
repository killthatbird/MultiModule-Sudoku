package jp.co.valtech.sudoku.web.logic;

import jp.co.valtech.sudoku.core.bean.LogicHandleBean;
import jp.co.valtech.sudoku.core.bean.NumberPlaceBean;
import jp.co.valtech.sudoku.core.bean.RecordBean;
import jp.co.valtech.sudoku.core.config.enums.Tables;
import jp.co.valtech.sudoku.core.domain.AnswerInfoTbl;
import jp.co.valtech.sudoku.core.domain.ScoreInfoTbl;
import jp.co.valtech.sudoku.core.exception.SudokuApplicationException;
import jp.co.valtech.sudoku.core.service.AnswerInfoService;
import jp.co.valtech.sudoku.core.service.ScoreInfoService;
import jp.co.valtech.sudoku.core.sudoku.Sudoku;
import jp.co.valtech.sudoku.core.utils.ESListWrapUtil;
import jp.co.valtech.sudoku.core.utils.ESMapWrapUtil;
import jp.co.valtech.sudoku.core.utils.SudokuUtils;
import jp.co.valtech.sudoku.web.form.CreateForm;
import jp.co.valtech.sudoku.web.form.PlayForm;
import jp.co.valtech.sudoku.web.form.ScoreForm;
import jp.co.valtech.sudoku.web.utils.CompareUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.api.list.MutableList;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class PlayLogic {

	/**
	 * @param handleBean
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	public void createQuestion(LogicHandleBean handleBean) {
		Model model = handleBean.getModel();
		model.addAttribute("selectTypes", ESMapWrapUtil.getSelectTypes());
		model.addAttribute("selectLevels", ESMapWrapUtil.getSelectLevels());
	}

	/**
	 * @param handleBean
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	public void playNumberPlace(LogicHandleBean handleBean)
					throws SudokuApplicationException {
		CreateForm form = (CreateForm) handleBean.getForm();
		Model model = handleBean.getModel();
		AnswerInfoService answerInfoService = (AnswerInfoService) handleBean.getService(Tables.ANSWER_INFO_TBL);
		AnswerInfoTbl answerInfoTbl = answerInfoService.findByType(form.getSelectType());
		NumberPlaceBean numberPlaceBean = SudokuUtils.answerInfoTblConvertBean(answerInfoTbl);
		// 一件から虫食いのリストを取得します。
		numberPlaceBean = new Sudoku(form.getSelectType()).filteredOfLevel(numberPlaceBean, form.getSelectLevel());
		PlayForm playForm = new ModelMapper().map(numberPlaceBean, PlayForm.class);
		playForm.setCount(0);
		playForm.setScore(SudokuUtils.calculationScore(form.getSelectType(), form.getSelectLevel()));
		model.addAttribute("playForm", playForm);
		model.addAttribute("selectNums", ESListWrapUtil.getSelectNum(form.getSelectType()));
		model.addAttribute("selectCells", ESListWrapUtil.createCells(form.getSelectType()));
	}

	/**
	 * @param handleBean
	 * @return
	 * @throws
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	public int isCheck(LogicHandleBean handleBean)
					throws SudokuApplicationException {
		PlayForm form = (PlayForm) handleBean.getForm();
		Model model = handleBean.getModel();
		AnswerInfoService answerInfoService = (AnswerInfoService) handleBean.getService(Tables.ANSWER_INFO_TBL);
		ScoreInfoService scoreInfoService = (ScoreInfoService) handleBean.getService(Tables.SCORE_INFO_TBL);

		AnswerInfoTbl answerInfoTbl = answerInfoService.findByTypeAndKeyHash(form.getType(), form.getKeyHash());
		NumberPlaceBean numberPlaceBean = SudokuUtils.answerInfoTblConvertBean(answerInfoTbl);

		CompareUtil.playFormCompareAnswer(form, numberPlaceBean);
		if (form.isCompareFlg()) {
			ScoreInfoTbl scoreInfoTbl = scoreInfoService.findByTypeAndKeyHash(form.getType(), form.getKeyHash());
			ScoreForm scoreForm = new ModelMapper().map(form, ScoreForm.class);
			if (scoreForm.getScore() > scoreInfoTbl.getScore()) {
				scoreForm.setName("");
				scoreForm.setMemo("");
				// modelに詰め込みます。
				model.addAttribute("scoreForm", scoreForm);
				model.addAttribute("hiScore", scoreInfoTbl.getScore());
				// score によって、ベストスコアに進む
				return 1;
			} else {
				scoreForm.setName(scoreInfoTbl.getName());
				scoreForm.setMemo(scoreInfoTbl.getMemo());
				// modelに詰め込みます。
				model.addAttribute("scoreForm", scoreForm);
				model.addAttribute("hiScore", scoreInfoTbl.getScore());
				// score によって、完了に進む
				return 2;
			}
		} else {
			// modelに詰め込みます。
			model.addAttribute("form", form);
			List<Integer> SELECT_NUMS = ESListWrapUtil.getSelectNum(form.getType());
			model.addAttribute("selectNums", SELECT_NUMS);
			MutableList<MutableList<String>> SELECT_CELLS = ESListWrapUtil.createCells(form.getType());
			model.addAttribute("selectCells", SELECT_CELLS);
			return 3;
		}
	}

	/**
	 * @param handleBean
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	public void bestScore(LogicHandleBean handleBean) {
		ScoreForm form = (ScoreForm) handleBean.getForm();
		Model model = handleBean.getModel();

		Optional<Long> noOpt = Optional.ofNullable(
						updateScore(handleBean)
		);
		if (noOpt.isPresent()) {
			// modelに詰め込みます。
			model.addAttribute("scoreForm", form);
			model.addAttribute("message", "登録完了です。");
			model.addAttribute("no", noOpt.get());
			List<RecordBean> list = new ArrayList<>();
			RecordBean bean = new RecordBean();
			bean.setNo(noOpt.get());
			bean.setType(form.getType());
			bean.setKeyHash(form.getKeyHash());
			bean.setScore(form.getScore());
			bean.setName(form.getName());
			bean.setMemo(form.getMemo());
			list.add(bean);
			model.addAttribute("list", list);
		} else {
			model.addAttribute("message", "更新失敗です。");
			log.debug("insertAnswerAndScore()でロールバックしています。");
		}
	}

	/**
	 * @param handleBean
	 * @return
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	@Transactional
	public Long updateScore(LogicHandleBean handleBean) {
		ScoreForm form = (ScoreForm) handleBean.getForm();
		ScoreInfoService scoreInfoService = (ScoreInfoService) handleBean.getService(Tables.SCORE_INFO_TBL);
		ScoreInfoTbl scoreInfoTbl = scoreInfoService.findByTypeAndKeyHash(form.getType(), form.getKeyHash());
		scoreInfoTbl.setName(form.getName());
		scoreInfoTbl.setMemo(form.getMemo());
		scoreInfoTbl.setScore(form.getScore());
		try {
			scoreInfoTbl = scoreInfoService.update(scoreInfoTbl);
			return scoreInfoTbl.getNo();
		} catch (Exception e) {
			return null;
		}
	}
}
