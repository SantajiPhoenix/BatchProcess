package nl.yestelecom.phoenix.batch.writer.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.opencsv.CSVWriter;

@Service
public class CsvWriterUtil {

    private static Logger logger = LoggerFactory.getLogger(CsvWriterUtil.class);
    String header;
    List<String> rowValue;
    private String fileName;
    private String filePath;
    private char delimiter = ';';

    public void write() {
        logger.info("Writing file >> " + fileName);
        writeCSVData();
    }

    public void writeCSVData() {
        CSVWriter writer;
        try {
            writer = new CSVWriter(new FileWriter(filePath + fileName), delimiter);
            final String[] head = header.split(",");
            writer.writeNext(head);
            for (final String val : rowValue) {
                final String[] entries = val.split(",");
                writer.writeNext(entries);
            }
            logger.info("Finished Write");
            writer.close();
        } catch (final IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setListRowValue(List<String> rowValue) {
        this.rowValue = rowValue;
    }

    public void setDelimiter(char delimiter) {
        this.delimiter = delimiter;
    }

    public void setFileProperties(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;

    }

}
