package nl.yestelecom.phoenix.batch.job.simoverview.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
//@Table(name = "SIMS")
@Data
public class DealerHeadQuarters {
	@Id
	String dlrId;
	String mdlrId;
	String dlrCode;
	String mdlrCode;
	String dealerName;
	String mainDealerName;
}
