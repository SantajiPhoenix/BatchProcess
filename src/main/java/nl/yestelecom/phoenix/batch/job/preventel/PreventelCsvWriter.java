package nl.yestelecom.phoenix.batch.job.preventel;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.writer.WriteVisitor;
import nl.yestelecom.phoenix.batch.writer.csv.CsvWriter;

@Service
public class PreventelCsvWriter implements CsvWriter {

	@Value("${preventel.fileName}")
	private String fileName;
	@Value("${preventel.filePath}")
	private String filePath;
	private List<String> rowList;
	private String header;

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
		return rowList;
	}

	public void setFilename(String fileName) {
		this.fileName = fileName;
	}

	public void setRowList(List<String> rowList) {
		this.rowList = rowList;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	@Override
	public void accept(WriteVisitor visitor) {
		visitor.writeContent(this);

	}

}
