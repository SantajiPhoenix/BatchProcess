package nl.yestelecom.phoenix.batch.job.c2yTrend;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.job.JobProcessor;
import nl.yestelecom.phoenix.batch.job.c2yTrend.repo.C2yReportRepository;
import nl.yestelecom.phoenix.batch.job.emaildetails.EmailDetails;
import nl.yestelecom.phoenix.batch.job.emaildetails.EmailDetailsRepo;
import nl.yestelecom.phoenix.batch.job.vasrecon.VasReconProcess;
import nl.yestelecom.phoenix.batch.sender.SenderVisitor;

@Service
@Transactional
public class C2yReportProcessor implements JobProcessor {
    private static Logger logger = LoggerFactory.getLogger(VasReconProcess.class);
    @Autowired
    C2yReportRepository c2yReportRepository;
    @Autowired
    GenerateExcelForC2yReport generateExcelForC2yReport;
    @Autowired
    C2ytrendEmailSender c2ytrendEmailSender;

    @Autowired
    SenderVisitor senderVisitor;

    @Autowired
    EmailDetailsRepo emailDetailsRepo;
    EmailDetails emailDetails;
    @Value("${c2yreport.jobname}")
    private String jobName;

    @Override
    public void read() {

        logger.info("Read : " + getJobName());

        emailDetails = emailDetailsRepo.getEmailDetailsForJob(getJobName());

    }

    @Override
    public void process() {
        // TODO Auto-generated method stub

    }

    @Override
    public void write() {
        generateExcelForC2yReport.generateExcel();
    }

    @Override
    public void send() {
        logger.info("Send : " + getJobName());
        c2ytrendEmailSender.setEmailDetails(emailDetails);
        c2ytrendEmailSender.accept(senderVisitor);
    }

    @Override
    public void postProcess() {
        // TODO Auto-generated method stub

    }

    @Override
    public String getJobName() {
        // TODO Auto-generated method stub
        return jobName;
    }
}
