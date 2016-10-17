package nl.yestelecom.phoenix.batch.sender.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailSenderUtil {
	@Autowired
    private JavaMailSender mailSender;

	public void sendMail(String emailTo, String emailFrom, String path, String subject, String text) throws Exception{
		
 		
 		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
 		MimeMessage message = mailSender.createMimeMessage();
 		simpleMailMessage.setFrom(emailFrom);
 		simpleMailMessage.setTo(getToList(emailTo));
 		simpleMailMessage.setSubject(subject);
 		simpleMailMessage.setText(text);
		
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setFrom(simpleMailMessage.getFrom());
		helper.setTo(simpleMailMessage.getTo());
		helper.setSubject(simpleMailMessage.getSubject());
		helper.setText(
				text);

		
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        System.out.println("File " + listOfFiles[i].getName());
		        helper.addAttachment(listOfFiles[i].getName(), listOfFiles[i]);
		      } else if (listOfFiles[i].isDirectory()) {
		        System.out.println("Directory " + listOfFiles[i].getName());
		      }
		   }
		
		mailSender.send(message);
 	
 	}
	
	private String[] getToList(String emailTo) {
		List<String> toList = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(emailTo,",");  
		 while (st.hasMoreTokens()) {  
	         String str = st.nextToken();
	         System.out.println("To >> "+str);
	         toList.add(str); 
	     }  
		 String[] toListArr = new String[toList.size()];
		 toListArr = toList.toArray(toListArr);
		return toListArr;
	}

}
