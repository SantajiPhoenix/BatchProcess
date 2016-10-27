package nl.yestelecom.phoenix.batch.job.creditcontrol;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.job.JobProcessor;
import nl.yestelecom.phoenix.batch.job.emaildetails.EmailDetails;
import nl.yestelecom.phoenix.batch.job.emaildetails.EmailDetailsRepo;
import nl.yestelecom.phoenix.batch.job.util.ArchiveFileCreatorUtil;
import nl.yestelecom.phoenix.batch.sender.SenderVisitor;
import nl.yestelecom.phoenix.batch.writer.WriteVisitor;

@Service
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

	public void read() {
		logger.info("Read : "+getJobName());
		creditControl = creditControlRepository.findAll();
		emailDetails = emailDetailsRepo.getEmailDetailsForJob(getJobName());
	}

	public void process() {
		logger.info("Process : "+getJobName());
		ccList = new ArrayList<String>();
		for (CreditControl cc : creditControl) {
			ccList.add(cc.toString());
		}
	}

	public void write() {
		logger.info("Write : "+getJobName());
		creditControlcsvWriter.setRowList(ccList);
		creditControlcsvWriter.accept(writerVisitorImpl);
	}

	public void send() {
		logger.info("Send : "+getJobName());
		creditControlEmailSender.setEmailDetails(emailDetails);
		creditControlEmailSender.accept(senderVisitor);
		creditControlFTPSender.accept(senderVisitor);
	}

	@Override
	public void postProcess() {
		logger.info("Post Process : "+getJobName());
		archiveFileCreator.setFileDirecotry(fileDirecotry);
		archiveFileCreator.archiveCurrentFile();;
		
	}

	@Override
	public String getJobName() {
		return jobName;
		
	}

}
