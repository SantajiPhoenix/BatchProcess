package nl.yestelecom.phoenix.batch.sender.ftp;

import nl.yestelecom.phoenix.batch.sender.Sender;

public interface FTPSender extends Sender {

	public String getSftpHost();

	public int getSftpPort() ;

	public String getUsername();

	public String getPassword();

	public String getPrivateKey() ;

	public String getRemoteDirectory();

	public String getFileName();

	public String getFilePath();

}
