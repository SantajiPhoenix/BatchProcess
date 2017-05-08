package nl.yestelecom.phoenix.batch.simupload.io.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.simupload.io.SimCreator;
import nl.yestelecom.phoenix.batch.simupload.model.LoadSim;
import nl.yestelecom.phoenix.batch.simupload.util.CSVFileReader;

@Configuration
@Service
public class M2MSimCreator implements SimCreator {

    @Value("${simUpload.m2m.requestfile}")
    private String requestFileName;

    @Value("${simupload.requestfile.path}")
    private String requestPath;

    @Autowired
    CSVFileReader cSVFileReader;

    @Override
    public List<LoadSim> createSimFromFile() {
        // TODO Auto-generated method stub
        List<LoadSim> m2mSims = new ArrayList<LoadSim>();
        final List<String[]> fileSimData = cSVFileReader.parseFileData(requestPath + requestFileName);
        if (null != fileSimData && fileSimData.size() > 0) {
            m2mSims = createM2MSim(fileSimData);
        }
        return m2mSims;
    }

    public List<LoadSim> createM2MSim(List<String[]> sims) {
        final List<LoadSim> loadSimM2M = new ArrayList<LoadSim>();

        System.out.println("Creating Sim");
        for (final String[] sim : sims) {
            final LoadSim loadSim = new LoadSim();
            loadSim.setSp(Integer.parseInt(sim[0]));
            loadSim.setCrDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
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
        // TODO Auto-generated method stub
        final String newGsmNumber = gsmNumber.substring(3, gsmNumber.length());
        return "0" + newGsmNumber;
    }

}
