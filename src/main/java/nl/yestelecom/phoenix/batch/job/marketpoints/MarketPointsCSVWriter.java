package nl.yestelecom.phoenix.batch.job.marketpoints;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.writer.WriteVisitor;
import nl.yestelecom.phoenix.batch.writer.csv.CsvWriterType;

@Service
public class MarketPointsCSVWriter implements CsvWriterType {

	@Value("${mail.file.directory}")
	private String fileDirecotry;

	@Value("${marketpoints.incentive1}")
	private String incentive1FileName;

	List<String> rows;
	String header;
	String fileName;

	@Override
	public String getHeader() {
		// TODO Auto-generated method stub
		return header;
	}

	@Override
	public String getFilePath() {
		return fileDirecotry;
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
	
	public void setHeader(String header){
		this.header = header;
	}
	
	public void setFilename(String filename){
		this.fileName = filename;
	}

}
