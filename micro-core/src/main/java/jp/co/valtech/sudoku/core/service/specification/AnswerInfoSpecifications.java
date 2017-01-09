package jp.co.valtech.sudoku.core.service.specification;

import jp.co.valtech.sudoku.core.config.enums.Selector;
import jp.co.valtech.sudoku.core.domain.AnswerInfoTbl;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import static lombok.AccessLevel.PRIVATE;

/**
 * ANSWER_INFO_TBLへの検索条件をSpecificationで返却するクラスです。
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class AnswerInfoSpecifications {

	private static final String NO = "no";
	private static final String TYPE = "type";
	private static final String KEYHASH = "keyHash";

	/**
	 * @param selectType
	 * @return
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	@Nullable
	public static Specification<AnswerInfoTbl> typeContains(int selectType) {
		return selectType == 0 ? null : (root, query, builder) -> builder.equal(root.get(TYPE), selectType);
	}

	/**
	 * @param no
	 * @param selectorNo
	 * @return
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	@Nullable
	public static Specification<AnswerInfoTbl> noContains(long no, int selectorNo) {
		if (no == 0) {
			return null;
		} else {
			Selector selector = Selector.getSelector(selectorNo);
			if (selector == null) {
				return (root, query, builder) -> builder.equal(root.get(NO), no);
			}
			switch (selector) {
				case PERFECT_MATCH:
					return (root, query, builder) -> builder.equal(root.get(NO), no);
				case AROUND5:
					long fromNo = no - 5 < 0 ? 0 : no - 5;
					long endNo = no + 5 > 9999999999L ? 9999999999L : no + 5;
					return (root, query, builder) -> builder.between(root.get(NO), fromNo, endNo);
				case MORE_BIG:
					return (root, query, builder) -> builder.greaterThan(root.get(NO), no);
				case MORE_SMALL:
					return (root, query, builder) -> builder.lessThan(root.get(NO), no);
				default:
					return (root, query, builder) -> builder.equal(root.get(NO), no);
			}
		}
	}

	/**
	 * @param keyHash
	 * @param selectorKeyHash
	 * @return
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	@Nullable
	public static Specification<AnswerInfoTbl> keyHashContains(@Nullable String keyHash, int selectorKeyHash) {
		if (StringUtils.isEmpty(keyHash)) {
			return null;
		} else {
			Selector selector = Selector.getSelector(selectorKeyHash);
			if (selector == null) {
				return (root, query, builder) -> builder.equal(root.get(KEYHASH), keyHash);
			}
			switch (selector) {
				case PERFECT_MATCH:
					return (root, query, builder) -> builder.equal(root.get(KEYHASH), keyHash);
				case FORWARD_MATCH:
					return (root, query, builder) -> builder.like(root.get(KEYHASH), keyHash + '%');
				case BACKWARD_MATCH:
					return (root, query, builder) -> builder.like(root.get(KEYHASH), '%' + keyHash);
				case PARTIAL_MATCH:
					return (root, query, builder) -> builder.like(root.get(KEYHASH), '%' + keyHash + '%');
				default:
					return (root, query, builder) -> builder.equal(root.get(KEYHASH), keyHash);
			}
		}
	}
}
