package jp.co.valtech.sudoku.batch.controller;

import jp.co.valtech.sudoku.batch.job.InsertSudokuJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@RestController
public class JobController {

	@Autowired
	private SchedulerFactoryBean schedulerFactory;

	/**
	 * @param jobName
	 * @param groupName
	 * @param quantity
	 * @param interval
	 * @return
	 * @version 1.0
	 * @since 1.0
	 */
	@RequestMapping(value = "/api/start/{jobName}/{groupName}/{quantity}/{interval}",
					method = RequestMethod.GET, produces = "application/json")
	public Boolean addJob(@PathVariable("jobName") String jobName,
	                      @PathVariable("groupName") String groupName,
	                      @PathVariable("quantity") int quantity,
	                      @PathVariable("interval") int interval) {
		Scheduler scheduler = schedulerFactory.getScheduler();
		JobDetail job = newJob(InsertSudokuJob.class)
						.withIdentity(jobName, groupName)
						.build();

		// Trigger the job to run now
		Trigger trigger = newTrigger()
						.withIdentity(jobName, groupName)
						.startNow()
						.withSchedule(
										simpleSchedule()
														.withIntervalInSeconds(interval)
														.withRepeatCount(quantity)
						)
						.build();

		// Tell quartz to schedule the job using our trigger
		try {
			scheduler.scheduleJob(job, trigger);
			log.info("試験ログです。");
			return true;
		} catch (SchedulerException e) {
			log.error(e.getMessage());
			return false;
		}
	}
}
