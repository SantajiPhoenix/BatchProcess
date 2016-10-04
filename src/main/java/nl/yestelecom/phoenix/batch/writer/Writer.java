package nl.yestelecom.phoenix.batch.writer;

public interface Writer {	

	public void accept(WriteVisitor visitor);
	
	//public void write();

   //public void setFileProperties(String fileName, String filePath);

}
