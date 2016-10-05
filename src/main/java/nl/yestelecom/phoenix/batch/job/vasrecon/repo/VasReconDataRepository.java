package nl.yestelecom.phoenix.batch.job.vasrecon.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.yestelecom.phoenix.batch.job.vasrecon.model.VasReconData;

public interface VasReconDataRepository extends JpaRepository<VasReconData, Long>{
	
	

}
