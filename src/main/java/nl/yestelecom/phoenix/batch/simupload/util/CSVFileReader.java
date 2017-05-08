package nl.yestelecom.phoenix.batch.simupload.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CSVFileReader {
    public List<String[]> parseFileData(String filaName) {
        final List<String[]> simsInFile = new ArrayList<>();
        BufferedReader br = null;
        String line = "";
        final String cvsSplitBy = ";";
        try {
            br = new BufferedReader(new FileReader(filaName));
            while ((line = br.readLine()) != null) {
                // System.out.println("Data is >> "+line);
                final String[] sims = line.split(cvsSplitBy);
                simsInFile.add(sims);

            }

        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return simsInFile;

    }

}
