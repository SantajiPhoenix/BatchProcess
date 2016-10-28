package nl.yestelecom.phoenix.batch.archiver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ArchiveFileCreatorUtil {

	private static Logger logger = LoggerFactory.getLogger(ArchiveFileCreatorUtil.class);

	private String fileDirecotry;

	public void archiveCurrentFile() {
		logger.info("Archiving files");
		Date today = new Date();
		SimpleDateFormat sds = new SimpleDateFormat("yyyyMMddHHmmSS");
		String date = sds.format(today);
		String archiveDir = fileDirecotry + "archive/";
		File[] files = new File(fileDirecotry).listFiles();

		for (File file : files) {
			if (!file.isDirectory()) {
				logger.info("File: " + (file.getName() + date).trim());
				String archiveFileName = (file.getName() + "_" + date).trim();
				Path src = Paths.get(fileDirecotry + file.getName());
				Path dest = Paths.get(archiveDir + archiveFileName);
				try {
					Files.move(src, dest, StandardCopyOption.REPLACE_EXISTING);
				} catch (Exception e) {
					logger.error("Error while copying >> " + e);
				}
			}
		}
	}

	public void setFileDirecotry(String fileDirecotry) {
		this.fileDirecotry = fileDirecotry;
	}
}
