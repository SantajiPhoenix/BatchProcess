package nl.yestelecom.phoenix.batch.sender;

@FunctionalInterface
public interface Sender {
    public void accept(SenderVisitor visitor);
}
