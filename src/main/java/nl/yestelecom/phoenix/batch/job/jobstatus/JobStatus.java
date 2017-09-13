package nl.yestelecom.phoenix.batch.job.jobstatus;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "BP_JOB_STATUS")
public class JobStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BP_JOB_STATUS")
    @SequenceGenerator(name = "SEQ_BP_JOB_STATUS", sequenceName = "SEQ_BP_JOB_STATUS", allocationSize = 1)
    Long id;
    String jobName;
    String status;
    Date jobDate;
    String error;
    Long timeTaken;

}
