package nl.yestelecom.phoenix.batch.job.vasrecon.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import nl.yestelecom.phoenix.batch.job.vasrecon.model.VasReconProductsView;

public interface VasReconRepository extends JpaRepository<VasReconProductsView, Long>{
	
	@Query(value="select count(*) from EXTCHANGES "
			+ " where gss_id =:gss_id "
			+ "  AND SYSTEM = 'ZYGO' "
			+ "AND azt_id = 16 "
			+ "AND action IN ('TOEV') "
			+ "AND execution_time <= SYSDATE", nativeQuery=true)
	Long getExternalChnagesCount(@Param("gss_id") Long gssId);

	
}
