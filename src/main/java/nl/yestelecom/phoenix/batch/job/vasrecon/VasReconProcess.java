package nl.yestelecom.phoenix.batch.job.vasrecon;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.job.JobProcessor;
import nl.yestelecom.phoenix.batch.job.vasrecon.model.VasReconData;
import nl.yestelecom.phoenix.batch.job.vasrecon.model.VasReconPriceView;
import nl.yestelecom.phoenix.batch.job.vasrecon.model.VasReconProductsView;
import nl.yestelecom.phoenix.batch.job.vasrecon.repo.VasReconDataRepository;
import nl.yestelecom.phoenix.batch.job.vasrecon.repo.VasReconPriceViewRepo;
import nl.yestelecom.phoenix.batch.job.vasrecon.repo.VasReconRepository;

@Service
public class VasReconProcess implements JobProcessor{

	@Autowired
	VasReconRepository vasReconRepository;
	@Autowired
	VasReconDataRepository vasReconDataRepository;
	@Autowired
	VasReconPriceViewRepo vasReconPriceViewRepo;
	
	List<VasReconProductsView> vasReconProductsView ;
	List<VasReconPriceView> vasPriceReconView;
	List<VasReconData> c2yList;
	List<VasReconData> zygoList;
	List<VasReconData> priceList;

	
	@Override
	public void read() {
		vasReconProductsView = vasReconRepository.findAll();
		vasPriceReconView = vasReconPriceViewRepo.findAll();
		
	}

	@Override
	public void process() {
		c2yList = new ArrayList<VasReconData>();
		zygoList = new ArrayList<VasReconData>();
		for ( int i = 0; i < 10; i++) {
			VasReconProductsView vasReconDataView = vasReconProductsView.get(i);
			if ("C2Y".equals(vasReconDataView.getSource())) {
				VasReconData vasReconData = processSkelRecord(vasReconDataView);
				if(vasReconData.getGssId() != null){
					c2yList.add(vasReconData);
				}
			}
			else if ("ZYGO".equals(vasReconDataView.getSource())) {
				VasReconData vasReconData = processZygoRecord(vasReconDataView);
				zygoList.add(vasReconData);
			}

		}
		processPriceChanges(vasPriceReconView);
		
	}

	private void processPriceChanges(List<VasReconPriceView> vasPriceReconView2) {
		priceList = new ArrayList<VasReconData>();
		for(VasReconPriceView priceChange : vasPriceReconView){
			VasReconData priceChangeToSave = new VasReconData();
			priceChangeToSave.setAction("CHANGE");
			priceChangeToSave.setGssId(priceChange.getGssId());
			priceChangeToSave.setGssProdId(null);
			priceChangeToSave.setC2yCode(priceChange.getCode());
			priceChangeToSave.setStatus(null);
			priceChangeToSave.setSource("ZYGO");
			priceChangeToSave.setRetry(null);
			priceChangeToSave.setAmountBilled(priceChange.getAmountBilled());
			priceChangeToSave.setPlannedAMount(priceChange.getPlannedAMount());
			priceChangeToSave.setC2yPrice(priceChange.getSktPrice());
			priceChangeToSave.setConfigAmount(priceChange.getConfigAmount());
			priceList.add(priceChangeToSave);
		}
	
	}

	private VasReconData processSkelRecord(VasReconProductsView vasReconDataView) {
		VasReconData vasReconData = new VasReconData();
			vasReconData.setAction("TOEV");
			vasReconData.setGssId(vasReconDataView.getGssId());
			vasReconData.setGssProdId(vasReconDataView.getGssId());
			vasReconData.setC2yCode(vasReconDataView.getCode());
			vasReconData.setStatus(null);
			vasReconData.setSource("SKEL");
			vasReconData.setRetry(null);
			if("DATA".equals(vasReconDataView.getVasCode())){
				vasReconData.setExecutionTime(vasReconDataView.getStartDateContractData());
			}
			else if("VOICE".equals(vasReconDataView.getVasCode())){
				vasReconData.setExecutionTime(vasReconDataView.getStartDateContract());
			}
			else{
				vasReconData.setExecutionTime(vasReconDataView.getBeginDateValid());
			}
			return vasReconData;
	}

	private VasReconData processZygoRecord(VasReconProductsView vasReconDataView) {
		VasReconData vasReconData = new VasReconData();
		vasReconData.setAction("BEIN");
		vasReconData.setGssId(vasReconDataView.getGssId());
		vasReconData.setGssProdId(vasReconDataView.getGssId());
		vasReconData.setC2yCode(vasReconDataView.getCode());
		vasReconData.setStatus(null);
		vasReconData.setSource("ZYGO");
		vasReconData.setRetry(null);
		return vasReconData;
	}

	@Override
	public void write() {
		// TODO Auto-generated method stub
		vasReconDataRepository.save(zygoList);
		vasReconDataRepository.save(c2yList);
		vasReconDataRepository.save(priceList);
		
	}

}
