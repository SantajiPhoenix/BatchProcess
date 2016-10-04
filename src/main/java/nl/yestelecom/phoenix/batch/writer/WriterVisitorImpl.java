package nl.yestelecom.phoenix.batch.writer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.writer.csv.CsvWriterType;
import nl.yestelecom.phoenix.batch.writer.csv.CsvWriterUtil;
import nl.yestelecom.phoenix.batch.writer.xml.XMLWriterType;

@Service
public class WriterVisitorImpl implements WriteVisitor {
	@Autowired
	CsvWriterUtil csvFileWriter;

	public void writeContent(CsvWriterType csv) {
		csvFileWriter.setFileProperties(csv.getFileName(), csv.getFilePath());
		csvFileWriter.setHeader(csv.getHeader());
		csvFileWriter.setListRowValue(csv.getRowList());
		csvFileWriter.write();
	}

	public void writeContent(XMLWriterType xml) {
		csvFileWriter.setFileProperties(xml.getFileName(), xml.getFilePath());
		csvFileWriter.write();
	}

}
