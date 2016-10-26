package nl.yestelecom.phoenix.batch.job.jobstatus;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobStatusRepo extends JpaRepository<JobStatus, Long>{

}
