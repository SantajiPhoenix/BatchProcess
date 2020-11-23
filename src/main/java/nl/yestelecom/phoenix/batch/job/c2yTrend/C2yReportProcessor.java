package nl.yestelecom.phoenix.batch.job.c2yTrend;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.job.JobProcessor;
import nl.yestelecom.phoenix.batch.job.c2yTrend.model.C2yReport;
import nl.yestelecom.phoenix.batch.job.c2yTrend.model.ConnectionList;
import nl.yestelecom.phoenix.batch.job.c2yTrend.model.FutureOutport;
import nl.yestelecom.phoenix.batch.job.c2yTrend.model.FutureTerimination;
import nl.yestelecom.phoenix.batch.job.c2yTrend.model.TodaysTrend;
import nl.yestelecom.phoenix.batch.job.c2yTrend.repo.C2yReportRepository;
import nl.yestelecom.phoenix.batch.job.c2yTrend.repo.ConnectionListRepo;
import nl.yestelecom.phoenix.batch.job.c2yTrend.repo.FutureOutportRepo;
import nl.yestelecom.phoenix.batch.job.c2yTrend.repo.FutureTeriminationRepo;
import nl.yestelecom.phoenix.batch.job.c2yTrend.repo.TodaysTrendRepo;
import nl.yestelecom.phoenix.batch.job.emaildetails.EmailDetails;
import nl.yestelecom.phoenix.batch.job.emaildetails.EmailDetailsRepo;
import nl.yestelecom.phoenix.batch.sender.SenderVisitor;

@Service
@Transactional
public class C2yReportProcessor implements JobProcessor {
    private static Logger logger = LoggerFactory.getLogger(C2yReportProcessor.class);
    @Autowired
    C2yReportRepository c2yReportRepository;
    @Autowired
    FutureTeriminationRepo futureTeriminationRepo;
    @Autowired
    ConnectionListRepo connectionListRepo;
    @Autowired
    FutureOutportRepo futureOutportRepo;
    @Autowired
    TodaysTrendRepo todaysTrendRepo;
    @Autowired
    GenerateExcelForC2yReport generateExcelForC2yReport;
    @Autowired
    C2ytrendEmailSender c2ytrendEmailSender;

    @Autowired
    SenderVisitor senderVisitor;
    @Autowired
    EmailDetailsRepo emailDetailsRepo;
    EmailDetails emailDetails;
    List<C2yReport> c2yTrends;
    List<FutureTerimination> findfutureTerminationlist;
    List<FutureOutport> findfutureOutportlist;
    List<ConnectionList> findTypeOfConnectionCountList;
    TodaysTrend findTodaysTrend;
    @Value("${c2yreport.jobname}")
    private String jobName;

    @Override
    public void read() {

        logger.info("Read : " + getJobName());

        emailDetails = emailDetailsRepo.getEmailDetailsForJob(getJobName());
        c2yTrends = c2yReportRepository.findc2ywithid();
        findfutureTerminationlist = futureTeriminationRepo.findfutureTermination();
        findfutureOutportlist = futureOutportRepo.findfutureOutport();
        findTypeOfConnectionCountList = connectionListRepo.findTypeOfConnectionCount();
        findTodaysTrend = todaysTrendRepo.findTodaysTrend();

    }

    @Override
    public void process() {
        // TODO Auto-generated method stub

    }

    @Override
    public void write() {
        generateExcelForC2yReport.generateExcel(c2yTrends, findfutureTerminationlist, findfutureOutportlist, findTypeOfConnectionCountList, findTodaysTrend);
    }

    @Override
    public void send() {
        logger.info("Send : " + getJobName());
        c2ytrendEmailSender.setEmailDetails(emailDetails);
        c2ytrendEmailSender.accept(senderVisitor);
    }

    @Override
    public void postProcess() {
        // TODO Auto-generated method stub

    }

    @Override
    public String getJobName() {
        // TODO Auto-generated method stub
        return jobName;
    }
}
