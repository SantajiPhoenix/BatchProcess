package nl.yestelecom.phoenix.batch.job.simoverview.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
// @Table(name = "SIM_CARD_STOCK_VIEW")
@Data
public class SimTypeCount {

    @Id
    private Long id;
    private String dlrId;
    private String simType;
    private Long count;

}
