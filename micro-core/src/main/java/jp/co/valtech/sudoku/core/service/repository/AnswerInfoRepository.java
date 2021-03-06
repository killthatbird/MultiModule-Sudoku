package jp.co.valtech.sudoku.core.service.repository;

import jp.co.valtech.sudoku.core.domain.AnswerInfoTbl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * ANSWER_INFO_TBLへのRepositoryクラスです。
 * このクラスでCRUD操作を実装してください。
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
public interface AnswerInfoRepository extends JpaRepository<AnswerInfoTbl, Long>, JpaSpecificationExecutor<AnswerInfoTbl> {

	/**
	 * ANSWER_INFO_TBLへAnswerKeyで検索を行います。
	 *
	 * @param answerKey
	 * @return
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	List<AnswerInfoTbl> findByAnswerKey(String answerKey);

	/**
	 * ANSWER_INFO_TBLへTypeとKeyHashで検索を行います。
	 *
	 * @param type
	 * @param keyHash
	 * @return AnswerInfoTbl
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	AnswerInfoTbl findByTypeAndKeyHash(int type, String keyHash);

	/**
	 * ANSWER_INFO_TBLへTypeで検索を行います。
	 *
	 * @param type
	 * @return AnswerInfoTbl
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	@Query(nativeQuery = true, value = "SELECT * FROM ANSWER_INFO_TBL WHERE TYPE = :TYPE ORDER BY NO DESC LIMIT 1")
	AnswerInfoTbl findByType(@Param("TYPE") int type);
}
