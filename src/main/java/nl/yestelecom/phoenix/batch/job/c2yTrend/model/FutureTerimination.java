package nl.yestelecom.phoenix.batch.job.c2yTrend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "V_FUTURE_TERMINATION")
@Data
public class FutureTerimination {
    @Id
    @Column(name = "Id")
    Long id;

    @Column(name = "MONTH")
    String MONTH;

    @Column(name = "MONTHDIGIT")
    String MONTHDIGIT;

    @Column(name = "TERMINATION_FUTURE_COUNT")
    Long TERMINATION_FUTURE_COUNT;

}
