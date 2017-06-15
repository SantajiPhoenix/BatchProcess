package nl.yestelecom.phoenix.batch.job.preventel;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.writer.WriteVisitor;
import nl.yestelecom.phoenix.batch.writer.txt.TxtWriter;

@Service
public class PreventelTxtWriter implements TxtWriter {

	@Value("${preventel.fileName}")
	private String fileName;
	@Value("${preventel.filePath}")
	private String filePath;

	private List<String> data;
	private String sequence;

	@Override
	public String getFilePath() {
		return filePath;
	}

	@Override
	public String getFileName() {
		return fileName + sequence;
	}

	@Override
	public List<String> getRowList() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	@Override
	public void accept(WriteVisitor visitor) {
		visitor.writeContent(this);
	}

}
