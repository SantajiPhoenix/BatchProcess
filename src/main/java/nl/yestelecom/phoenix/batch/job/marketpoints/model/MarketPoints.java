package nl.yestelecom.phoenix.batch.job.marketpoints.model;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "TEMP_VIEW_POINTS_PER_CONTRACT")
@Data
public class MarketPoints {
	@Column(name="VOICE_POINTS")
	long voicePoints;
	
	@Column(name="DATA_POINTS")
	long dataPoints;
	
	@Column(name="PRODUCT_POINTS",nullable=true)
	long productPoints;
	

	@Column(name="CODE")
	String code;
	
	@Column(name="DEALER_NAME")
	String dealerName;
	
	@Column(name="ACCOUNT_MANAGER")
	String accountManager;

	@Column(name="ACCEPTED_DATE")
	Date acceptedDate;
	
	@Column(name="CONTRACT_NR")
	String contractNr;
	
	@Column(name="INCENTIVE")
	Long incentive;
	
	@Id
	@Column(name="ID")
	long id;
	
}
