package nl.yestelecom.phoenix.batch.job.simoverview;

import org.springframework.stereotype.Service;

@Service
public class SimOverviewFileFormat {
    public static final String HEADERROWS = "Hoofdkantoor;Business Partner;Y32K;Y32KDUO;YES USIM;YES USIM DUO;MICRO USIM;MICRO USIM DUO;NANO USIM;NANO USIM DUO;NANO MICRO COMBI DUO;NANO-NANO DUO;Bruto voorraad;;Benodigd;Netto voorraad;;";

    String createHeader() {
        final String header = ";;;;;;;;;;;;;;;\n";
        return header;

    }

}
