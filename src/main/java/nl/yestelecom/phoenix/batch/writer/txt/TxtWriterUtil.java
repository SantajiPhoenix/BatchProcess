package nl.yestelecom.phoenix.batch.writer.txt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TxtWriterUtil {
    private static Logger logger = LoggerFactory.getLogger(TxtWriterUtil.class);

    private String fileName;
    private String filePath;
    private List<String> rowValue;

    public void write() {

        final File file = new File(filePath + fileName);
        boolean fileCreated = false;
        try {
            if (!file.exists()) {
                fileCreated = file.createNewFile();
                logger.info("File created " + fileCreated);
            }
        } catch (final IOException e) {
            logger.error("Error while file creation " + e);
        }
        try (BufferedWriter bufferWritter = new BufferedWriter(new FileWriter(file.getAbsoluteFile()))) {
            for (final String val : rowValue) {
                bufferWritter.write(val);
                bufferWritter.newLine();
            }
            logger.info("Finished Write");
        } catch (final IOException e) {
            logger.error("Error while writing file >> " + e);
        }

    }

    public void setListRowValue(List<String> rowValue) {
        this.rowValue = rowValue;
    }

    public void setFileProperties(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }
}
