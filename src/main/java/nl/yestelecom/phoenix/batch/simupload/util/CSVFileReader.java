package nl.yestelecom.phoenix.batch.simupload.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.archiver.ArchiveFileCreatorUtil;

@Service
public class CSVFileReader {

    private static Logger logger = LoggerFactory.getLogger(ArchiveFileCreatorUtil.class);

    public List<String[]> parseFileData(String fileName) {
        final List<String[]> simsInFile = new ArrayList<>();
        BufferedReader br = null;
        FileReader fr = null;
        String line = "";
        final String cvsSplitBy = ";";
        try {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                final String[] sims = line.split(cvsSplitBy);
                simsInFile.add(sims);

            }

        } catch (final FileNotFoundException e) {
            logger.error("File Not found : " + e);
        } catch (final IOException e) {
            logger.error("IO Error : " + e);
        } finally {
            try {
                if (null != br) {
                    br.close();
                }
                if (null != fr) {
                    fr.close();
                }
            } catch (final IOException e) {
                logger.error("file close failed Error : " + e);
            }
        }

        return simsInFile;

    }

}
