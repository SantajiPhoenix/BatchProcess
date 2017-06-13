package nl.yestelecom.phoenix.batch.job.simoverview.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import nl.yestelecom.phoenix.batch.job.simoverview.model.SimTypeCount;

public interface SimTypeCountRepository extends JpaRepository<SimTypeCount, Long> {

    @Query(value="select  rownum As id, dlr_id, sim_type, count from ( select dlr1.id as dlr_id, "
			+ "s1.type2 as sim_type, "
			+ "count(*) as count  "
			+ "from sims s1 "
			+ "left join sim_status ss1 on ss1.id = s1.sst_id "
			+ "left join dealers dlr1 on dlr1.id = s1.dlr_id "
			+ "left join gsm_sims gs1 on gs1.sim_id = s1.id "
			+ "where "
			+ "ss1.description = 'Niet toegekend' and "
			+ "( "
			+ "gs1.activation_date is null or "
			+ "gs1.activation_date >= sysdate ) "
			+ "and dlr1.id is not null "
			+ "group by "
			+ "dlr1.id, "
			+ "s1.type2)  where dlr_id =:dlrId",nativeQuery=true)
	List<Object[]> getTypeCount(@Param("dlrId")String dlrId);

    @Query(value= "select sim_type, count from( "
			+ "select dlr1.id as dlr_id, "
					+ "s1.type2 as sim_type, "
					+ "count(*) as count  "
					+ "from sims s1 "
					+ "left join sim_status ss1 on ss1.id = s1.sst_id "
					+ "left join dealers dlr1 on dlr1.id = s1.dlr_id "
					+ "left join gsm_sims gs1 on gs1.sim_id = s1.id "
					+ "where "
					+ "ss1.description = 'Niet toegekend' and "
					+ "( "
					+ "gs1.activation_date is null or "
					+ "gs1.activation_date >= sysdate ) "
					+ "group by "
					+ "dlr1.id, "
					+ "s1.type2) "
					+ "where dlr_id is null  and sim_type is not null", nativeQuery=true)
	List<Object[]> getCountForYesTel();

    @Query(value="select sum(count) from (select dlr1.id as dlr_id, "
			+ "s1.type2 as sim_type, "
			+ "count(*) as count  "
			+ "from sims s1 "
			+ "left join sim_status ss1 on ss1.id = s1.sst_id "
			+ "left join dealers dlr1 on dlr1.id = s1.dlr_id "
			+ "left join gsm_sims gs1 on gs1.sim_id = s1.id "
			+ "where "
			+ "ss1.description = 'Niet toegekend' and "
			+ "( "
			+ "gs1.activation_date is null or "
			+ "gs1.activation_date >= sysdate ) "
			+ "group by "
			+ "dlr1.id, "
			+ "s1.type2) "
			+ "where dlr_id =:dlrId", nativeQuery=true)
	Long getTotalSimxCountForDelaer(@Param("dlrId")String dlrId);

    @Query(value="select sim_type, sum(count) as count "
			+ "from (select dlr1.id as dlr_id, s1.type2 as sim_type, count(*) as count  "
			+ "from sims s1 "
			+ "left join sim_status ss1 on ss1.id = s1.sst_id "
			+ "left join dealers dlr1 on dlr1.id = s1.dlr_id "
			+ "left join gsm_sims gs1 on gs1.sim_id = s1.id "
			+ "where "
			+ "ss1.description = 'Niet toegekend' and ( "
			+ "gs1.activation_date is null or "
			+ "gs1.activation_date >= sysdate ) "
			+ "group by "
			+ "dlr1.id, "
			+ "s1.type2) "
			+ "where "
			+ "dlr_id is not null "
			+ "group by "
			+ "sim_type "
			+ "union "
			+ "select 'benodigd' as sim_type, sum(count) as count from ( "
			+ "select dlr1.id as dlr_id, "
			+ "s1.type2 as sim_type, "
			+ "count(*) as count  "
			+ "from sims s1 "
			+ "left join sim_status ss1 on ss1.id = s1.sst_id "
			+ "left join dealers dlr1 on dlr1.id = s1.dlr_id "
			+ "left join gsm_sims gs1 on gs1.sim_id = s1.id "
			+ "where "
			+ "ss1.description = 'Niet toegekend' and ( "
			+ "gs1.activation_date is null or "
			+ "gs1.activation_date >= sysdate ) "
			+ "group by "
			+ "dlr1.id, "
			+ "s1.type2)", nativeQuery=true)
	List<Object[]> getBusinessPartenerCount();

}
