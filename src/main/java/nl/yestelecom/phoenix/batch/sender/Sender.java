package nl.yestelecom.phoenix.batch.sender;

public interface Sender {
	public void accept(SenderVisitor visitor);
}
