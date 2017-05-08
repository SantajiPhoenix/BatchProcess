package nl.yestelecom.phoenix.batch.simupload.io.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.simupload.io.FileCreator;
import nl.yestelecom.phoenix.batch.simupload.model.LoadSim;
import nl.yestelecom.phoenix.batch.simupload.util.ZygoFileWriter;

@Service
@Configuration
public class M2MFileCreatorImpl implements FileCreator {

    @Value("${simUpload.m2m.response.file}")
    private String responseFileName;

    @Value("${simupload.requestfile.path}")
    private String requestPath;

    @Autowired
    ZygoFileWriter zygoFileWriter;

    @Override
    public void writeData(List<LoadSim> sims, String filename) {
        // TODO Auto-generated method stub
        final String header = getHeader(sims);
        final String trailer = getTrailer(sims);
        final List<String> data = getSimData(sims);
        final String fileName = getFileName(responseFileName);
        zygoFileWriter.generateZygoFile(header, trailer, data, requestPath + fileName);

    }

    // TODO rewrite type2 code
    private List<String> getSimData(List<LoadSim> sims) {
        final SimpleDateFormat dt2 = new SimpleDateFormat("yyyyMMdd");
        final List<String> simData = new ArrayList<>();
        for (final LoadSim sim : sims) {
            String lineForZygo = "";
            lineForZygo = lineForZygo + sim.getSp();
            lineForZygo = lineForZygo + "  " + dt2.format(sim.getCrDate());
            lineForZygo = lineForZygo + "  " + sim.getSimNr();
            lineForZygo = lineForZygo + "  " + sim.getLongSimNr();
            lineForZygo = lineForZygo + "  " + sim.getImSimNr();
            lineForZygo = lineForZygo + "     " + "PLUG-IN S";
            lineForZygo = lineForZygo + "   " + getType2(sim.getType2());
            lineForZygo = lineForZygo + "    " + "00000000";
            simData.add(lineForZygo);
        }
        return simData;
    }

    private String getTrailer(List<LoadSim> sims) {

        return "__einde mcp156ai";
    }

    private String getHeader(List<LoadSim> sims) {
        final Date date = new Date();
        final SimpleDateFormat dt = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
        String header = "\n" + "__ mcp156ai __________________________Gegenereerd op :";
        header = header + " " + dt.format(date);
        header = header + "\nICC-nr vanaf  : " + sims.get(0).getLongSimNr().substring(10, 18);
        header = header + "\nICC-nr tot    : " + sims.get(sims.size() - 1).getLongSimNr().substring(10, 18);
        header = header + "\n" + "__________________________________________";

        return header;
    }

    private String getType2(String fileType2) {
        String type2 = "JSP_EMIC";
        if ("MICRO USIM".equals(fileType2) || "NANO USIM".equals(fileType2) || "YES USIM".equals(fileType2)) {
            type2 = "YES USIM";
        } else if ("YES USIM DUO".equals(fileType2)) {
            type2 = "YES DUO U";
        } else if ("Y32K".equals(fileType2)) {
            type2 = "Y32K";
        } else if ("Y32KDUO".equals(fileType2)) {
            type2 = "Y32KDUO";
        } else if ("M2M".equals(fileType2)) {
            type2 = "JSP_EMIC";
        }
        return type2;
    }

    private String getFileName(String responseFileName) {
        final SimpleDateFormat dt2 = new SimpleDateFormat("yyyyMMdd-HHmm");
        final String date = dt2.format(new Date());
        final String fileName = responseFileName + date + ".txt";
        return fileName;
    }
}
