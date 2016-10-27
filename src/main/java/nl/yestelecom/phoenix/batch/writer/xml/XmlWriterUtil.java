package nl.yestelecom.phoenix.batch.writer.xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class XmlWriterUtil {
	private static Logger logger = LoggerFactory.getLogger(XmlWriterUtil.class);
	@Autowired
	@Qualifier("classPathTemplateEngineXML")
	private TemplateEngine templateEngineXML;

	private String fileName;
	private String filePath;
	private String templateName;
	private Map<String, Object> xmlData;
	private String xmlContent;

	public void generateXML() {
		logger.info("Writing file >> " + fileName);
		xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		Context ctx = new Context(LocaleContextHolder.getLocale(), xmlData);
		xmlContent += templateEngineXML.process(templateName, ctx);
	}

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
			bufferWritter.write(xmlContent);
			logger.info("Finished Write");
		} catch (IOException e) {
			logger.error("Error while writing file >> " + e);
		} finally {
			try {
				bufferWritter.close();
				fileWriter.close();
			} catch (IOException e) {
				logger.error("Error while closing buffer writers >> " + e);
			}
		}
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public void setXmlData(Map<String, Object> xmlData) {
		this.xmlData = xmlData;
	}

	public void setFileProperties(String fileName, String filePath) {
		this.fileName = fileName;
		this.filePath = filePath;
	}
}
