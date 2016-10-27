package nl.yestelecom.phoenix.batch.job.preventel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "V_PREVENTEL")
@Data
public class Preventel {

	@Column(name = "STATUS")
	private String status;

	@Column(name = "ID_PROVIDER")
	private String idProvider;

	@Column(name = "RELATIETYPE")
	private String relationType;

	@Column(name = "RELATIENAAM")
	private String companyName;

	@Id
	@Column(name = "RELATIEIDENTIFICATIE")
	private String zygoNumber;

	@Column(name = "RELATIEBEDRIJFSVORM")
	private String relationshipBusinessCode;

	@Column(name = "RELATIEDATUM")
	private String createdDate;

	@Column(name = "COUNTRY")
	private String country;

	@Column(name = "RELATIEVOORLETTERS")
	private String initials;

	@Column(name = "RELATIEVOORVOEGSEL")
	private String prefix;

	@Column(name = "BANKACCOUNT")
	private String bankAccount;

	@Column(name = "RELATIEIDENT")
	private String idNumber;

	@Column(name = "HUISNUMMER")
	private String houseNumber;

	@Column(name = "POSTCODE")
	private String postCode;

	@Column(name = "STRAATNAAM")
	private String street;

	@Column(name = "PLAATS")
	private String city;

	@Column(name = "TELEFOON")
	private String telephone;

	@Column(name = "FAX")
	private String fax;

	@Column(name = "POSTBUS")
	private String postBox;

	@Column(name = "POSTCODE_POSTBUS")
	private String postCodePostBus;

	@Column(name = "HUISNUMMERTOEV")
	private String addition;

	@Column(name = "REGISTRATIEDATUM")
	private String registration;

	@Column(name = "EMPTYSPACE")
	private String emptyspace;

	@Override
	public String toString() {
		return status + idProvider + relationType + companyName + zygoNumber + relationshipBusinessCode + createdDate
				+ country + initials + prefix + bankAccount + idNumber + houseNumber + postCode + street + city
				+ telephone + fax + postBox + postCodePostBus + addition + registration + emptyspace;
	}

}
