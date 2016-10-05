package nl.yestelecom.phoenix.batch.job.creditcontrol;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.sender.SenderVisitor;
import nl.yestelecom.phoenix.batch.sender.ftp.FTPSender;

@Service
public class CreditControlFTPSender implements FTPSender {

	@Value("${creditcontrol.ftp.host}")
	private String sftpHost;
	@Value("${creditcontrol.ftp.port}")
	private int sftpPort;
	@Value("${creditcontrol.ftp.username}")
	private String username;
	@Value("${creditcontrol.ftp.password}")
	private String password;
	@Value("${creditcontrol.ftp.privateKey}")
	private String privateKey;
	@Value("${creditcontrol.fileName}")
	private String fileName;
	@Value("${creditcontrol.filePath}")
	private String filePath;
	@Value("${creditcontrol.ftp.remoteDirectory}")
	private String remoteDirectory;

	public String getSftpHost() {
		return sftpHost;
	}

	public int getSftpPort() {
		return sftpPort;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public String getRemoteDirectory() {
		return remoteDirectory;
	}

	@Override
	public void accept(SenderVisitor visitor) {
		visitor.sendContent(this);
	}

	@Override
	public String getFileName() {
		return fileName;
	}

	@Override
	public String getFilePath() {
		return filePath;
	}

}
