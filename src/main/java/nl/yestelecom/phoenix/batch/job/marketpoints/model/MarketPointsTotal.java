package nl.yestelecom.phoenix.batch.job.marketpoints.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "VIEW_POINTS_TOTAL")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketPointsTotal {
	
	@Column(name="VOICE_POINTS")
	Long voicePoints;
	
	@Column(name="DATA_POINTS")
	Long dataPoints;
	
	@Column(name="PRODUCT_POINTS")
	Long productPoints;
	
	@Column(name="USED_POINTS")
	Long usedPoints;
	
	@Column(name="TOTAL")
	Long total;
	
	@Column(name="GRAND_TOTAL")
	Long grandTotal;
	
	@Column(name="CODE")
	String code;
	
	@Column(name="DEALER_NAME")
	String dealerName;
	
	@Column(name="ACCOUNT_MANAGER")
	String accountManager;
	
	
	@Id
	@Column(name="ID")
	Long id;

}
