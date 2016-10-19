package nl.yestelecom.phoenix.batch.job.simoverview;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.writer.WriteVisitor;
import nl.yestelecom.phoenix.batch.writer.csv.CsvWriter;

@Service
public class SimOverviewCSVWriter implements CsvWriter {
	
	@Value("${simoverview.filepath}")
	private String fileDirecotry;

	@Value("${simoverview.filename}")
	private String fileName;
	
	String header;
	List<String> rows;

	@Override
	public String getHeader() {
		// TODO Auto-generated method stub
		return header;
	}

	@Override
	public String getFilePath() {
		// TODO Auto-generated method stub
		return fileDirecotry;
	}

	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return fileName;
	}

	@Override
	public List<String> getRowList() {
		// TODO Auto-generated method stub
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

}
