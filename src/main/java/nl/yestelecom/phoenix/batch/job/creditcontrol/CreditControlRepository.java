package nl.yestelecom.phoenix.batch.job.creditcontrol;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CreditControlRepository extends JpaRepository<CreditControl, String> {
	public List<CreditControl> findAll();

}
