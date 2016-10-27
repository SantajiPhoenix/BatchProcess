package nl.yestelecom.phoenix.batch.job.preventel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PreventelRepository extends JpaRepository<Preventel, String> {

	public List<Preventel> findAll();

	@Query(value = "select * from V_PREVENTEL where RELATIEIDENTIFICATIE between '1001041             ' and '1001201             '", nativeQuery = true)
    public List<Preventel> fetchOneData();

}
