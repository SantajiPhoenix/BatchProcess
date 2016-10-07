package nl.yestelecom.phoenix.batch.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.sender.email.EmailSender;
import nl.yestelecom.phoenix.batch.sender.ftp.FTPSender;
import nl.yestelecom.phoenix.batch.sender.ftp.FTPSenderUtil;

@Service
public class SenderVisitorImpl implements SenderVisitor {
	@Autowired
	FTPSenderUtil ftpSenderUtil;

	@Override
	public void sendContent(FTPSender ftp) {
		if (ftp.getPrivateKey() != null && !ftp.getPrivateKey().isEmpty()) {
			ftpSenderUtil.addIdentity(ftp.getPrivateKey());
		}
		ftpSenderUtil.setSession(ftp.getUsername(), ftp.getSftpHost(), ftp.getSftpPort(), ftp.getPassword());
		ftpSenderUtil.setChannel();
		ftpSenderUtil.connect();
		ftpSenderUtil.transferFile(ftp.getFileName(), ftp.getFilePath(), ftp.getRemoteDirectory());
	}

	@Override
	public void sendContent(EmailSender email) {
		// TODO Auto-generated method stub

	}

}
