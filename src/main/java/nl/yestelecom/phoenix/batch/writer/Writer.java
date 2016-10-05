package nl.yestelecom.phoenix.batch.writer;

public interface Writer {

	public void accept(WriteVisitor visitor);

}
