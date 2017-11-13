package nl.yestelecom.phoenix.batch.job.preventel;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.job.marketpoints.MarketPointsProcess;
import nl.yestelecom.phoenix.batch.job.marketpoints.model.MarketPoints;

@Service
public class PreventelFileFormat {

	public static final String CSV_FILE_DELIMETER = ",";
	private static Logger logger = LoggerFactory.getLogger(PreventelProcess.class);

	public List<String> formatPreventelData(List<Preventel> preventelData) {
		logger.info("Formating Preventel file");
		final List<String> valuesToWrite = new ArrayList<>();
		for (final Preventel preventel : preventelData) {

			final String value = preventel.getServiceType() + CSV_FILE_DELIMETER + preventel.getZygoNumber()
					+ CSV_FILE_DELIMETER + preventel.getRegistration() + CSV_FILE_DELIMETER + preventel.getStatus()
					+ CSV_FILE_DELIMETER + preventel.getIdProvider() + CSV_FILE_DELIMETER + preventel.getCountry()
					+ CSV_FILE_DELIMETER + preventel.getFirstname() + CSV_FILE_DELIMETER + preventel.getInitials()
					+ CSV_FILE_DELIMETER + preventel.getLastname() + CSV_FILE_DELIMETER + preventel.getIdNumber()
					+ CSV_FILE_DELIMETER + preventel.getRelationshipBusinessCode() + CSV_FILE_DELIMETER
					+ preventel.getCreatedDate() + CSV_FILE_DELIMETER + preventel.getStreet() + CSV_FILE_DELIMETER
					+ preventel.getHouseNumber() + CSV_FILE_DELIMETER + preventel.getPostCode() + CSV_FILE_DELIMETER
					+ preventel.getCity() + CSV_FILE_DELIMETER + preventel.getBankAccount() + CSV_FILE_DELIMETER
					+ preventel.getTelephone() + CSV_FILE_DELIMETER + preventel.getFax() + CSV_FILE_DELIMETER
					+ preventel.getEmail() + CSV_FILE_DELIMETER + preventel.getPostBox() + CSV_FILE_DELIMETER
					+ preventel.getPostCodePostBus();

			valuesToWrite.add(value);
		}
		return valuesToWrite;
	}

}
