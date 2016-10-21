package nl.yestelecom.phoenix.batch.job.simoverview;

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
import nl.yestelecom.phoenix.batch.job.simoverview.model.DealerHeadQuarters;
import nl.yestelecom.phoenix.batch.job.simoverview.model.SimOverview;
import nl.yestelecom.phoenix.batch.job.simoverview.model.SimTypeCount;
import nl.yestelecom.phoenix.batch.job.simoverview.repo.DealerHeadQuartersRepository;
import nl.yestelecom.phoenix.batch.job.simoverview.repo.SimTypeCountRepository;
import nl.yestelecom.phoenix.batch.job.util.ArchiveFileCreator;
import nl.yestelecom.phoenix.batch.sender.SenderVisitor;
import nl.yestelecom.phoenix.batch.writer.WriteVisitor;

@Service
public class SimOverviewProcess implements JobProcessor{
	private static Logger logger = LoggerFactory.getLogger(SimOverviewProcess.class);

	@Autowired
	SimTypeCountRepository simTypeCountRepository;
	
	@Autowired
	DealerHeadQuartersRepository dealerHeadQuartersRepository;
	
	@Autowired
	SimOverviewFileFormat simOverviewFileFormat;
	
	@Autowired
	SimOverviewCSVWriter simOverviewCSVWriter;
	
	@Autowired
	WriteVisitor writerVisitorImpl;
	
	@Autowired
	SimOverviewEmailSender simOverviewEmailSender;
	
	@Autowired
	SimOverviewHelper simOverviewHelper;
	
	@Autowired
	EmailDetailsRepo emailDetailsRepo;
	@Autowired
	ArchiveFileCreator archiveFileCreator;
	
	@Autowired
	SenderVisitor senderVisitor;
	
	@Value("${simoverview.filepath}")
	private String fileDirecotry;
	
	List<SimTypeCount> simTypeCount ;
	List<DealerHeadQuarters> dealerHQ;
	List<SimTypeCount> yestelList;
	List<Object[]> busPartnerList;
	List<SimOverview> simOvewviewDataList;
	EmailDetails emailDetails;
	
	@Override
	public void read() {
		logger.info("Read : "+getJobName());
		
		yestelList = simTypeCountRepository.getCountForYesTel();
		System.out.println("size is >> "+yestelList.size());
		
		busPartnerList = simTypeCountRepository.getBusinessPartenerCount();
		
		
		dealerHQ  = dealerHeadQuartersRepository.getDealersandMainDealers();
		System.out.println("size is >> "+dealerHQ.get(0).getDlrId()+" >> "+dealerHQ.get(0).getDealerName());
		
		emailDetails = emailDetailsRepo.getEmailDetailsForJob(getJobName());
		
	}

	@Override
	public void process() {
		logger.info("Process : "+getJobName());
		processYesTelList();
	}

	private void processYesTelList() {
		logger.info("Processeing YesTel List");
		simOvewviewDataList = new ArrayList<SimOverview>();
		SimOverview simOverviewYesTel = createYesTelSimCount();
		SimOverview simOverviewbusPartner = createBusTelSimCount();
		simOvewviewDataList.add(simOverviewYesTel);
		simOvewviewDataList.add(simOverviewbusPartner);
		for(DealerHeadQuarters delear : dealerHQ){
			SimOverview simOverview = new SimOverview();
			simOverview.setDealerName(delear.getDealerName());
			simOverview.setMainDealerName(delear.getMainDealerName());
			addSimTypeCount(delear, simOverview);
			Long grossStock = simTypeCountRepository.getTotalSimxCountForDelaer(delear.getDlrId());
			simOverview.setGrossStock(grossStock);
		}
	}

	private SimOverview createBusTelSimCount() {
		logger.info("Processeing BusTel List");
		SimOverview simOverview = new SimOverview();
	
		Long grossStock = new Long(0);
		for (Object[] bp : busPartnerList) {
			SimTypeCount simTypeCount = new SimTypeCount();
			simTypeCount.setSimType(bp[0].toString());
			simTypeCount.setCount(Long.valueOf(bp[1].toString()));
			simOverview = simOverviewHelper.buildSimOverview(simTypeCount, simOverview);
			if(SimOverviewConstants.BENODIGD.equals(simTypeCount.getSimType())){
				simOverview.setNecessary(simTypeCount.getCount());
			}
		}
		grossStock = simOverview.getY32KCount() + simOverview.getUSIMCount() + simOverview.getUSIMDUOCount();
		simOverview.setGrossStock(grossStock);
		simOverview.setNetStock(simOverview.getGrossStock()-simOverview.getNecessary());
		simOverview.setMainDealerName("");
		simOverview.setDealerName("Totaal bij Business Partners");
		return simOverview;
	}

	private SimOverview createYesTelSimCount() {
		SimOverview simOverview = new SimOverview();
		simOverview.setMainDealerName("");
		simOverview.setDealerName("YES SERVICE PROVIDER");
		for(SimTypeCount yesTel : yestelList){
			simOverview = simOverviewHelper.buildSimOverview(yesTel, simOverview);
			
		}
		
		return simOverview;
		
	}

	private void addSimTypeCount(DealerHeadQuarters delear, SimOverview simOverview ) {
		List<SimTypeCount> simTypeCount = simTypeCountRepository.getTypeCount(delear.getDlrId());
		for(SimTypeCount simType : simTypeCount){
			if(delear.getDlrId().equals(simType.getDlrId())){
				simOverview = simOverviewHelper.buildSimOverview(simType, simOverview);
			}
		}
		simOvewviewDataList.add(simOverview);
		
	}

	@Override
	public void write() {
		logger.info("Write : "+getJobName());
		String header = simOverviewFileFormat.createHeader();
		simOverviewCSVWriter.setHeader(header);
		List<String> simOverviewStringData = simOverviewHelper.simOverviewStringData(simOvewviewDataList);
		simOverviewCSVWriter.setRowList(simOverviewStringData);
		simOverviewCSVWriter.accept(writerVisitorImpl);
		
		
	}

	@Override
	public void send() {
		logger.info("Send : "+getJobName());
		simOverviewEmailSender.setEmailDetails(emailDetails);
		simOverviewEmailSender.accept(senderVisitor);
		
	}

	@Override
	public void postProcess() {
		logger.info("Post Process : "+getJobName());
		archiveFileCreator.createArchiveFile(fileDirecotry);
		
	}

	@Override
	public String getJobName() {
		String jobName = "SIM_OVERVIEW";
		return jobName;
		// TODO Auto-generated method stub
		
	}

}
