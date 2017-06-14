package nl.yestelecom.phoenix.batch.job.ciot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.sender.SenderVisitor;
import nl.yestelecom.phoenix.batch.sender.ftp.FTPSender;

@Service
public class CiotFTPSender implements FTPSender {

	@Value("${ciot.ftp.host}")
	private String sftpHost;
	@Value("${ciot.ftp.port}")
	private int sftpPort;
	@Value("${ciot.ftp.username}")
	private String username;
	@Value("${ciot.ftp.password}")
	private String password;
	@Value("${ciot.ftp.privateKey}")
	private String privateKey;
	@Value("${ciot.ftp.remoteDirectory}")
	private String remoteDirectory;
	@Value("${ciot.fileName}")
	private String fileName;
	@Value("${ciot.filePath}")
	private String filePath;

	private String sequence;

	@Override
	public String getSftpHost() {
		return sftpHost;
	}

	@Override
	public int getSftpPort() {
		return sftpPort;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getPrivateKey() {
		return privateKey;
	}

	@Override
	public String getRemoteDirectory() {
		return remoteDirectory;
	}

	@Override
	public String getFileName() {
		return fileName + sequence + ".xml";
	}

	@Override
	public String getFilePath() {
		return filePath;
	}

	@Override
	public void accept(SenderVisitor visitor) {
		visitor.sendContent(this);
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

}
