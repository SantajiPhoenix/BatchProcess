package nl.yestelecom.phoenix.batch.sender;

import nl.yestelecom.phoenix.batch.sender.email.EmailSender;
import nl.yestelecom.phoenix.batch.sender.ftp.FTPSender;

public interface SenderVisitor {

	void sendContent(FTPSender ftp);

	void sendContent(EmailSender email);

}
