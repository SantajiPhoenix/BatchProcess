package nl.yestelecom.phoenix.batch.portingrequestreport;

import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.job.emaildetails.EmailDetails;
import nl.yestelecom.phoenix.batch.sender.SenderVisitor;
import nl.yestelecom.phoenix.batch.sender.email.EmailSender;

@Service
public class PortingReportEmailSender implements EmailSender {
    String text;
    String emailFrom;
    String emailTo;
    String subject;
    String attachFile;

    @Override
    public void accept(SenderVisitor visitor) {
        visitor.sendContent(this);

    }

    @Override
    public String getFilePath() {
        return null;
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

    @Override
    public String getAttachFile() {
        return null;
    }

    @Override
    public void setEmailDetails(EmailDetails emailDetails) {
        text = emailDetails.getText();
        subject = emailDetails.getSubject();
        emailFrom = emailDetails.getEmailFrom();
        emailTo = emailDetails.getEmailTo();
        attachFile = emailDetails.getAttachFile();
    }

    public void setText(String text) {
        this.text = text;
    }
}
