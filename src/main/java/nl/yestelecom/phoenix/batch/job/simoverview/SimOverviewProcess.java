package nl.yestelecom.phoenix.batch.job.simoverview;

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
import nl.yestelecom.phoenix.batch.job.simoverview.model.DealerHeadQuarters;
import nl.yestelecom.phoenix.batch.job.simoverview.model.SimOverview;
import nl.yestelecom.phoenix.batch.job.simoverview.model.SimTypeCount;
import nl.yestelecom.phoenix.batch.job.simoverview.repo.DealerHeadQuartersRepository;
import nl.yestelecom.phoenix.batch.job.simoverview.repo.SimTypeCountRepository;
import nl.yestelecom.phoenix.batch.sender.SenderVisitor;
import nl.yestelecom.phoenix.batch.writer.WriteVisitor;

@Service
public class SimOverviewProcess implements JobProcessor {
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
    ArchiveFileCreatorUtil archiveFileCreator;

    @Autowired
    SenderVisitor senderVisitor;

    @Value("${simoverview.jobname}")
    private String jobName;
    @Value("${simoverview.filepath}")
    private String fileDirecotry;

    List<DealerHeadQuarters> dealerHQ;
    List<SimTypeCount> yestelList;
    List<Object[]> busPartnerList;
    List<SimOverview> simOvewviewDataList;
    EmailDetails emailDetails;

    @Override
    public void read() {
        logger.info("Read : " + getJobName());

        yestelList = simTypeCountRepository.getCountForYesTel();
        logger.debug("size is >> " + yestelList.size());

        busPartnerList = simTypeCountRepository.getBusinessPartenerCount();

        dealerHQ = dealerHeadQuartersRepository.getDealersandMainDealers();
        logger.debug("size is >> " + dealerHQ.get(0).getDlrId() + " >> " + dealerHQ.get(0).getDealerName());

        emailDetails = emailDetailsRepo.getEmailDetailsForJob(getJobName());

    }

    @Override
    public void process() {
        logger.info("Process : " + getJobName());
        processYesTelList();
    }

    private void processYesTelList() {
        logger.info("Processeing YesTel List");
        simOvewviewDataList = new ArrayList<>();
        final SimOverview simOverviewYesTel = createYesTelSimCount();
        final SimOverview simOverviewbusPartner = createBusTelSimCount();
        simOvewviewDataList.add(simOverviewYesTel);
        simOvewviewDataList.add(simOverviewbusPartner);
        for (final DealerHeadQuarters delear : dealerHQ) {
            final SimOverview simOverview = new SimOverview();
            simOverview.setDealerName(delear.getDealerName());
            simOverview.setMainDealerName(delear.getMainDealerName());
            addSimTypeCount(delear, simOverview);
            final Long grossStock = simTypeCountRepository.getTotalSimxCountForDelaer(delear.getDlrId());
            simOverview.setGrossStock(grossStock);
        }
    }

    private SimOverview createBusTelSimCount() {
        logger.info("Processeing BusTel List");
        SimOverview simOverview = new SimOverview();

        Long grossStock;
        for (final Object[] bp : busPartnerList) {
            final SimTypeCount simTypeCountObj = new SimTypeCount();
            simTypeCountObj.setSimType(bp[0].toString());
            simTypeCountObj.setCount(Long.valueOf(bp[1].toString()));
            simOverview = simOverviewHelper.buildSimOverview(simTypeCountObj, simOverview);
            if (SimOverviewConstants.BENODIGD.equals(simTypeCountObj.getSimType())) {
                simOverview.setNecessary(simTypeCountObj.getCount());
            }
        }
        grossStock = simOverview.getY32KCount() + simOverview.getUSIMCount() + simOverview.getUSIMDUOCount();
        simOverview.setGrossStock(grossStock);
        simOverview.setNetStock(simOverview.getGrossStock() - simOverview.getNecessary());
        simOverview.setMainDealerName("");
        simOverview.setDealerName("Totaal bij Business Partners");
        return simOverview;
    }

    private SimOverview createYesTelSimCount() {
        SimOverview simOverview = new SimOverview();
        simOverview.setMainDealerName("");
        simOverview.setDealerName("YES SERVICE PROVIDER");
        for (final SimTypeCount yesTel : yestelList) {
            simOverview = simOverviewHelper.buildSimOverview(yesTel, simOverview);

        }

        return simOverview;

    }

    private void addSimTypeCount(DealerHeadQuarters delear, SimOverview simOverview) {
        final List<SimTypeCount> simTypeCount = simTypeCountRepository.getTypeCount(delear.getDlrId());
        for (final SimTypeCount simType : simTypeCount) {
            if (delear.getDlrId().equals(simType.getDlrId())) {
                simOverview = simOverviewHelper.buildSimOverview(simType, simOverview);
            }
        }
        simOvewviewDataList.add(simOverview);

    }

    @Override
    public void write() {
        logger.info("Write : " + getJobName());
        final String header = simOverviewFileFormat.createHeader();
        simOverviewCSVWriter.setHeader(header);
        final List<String> simOverviewStringData = simOverviewHelper.simOverviewStringData(simOvewviewDataList);
        simOverviewCSVWriter.setRowList(simOverviewStringData);
        simOverviewCSVWriter.accept(writerVisitorImpl);

    }

    @Override
    public void send() {
        logger.info("Send : " + getJobName());
        simOverviewEmailSender.setEmailDetails(emailDetails);
        simOverviewEmailSender.accept(senderVisitor);

    }

    @Override
    public void postProcess() {
        logger.info("Post Process : " + getJobName());
        archiveFileCreator.setFileDirecotry(fileDirecotry);
        archiveFileCreator.archiveCurrentFile();

    }

    @Override
    public String getJobName() {
        return jobName;

    }

}
