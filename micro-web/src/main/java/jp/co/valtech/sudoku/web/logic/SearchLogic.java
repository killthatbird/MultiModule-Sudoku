package jp.co.valtech.sudoku.web.logic;

import jp.co.valtech.sudoku.core.bean.LogicHandleBean;
import jp.co.valtech.sudoku.core.bean.NumberPlaceBean;
import jp.co.valtech.sudoku.core.bean.SearchConditionBean;
import jp.co.valtech.sudoku.core.config.enums.Tables;
import jp.co.valtech.sudoku.core.domain.AnswerInfoTbl;
import jp.co.valtech.sudoku.core.exception.SudokuApplicationException;
import jp.co.valtech.sudoku.core.service.AnswerInfoService;
import jp.co.valtech.sudoku.core.sudoku.Sudoku;
import jp.co.valtech.sudoku.core.utils.ESListWrapUtil;
import jp.co.valtech.sudoku.core.utils.ESMapWrapUtil;
import jp.co.valtech.sudoku.core.utils.SudokuUtils;
import jp.co.valtech.sudoku.web.form.DetailForm;
import jp.co.valtech.sudoku.web.form.PlayForm;
import jp.co.valtech.sudoku.web.form.SearchForm;
import jp.co.valtech.sudoku.web.utils.PagenationHelper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.parser.OrderBySource;
import org.springframework.ui.Model;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class SearchLogic {

	/**
	 * @param handleBean
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	public void searchAnswer(LogicHandleBean handleBean) {
		Model model = handleBean.getModel();
		model.addAttribute("selectTypes", ESMapWrapUtil.getSelectTypes());
		model.addAttribute("selectorNo", ESMapWrapUtil.getSelectorNo());
		model.addAttribute("selectorKeyHash", ESMapWrapUtil.getSelectorKeyHash());
		model.addAttribute("selectorScore", ESMapWrapUtil.getSelectorScore());
		model.addAttribute("selectorName", ESMapWrapUtil.getSelectorName());
	}

	/**
	 * @param handleBean
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	public void isSearch(LogicHandleBean handleBean) {
		SearchForm form = (SearchForm) handleBean.getForm();
		Model model = handleBean.getModel();
		AnswerInfoService answerInfoService = (AnswerInfoService) handleBean.getService(Tables.ANSWER_INFO_TBL);
		model.addAttribute("selectTypes", ESMapWrapUtil.getSelectTypes());
		model.addAttribute("selectorNo", ESMapWrapUtil.getSelectorNo());
		model.addAttribute("selectorKeyHash", ESMapWrapUtil.getSelectorKeyHash());
		model.addAttribute("selectorScore", ESMapWrapUtil.getSelectorScore());
		model.addAttribute("selectorName", ESMapWrapUtil.getSelectorName());
		Pageable pageable = new PageRequest(form.getPageNumber(), form.getPageSize(), new OrderBySource("NoAsc").toSort());
		SearchConditionBean conditionBean = new ModelMapper().map(form, SearchConditionBean.class);
		Page<AnswerInfoTbl> page = answerInfoService.findRecords(conditionBean, pageable);
		// ページ番号を設定し直す
		form.setPageNumber(page.getNumber());
		PagenationHelper ph = new PagenationHelper(page);
		model.addAttribute("page", page);
		model.addAttribute("ph", ph);
	}

	/**
	 * @param handleBean
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	public void detail(LogicHandleBean handleBean) {
		DetailForm form = (DetailForm) handleBean.getForm();
		Model model = handleBean.getModel();
		model.addAttribute("detailForm", form);
		model.addAttribute("selectLevels", ESMapWrapUtil.getSelectLevels());
	}

	/**
	 * @param handleBean
	 * @throws
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	public void playNumberPlaceDetail(LogicHandleBean handleBean)
					throws SudokuApplicationException {
		DetailForm form = (DetailForm) handleBean.getForm();
		Model model = handleBean.getModel();
		AnswerInfoService answerInfoService = (AnswerInfoService) handleBean.getService(Tables.ANSWER_INFO_TBL);
		AnswerInfoTbl answerInfoTbl = answerInfoService.findByTypeAndKeyHash(form.getType(), form.getKeyHash());
		NumberPlaceBean numberPlaceBean = SudokuUtils.answerInfoTblConvertBean(answerInfoTbl);
		// 一件から虫食いのリストを取得します。
		numberPlaceBean = new Sudoku(form.getType()).filteredOfLevel(numberPlaceBean, form.getSelectLevel());
		PlayForm playForm = new ModelMapper().map(numberPlaceBean, PlayForm.class);
		playForm.setCount(0);
		playForm.setScore(SudokuUtils.calculationScore(form.getType(), form.getSelectLevel()));
		model.addAttribute("playForm", playForm);
		model.addAttribute("selectNums", ESListWrapUtil.getSelectNum(form.getType()));
		model.addAttribute("selectCells", ESListWrapUtil.createCells(form.getType()));
	}
}
