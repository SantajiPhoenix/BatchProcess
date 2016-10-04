package nl.yestelecom.phoenix.batch.job.creditcontrol;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.job.JobProcessor;
import nl.yestelecom.phoenix.batch.writer.WriteVisitor;

@Service
public class CreditControlProcess implements JobProcessor {

	@Autowired
	CreditControlCSVWriter creditControlcsvWriter;

	@Autowired
	WriteVisitor writerVisitorImpl;

	@Autowired
	CreditControlRepository creditControlRepository;

	List<CreditControl> creditControl;

	List<String> ccList;

	public void read() {
		creditControl = creditControlRepository.findAll();
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

	}

	// public void send() {
	// creditControlFTPConfig.sendContent();
	// }

}
