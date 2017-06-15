package nl.yestelecom.phoenix.batch.job.creditcontrol;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.archiver.ArchiveFileCreatorUtil;
import nl.yestelecom.phoenix.batch.job.JobProcessor;
import nl.yestelecom.phoenix.batch.job.emaildetails.EmailDetails;
import nl.yestelecom.phoenix.batch.job.emaildetails.EmailDetailsRepo;
import nl.yestelecom.phoenix.batch.sender.SenderVisitor;
import nl.yestelecom.phoenix.batch.writer.WriteVisitor;

@Service
@Configuration
public class CreditControlProcess implements JobProcessor {
    private static Logger logger = LoggerFactory.getLogger(CreditControlProcess.class);

    @Autowired
    CreditControlCSVWriter creditControlcsvWriter;

    @Autowired
    WriteVisitor writerVisitorImpl;

    @Autowired
    SenderVisitor senderVisitor;

    @Autowired
    CreditControlFTPSender creditControlFTPSender;

    @Autowired
    CreditControlRepository creditControlRepository;

    @Autowired
    CreditControlEmailSender creditControlEmailSender;

    @Autowired
    EmailDetailsRepo emailDetailsRepo;

    @Autowired
    ArchiveFileCreatorUtil archiveFileCreator;

    @Value("${creditcontrol.jobname}")
    private String jobName;
    @Value("${creditcontrol.filePath}")
    private String fileDirecotry;

    List<CreditControl> creditControl;

    List<String> ccList;
    EmailDetails emailDetails;

    @Override
    public void read() {
        logger.info("Read : " + getJobName());
        creditControl = creditControlRepository.fetchAllData();

        emailDetails = emailDetailsRepo.getEmailDetailsForJob(getJobName());
    }

    @Override
    public void process() {
        logger.info("Process : " + getJobName());
        ccList = new ArrayList<>();
        for (final CreditControl cc : creditControl) {
            if (cc != null) {
                ccList.add(cc.toString());
            }
        }
    }

    @Override
    public void write() {
        logger.info("Write : " + getJobName());
        creditControlcsvWriter.setRowList(ccList);
        creditControlcsvWriter.accept(writerVisitorImpl);
    }

    @Override
    public void send() {
        logger.info("Send : " + getJobName());
        creditControlEmailSender.setEmailDetails(emailDetails);
        creditControlEmailSender.accept(senderVisitor);
        creditControlFTPSender.accept(senderVisitor);
    }

    @Override
    public void postProcess() {
        logger.info("Post Process : " + getJobName());
        archiveFileCreator.setFileDirecotry(fileDirecotry);
        archiveFileCreator.archiveCurrentFile();

    }

    @Override
    public String getJobName() {
        return jobName;

    }

}
