package nl.yestelecom.phoenix.batch.writer.csv;

import java.util.List;

import nl.yestelecom.phoenix.batch.writer.WriteVisitor;
import nl.yestelecom.phoenix.batch.writer.Writer;

public interface CsvWriter extends Writer {

public  String getHeader();

public  String getFilePath();

public  String getFileName();

public  List<String> getRowList();

@Override
public  void accept(WriteVisitor visitor);

}
