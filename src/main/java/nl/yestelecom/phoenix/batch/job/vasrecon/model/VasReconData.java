package nl.yestelecom.phoenix.batch.job.vasrecon.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "VAS_RECON_DATA")
@Data
public class VasReconData {

    @Id
    @SequenceGenerator(name = "SEQ_VAS_RECON_DATA", sequenceName = "SEQ_VAS_RECON_DATA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VAS_RECON_DATA")
    @Column(name = "Id")
    Long id;

    @Column(name = "GSS_ID")
    Long gssId;

    @Column(name = "GSS_PROD_ID")
    Long gssProdId;

    @Column(name = "C2Y_CODE")
    String c2yCode;

    @Column(name = "ACTION")
    String action;

    @Column(name = "STATUS")
    Date status;

    @Column(name = "EXECUTION_TIME")
    Date executionTime;

    @Column(name = "CREATION_DATE")
    Date creationDate;

    @Column(name = "RETRY")
    String retry;

    @Column(name = "LAST_UPDATED")
    Date lastUpdated;

    @Column(name = "SOURCE")
    String source;

    @Column(name = "AMOUNT_BILLED")
    Long amountBilled;

    @Column(name = "C2Y_PRICE")
    Long c2yPrice;

    @Column(name = "PLANNED_AMOUNT")
    Long plannedAMount;

    @Column(name = "CONFIG_AMOUNT")
    Long configAmount;

}
