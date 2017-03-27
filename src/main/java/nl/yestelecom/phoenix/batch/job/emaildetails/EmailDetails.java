package nl.yestelecom.phoenix.batch.job.emaildetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "BP_EMAIL_DETAILS")
@Entity
public class EmailDetails {
    @Id
    Long id;
    String emailFrom;
    String emailTo;
    String subject;
    String jobName;
    String text;
    String attachFile;

}
