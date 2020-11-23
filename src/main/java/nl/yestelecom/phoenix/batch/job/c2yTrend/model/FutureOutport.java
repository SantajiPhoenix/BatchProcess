package nl.yestelecom.phoenix.batch.job.c2yTrend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "FUTURE_TRENDS")
@Data
public class FutureOutport {
    @Id
    @Column(name = "Id")
    Long id;

    @Column(name = "MONTH")
    String MONTH;

    @Column(name = "MONTHDIGIT")
    String MONTHDIGIT;

    @Column(name = "COUNT")
    Long Future_Outport_Count;

}
