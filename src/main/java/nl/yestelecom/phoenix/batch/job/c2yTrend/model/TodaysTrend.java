package nl.yestelecom.phoenix.batch.job.c2yTrend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "V_TODAYS_TREND")
@Data
public class TodaysTrend {
    @Id
    @Column(name = "Id")
    Long id;

    @Column(name = "ACTIVE_CONN_COUNT")
    Long activeConnCount;

    @Column(name = "ACTIVE_CUS_COUNT")
    Long activeCusCount;

    @Column(name = "ACTIVE_DLR_COUNT")
    Long activeDlrCount;

}
