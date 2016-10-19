package nl.yestelecom.phoenix.batch.job.simoverview;

import org.springframework.stereotype.Service;

@Service
public class SimOverviewFileFormat {
	public static final String headerRows= "Hoofdkantoor;Business Partner;Y32K;Y32KDUO;YES USIM;YES USIM DUO;MICRO USIM;MICRO USIM DUO;NANO USIM;NANO USIM DUO;NANO MICRO COMBI DUO;NANO-NANO DUO;Bruto voorraad;;Benodigd;Netto voorraad;;";
	
	String createHeader(){
		String header = ";;;;;;;;;;;;;;;\"\n";
		header = header +"Voorraad;;Soort;;;;;;;;;;;;;";
		header = header +"\n"+ headerRows;
		System.out.println(header);
		return header;
		
	}


}
