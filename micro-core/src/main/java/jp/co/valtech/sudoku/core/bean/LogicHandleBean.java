package jp.co.valtech.sudoku.core.bean;

import jp.co.valtech.sudoku.core.config.enums.Tables;
import jp.co.valtech.sudoku.core.form.BaseForm;
import jp.co.valtech.sudoku.core.service.AnswerInfoService;
import jp.co.valtech.sudoku.core.service.ScoreInfoService;
import jp.co.valtech.sudoku.core.service.ServiceBase;
import lombok.Data;
import lombok.experimental.Accessors;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.factory.Maps;
import org.springframework.ui.Model;

/**
 * コントローラからロジックへ橋渡しするBeanです。
 *
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class LogicHandleBean {

	@Nullable
	private BaseForm form;
	@Nullable
	private Model model;

	private MutableMap<String, ServiceBase> serviceHandleMap;

	/**
	 * コンストラクタです。
	 *
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	public LogicHandleBean() {
		this.form = null;
		this.model = null;
		this.serviceHandleMap = Maps.mutable.empty();
	}

	/**
	 * テーブル名を引数にサービスクラスを取得します。
	 *
	 * @param tableName
	 * 				テーブル名
	 * @return ServiceBase
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	@Nullable
	public ServiceBase getService(Tables tableName) {
		if (tableName == null) {
			return null;
		} else {
			return this.serviceHandleMap.get(tableName.name());
		}
	}

	/**
	 * テーブル名を引数にサービスクラスを設定します。
	 *
	 * @param tableName
	 * 				テーブル名
	 * @param service
	 * 				サービスクラス
	 * @return LogicHandleBean
	 * @author uratamanabu
	 * @version 1.0
	 * @since 1.0
	 */
	public LogicHandleBean setService(Tables tableName, ServiceBase service) {
		if (Tables.ANSWER_INFO_TBL.equals(tableName)
						&& service instanceof AnswerInfoService) {
			this.serviceHandleMap.put(Tables.ANSWER_INFO_TBL.name(), service);
		} else if (Tables.SCORE_INFO_TBL.equals(tableName)
						&& service instanceof ScoreInfoService) {
			this.serviceHandleMap.put(Tables.SCORE_INFO_TBL.name(), service);
		}
		return this;
	}
}
