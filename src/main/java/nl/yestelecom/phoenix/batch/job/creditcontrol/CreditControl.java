package nl.yestelecom.phoenix.batch.job.creditcontrol;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "V_CREDITCONTROL")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreditControl {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "GSM_NO")
    private String gsmNo;
    @Column(name = "CUSTOMER_NUMBER")
    private String customerNumber;
    @Column(name = "CUSTOMER_NAME")
    private String customerName;
    @Column(name = "DEALER_CODE")
    private String dealerCode;
    @Column(name = "DEALER_NAME")
    private String dealerName;
    @Column(name = "ZYGO_CODE")
    private String zygoCode;

    @Column(name = "ZYGO_AMOUNT")
    private Double zygoAmount;
    @Column(name = "INVOICE_DATE")
    private Date invoiceDate;
    @Column(name = "MONTHLY_AMOUNT")
    private Double monthlyAmount;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "CHARGE_LEVEL")
    private String chargeLevel;
    @Column(name = "CHARGE_TYPE")
    private String chargeType;
    @Column(name = "CHARGE_CODE")
    private String chargeCode;
    @Column(name = "BEGIN_DATE")
    private Date beginDate;
    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "AUTORISATOR")
    private String autorisator;
    @Column(name = "CREATOR")
    private String creator;
    @Column(name = "REASON")
    private String reason;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "SYSTEM")
    private String system;

    @Override
    public String toString() {
        return gsmNo + "," + customerNumber + "," + customerName + "," + dealerCode + "," + dealerName + "," + zygoCode + "," + zygoAmount + "," + invoiceDate + "," + monthlyAmount + "," + status
                + "," + chargeLevel + "," + chargeType + "," + chargeCode + "," + beginDate + "," + endDate + "," + autorisator + "," + creator + "," + reason + "," + type + "," + system;
    }
}