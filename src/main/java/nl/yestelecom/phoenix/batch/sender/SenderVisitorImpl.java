package nl.yestelecom.phoenix.batch.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.sender.email.EmailSender;
import nl.yestelecom.phoenix.batch.sender.ftp.FTPSender;
import nl.yestelecom.phoenix.batch.sender.ftp.FTPSenderUtil;
import nl.yestelecom.phoenix.batch.sender.mail.MailSenderUtil;

@Service
public class SenderVisitorImpl implements SenderVisitor {
	@Autowired
	FTPSenderUtil ftpSenderUtil;
	
	@Autowired
	MailSenderUtil mailSenderUtil;
	
	private static Logger logger = LoggerFactory.getLogger(SenderVisitorImpl.class);

	@Override
	public void sendContent(FTPSender ftp) {
		logger.info("FTP Initiated ");
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
		try{
			logger.info("SENDING MAIL ");
			mailSenderUtil.sendMail(email.getEmailTo(), email.getEmailFrom(), email.getFilePath(), email.getSubject(), email.getText());
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		
	}

}
