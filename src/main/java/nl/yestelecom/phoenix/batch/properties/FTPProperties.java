package nl.yestelecom.phoenix.batch.properties;

public class FTPProperties {
	private String host;
	private int port;
	private String username;
	private String password;
	private String privateKey;
	private String remoteDirectory;

	public FTPProperties(String host, int port, String username, String password, String privateKey,
			String remoteDirectory) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
		this.privateKey = privateKey;
		this.remoteDirectory = remoteDirectory;
	}

}
