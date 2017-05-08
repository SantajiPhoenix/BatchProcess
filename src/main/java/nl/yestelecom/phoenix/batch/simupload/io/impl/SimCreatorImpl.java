package nl.yestelecom.phoenix.batch.simupload.io.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.simupload.io.SimCreator;
import nl.yestelecom.phoenix.batch.simupload.model.LoadSim;
import nl.yestelecom.phoenix.batch.simupload.util.CSVFileReader;

@Service
@Configuration
public class SimCreatorImpl implements SimCreator {

    @Value("${simUpload.requestfile}")
    private String requestFileName;

    @Value("${simupload.requestfile.path}")
    private String requestPath;

    @Autowired
    CSVFileReader cSVFileReader;

    @Override
    public List<LoadSim> createSimFromFile() {
        List<LoadSim> sims = new ArrayList<LoadSim>();
        final List<String[]> fileSimData = cSVFileReader.parseFileData(requestPath + requestFileName);
        if (null != fileSimData && fileSimData.size() > 0) {
            sims = createM2MSim(fileSimData);
        }
        return sims;
    }

    private List<LoadSim> createM2MSim(List<String[]> fileSimData) {
        final List<LoadSim> sims = new ArrayList<LoadSim>();
        for (final String[] sim : fileSimData) {
            final LoadSim loadSim = new LoadSim();
            loadSim.setSp(Integer.parseInt(sim[0]));
            loadSim.setCrDate(Date.valueOf(sim[1]));
            loadSim.setSimNr(sim[2]);
            loadSim.setLongSimNr(sim[3]);
            loadSim.setImSimNr(sim[4]);
            loadSim.setType1(sim[5]);
            loadSim.setType2(sim[6]);
            loadSim.setPuk1(sim[7]);
            sims.add(loadSim);
        }
        return sims;
    }

}
