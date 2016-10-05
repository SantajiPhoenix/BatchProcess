package nl.yestelecom.phoenix.batch.job.marketpoints.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TEMP_VIEW_POINTS_TOTAL")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketPointsTotal {
	
	@Column(name="VOICE_POINTS")
	long voicePoints;
	
	@Column(name="DATA_POINTS")
	long dataPoints;
	
	@Column(name="PRODUCT_POINTS")
	long productPoints;
	
	@Column(name="USED_POINTS")
	long usedPoints;
	
	@Column(name="TOTAL")
	long total;
	
	@Column(name="GRAND_TOTAL")
	long grandTotal;
	
	
	@Column(name="CODE")
	String code;
	
	@Column(name="DEALER_NAME")
	String dealerName;
	
	@Column(name="ACCOUNT_MANAGER")
	String accountManager;
	
	
	@Column(name="INCENTIVE")
	Long incentive;
	
	@Id
	@Column(name="Id")
	long id;

}
