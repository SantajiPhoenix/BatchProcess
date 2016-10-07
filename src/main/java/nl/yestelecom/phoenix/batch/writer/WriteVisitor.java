package nl.yestelecom.phoenix.batch.writer;

import nl.yestelecom.phoenix.batch.writer.csv.CsvWriter;
import nl.yestelecom.phoenix.batch.writer.xml.XmlWriter;

public interface WriteVisitor {

	void writeContent(CsvWriter csv);

	void writeContent(XmlWriter xml);

}
