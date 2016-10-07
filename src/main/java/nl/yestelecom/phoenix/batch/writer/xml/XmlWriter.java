package nl.yestelecom.phoenix.batch.writer.xml;

import java.util.Map;

import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.writer.WriteVisitor;
import nl.yestelecom.phoenix.batch.writer.Writer;

@Service
public interface XmlWriter extends Writer {

	public String getFilePath();

	public String getFileName();

	public String getTemplateName();

	public Map<String, Object> getXmlData();

	public void accept(WriteVisitor visitor);

}
