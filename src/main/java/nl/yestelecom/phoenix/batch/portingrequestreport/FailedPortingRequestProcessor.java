package nl.yestelecom.phoenix.batch.portingrequestreport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.job.JobProcessor;
import nl.yestelecom.phoenix.batch.job.creditcontrol.CreditControlProcess;
import nl.yestelecom.phoenix.batch.job.emaildetails.EmailDetails;
import nl.yestelecom.phoenix.batch.job.emaildetails.EmailDetailsRepo;
import nl.yestelecom.phoenix.batch.sender.SenderVisitor;

@Service
public class FailedPortingRequestProcessor implements JobProcessor {
    private static Logger logger = LoggerFactory.getLogger(CreditControlProcess.class);

    private List<PortingRequestObject> failedPortingObjectList;
    private List<PortingRequestObject> portingObjectList;
    private EmailDetails emailDetails;
    private String mailText;

    @Autowired
    private FailedPortingRequestRepository failedPortingRequestRepository;
    @Autowired
    private EmailDetailsRepo emailDetailsRepo;
    @Autowired
    private PortingReportEmailSender portingReportEmailSender;
    @Autowired
    private SenderVisitor senderVisitor;

    @Override
    public void read() {
        logger.info("Read : " + getJobName());
        portingObjectList = failedPortingRequestRepository.fetchData();
        emailDetails = emailDetailsRepo.getEmailDetailsForJob(getJobName());
    }

    @Override
    public void process() {
        if (null != portingObjectList && !portingObjectList.isEmpty()) {
            failedPortingObjectList = new ArrayList<>();
            logger.info("Size is " + portingObjectList.size());
            final Date date = new Date();
            for (final PortingRequestObject portingRequestObject : portingObjectList) {

                if (null != portingRequestObject && date.after(portingRequestObject.getPortingDate())) {
                    failedPortingObjectList.add(portingRequestObject);
                    mailText = mailText + "\n" + portingRequestObject.toString();

                }
            }
            logger.info("Size is " + failedPortingObjectList.size());
        }
    }

    @Override
    public void write() {
    }

    @Override
    public void send() {
        logger.info("SEND");
        emailDetails.setText(mailText);
        portingReportEmailSender.setEmailDetails(emailDetails);
        portingReportEmailSender.accept(senderVisitor);

    }

    @Override
    public void postProcess() {
        logger.info("Done");

    }

    @Override
    public String getJobName() {
        return "PORTING_REPORTS";
    }

}
