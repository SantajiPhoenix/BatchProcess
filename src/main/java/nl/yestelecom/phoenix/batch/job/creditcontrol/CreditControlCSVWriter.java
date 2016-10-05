package nl.yestelecom.phoenix.batch.job.creditcontrol;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.writer.WriteVisitor;
import nl.yestelecom.phoenix.batch.writer.csv.CsvWriter;

@Service
//public class CreditControlCSVWriter extends CsvWriterType {
public class CreditControlCSVWriter implements CsvWriter {
	

	@Value("${creditcontrol.fileName}")
	private String fileName;
	@Value("${creditcontrol.filePath}")
	private String filePath;
	@Value("${creditcontrol.csv.header}")
	private String header;

	List<String> rows;

	@Override
	public String getHeader() {
		return header;
	}

	@Override
	public String getFilePath() {
		return filePath;
	}

	@Override
	public String getFileName() {
		return fileName;
	}

	@Override
	public List<String> getRowList() {
		return rows;
	}

	public void setRowList(List<String> rows) {
		this.rows = rows;
	}
	
	@Override
	public void accept(WriteVisitor visitor) {
		visitor.writeContent(this);
	}

}
