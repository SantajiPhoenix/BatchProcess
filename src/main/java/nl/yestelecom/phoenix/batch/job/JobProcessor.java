package nl.yestelecom.phoenix.batch.job;

public interface JobProcessor {

	default void execute() {
		read();
		process();
		write();
		send();
		postProcess();
	}

	public void read();

	public void process();

	public void write();
	
	public void send();
	
	public void postProcess();
	
	public String getJobName();

}
