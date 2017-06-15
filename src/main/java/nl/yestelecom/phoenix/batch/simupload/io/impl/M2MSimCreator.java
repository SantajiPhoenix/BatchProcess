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

@Configuration
@Service
public class M2MSimCreator implements SimCreator {

    private static Logger logger = LoggerFactory.getLogger(SimLoadService.class);

    @Value("${simUpload.m2m.requestfile}")
    private String requestFileName;

    @Value("${simupload.requestfile.path}")
    private String requestPath;

    @Autowired
    CSVFileReader cSVFileReader;

    @Override
    public List<LoadSim> createSimFromFile() {

        List<LoadSim> m2mSims = new ArrayList<>();
        final List<String[]> fileSimData = cSVFileReader.parseFileData(requestPath + requestFileName);
        if (null != fileSimData && !fileSimData.isEmpty()) {
            m2mSims = createM2MSim(fileSimData);
        }
        return m2mSims;
    }

    public List<LoadSim> createM2MSim(List<String[]> sims) {
        final List<LoadSim> loadSimM2M = new ArrayList<>();
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        logger.info("Creating M2M Sim");
        for (final String[] sim : sims) {
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
            loadSim.setGsmNumber(getGsm(sim[9]));
            loadSimM2M.add(loadSim);
        }

        return loadSimM2M;

    }

    private String getGsm(String gsmNumber) {
        final String newGsmNumber = gsmNumber.substring(3, gsmNumber.length());
        return "0" + newGsmNumber;
    }

}
