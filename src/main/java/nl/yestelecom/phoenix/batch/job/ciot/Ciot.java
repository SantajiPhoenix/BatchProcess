package nl.yestelecom.phoenix.batch.job.ciot;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "V_UPLOAD_CIOT")
@Data
public class Ciot {

    @Column(name = "SERVICE_PROVIDER")
    private String serviceProvider;

    @Column(name = "ACHTERNAAM")
    private String lastName;

    @Column(name = "VOORVOEGSEL")
    private String prefix;

    @Column(name = "VOORLETTERS")
    private String initials;

    @Column(name = "STRAATNAAM")
    private String streetName;

    @Column(name = "HUISNUMMER")
    private Integer houseNumber;

    @Column(name = "POSTCODE")
    private String postCode;

    @Column(name = "WOONPLAATS")
    private String residence;

    @Column(name = "SOORTVERBINDING")
    private String linkType;

    @Column(name = "GENERATIEDATUM")
    private String generatedDate;

    @Id
    @Column(name = "TELEFOONNUMMER")
    private Long telephoneNumber;
}
