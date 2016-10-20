package nl.yestelecom.phoenix.batch.job.marketpoints;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.job.JobProcessor;
import nl.yestelecom.phoenix.batch.job.emaildetails.EmailDetails;
import nl.yestelecom.phoenix.batch.job.emaildetails.EmailDetailsRepo;
import nl.yestelecom.phoenix.batch.job.marketpoints.model.MarketPoints;
import nl.yestelecom.phoenix.batch.job.marketpoints.model.MarketPointsTotal;
import nl.yestelecom.phoenix.batch.job.marketpoints.repo.MarketPointsRepository;
import nl.yestelecom.phoenix.batch.job.marketpoints.repo.MarketPointsTotalRepository;
import nl.yestelecom.phoenix.batch.job.util.ArchiveFileCreator;
import nl.yestelecom.phoenix.batch.sender.SenderVisitor;
import nl.yestelecom.phoenix.batch.writer.WriteVisitor;

@Service
@Configuration
public class MarketPointsProcess implements JobProcessor {

	@Autowired
	MarketPointsRepository marketPointsRepository;

	@Autowired
	MarketPointsTotalRepository marketingPointsTotalRepository;

	@Autowired
	MarketPointsFileFormat marketPointsFileFormat;

	@Autowired
	MarketPointsCSVWriter marketPointsCSVWriter;

	@Autowired
	WriteVisitor writerVisitorImpl;
	
	@Autowired
	MarketPointsEmailSender marketPointsEmailSender;
	
	@Autowired
	SenderVisitor senderVisitor;
	
	@Autowired
	ArchiveFileCreator archiveFileCreator;
	
	@Autowired
	EmailDetailsRepo emailDetailsRepo;

	@Value("${marketpoints.incentive1}")
	private String incentive1FileName;
	
	@Value("${marketpoints.incentive1.total}")
	private String incentive1TotalFileName;

	@Value("${marketpoints.incentive2.total}")
	private String incentive2TotalFileName;

	@Value("${marketpoints.incentive2.contract}")
	private String incentive2contractFileName;

	@Value("${marketpoints.incentive1+2.contract}")
	private String incentive1and2contractFileName;

	@Value("${marketpoints.incentive1+2.total}")
	private String incentive1and2totaalFileName;

	@Value("${mail.file.directory}")
	private String fileDirecotry;
	
	@Value("${marketpoints.contract.column.names}")
	private String contractColumns;

	@Value("${marketpoints.totaal.column.names}")
	private String totalColumns;


	List<MarketPoints> marketPointsInc1;
	List<MarketPoints> marketPointsInc2;
	List<MarketPointsTotal> marketPointsInc1Totaal;
	List<MarketPointsTotal> marketPointsInc2Totaal;
	List<Object[]> marketPointsInc1and2;
	List<Object[]> marketPointsInc1and2Totaal;
	
	List<String> marketPointsInc1ToWrite;
	List<String> marketPointsInc2ToWrite;
	List<String> marketPointsInc1TotaalToWrite;
	List<String> marketPointsInc2TotaalToWrite;
	List<String> marketPointsInc1and2ToWrite;
	List<String> marketPointsInc1and2TotaalToWrite;
	EmailDetails emailDetails;

	@Override
	public void read() {
		marketPointsInc1 = marketPointsRepository.getViewPointsPerContract(new Long(1));
		marketPointsInc2 = marketPointsRepository.getViewPointsPerContract(new Long(2));
		marketPointsInc1Totaal = marketingPointsTotalRepository.getViewPointsTotal(new Long(1));
		marketPointsInc2Totaal = marketingPointsTotalRepository.getViewPointsTotal(new Long(2));
		marketPointsInc1and2 = marketPointsRepository.getViewPointsPerContractMerged();
		marketPointsInc1and2Totaal = marketingPointsTotalRepository.getMergedViewPointsTotal();
		emailDetails = emailDetailsRepo.getEmailDetailsForJob(getJobName());

	}

	@Override
	public void process() {
		// TODO Auto-generated method stub
		marketPointsInc1ToWrite = marketPointsFileFormat.formatMarketPointsContractFileData(marketPointsInc1);
		marketPointsInc2ToWrite = marketPointsFileFormat.formatMarketPointsContractFileData(marketPointsInc2);
		marketPointsInc1TotaalToWrite = marketPointsFileFormat.formatMarketPointsTotaalFileData(marketPointsInc1Totaal);
		marketPointsInc2TotaalToWrite = marketPointsFileFormat.formatMarketPointsTotaalFileData(marketPointsInc2Totaal);
		marketPointsInc1and2ToWrite = marketPointsFileFormat.formatMarketPointsMergedData(marketPointsInc1and2);
		marketPointsInc1and2TotaalToWrite =  marketPointsFileFormat.formatMarketPointsMergedData(marketPointsInc1and2Totaal);
	}

	@Override
	public void write() {
		marketPointsCSVWriter.setRowList(marketPointsInc1ToWrite);
		marketPointsCSVWriter.setHeader(contractColumns);
		marketPointsCSVWriter.setFilename(incentive1FileName);
		marketPointsCSVWriter.accept(writerVisitorImpl);
		
		marketPointsCSVWriter.setRowList(marketPointsInc2ToWrite);
		marketPointsCSVWriter.setFilename(incentive2contractFileName);
		marketPointsCSVWriter.accept(writerVisitorImpl);
		
		marketPointsCSVWriter.setRowList(marketPointsInc1and2ToWrite);
		marketPointsCSVWriter.setFilename(incentive1and2contractFileName);
		marketPointsCSVWriter.accept(writerVisitorImpl);
		
		marketPointsCSVWriter.setRowList(marketPointsInc1TotaalToWrite);
		marketPointsCSVWriter.setHeader(totalColumns);
		marketPointsCSVWriter.setFilename(incentive1TotalFileName);
		marketPointsCSVWriter.accept(writerVisitorImpl);
		
		marketPointsCSVWriter.setRowList(marketPointsInc2TotaalToWrite);
		marketPointsCSVWriter.setFilename(incentive2TotalFileName);
		marketPointsCSVWriter.accept(writerVisitorImpl);
		
		marketPointsCSVWriter.setRowList(marketPointsInc1and2TotaalToWrite);
		marketPointsCSVWriter.setFilename(incentive1and2totaalFileName);
		marketPointsCSVWriter.accept(writerVisitorImpl);
		
		

	}

	@Override
	public void send() {
		// TODO Auto-generated method stub
		marketPointsEmailSender.setEmailDetails(emailDetails);
		marketPointsEmailSender.accept(senderVisitor);
		
		
	}
	
	@Override
	public void postProcess(){
		archiveFileCreator.createArchiveFile(fileDirecotry);
	}

	@Override
	public String getJobName() {
		String jobName = "MARKET_POINTS";
		return jobName;
	}

}
