package nl.yestelecom.phoenix.batch.job.simoverview.model;

import javax.persistence.Id;

import lombok.Data;

@Data
public class SimOverviewBusinessPartner {
	@Id
	String simType;
	Long count;

}
