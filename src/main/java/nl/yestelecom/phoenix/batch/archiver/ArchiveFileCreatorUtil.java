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
        final Date today = new Date();
        final SimpleDateFormat sds = new SimpleDateFormat("yyyyMMddHHmmSS");
        final String date = sds.format(today);
        final String archiveDir = fileDirecotry + "archive/";
        final File[] files = new File(fileDirecotry).listFiles();

        for (final File file : files) {
            if (!file.isDirectory()) {
                logger.info("File: " + (file.getName() + date).trim());
                final String archiveFileName = (file.getName() + "_" + date).trim();
                final Path src = Paths.get(fileDirecotry + file.getName());
                final Path dest = Paths.get(archiveDir + archiveFileName);
                try {
                    Files.move(src, dest, StandardCopyOption.REPLACE_EXISTING);
                } catch (final Exception e) {
                    logger.error("Error while copying >> " + e);
                }
            }
        }
    }

    public void setFileDirecotry(String fileDirecotry) {
        this.fileDirecotry = fileDirecotry;
    }
}
