package nl.yestelecom.phoenix.batch.writer.txt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TxtWriterUtil {

	private String fileName;
	private String filePath;
	private List<String> rowValue;

	public void write() {

		File file = new File(filePath + fileName);
		FileWriter fileWriter = null;
		BufferedWriter bufferWritter = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			fileWriter = new FileWriter(file.getAbsoluteFile());
			bufferWritter = new BufferedWriter(fileWriter);
			for (String val : rowValue) {
				bufferWritter.write(val);
				bufferWritter.newLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferWritter.close();
			} catch (IOException e) {
				e.printStackTrace();
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
