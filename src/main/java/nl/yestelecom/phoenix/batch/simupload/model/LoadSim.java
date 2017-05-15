package nl.yestelecom.phoenix.batch.simupload.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "LOAD_SIM")
@Data

public class LoadSim {
    @Column(name = "SP")
    Integer sp;

    @Column(name = "CR_DATE")
    Date crDate;

    @Id
    @Column(name = "SIM_NR")
    String simNr;

    @Column(name = "LONG_SIM_NR")
    String longSimNr;

    @Column(name = "IMSI_NR")
    String imSimNr;

    @Column(name = "TYPE1")
    String type1;

    @Column(name = "TYPE2")
    String type2;

    @Column(name = "PUK1")
    String puk1;

    @Column(name = "GSM_NUMBER")
    String gsmNumber;

}
