package nl.yestelecom.phoenix.batch.writer.xml;

import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.writer.WriteVisitor;
import nl.yestelecom.phoenix.batch.writer.Writer;

@Service
public abstract class XMLWriter implements Writer {

	public void write(WriteVisitor visitor) {
		visitor.writeContent(this);
	}

}
