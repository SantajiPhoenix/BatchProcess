package nl.yestelecom.phoenix.batch.simupload.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ZygoFileWriter {

    private static final Logger LOG = LoggerFactory.getLogger(FileSender.class);

    public void generateZygoFile(String header, String trailer, List<String> data, String fileName) {

        try {
            final File file = new File(fileName);

            if (!file.exists()) {
                file.createNewFile();
            }
            final FileWriter fw = new FileWriter(file.getAbsoluteFile());
            final BufferedWriter bw = new BufferedWriter(fw);
            bw.write(header);
            bw.write("\n\n");
            for (final String str : data) {
                bw.write(str);
                bw.write("\n");
            }
            bw.write("\n");
            bw.write(trailer);
            fw.close();
            bw.close();
        } catch (final Exception e) {
            LOG.error("Exceptin is >> " + e);
        }
    }

    public void generateZygoPukFile(List<String> data, String fileName) {

        try {
            final File file = new File(fileName);

            if (!file.exists()) {
                file.createNewFile();
            }
            final FileWriter fw = new FileWriter(file.getAbsoluteFile());
            final BufferedWriter bw = new BufferedWriter(fw);
            bw.write("\n\n");
            for (final String str : data) {
                bw.write(str);
                bw.newLine();
            }
            bw.write("\n");
            fw.close();
            bw.close();
        } catch (final Exception e) {
            LOG.error("Exceptin is >> " + e);
        }
    }

}
