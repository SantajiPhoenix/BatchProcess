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
        FileWriter fileWriter = null;
        BufferedWriter bufferWritter = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file.getAbsoluteFile());
            bufferWritter = new BufferedWriter(fileWriter);
            for (final String val : rowValue) {
                bufferWritter.write(val);
                bufferWritter.newLine();
            }

        } catch (final IOException e) {
            logger.error(e.getMessage(), e);

        } finally {
            try {
                if (null != bufferWritter) {
                    bufferWritter.close();
                }
                if (null != fileWriter) {
                    fileWriter.close();
                }
            } catch (final IOException e) {
                logger.error(e.getMessage(), e);

            }
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
