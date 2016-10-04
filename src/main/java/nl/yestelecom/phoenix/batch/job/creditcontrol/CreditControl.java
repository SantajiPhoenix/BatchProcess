package nl.yestelecom.phoenix.batch.job.creditcontrol;

import java.sql.Date;

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
	private String gsmNo;
	private String customerNumber;
	private String customerName;
	private String dealerCode;
	private String dealerName;
	private String zygoCode;
	private Double zygoAmount;
	private Date invoiceDate;
	private Double monthlyAmount;
	private String status;
	private String chargeLevel;
	private String chargeType;
	private String chargeCode;
	private Date beginDate;
	private Date endDate;
	private String autorisator;
	private String creator;
	private String reason;
	private String type;
	private String system;

	@Override
	public String toString() {
		return gsmNo + "," + customerNumber + "," + customerName + "," + dealerCode + "," + dealerName + "," + zygoCode
				+ "," + zygoAmount + "," + invoiceDate + "," + monthlyAmount + "," + status + "," + chargeLevel + ","
				+ chargeType + "," + chargeCode + "," + beginDate + "," + endDate + "," + autorisator + "," + creator
				+ "," + reason + "," + type + "," + system;
	}
}