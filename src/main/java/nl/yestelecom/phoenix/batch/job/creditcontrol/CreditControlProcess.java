package nl.yestelecom.phoenix.batch.job.creditcontrol;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.job.JobProcessor;
import nl.yestelecom.phoenix.batch.job.emaildetails.EmailDetails;
import nl.yestelecom.phoenix.batch.job.emaildetails.EmailDetailsRepo;
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

	List<CreditControl> creditControl;

	List<String> ccList;
	EmailDetails emailDetails;

	public void read() {
		creditControl = creditControlRepository.findAll();
		emailDetails = emailDetailsRepo.getEmailDetailsForJob("CREDIT_CONTROL");
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

}
