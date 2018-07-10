package nl.yestelecom.phoenix.batch.job.preventel;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.job.JobProcessor;
import nl.yestelecom.phoenix.batch.writer.WriteVisitor;

@Service
public class PreventelProcess implements JobProcessor {

    private static Logger logger = LoggerFactory.getLogger(PreventelProcess.class);

    @Autowired
    private PreventelRepository preventelRepo;
    @Autowired
    private PreventelCsvWriter preventelCsvWriter;
    @Autowired
    private PreventelFileFormat preventelFileFormat;
    @Autowired
    private PreventelUtil preventelUtil;
    @Autowired
    private WriteVisitor writerVisitorImpl;

    @Value("${preventel.fileName}")
    private String fileName;
    @Value("${preventel.jobname}")
    private String jobName;
    @Value("${preventel.filePath}")
    private String fileDirecotry;

    @Value("${preventel.column}")
    private String cloumnNames;

    private List<Preventel> preventelList;
    private String sequence;

    List<String> preventelDataToWrite;

    @Override
    public void read() {
        logger.info("Read : " + getJobName());
        preventelList = preventelRepo.findAll();
        logger.info("Feteched >> " + preventelList.size() + "  records ");

    }

    @Override
    public void process() {
        logger.info("Process : " + getJobName());

        preventelDataToWrite = preventelFileFormat.formatPreventelData(preventelList);
        sequence = preventelUtil.getDate() + "V1.csv";

    }

    @Override
    public void write() {
        logger.info("Write : " + getJobName());
        preventelCsvWriter.setFilename(fileName + sequence);
        preventelCsvWriter.setHeader(cloumnNames);
        preventelCsvWriter.setRowList(preventelDataToWrite);
        preventelCsvWriter.accept(writerVisitorImpl);

    }

    @Override
    public void send() {
    }

    @Override
    public void postProcess() {
    }

    @Override
    public String getJobName() {
        return jobName;
    }

}
