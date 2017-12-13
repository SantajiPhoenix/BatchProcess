package nl.yestelecom.phoenix.batch.portingrequestreport;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "V_PORTING_FAILED_REQUEST_VIEW")
public class PortingRequestObject {

    private String gsmNo;
    private Long gssId;
    @Id
    private Long porId;
    private Long pstId;
    private Long spsId;
    private Long porTraId;
    private Long cesId;
    private Long scsId;
    private Long gssTraId;
    private String externalOrderRef;
    private Long netId;
    private Long proId;
    private String portingId;
    private Date portingDate;
    private Date activationDate;
    private Date deactivationDateVoice;
    private Date activationWishDate;
    private Long tempGsmId;

    @Override
    public String toString() {
        return gsmNo + "       " + portingId + "        " + externalOrderRef + "       " + pstId + "          " + spsId;
    }

}
