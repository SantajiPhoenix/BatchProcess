package nl.yestelecom.phoenix.batch.job.vasrecon.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "V_VAS_RECON_PRICE_VIEW")
@Data
public class VasReconPriceView {

    @Id
    @Column(name = "GSS_ID")
    Long gssId;

    @Column(name = "SOURCE")
    String source;

    @Column(name = "CHARGE_CODE")
    String chargeCode;

    @Column(name = "SERVICE_CODE")
    String serviceCode;

    @Column(name = "CODE")
    String code;

    @Column(name = "NAME")
    String name;

    @Column(name = "DATE_FROM")
    Date dateFrom;

    @Column(name = "BEGINDATE_VALID")
    Date begindateValid;

    @Column(name = "AMOUNT_BILLED")
    Long amountBilled;

    @Column(name = "SKT_PRICE")
    Long sktPrice;

    @Column(name = "PLANNED_AMOUNT")
    Long plannedAMount;

    @Column(name = "CONFIG_AMOUNT")
    Long configAmount;
}
