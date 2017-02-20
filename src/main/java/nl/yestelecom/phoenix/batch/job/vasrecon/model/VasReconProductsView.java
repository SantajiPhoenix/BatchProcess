package nl.yestelecom.phoenix.batch.job.vasrecon.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "V_VAS_RECON_DATA_VIEW")
@Data
public class VasReconProductsView {
    @Id
    @Column(name = "GSS_ID")
    Long gssId;

    @Column(name = "GSS_PROD_ID")
    Long gssProdId;

    @Column(name = "C2Y_ID")
    Long c2yId;

    @Column(name = "SERVICE_CODE")
    String serviceCode;

    @Column(name = "START_DATE_CONTRACT")
    Date startDateContract;

    @Column(name = "START_DATE_CONTRACT_DATA")
    Date startDateContractData;

    @Column(name = "MODIFY_DATE")
    Date modifyDate;

    @Column(name = "ENTRY_DATE")
    Date entryDate;

    @Column(name = "BEGINDATE_VALID")
    Date beginDateValid;

    @Column(name = "END_DATE_VALID")
    Date endDateValid;

    @Column(name = "CODE")
    String code;

    @Column(name = "VAS_STATE_CODE")
    String vasStateCode;

    @Column(name = "VAS_CODE")
    String vasCode;

    @Column(name = "CES_ID")
    Long cesId;

    @Column(name = "PRODUCT_IN_ZYGO")
    String productInZygo;

    @Column(name = "PRODUCT_IN_SKT")
    String productInSkt;

    @Column(name = "SOURCE")
    String source;

}
