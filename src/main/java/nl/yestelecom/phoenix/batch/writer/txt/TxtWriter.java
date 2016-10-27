package nl.yestelecom.phoenix.batch.writer.txt;

import java.util.List;

import nl.yestelecom.phoenix.batch.writer.WriteVisitor;
import nl.yestelecom.phoenix.batch.writer.Writer;

public interface TxtWriter extends Writer {

public  String getFilePath();

public  String getFileName();

public  List<String> getRowList();

public  void accept(WriteVisitor visitor);

}
