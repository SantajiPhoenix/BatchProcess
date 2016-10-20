package nl.yestelecom.phoenix.batch.job.simoverview;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.job.JobProcessor;
import nl.yestelecom.phoenix.batch.job.emaildetails.EmailDetails;
import nl.yestelecom.phoenix.batch.job.emaildetails.EmailDetailsRepo;
import nl.yestelecom.phoenix.batch.job.marketpoints.MarketPointsEmailSender;
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
		/*simTypeCount = simTypeCountRepository.getTypeCount();
		System.out.println("size is >> "+simTypeCount.get(0).getDlrId());*/
		
		yestelList = simTypeCountRepository.getCountForYesTel();
		System.out.println("size is >> "+yestelList.size());
		
		busPartnerList = simTypeCountRepository.getBusinessPartenerCount();
		
		
		dealerHQ  = dealerHeadQuartersRepository.getDealersandMainDealers();
		System.out.println("size is >> "+dealerHQ.get(0).getDlrId()+" >> "+dealerHQ.get(0).getDealerName());
		
		emailDetails = emailDetailsRepo.getEmailDetailsForJob(getJobName());
		
	}

	@Override
	public void process() {
		processYesTelList();
	}

	private void processYesTelList() {
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
		SimOverview simOverview = new SimOverview();
	///p_bp_totaal := p_bp_32k + p_bp_32kduo + p_bp_usim + p_bp_usimduo;
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
		// simOverview.getY32KDUOCount() +
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
		// TODO Auto-generated method stub
		String header = simOverviewFileFormat.createHeader();
		simOverviewCSVWriter.setHeader(header);
		List<String> simOverviewStringData = simOverviewHelper.simOverviewStringData(simOvewviewDataList);
		simOverviewCSVWriter.setRowList(simOverviewStringData);
		simOverviewCSVWriter.accept(writerVisitorImpl);
		
		
	}

	@Override
	public void send() {
		simOverviewEmailSender.setEmailDetails(emailDetails);
		simOverviewEmailSender.accept(senderVisitor);
		
	}

	@Override
	public void postProcess() {
		archiveFileCreator.createArchiveFile(fileDirecotry);
		
	}

	@Override
	public String getJobName() {
		String jobName = "SIM_OVERVIEW";
		return jobName;
		// TODO Auto-generated method stub
		
	}

}
