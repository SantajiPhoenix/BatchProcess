package nl.yestelecom.phoenix.batch.writer;

import nl.yestelecom.phoenix.batch.writer.csv.CsvWriter;
import nl.yestelecom.phoenix.batch.writer.xml.XMLWriter;

public interface WriteVisitor {

	void writeContent(CsvWriter csv);

	void writeContent(XMLWriter xml);

}
