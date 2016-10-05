package nl.yestelecom.phoenix.batch.sender.ftp.generic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import nl.yestelecom.phoenix.batch.sender.ftp.FTPSender;

@Configuration
public abstract class zygoFTPSender implements FTPSender {

	@Value("${zygo.ftp.host}")
	private String sftpHost;
	@Value("${zygo.ftp.port}")
	private int sftpPort;
	@Value("${zygo.ftp.username}")
	private String username;
	@Value("${zygo.ftp.password}")
	private String password;
	@Value("${zygo.ftp.privatekey}")
	private String privateKey;
	// @Value("${zygo.fileName}")
	// private String fileName;
	// @Value("${zygo.filePath}")
	// private String filePath;
	// @Value("${creditcontrol.ftp.remotedirectory}")
	// private String remoteDirectory;

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

	public abstract String getFileName() ;

	public abstract String getFilePath();

	public abstract String getRemoteDirectory();

//	@Override
//	public void accept(SenderVisitor visitor) {
//		visitor.sendContent(this);
//	}

}
