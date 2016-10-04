package nl.yestelecom.phoenix.batch.writer.csv;

import java.util.List;

import nl.yestelecom.phoenix.batch.writer.WriteVisitor;
import nl.yestelecom.phoenix.batch.writer.Writer;

public interface CsvWriterType extends Writer {

public abstract String getHeader();

public abstract String getFilePath();

public abstract String getFileName();

public abstract List<String> getRowList();

public abstract void accept(WriteVisitor visitor);

}
