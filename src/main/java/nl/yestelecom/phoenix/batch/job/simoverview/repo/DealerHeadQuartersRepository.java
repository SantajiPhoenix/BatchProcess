package nl.yestelecom.phoenix.batch.job.simoverview.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import nl.yestelecom.phoenix.batch.job.simoverview.model.DealerHeadQuarters;

public interface DealerHeadQuartersRepository extends JpaRepository<DealerHeadQuarters, Long>{
	
	@Query(value="select d.code dlr_code,d.dealer_name as dealer_name, md.code as mdlr_code, md.dealer_name main_dealer_name, d.id as dlr_id, d.mdl_id as mdlr_id  "
			+ "from dealers d ,dealers md  where d.mdl_id = md.id(+) "
			+ "and d.MDL_ID is not null "
			+ "order by d.mdl_id",nativeQuery=true)
	List<DealerHeadQuarters> getDealersandMainDealers();

}

//  and d.dealer_name = 'Retail Synergie XBA Telecom' 