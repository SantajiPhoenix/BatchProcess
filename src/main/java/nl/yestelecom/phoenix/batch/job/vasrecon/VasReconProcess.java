package nl.yestelecom.phoenix.batch.job.vasrecon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.job.JobProcessor;
import nl.yestelecom.phoenix.batch.job.emaildetails.EmailDetails;
import nl.yestelecom.phoenix.batch.job.emaildetails.EmailDetailsRepo;
import nl.yestelecom.phoenix.batch.job.vasrecon.model.VasReconData;
import nl.yestelecom.phoenix.batch.job.vasrecon.model.VasReconPriceView;
import nl.yestelecom.phoenix.batch.job.vasrecon.model.VasReconProductsView;
import nl.yestelecom.phoenix.batch.job.vasrecon.repo.VasReconDataRepository;
import nl.yestelecom.phoenix.batch.job.vasrecon.repo.VasReconPriceViewRepo;
import nl.yestelecom.phoenix.batch.job.vasrecon.repo.VasReconRepository;
import nl.yestelecom.phoenix.batch.sender.SenderVisitor;

@Service
@Transactional
public class VasReconProcess implements JobProcessor {
    private static Logger logger = LoggerFactory.getLogger(VasReconProcess.class);

    @Autowired
    VasReconRepository vasReconRepository;
    @Autowired
    VasReconDataRepository vasReconDataRepository;
    @Autowired
    VasReconPriceViewRepo vasReconPriceViewRepo;
    @Autowired
    GenerateExcelForVasRecon generateExcelForVasRecon;
    @Autowired
    VasReconEmailSender vasReconEmailSender;
    @Autowired
    SenderVisitor senderVisitor;

    @Autowired
    EmailDetailsRepo emailDetailsRepo;

    @Value("${vasrecon.jobname}")
    private String jobName;

    List<VasReconProductsView> vasReconProductsView;
    List<VasReconPriceView> vasPriceReconView;
    List<VasReconData> c2yList;
    List<VasReconData> zygoList;
    List<VasReconData> priceList;
    EmailDetails emailDetails;

    @Override
    public void read() {
        logger.info("Read : " + getJobName());

        emailDetails = emailDetailsRepo.getEmailDetailsForJob(getJobName());
        vasReconProductsView = vasReconRepository.findAll();
        vasPriceReconView = vasReconPriceViewRepo.findAll();
    }

    @Override
    public void process() {
        logger.info("Process : " + getJobName());
        c2yList = new ArrayList<>();
        zygoList = new ArrayList<>();
        for (final VasReconProductsView reconProductView : vasReconProductsView) {
            logger.info("***reconProductView info " + reconProductView);
            if (null != reconProductView) {
                if ("C2Y".equals(reconProductView.getSource())) {
                    logger.info("In if");
                    final VasReconData vasReconData = processSkelRecord(reconProductView);

                    if (vasReconData.getGssId() != null) {
                        logger.info("In add");
                        c2yList.add(vasReconData);
                    }
                } else if ("ZYGO".equals(reconProductView.getSource())) {
                    final VasReconData vasReconData = processZygoRecord(reconProductView);
                    zygoList.add(vasReconData);
                }
            }
        }
        processPriceChanges();

    }

    private void processPriceChanges() {
        logger.info("Process Price differences");
        priceList = new ArrayList<>();
        for (final VasReconPriceView priceChange : vasPriceReconView) {
            final VasReconData priceChangeToSave = new VasReconData();
            priceChangeToSave.setAction("CHANGE");
            priceChangeToSave.setGssId(priceChange.getGssId());
            priceChangeToSave.setGssProdId(null);
            priceChangeToSave.setC2yCode(priceChange.getCode());
            priceChangeToSave.setStatus(null);
            priceChangeToSave.setSource("ZYGO");
            priceChangeToSave.setRetry(null);
            priceChangeToSave.setAmountBilled(priceChange.getAmountBilled());
            priceChangeToSave.setPlannedAMount(priceChange.getPlannedAMount());
            priceChangeToSave.setC2yPrice(priceChange.getC2yPrice());
            priceChangeToSave.setConfigAmount(priceChange.getConfigAmount());
            priceList.add(priceChangeToSave);
        }

    }

    private VasReconData processSkelRecord(VasReconProductsView vasReconDataView) {
        logger.info("In processSkelRecord");
        final VasReconData vasReconData = new VasReconData();
        vasReconData.setAction("TOEV");
        vasReconData.setGssId(vasReconDataView.getGssId());
        vasReconData.setGssProdId(vasReconDataView.getGssProdId());
        vasReconData.setC2yCode(vasReconDataView.getCode());
        vasReconData.setStatus(null);
        vasReconData.setSource("SKEL");
        vasReconData.setRetry(null);
        if ("DATA".equals(vasReconDataView.getVasCode())) {
            vasReconData.setExecutionTime(vasReconDataView.getStartDateContractData());
        } else if ("VOICE".equals(vasReconDataView.getVasCode())) {
            vasReconData.setExecutionTime(vasReconDataView.getStartDateContract());
        } else {
            vasReconData.setExecutionTime(vasReconDataView.getBeginDateValid());
        }
        return vasReconData;
    }

    private VasReconData processZygoRecord(VasReconProductsView vasReconDataView) {
        logger.info("In processZygoRecord");
        final VasReconData vasReconData = new VasReconData();
        vasReconData.setAction("BEIN");
        vasReconData.setGssId(vasReconDataView.getGssId());
        vasReconData.setGssProdId(vasReconDataView.getGssProdId());
        vasReconData.setC2yCode(vasReconDataView.getCode());
        vasReconData.setStatus(null);
        vasReconData.setSource("ZYGO");
        vasReconData.setRetry(null);
        return vasReconData;
    }

    @Override
    public void write() {

        if (null != zygoList || null != c2yList || null != priceList) {
            vasReconDataRepository.deleteAllInBatch();
        }
        logger.info("Write : " + getJobName());
        vasReconDataRepository.saveAll(zygoList);
        vasReconDataRepository.saveAll(c2yList);
        vasReconDataRepository.saveAll(priceList);
        try {
            generateExcelForVasRecon.generateExcel();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send() {
        logger.info("Send : " + getJobName());
        vasReconEmailSender.setEmailDetails(emailDetails);
        vasReconEmailSender.accept(senderVisitor);
    }

    @Override
    public void postProcess() {

        /*
         * To Implemented in sub clasess
         */
    }

    @Override
    public String getJobName() {
        return jobName;

    }

}
