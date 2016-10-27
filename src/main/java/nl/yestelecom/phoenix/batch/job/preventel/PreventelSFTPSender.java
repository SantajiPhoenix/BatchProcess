package nl.yestelecom.phoenix.batch.job.preventel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.sender.SenderVisitor;
import nl.yestelecom.phoenix.batch.sender.ftp.FTPSender;

@Service
public class PreventelSFTPSender implements FTPSender {

	@Value("${preventel.sftp.host}")
	private String sftpHost;
	@Value("${preventel.sftp.port}")
	private int sftpPort;
	@Value("${preventel.sftp.username}")
	private String username;
	@Value("${preventel.sftp.password}")
	private String password;
	@Value("${preventel.sftp.privateKey}")
	private String privateKey;
	@Value("${preventel.sftp.remoteDirectory}")
	private String remoteDirectory;
	@Value("${preventel.fileName}")
	private String fileName;
	@Value("${preventel.filePath}")
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
		fileName = fileName + sequence;
		return fileName;
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
