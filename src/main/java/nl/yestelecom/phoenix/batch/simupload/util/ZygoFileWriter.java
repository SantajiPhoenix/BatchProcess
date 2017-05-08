package nl.yestelecom.phoenix.batch.simupload.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ZygoFileWriter {

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
            bw.close();
        } catch (final Exception e) {
            System.out.println("Exceptin is >> " + e);
        }
    }

}
