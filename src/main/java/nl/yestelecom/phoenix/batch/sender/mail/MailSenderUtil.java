package nl.yestelecom.phoenix.batch.sender.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailSenderUtil {
    @Autowired
    private JavaMailSender mailSender;

    private static Logger logger = LoggerFactory.getLogger(MailSenderUtil.class);

    public void sendMail(String emailTo, String emailFrom, String path, String subject, String text, String attachFile) throws MessagingException {
        final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        final MimeMessage message = mailSender.createMimeMessage();
        simpleMailMessage.setFrom(emailFrom);
        simpleMailMessage.setTo(getToList(emailTo));
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        final MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(simpleMailMessage.getFrom());
        helper.setTo(simpleMailMessage.getTo());
        helper.setSubject(simpleMailMessage.getSubject());
        helper.setText(text);
        if ("TRUE".equals(attachFile)) {
            final File folder = new File(path);
            final File[] listOfFiles = folder.listFiles();
            for (final File file : listOfFiles) {
                if (file.isFile()) {
                    helper.addAttachment(file.getName(), file);
                } else if (file.isDirectory()) {
                    logger.info("It is a direcory");
                }

            }
        }
        mailSender.send(message);
        logger.info("Mail sent");

    }

    private String[] getToList(String emailTo) {
        logger.info("Getting to List");
        final List<String> toList = new ArrayList<>();
        final StringTokenizer st = new StringTokenizer(emailTo, ",");
        while (st.hasMoreTokens()) {
            final String str = st.nextToken();
            toList.add(str);
        }
        String[] toListArr = new String[toList.size()];
        toListArr = toList.toArray(toListArr);
        return toListArr;
    }

}
