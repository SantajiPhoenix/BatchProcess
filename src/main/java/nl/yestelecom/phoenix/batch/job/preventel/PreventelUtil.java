package nl.yestelecom.phoenix.batch.job.preventel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class PreventelUtil {

	public String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyMdd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public String rightPad(String str, int num) {
		return String.format("%1$-" + num + "s", str);
	}

}
