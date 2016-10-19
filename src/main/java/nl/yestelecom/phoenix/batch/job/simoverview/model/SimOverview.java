package nl.yestelecom.phoenix.batch.job.simoverview.model;

import lombok.Data;

@Data
public class SimOverview {

	String dealerCode;
	String mainDealerCode;
	String mainDealerName;
	String dealerName;
	Long Y32KCount;
	Long Y32KDUOCount;
	Long USIMCount;
	Long USIMDUOCount;
	Long MICROUSIMCount;
	Long MICROUSIMDUOCount;
	Long NANOUSIMCount;
	Long NANOUSIMDUOCount;
	Long YESNANOMICROCOMBIDUOCount;
	Long YESNANONANODUOCount;
	Long NFCV1USIMCount;
	Long grossStock;
	Long necessary;
	Long netStock;
}
