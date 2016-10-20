package nl.yestelecom.phoenix.batch.job.creditcontrol;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.job.JobProcessor;
import nl.yestelecom.phoenix.batch.job.emaildetails.EmailDetails;
import nl.yestelecom.phoenix.batch.job.emaildetails.EmailDetailsRepo;
import nl.yestelecom.phoenix.batch.job.util.ArchiveFileCreator;
import nl.yestelecom.phoenix.batch.sender.SenderVisitor;
import nl.yestelecom.phoenix.batch.writer.WriteVisitor;

@Service
public class CreditControlProcess implements JobProcessor {

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
	ArchiveFileCreator archiveFileCreator;
	
	@Value("${creditcontrol.filePath}")
	private String fileDirecotry;

	List<CreditControl> creditControl;

	List<String> ccList;
	EmailDetails emailDetails;

	public void read() {
		creditControl = creditControlRepository.findAll();
		emailDetails = emailDetailsRepo.getEmailDetailsForJob(getJobName());
	}

	public void process() {
		ccList = new ArrayList<String>();
		for (CreditControl cc : creditControl) {
			ccList.add(cc.toString());
		}
	}

	public void write() {
		creditControlcsvWriter.setRowList(ccList);
		creditControlcsvWriter.accept(writerVisitorImpl);
	}

	public void send() {
		creditControlEmailSender.setEmailDetails(emailDetails);
		creditControlEmailSender.accept(senderVisitor);
		creditControlFTPSender.accept(senderVisitor);
	}

	@Override
	public void postProcess() {
		archiveFileCreator.createArchiveFile(fileDirecotry);
		
	}

	@Override
	public String getJobName() {
		String jobName = "CREDIT_CONTROL";
		return jobName;
		// TODO Auto-generated method stub
		
	}

}
