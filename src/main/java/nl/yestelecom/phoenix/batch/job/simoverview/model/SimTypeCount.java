package nl.yestelecom.phoenix.batch.job.simoverview.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
//@Table(name = "SIM_CARD_STOCK_VIEW")
@Data
public class SimTypeCount {

	String dlrId;
	@Id
	String simType;
	Long count;

}
