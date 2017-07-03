package jp.co.valtech.sudoku.core.service;

import java.time.LocalDateTime;
import jp.co.valtech.sudoku.core.bean.NumberPlaceBean;
import jp.co.valtech.sudoku.core.config.CommonConstant;
import jp.co.valtech.sudoku.core.domain.ScoreInfoTbl;
import jp.co.valtech.sudoku.core.service.repository.ScoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SCORE_INFO_TBLへのサービスクラスです。
 * このクラスを経由してCRUD操作を実行してください。
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Service
public class ScoreInfoService extends ServiceBase {

	@SuppressWarnings("initialization.fields.uninitialized")
	@Autowired
	private ScoreRepository scoreRepository;

	@SuppressWarnings("initialization.fields.uninitialized")
	@Autowired
	private ModelMapper modelMapper;

	/**
	 * SCORE_INFO_TBLへ空スコア情報をインサートします。
	 *
	 * @param numberplaceBean
	 * 				数独
	 * @return ScoreInfoTbl
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	public ScoreInfoTbl insert(NumberPlaceBean numberplaceBean) {
		ScoreInfoTbl scoreInfoTbl = modelMapper.map(numberplaceBean, ScoreInfoTbl.class);
		scoreInfoTbl.setScore(CommonConstant.ZERO);
		scoreInfoTbl.setName(CommonConstant.EMPTY_STR);
		scoreInfoTbl.setMemo(CommonConstant.EMPTY_STR);
		scoreInfoTbl.setUpdateDate(LocalDateTime.now());
			return scoreRepository.save(scoreInfoTbl);
	}

	/**
	 * SCORE_INFO_TBLへTypeとKeyHashで検索を行います。
	 *
	 * @param type
	 * 				タイプ
	 * @param keyHash
	 * 				KeyHash
	 * @return ScoreInfoTbl
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	public ScoreInfoTbl findByTypeAndKeyHash(int type, String keyHash) {
		return scoreRepository.findByTypeAndKeyHash(type, keyHash);
	}

	/**
	 * SCORE_INFO_TBLへスコア情報をアップデートします。
	 *
	 * @param numberplaceBean
	 * 				数独
	 * @return ScoreInfoTbl
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	public ScoreInfoTbl update(NumberPlaceBean numberplaceBean) {
		ScoreInfoTbl scoreInfoTbl = modelMapper.map(numberplaceBean, ScoreInfoTbl.class);
		scoreInfoTbl.setUpdateDate(LocalDateTime.now());
			return scoreRepository.save(scoreInfoTbl);
	}

	/**
	 * SCORE_INFO_TBLへスコア情報をアップデートします。
	 *
	 * @param scoreInfoTbl
	 * 				スコア情報
	 * @return ScoreInfoTbl
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	public ScoreInfoTbl update(ScoreInfoTbl scoreInfoTbl) {
		scoreInfoTbl.setUpdateDate(LocalDateTime.now());
			return scoreRepository.save(scoreInfoTbl);
	}

}
