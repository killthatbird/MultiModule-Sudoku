package jp.co.valtech.sudoku.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

/**
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
@Import({SchedulerConfig.class})
public class BatchApp extends SpringBootServletInitializer {

	/**
	 * @param args
	 * @version 1.0
	 * @since 1.0
	 */
	public static void main(String[] args) {
		SpringApplication.run(BatchApp.class, args);
	}
}
