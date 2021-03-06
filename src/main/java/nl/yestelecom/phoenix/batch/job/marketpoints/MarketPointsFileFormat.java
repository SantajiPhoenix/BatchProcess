package nl.yestelecom.phoenix.batch.job.marketpoints;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.job.marketpoints.model.MarketPoints;
import nl.yestelecom.phoenix.batch.job.marketpoints.model.MarketPointsTotal;

@Service
public class MarketPointsFileFormat {
    public static final String CSV_FILE_DELIMETER = ",";
    private static Logger logger = LoggerFactory.getLogger(MarketPointsProcess.class);

    public List<String> formatMarketPointsContractFileData(List<MarketPoints> marketPoints) {
        logger.info("Formating Contract file");
        final List<String> valuesToWrite = new ArrayList<>();
        for (final MarketPoints mp : marketPoints) {
            final String value = mp.getVoicePoints() + CSV_FILE_DELIMETER + mp.getDataPoints() + CSV_FILE_DELIMETER + mp.getProductPoints() + CSV_FILE_DELIMETER + mp.getCode() + CSV_FILE_DELIMETER
                    + mp.getDealerName() + CSV_FILE_DELIMETER + mp.getAccountManager() + CSV_FILE_DELIMETER + mp.getAcceptedDate() + CSV_FILE_DELIMETER + mp.getContractNr() + CSV_FILE_DELIMETER;
            valuesToWrite.add(value);
        }
        return valuesToWrite;
    }

    public List<String> formatMarketPointsTotaalFileData(List<MarketPointsTotal> marketPoints) {
        logger.info("Formating Totaal file");
        final List<String> valuesToWrite = new ArrayList<>();
        for (final MarketPointsTotal mpt : marketPoints) {
            final String value = mpt.getVoicePoints() + CSV_FILE_DELIMETER + mpt.getDataPoints() + CSV_FILE_DELIMETER + mpt.getProductPoints() + CSV_FILE_DELIMETER + mpt.getTotal()
                    + CSV_FILE_DELIMETER + mpt.getUsedPoints() + CSV_FILE_DELIMETER + mpt.getGrandTotal() + CSV_FILE_DELIMETER + mpt.getCode() + CSV_FILE_DELIMETER + mpt.getDealerName()
                    + CSV_FILE_DELIMETER + mpt.getAccountManager() + CSV_FILE_DELIMETER;
            valuesToWrite.add(value);
        }
        return valuesToWrite;
    }

    public List<String> formatMarketPointsMergedData(List<Object[]> marketPoints) {
        logger.info("Formating Mergeed file");
        final List<String> valuesToWrite = new ArrayList<>();
        for (final Object[] mp : marketPoints) {
            String value = "";
            for (final Object element : mp) {
                value = value + element + CSV_FILE_DELIMETER;
            }
            valuesToWrite.add(value);
        }

        return valuesToWrite;
    }

}
