package nl.yestelecom.phoenix.batch.job.ciot;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CiotRepository extends JpaRepository<Ciot, Long> {

    @Override
	public List<Ciot> findAll();

    @Query(value = "select * from V_UPLOAD_CIOT where telefoonnummer in (31613132107,31653319131)", nativeQuery = true)
    public List<Ciot> fetchOneData();

    @Query(value="SELECT TRIM(TO_CHAR(SEQ_CIOT.nextval, '000')) FROM DUAL", nativeQuery=true)
    public String generateFileSequence();

}
