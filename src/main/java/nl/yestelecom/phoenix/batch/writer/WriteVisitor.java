package nl.yestelecom.phoenix.batch.writer;

import nl.yestelecom.phoenix.batch.writer.csv.CsvWriterType;
import nl.yestelecom.phoenix.batch.writer.xml.XMLWriterType;

public interface WriteVisitor {

	void writeContent(CsvWriterType csv);

	void writeContent(XMLWriterType xml);

}
