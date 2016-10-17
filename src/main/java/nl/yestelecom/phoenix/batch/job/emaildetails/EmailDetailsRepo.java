package nl.yestelecom.phoenix.batch.job.emaildetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmailDetailsRepo extends JpaRepository<EmailDetails,Long>{
	@Query(value="select * from BP_EMAIL_DETAILS "
			+ "where job_name =:jobName",nativeQuery=true)
	EmailDetails getEmailDetailsForJob(@Param("jobName") String jobName);

}
