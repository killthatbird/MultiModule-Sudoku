package jp.co.valtech.sudoku.core.service;

import java.time.LocalDateTime;
import java.util.List;
import jp.co.valtech.sudoku.core.bean.NumberPlaceBean;
import jp.co.valtech.sudoku.core.bean.SearchConditionBean;
import jp.co.valtech.sudoku.core.domain.AnswerInfoTbl;
import jp.co.valtech.sudoku.core.service.repository.AnswerInfoRepository;
import jp.co.valtech.sudoku.core.service.specification.AnswerInfoSpecifications;
import jp.co.valtech.sudoku.core.service.specification.ScoreInfoSpecifications;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

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

	@SuppressWarnings("initialization.fields.uninitialized")
	@Autowired
	private AnswerInfoRepository answerInfoRepository;

	@SuppressWarnings("initialization.fields.uninitialized")
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

		public List<AnswerInfoTbl> findByAnswerKey(String answerKey) {
				return answerInfoRepository.findByAnswerKey(answerKey);
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
	public Page<AnswerInfoTbl> findRecords(SearchConditionBean condition, Pageable pageable) {

		Specification<AnswerInfoTbl> typeContains = AnswerInfoSpecifications.typeContains(condition.getType());
		Specification<AnswerInfoTbl> noContains = AnswerInfoSpecifications.noContains(condition.getNo(), condition.getSelectorNo());
		Specification<AnswerInfoTbl> keyHashContains = AnswerInfoSpecifications.keyHashContains(condition.getKeyHash(), condition.getSelectorKeyHash());
		Specification<AnswerInfoTbl> scoreContains = ScoreInfoSpecifications.scoreContains(condition.getScore(), condition.getSelectorScore());
		Specification<AnswerInfoTbl> nameContains = ScoreInfoSpecifications.nameContains(condition.getName(), condition.getSelectorName());
		Specification<AnswerInfoTbl> dateContains = ScoreInfoSpecifications.dateContains(condition.getDateStart(), condition.getDateEnd());

		Specifications answerSpecification = Specifications.where(typeContains);
		if (noContains != null) {
			answerSpecification.and(noContains);
		}
		if (keyHashContains != null) {
			answerSpecification.and(keyHashContains);
		}
		if (scoreContains != null) {
			answerSpecification.and(scoreContains);
		}
		if (nameContains != null) {
			answerSpecification.and(nameContains);
		}
		if (dateContains != null) {
			answerSpecification.and(dateContains);
		}
		Page<AnswerInfoTbl> page = answerInfoRepository.findAll(answerSpecification, pageable);
		return page;
	}
}
