package jp.co.valtech.sudoku.core.service;

import jp.co.valtech.sudoku.core.bean.NumberPlaceBean;
import jp.co.valtech.sudoku.core.bean.SearchConditionBean;
import jp.co.valtech.sudoku.core.domain.AnswerInfoTbl;
import jp.co.valtech.sudoku.core.service.repository.AnswerInfoRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static jp.co.valtech.sudoku.core.service.specification.AnswerInfoSpecifications.*;
import static jp.co.valtech.sudoku.core.service.specification.ScoreInfoSpecifications.*;

/**
 * ANSWER_INFO_TBLへのサービスクラスです。
 * このクラスを経由してCRUD操作を実行してください。
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Service
public class AnswerInfoService extends ServiceBase {

	private static final String NO = "no";

	@Autowired
	private AnswerInfoRepository answerInfoRepository;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * ANSWER_INFO_TBLへ数独をインサートします。
	 *
	 * @param numberplaceBean
	 * 				数独
	 * @return AnswerInfoTbl
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	public AnswerInfoTbl insert(NumberPlaceBean numberplaceBean) {
		AnswerInfoTbl answerInfoTbl = modelMapper.map(numberplaceBean, AnswerInfoTbl.class);
		answerInfoTbl.setCreateDate(LocalDateTime.now());
		return answerInfoRepository.saveAndFlush(answerInfoTbl);
	}

	/**
	 * ANSWER_INFO_TBLへAnswerKeyで検索を行います。
	 *
	 * @param numberplaceBean
	 * 				数独
	 * @return
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	public List<AnswerInfoTbl> select(NumberPlaceBean numberplaceBean) {
		return answerInfoRepository.findByAnswerKey(numberplaceBean.getAnswerKey());
	}

	/**
	 * ANSWER_INFO_TBLへTypeで検索を行います。
	 *
	 * @param type
	 * 				タイプ
	 * @return AnswerInfoTbl
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	public AnswerInfoTbl findByType(int type) {
		return answerInfoRepository.findByType(type);
	}

	/**
	 * ANSWER_INFO_TBLへTypeとKeyHashで検索を行います。
	 *
	 * @param type
	 * 				タイプ
	 * @param keyHash
	 * 				KeyHash
	 * @return AnswerInfoTbl
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	public AnswerInfoTbl findByTypeAndKeyHash(int type, String keyHash) {
		return answerInfoRepository.findByTypeAndKeyHash(type, keyHash);
	}

	/**
	 * ANSWER_INFO_TBLとScore_INFO_TBLの結合テーブルへ検索画面から入力された条件で検索します。
	 *
	 * @param condition
	 * 				検索条件
	 * @param pageable
	 * 				ページ情報
	 * @return
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	@Nullable
	public Page<AnswerInfoTbl> findRecords(@NonNull SearchConditionBean condition, Pageable pageable) {
		Specifications answerSpecification = Specifications
						.where(typeContains(condition.getType()))
						.and(noContains(condition.getNo(), condition.getSelectorNo()))
						.and(keyHashContains(condition.getKeyHash(), condition.getSelectorKeyHash()))
						.and(scoreContains(condition.getScore(), condition.getSelectorScore()))
						.and(nameContains(condition.getName(), condition.getSelectorName()))
						.and(dateContains(condition.getDateStart(), condition.getDateEnd()));
		Page<AnswerInfoTbl> page = answerInfoRepository.findAll(answerSpecification, pageable);
		return page;
	}
}
