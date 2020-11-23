package nl.yestelecom.phoenix.batch.job.c2yTrend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "CONNECTION_LIST")
@Data
public class ConnectionList {
    @Id
    @Column(name = "Id")
    Long id;

    @Column(name = "TYPE")
    String type;

    @Column(name = "COUNT")
    Long outportConnCount;

}
