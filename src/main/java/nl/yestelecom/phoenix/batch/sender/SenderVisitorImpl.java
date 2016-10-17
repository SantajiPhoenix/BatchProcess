package nl.yestelecom.phoenix.batch.sender;

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
		try{
			mailSenderUtil.sendMail(email.getEmailTo(), email.getEmailFrom(), email.getFilePath(), email.getSubject(), email.getText());
		}catch(Exception e){
			System.out.println(e);
		}
		
	}

}
