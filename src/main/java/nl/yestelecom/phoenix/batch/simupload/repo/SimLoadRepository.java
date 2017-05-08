package nl.yestelecom.phoenix.batch.simupload.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import nl.yestelecom.phoenix.batch.simupload.model.LoadSim;

public interface SimLoadRepository extends JpaRepository<LoadSim, Long> {
    @Query(value="SELECT C2Y_TYPE FROM SIM_TYPE_MAPPING WHERE KPN_TYPE = :type2",nativeQuery=true)
	String getKpnType(@Param("type2") String type2);

}
