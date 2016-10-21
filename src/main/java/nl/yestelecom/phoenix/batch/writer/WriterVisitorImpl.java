package nl.yestelecom.phoenix.batch.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.writer.csv.CsvWriter;
import nl.yestelecom.phoenix.batch.writer.csv.CsvWriterUtil;
import nl.yestelecom.phoenix.batch.writer.xml.XmlWriter;
import nl.yestelecom.phoenix.batch.writer.xml.XmlWriterUtil;

@Service
public class WriterVisitorImpl implements WriteVisitor {
	@Autowired
	CsvWriterUtil csvFileWriter;

	@Autowired
	private XmlWriterUtil xmlFileWriter;
	private static Logger logger = LoggerFactory.getLogger(WriterVisitorImpl.class);

	public void writeContent(CsvWriter csv) {
		logger.info("Writing CSV File");
		csvFileWriter.setFileProperties(csv.getFileName(), csv.getFilePath());
		csvFileWriter.setHeader(csv.getHeader());
		csvFileWriter.setListRowValue(csv.getRowList());
		csvFileWriter.write();
	}

	public void writeContent(XmlWriter xml) {
		logger.info("Writing XML File");
		xmlFileWriter.setFileProperties(xml.getFileName(), xml.getFilePath());
		xmlFileWriter.setXmlData(xml.getXmlData());
		xmlFileWriter.setTemplateName(xml.getTemplateName());
		xmlFileWriter.generateXML();
		xmlFileWriter.write();
	}
}
