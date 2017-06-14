package nl.yestelecom.phoenix.batch.job.ciot;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.writer.WriteVisitor;
import nl.yestelecom.phoenix.batch.writer.xml.XmlWriter;

@Service
public class CiotXMLWriter implements XmlWriter {

	@Value("${ciot.fileName}")
	private String fileName;
	@Value("${ciot.filePath}")
	private String filePath;
	@Value("${ciot.templateName}")
	private String templateName;

	private Map<String, Object> xmlData;
	private String sequence;

	@Override
	public String getFilePath() {
		return filePath;
	}

	@Override
	public String getFileName() {
		return fileName + sequence + ".xml";
	}

	@Override
	public String getTemplateName() {
		return templateName;
	}

	@Override
	public Map<String, Object> getXmlData() {
		return xmlData;
	}

	public void setXmlData(Map<String, Object> xmlData) {
		this.xmlData = xmlData;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	@Override
	public void accept(WriteVisitor visitor) {
		visitor.writeContent(this);
	}

}
