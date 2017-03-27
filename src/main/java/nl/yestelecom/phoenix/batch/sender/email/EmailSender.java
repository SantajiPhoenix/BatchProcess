package nl.yestelecom.phoenix.batch.sender.email;

import nl.yestelecom.phoenix.batch.job.emaildetails.EmailDetails;
import nl.yestelecom.phoenix.batch.sender.Sender;

public interface EmailSender extends Sender {

    String getFilePath();

    String getEmailTo();

    String getEmailFrom();

    String getSubject();

    String getText();

    String getAttachFile();

    void setEmailDetails(EmailDetails emailDetails);

}
