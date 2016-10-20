package nl.yestelecom.phoenix.batch.job.ciot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class CiotProcess implements JobProcessor {

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
	ArchiveFileCreator archiveFileCreator;
	
	@Autowired
	EmailDetailsRepo emailDetailsRepo;
	
	@Value("${ciot.filePath}")
	private String fileDirecotry;

	private List<Ciot> ciotData;
	private String sequence;
	private Map<String, Object> xmlData = new HashMap<>();
	EmailDetails emailDetails;

	@Override
	public void read() {
		ciotData = ciotRepo.findAll();
		sequence = ciotRepo.generateFileSequence();
		emailDetails= emailDetailsRepo.getEmailDetailsForJob(getJobName());
	}

	@Override
	public void process() {
		xmlData.put("abonneelist", ciotData);
		sequence = ciotUtil.getDate() + sequence;
	}

	@Override
	public void write() {
		ciotXmlWriter.setSequence(sequence);
		ciotXmlWriter.setXmlData(xmlData);
		ciotXmlWriter.accept(writerVisitorImpl);
	}

	@Override
	public void send() {
		ciotEmailSender.setEmailDetails(emailDetails);
		ciotEmailSender.accept(senderVisitor);
		
		ciotFTPSender.setSequence(sequence);
		ciotFTPSender.accept(senderVisitor);
		
	}

	@Override
	public void postProcess() {
		archiveFileCreator.createArchiveFile(fileDirecotry);
		
	}

	@Override
	public String getJobName() {
		String jobName = "CIOT";
		return jobName;
		
	}

}
