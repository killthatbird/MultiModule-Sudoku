package jp.co.valtech.sudoku.api;

import java.util.concurrent.Executor;
import jp.co.valtech.sudoku.core.Domain;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.web.client.RestTemplate;

/**
 * @author uratamanabu
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
@Import(Domain.class)
@EntityScan(basePackageClasses = {
		ApiApp.class,
		Jsr310JpaConverters.class
})
@EnableScheduling
public class ApiApp extends SpringBootServletInitializer implements SchedulingConfigurer {

		/**
		 * @author uratamanabu
		 * @version 1.0
		 * @since 1.0
		 */
		public static void main(String[] args) {
				new SpringApplicationBuilder(ApiApp.class)
						.profiles("dev")
						.run(args);
		}

		@Bean
		public RestTemplate getRestTemplate() {
				return new RestTemplate();
		}

		@Override
		protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
				return builder.sources(ApiApp.class);
		}

		/**
		 * @author uratamanabu
		 * @version 1.0
		 * @since 1.0
		 */
		@Override
		public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
				taskRegistrar.setScheduler(taskScheduler());
		}

		/**
		 * @author uratamanabu
		 * @version 1.0
		 * @since 1.0
		 */
		@Bean(destroyMethod = "shutdown")
		public Executor taskScheduler() {
				ThreadPoolTaskScheduler threadPool = new ThreadPoolTaskScheduler();
				threadPool.setPoolSize(5);

				// shutdown 時に Task の完了を待つ（最大10分間）
				threadPool.setWaitForTasksToCompleteOnShutdown(true);
				threadPool.setAwaitTerminationSeconds(60 * 10);
				return threadPool;
		}

}
