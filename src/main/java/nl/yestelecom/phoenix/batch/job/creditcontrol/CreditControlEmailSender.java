package nl.yestelecom.phoenix.batch.job.creditcontrol;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.job.emaildetails.EmailDetails;
import nl.yestelecom.phoenix.batch.sender.SenderVisitor;
import nl.yestelecom.phoenix.batch.sender.email.EmailSender;

@Service
public class CreditControlEmailSender implements EmailSender {

    String text;
    String emailFrom;
    String emailTo;
    String subject;
    String attachFile;

    @Value("${creditcontrol.filePath}")
    private String fileDirecotry;

    @Override
    public void accept(SenderVisitor visitor) {
        visitor.sendContent(this);
    }

    @Override
    public String getFilePath() {
        return fileDirecotry;
    }

    @Override
    public String getEmailTo() {
        return emailTo;
    }

    @Override
    public String getEmailFrom() {
        return emailFrom;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public String getText() {
        return text;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;

    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;

    }

    public void setSubject(String subject) {
        this.subject = subject;

    }

    public void setText(String text) {
        this.text = text;

    }

    public void setAttachFile(String attachFile) {
        this.attachFile = attachFile;

    }

    @Override
    public void setEmailDetails(EmailDetails emailDetails) {
        text = emailDetails.getText();
        subject = emailDetails.getSubject();
        emailFrom = emailDetails.getEmailFrom();
        emailTo = emailDetails.getEmailTo();
        attachFile = emailDetails.getAttachFile();

    }

    @Override
    public String getAttachFile() {
        return attachFile;
    }

}
