package nl.yestelecom.phoenix.batch.writer.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.opencsv.CSVWriter;

@Service
public class CsvWriterUtil {

	String header;
	List<String> rowValue;
	private String fileName;
	private String filePath;
	private char delimiter = ';';

	public void write() {
		writeCSVData();
	}

	public void writeCSVData() {
		CSVWriter writer;
		try {
			writer = new CSVWriter(new FileWriter(filePath+fileName), delimiter);
			String[] head = header.split(",");
			writer.writeNext(head);
			for (String val : rowValue) {
				String[] entries = val.split(",");
				writer.writeNext(entries);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
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
