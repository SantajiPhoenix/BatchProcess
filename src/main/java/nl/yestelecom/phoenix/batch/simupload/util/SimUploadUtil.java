package nl.yestelecom.phoenix.batch.simupload.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class SimUploadUtil {

    public String getFileName(String fileName) {
        final SimpleDateFormat dt2 = new SimpleDateFormat("yyyyMMddHHmm");
        final String date = dt2.format(new Date());
        return fileName + date + ".txt";
    }

}
