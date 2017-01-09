package jp.co.valtech.sudoku.batch.job;

import jp.co.valtech.sudoku.batch.service.InsertSudokuService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @version 1.0
 * @since 1.0
 */
public class InsertSudokuJob implements Job {
	@Autowired
	private InsertSudokuService service;

	/**
	 * @version 1.0
	 * @since 1.0
	 */
	@Override
	public void execute(JobExecutionContext jobExecutionContext) {
		service.hello();
	}
}
