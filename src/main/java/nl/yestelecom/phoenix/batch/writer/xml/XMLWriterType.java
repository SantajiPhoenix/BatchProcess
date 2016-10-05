package nl.yestelecom.phoenix.batch.writer.xml;

import java.util.List;

import nl.yestelecom.phoenix.batch.writer.WriteVisitor;
import nl.yestelecom.phoenix.batch.writer.Writer;

public interface XMLWriterType extends Writer {

	public abstract String getHeader();

	public abstract String getFilePath();

	public abstract String getFileName();

	public abstract List<String> getRowList();

	public void write(WriteVisitor visitor);

}
