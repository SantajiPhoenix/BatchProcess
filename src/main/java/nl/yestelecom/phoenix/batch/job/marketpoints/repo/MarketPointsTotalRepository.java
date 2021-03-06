package nl.yestelecom.phoenix.batch.job.marketpoints.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import nl.yestelecom.phoenix.batch.job.marketpoints.model.MarketPointsTotal;

public interface MarketPointsTotalRepository  extends JpaRepository<MarketPointsTotal, Long>{

	@Query(value="select * from TEMP_VIEW_POINTS_TOTAL "
			+ " where INCENTIVE =:incentive", nativeQuery=true)
	List<MarketPointsTotal> getViewPointsTotal(@Param("incentive") Long incentive);
	
	@Query(value="select * from (SELECT SUM(VOICE_POINTS) AS VOICE_POINTS, "
			+ "SUM(DATA_POINTS)       AS DATA_POINTS, "
			+ "SUM(PRODUCT_POINTS)    AS PRODUCT_POINTS, "
			+ "SUM(USED_POINTS)       AS USED_POINTS, "
			+ "SUM(TOTAL)            AS TOTAL, "
			+ "SUM(GRAND_TOTAL)       AS GRAND_TOTAL, "
			+ "CODE, "
			+ "DEALER_NAME, "
			+ "ACCOUNT_MANAGER "
			+ "INCENTIVE "
			+ "FROM TEMP_VIEW_POINTS_TOTAL  "
			+ "GROUP BY "
			+ "CODE, "
			+ "DEALER_NAME, "
			+ "ACCOUNT_MANAGER, "
			+ "INCENTIVE ) temptable" 
			, nativeQuery=true)
	List<Object[]> getMergedViewPointsTotal();
	
	@Query(value="select rownum As ID,VOICE_POINTS, DATA_POINTS, PRODUCT_POINTS,USED_POINTS,TOTAL,GRAND_TOTAL,CODE, DEALER_NAME, ACCOUNT_MANAGER from VIEW_POINTS_TOTAL ", nativeQuery=true)
	List<MarketPointsTotal> getViewPointsTotalInc1();
	
	@Query(value="select rownum As ID,VOICE_POINTS, DATA_POINTS, PRODUCT_POINTS,USED_POINTS,TOTAL,GRAND_TOTAL,CODE, DEALER_NAME, ACCOUNT_MANAGER from VIEW_POINTS_TOTAL_INC2 ", nativeQuery=true)
	List<MarketPointsTotal> getViewPointsTotalInc2();
	
	@Query(value="select * from (SELECT SUM(VOICE_POINTS) AS VOICE_POINTS, "
			+ "SUM(DATA_POINTS)       AS DATA_POINTS, "
			+ "SUM(PRODUCT_POINTS)    AS PRODUCT_POINTS, "
			+ "SUM(USED_POINTS)       AS USED_POINTS, "
			+ "SUM(TOTAL)            AS TOTAL, "
			+ "SUM(GRAND_TOTAL)       AS GRAND_TOTAL, "
			+ "CODE, "
			+ "DEALER_NAME, "
			+ "ACCOUNT_MANAGER "
			+ "INCENTIVE "
			+ "FROM VIEW_POINTS_TOTAL  "
			+ "GROUP BY "
			+ "CODE, "
			+ "DEALER_NAME, "
			+ "ACCOUNT_MANAGER, "
			+ "INCENTIVE ) temptable" 
			, nativeQuery=true)
	List<Object[]> getMergedViewPointsTotalInc1();
	
	@Query(value="select * from (SELECT SUM(VOICE_POINTS) AS VOICE_POINTS, "
			+ "SUM(DATA_POINTS)       AS DATA_POINTS, "
			+ "SUM(PRODUCT_POINTS)    AS PRODUCT_POINTS, "
			+ "SUM(USED_POINTS)       AS USED_POINTS, "
			+ "SUM(TOTAL)            AS TOTAL, "
			+ "SUM(GRAND_TOTAL)       AS GRAND_TOTAL, "
			+ "CODE, "
			+ "DEALER_NAME, "
			+ "ACCOUNT_MANAGER "
			+ "INCENTIVE "
			+ "FROM VIEW_POINTS_TOTAL_INC2  "
			+ "GROUP BY "
			+ "CODE, "
			+ "DEALER_NAME, "
			+ "ACCOUNT_MANAGER, "
			+ "INCENTIVE ) temptable" 
			, nativeQuery=true)
	List<Object[]> getMergedViewPointsTotalInc2();
	
}
