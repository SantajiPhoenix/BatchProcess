package nl.yestelecom.phoenix.batch.job.ciot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class CiotUtil {
	
	public String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yy");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
