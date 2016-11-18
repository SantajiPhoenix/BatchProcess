package nl.yestelecom.phoenix.batch.job.marketpoints.model;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "VIEW_POINTS_PER_CONTRACT")
@Data
public class MarketPoints {
	@Column(name="VOICE_POINTS",nullable=true)
	Long voicePoints;
	
	@Column(name="DATA_POINTS",nullable=true)
	Long dataPoints;
	
	@Column(name="PRODUCT_POINTS",nullable=true)
	Long productPoints;
	
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
	
	@Id
	@Column(name="ID")
	Long id;
	
}
