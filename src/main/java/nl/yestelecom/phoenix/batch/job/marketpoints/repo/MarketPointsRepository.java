package nl.yestelecom.phoenix.batch.job.marketpoints.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import nl.yestelecom.phoenix.batch.job.marketpoints.model.MarketPoints;

public interface MarketPointsRepository extends JpaRepository<MarketPoints, Long>{
	@Query(value="select * from TEMP_VIEW_POINTS_PER_CONTRACT "
			+ " where INCENTIVE =:incentive", nativeQuery=true)
	List<MarketPoints> getViewPointsPerContract(@Param("incentive") Long incentive);
	
	@Query(value=" SELECT   SUM (VOICE_POINTS) AS VOICEPOINTS, "
			+ "SUM (DATA_POINTS) AS DATAPOINTS, "
			+ "SUM (PRODUCT_POINTS) AS PRODUCTPOINTS, "
			+ "CODE, "
			+ "DEALER_NAME, "
			+ "ACCOUNT_MANAGER, "
			+ "ACCEPTED_DATE, "
			+ "CONTRACT_NR "
			+ "FROM   TEMP_VIEW_POINTS_PER_CONTRACT "
			+ "GROUP BY   "
			+ "CODE, "
			+ "DEALER_NAME, "
			+ "ACCOUNT_MANAGER, "
			+ "ACCEPTED_DATE, "
			+ "CONTRACT_NR", nativeQuery=true)
	List<Object []> getViewPointsPerContractMerged();
	
	@Query(value="select rownum As ID, VOICE_POINTS, DATA_POINTS, PRODUCT_POINTS, CODE, DEALER_NAME, ACCOUNT_MANAGER, ACCEPTED_DATE, CONTRACT_NR  from VIEW_POINTS_PER_CONTRACT", nativeQuery=true)
	List<MarketPoints> getViewPointsPerContractInc1();
	
	@Query(value="select rownum As ID, VOICE_POINTS, DATA_POINTS, PRODUCT_POINTS, CODE, DEALER_NAME, ACCOUNT_MANAGER, ACCEPTED_DATE, CONTRACT_NR  from VIEW_POINTS_PER_CONTRACT_INC2" , nativeQuery=true)
	List<MarketPoints> getViewPointsPerContractInc2();
	
	
	@Query(value=" SELECT   SUM (VOICE_POINTS) AS VOICEPOINTS, "
			+ "SUM (DATA_POINTS) AS DATAPOINTS, "
			+ "SUM (PRODUCT_POINTS) AS PRODUCTPOINTS, "
			+ "CODE, "
			+ "DEALER_NAME, "
			+ "ACCOUNT_MANAGER, "
			+ "ACCEPTED_DATE, "
			+ "CONTRACT_NR "
			+ "FROM   VIEW_POINTS_PER_CONTRACT "
			+ "GROUP BY   "
			+ "CODE, "
			+ "DEALER_NAME, "
			+ "ACCOUNT_MANAGER, "
			+ "ACCEPTED_DATE, "
			+ "CONTRACT_NR", nativeQuery=true)
	List<Object []> getViewPointsPerContractMergedInc1();
	
	@Query(value=" SELECT   SUM (VOICE_POINTS) AS VOICEPOINTS, "
			+ "SUM (DATA_POINTS) AS DATAPOINTS, "
			+ "SUM (PRODUCT_POINTS) AS PRODUCTPOINTS, "
			+ "CODE, "
			+ "DEALER_NAME, "
			+ "ACCOUNT_MANAGER, "
			+ "ACCEPTED_DATE, "
			+ "CONTRACT_NR "
			+ "FROM   VIEW_POINTS_PER_CONTRACT_INC2 "
			+ "GROUP BY   "
			+ "CODE, "
			+ "DEALER_NAME, "
			+ "ACCOUNT_MANAGER, "
			+ "ACCEPTED_DATE, "
			+ "CONTRACT_NR", nativeQuery=true)
	List<Object []> getViewPointsPerContractMergedInc2();

}
