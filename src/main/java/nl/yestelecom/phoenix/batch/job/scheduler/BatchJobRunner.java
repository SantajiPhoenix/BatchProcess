package nl.yestelecom.phoenix.batch.job.scheduler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.job.JobProcessor;
import nl.yestelecom.phoenix.batch.job.ciot.CiotProcess;
import nl.yestelecom.phoenix.batch.job.creditcontrol.CreditControlProcess;
import nl.yestelecom.phoenix.batch.job.jobstatus.JobStatus;
import nl.yestelecom.phoenix.batch.job.jobstatus.JobStatusRepo;
import nl.yestelecom.phoenix.batch.job.marketpoints.MarketPointsProcess;
import nl.yestelecom.phoenix.batch.job.preventel.PreventelProcess;
import nl.yestelecom.phoenix.batch.job.simoverview.SimOverviewProcess;
import nl.yestelecom.phoenix.batch.job.vasrecon.VasReconProcess;

@Service
public class BatchJobRunner {
    private static Logger logger = LoggerFactory.getLogger(BatchJobRunner.class);

    @Autowired
    private CiotProcess ciotProcess;

    @Autowired
    private SimOverviewProcess simOverviewProcess;

    @Autowired
    private MarketPointsProcess marketPointsProcess;

    @Autowired
    private CreditControlProcess creditControlProcess;

    @Autowired
    private PreventelProcess preventelProcess;

    @Autowired
    private JobStatusRepo jobStatusRepo;

    @Autowired
    private VasReconProcess vasReconProcess;

    List<JobProcessor> jobs = new ArrayList<>();

    public void addJobs() {
        jobs.add(ciotProcess);
        jobs.add(preventelProcess);
        jobs.add(simOverviewProcess);
        jobs.add(creditControlProcess);
        jobs.add(marketPointsProcess);
    }

    public List<JobProcessor> getJobs() {
        return jobs;
    }

    public void runJobs() {
        addJobs();
        for (final JobProcessor job : jobs) {
            processJob(job);
        }

    }

    @Scheduled(cron = "0 0 5 * * ?")
    public void runMarketPointsProcess() {
        processJob(marketPointsProcess);
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void runCiotProcess() {
        processJob(ciotProcess);

    }

    @Scheduled(cron = "0 0 4 * * ?")
    public void runPreventelProcess() {
        processJob(preventelProcess);

    }

    @Scheduled(cron = "0 0 5 * * MON-WED")
    public void runSimOverviewProcess() {
        processJob(simOverviewProcess);

    }

    @Scheduled(cron = "0 0 23 15 * ?")
    public void runCreditControlProcess() {
        processJob(creditControlProcess);

    }

    public void runVasRecon() {
        processJob(vasReconProcess);
    }

    private void processJob(JobProcessor job) {
        final JobStatus jobStatus = buildJobStaus(job, "RUNNING");
        jobStatusRepo.save(jobStatus);
        final long startTime = System.nanoTime();
        logger.info(" Starting >> " + job.getJobName());
        try {
            job.execute();
            final long endTime = System.nanoTime();
            final long duration = endTime - startTime;
            jobStatus.setTimeTaken(duration);
            jobStatus.setStatus("SUCCESS");
        } catch (final Exception e) {
            final long endTime = System.nanoTime();
            final long duration = endTime - startTime;
            jobStatus.setTimeTaken(duration);
            jobStatus.setStatus("FAILED");
            jobStatus.setError(e.getMessage());
            logger.error(e.getMessage(), e);
        }
        logger.info(" FINISHED >> " + job.getJobName());
        jobStatusRepo.save(jobStatus);

    }

    private JobStatus buildJobStaus(JobProcessor job, String status) {
        final JobStatus jobStatus = new JobStatus();
        jobStatus.setError("");
        jobStatus.setJobDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        jobStatus.setJobName(job.getJobName());
        jobStatus.setStatus(status);

        return jobStatus;
    }

}
