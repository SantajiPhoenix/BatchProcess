package nl.yestelecom.phoenix.batch.job.ciot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.archiver.ArchiveFileCreatorUtil;
import nl.yestelecom.phoenix.batch.job.JobProcessor;
import nl.yestelecom.phoenix.batch.job.emaildetails.EmailDetails;
import nl.yestelecom.phoenix.batch.job.emaildetails.EmailDetailsRepo;
import nl.yestelecom.phoenix.batch.sender.SenderVisitor;
import nl.yestelecom.phoenix.batch.writer.WriteVisitor;

@Service
public class CiotProcess implements JobProcessor {
    private static Logger logger = LoggerFactory.getLogger(CiotProcess.class);

    @Autowired
    private CiotRepository ciotRepo;
    @Autowired
    private CiotXMLWriter ciotXmlWriter;
    @Autowired
    private CiotUtil ciotUtil;
    @Autowired
    private WriteVisitor writerVisitorImpl;
    @Autowired
    private CiotFTPSender ciotFTPSender;
    @Autowired
    private SenderVisitor senderVisitor;
    @Autowired
    private CiotEmailSender ciotEmailSender;
    @Autowired
    private ArchiveFileCreatorUtil archiveFileCreator;

    @Autowired
    private EmailDetailsRepo emailDetailsRepo;

    @Value("${ciot.filePath}")
    private String fileDirecotry;
    @Value("${ciot.jobName}")
    private String jobName;

    private List<Ciot> ciotData;
    private String sequence;
    private final Map<String, Object> xmlData = new HashMap<>();
    private EmailDetails emailDetails;

    @Override
    public void read() {
        logger.info("Read : " + getJobName());
        ciotData = ciotRepo.findAll();
        sequence = ciotRepo.generateFileSequence();
        emailDetails = emailDetailsRepo.getEmailDetailsForJob(getJobName());
    }

    @Override
    public void process() {
        logger.info("Process : " + getJobName());
        xmlData.put("abonneelist", ciotData);
        sequence = ciotUtil.getDate() + sequence;
    }

    @Override
    public void write() {
        logger.info("Write : " + getJobName());
        ciotXmlWriter.setSequence(sequence);
        ciotXmlWriter.setXmlData(xmlData);
        ciotXmlWriter.accept(writerVisitorImpl);
    }

    @Override
    public void send() {
        logger.info("Send : " + getJobName());
        ciotEmailSender.setEmailDetails(emailDetails);
        ciotEmailSender.accept(senderVisitor);

        ciotFTPSender.setSequence(sequence);
        ciotFTPSender.accept(senderVisitor);

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
