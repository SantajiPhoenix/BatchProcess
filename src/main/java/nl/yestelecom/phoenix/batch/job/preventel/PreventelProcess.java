package nl.yestelecom.phoenix.batch.job.preventel;

import java.util.ArrayList;
import java.util.List;

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
public class PreventelProcess implements JobProcessor {

    private static Logger logger = LoggerFactory.getLogger(PreventelProcess.class);

    @Autowired
    private PreventelRepository preventelRepo;
    @Autowired
    private PreventelTxtWriter preventelTxtWriter;
    @Autowired
    private PreventelUtil preventelUtil;
    @Autowired
    private WriteVisitor writerVisitorImpl;
    @Autowired
    private PreventelEncodedFileSender preventelEncodedFileSender;
    @Autowired
    private ArchiveFileCreatorUtil archiveFileCreator;
    @Autowired
    private PreventelEmailSender preventelEmailSender;
    @Autowired
    private EmailDetailsRepo emailDetailsRepo;
    @Autowired
    private SenderVisitor senderVisitor;

    @Value("${preventel.fileName}")
    private String fileName;
    @Value("${preventel.jobname}")
    private String jobName;
    @Value("${preventel.filePath}")
    private String fileDirecotry;

    private List<Preventel> preventelList;
    private List<String> preventelDataList;
    private String sequence;
    private EmailDetails emailDetails;

    @Override
    public void read() {
        logger.info("Read : " + getJobName());
        preventelList = preventelRepo.findAll();
        emailDetails = emailDetailsRepo.getEmailDetailsForJob(getJobName());

    }

    @Override
    public void process() {
        logger.info("Process : " + getJobName());
        preventelDataList = new ArrayList<>();
        int count = 0;
        sequence = preventelUtil.getDate() + "V1";
        final String startRow = preventelUtil.rightPad("A" + fileName + sequence, 330);
        String endRow = preventelUtil.rightPad("Z" + fileName + sequence, 14);

        preventelDataList.add(startRow);
        for (final Preventel preventelData : preventelList) {
            preventelDataList.add(preventelData.toString());
            count++;
        }

        endRow = preventelUtil.rightPad(endRow + count, 330);
        preventelDataList.add(endRow);
    }

    @Override
    public void write() {
        logger.info("Write : " + getJobName());
        preventelTxtWriter.setSequence(sequence);
        preventelTxtWriter.setData(preventelDataList);
        preventelTxtWriter.accept(writerVisitorImpl);
    }

    @Override
    public void send() {
        logger.info("Send : " + getJobName());
        preventelEmailSender.setEmailDetails(emailDetails);
        preventelEmailSender.accept(senderVisitor);

        preventelEncodedFileSender.setSequence(sequence);
        preventelEncodedFileSender.fileEncoderAndSender();
    }

    @Override
    public void postProcess() {
        logger.info("Post Process : " + getJobName());
        //archiveFileCreator.setFileDirecotry(fileDirecotry);
        //archiveFileCreator.archiveCurrentFile();
    }

    @Override
    public String getJobName() {
        return jobName;
    }

}
