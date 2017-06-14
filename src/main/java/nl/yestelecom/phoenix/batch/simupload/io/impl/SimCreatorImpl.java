package nl.yestelecom.phoenix.batch.simupload.io.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.simupload.io.SimCreator;
import nl.yestelecom.phoenix.batch.simupload.model.LoadSim;
import nl.yestelecom.phoenix.batch.simupload.service.SimLoadService;
import nl.yestelecom.phoenix.batch.simupload.util.CSVFileReader;

@Service
@Configuration
public class SimCreatorImpl implements SimCreator {

    private static Logger logger = LoggerFactory.getLogger(SimLoadService.class);

    @Value("${simUpload.requestfile}")
    private String requestFileName;

    @Value("${simupload.requestfile.path}")
    private String requestPath;

    @Autowired
    CSVFileReader cSVFileReader;

    @Override
    public List<LoadSim> createSimFromFile() {
        List<LoadSim> sims = new ArrayList<>();
        final List<String[]> fileSimData = cSVFileReader.parseFileData(requestPath + requestFileName);
        if (null != fileSimData && !fileSimData.isEmpty()) {
            sims = createSim(fileSimData);
        }
        return sims;
    }

    private List<LoadSim> createSim(List<String[]> fileSimData) {
        final SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
        final List<LoadSim> sims = new ArrayList<>();
        for (final String[] sim : fileSimData) {
            final LoadSim loadSim = new LoadSim();
            loadSim.setSp(Integer.parseInt(sim[0]));
            try {
                loadSim.setCrDate(formatter.parse(sim[1]));
            } catch (final ParseException e) {
                logger.error(e.toString());
            }
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
