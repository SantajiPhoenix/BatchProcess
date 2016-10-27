package nl.yestelecom.phoenix.batch.job.scheduler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.job.JobProcessor;
import nl.yestelecom.phoenix.batch.job.ciot.CiotProcess;
import nl.yestelecom.phoenix.batch.job.creditcontrol.CreditControlProcess;
import nl.yestelecom.phoenix.batch.job.jobstatus.JobStatus;
import nl.yestelecom.phoenix.batch.job.jobstatus.JobStatusRepo;
import nl.yestelecom.phoenix.batch.job.marketpoints.MarketPointsProcess;
import nl.yestelecom.phoenix.batch.job.preventel.PreventelProcess;
import nl.yestelecom.phoenix.batch.job.simoverview.SimOverviewProcess;

@Service
public class JobRunner {
	private static Logger logger = LoggerFactory.getLogger(JobRunner.class);
	
	@Autowired
	CiotProcess ciotProcess;
	
	@Autowired
	SimOverviewProcess simOverviewProcess;
	
	@Autowired
	MarketPointsProcess marketPointsProcess;
	
	@Autowired
	CreditControlProcess creditControlProcess;
	
	@Autowired
	PreventelProcess preventelProcess;
	
	@Autowired
	JobStatusRepo jobStatusRepo;
	
	List<JobProcessor> jobs = new ArrayList<JobProcessor>();
	
	public void addJobs(){
		jobs.add(ciotProcess);
		jobs.add(preventelProcess);
		jobs.add(simOverviewProcess);
		jobs.add(creditControlProcess);
		jobs.add(marketPointsProcess);
	}
	
	public List<JobProcessor> getJobs(){
		return jobs;
	}
	
	public void runJobs(){
		addJobs();
		for(JobProcessor job : jobs){
			JobStatus jobStatus = buildJobStaus(job, "RUNNING");
			jobStatusRepo.save(jobStatus);
			long startTime = System.nanoTime();
			logger.info(" Starting >> "+job.getJobName());
			try{
				job.execute();
				long endTime = System.nanoTime();
				long duration = (endTime - startTime); 
				jobStatus.setTimeTaken(duration);;
				jobStatus.setStatus("SUCCESS");
			}catch (Exception e){
				long endTime = System.nanoTime();
				long duration = (endTime - startTime); 
				jobStatus.setTimeTaken(duration);;
				jobStatus.setStatus("FAILED");
				jobStatus.setError(e.getMessage());
			}
			logger.info(" FINISHED >> "+job.getJobName());
			jobStatusRepo.save(jobStatus);
		}
	}

	private JobStatus buildJobStaus(JobProcessor job, String status) {
		JobStatus jobStatus = new JobStatus();
		jobStatus.setError("");
		jobStatus.setJobDate( new java.sql.Date(Calendar.getInstance().getTime().getTime()));
		jobStatus.setJobName(job.getJobName());
		jobStatus.setStatus(status);
		
		return jobStatus;
	}

}
