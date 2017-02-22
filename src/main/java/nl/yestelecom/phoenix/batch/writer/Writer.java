package nl.yestelecom.phoenix.batch.writer;

@FunctionalInterface
public interface Writer {

    public void accept(WriteVisitor visitor);

}
