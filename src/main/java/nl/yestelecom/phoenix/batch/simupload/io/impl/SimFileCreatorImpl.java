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
public class SimFileCreatorImpl implements FileCreator {
    @Value("${simUpload.response.file}")
    private String responseFileName;

    @Value("${simupload.requestfile.path}")
    private String requestPath;

    public static final String YESUSIM = "YES USIM";
    public static final String TRAILERTEXT = "__einde mcp156ai";

    @Autowired
    ZygoFileWriter zygoFileWriter;

    @Override
    public void writeData(List<LoadSim> sims, String fileName) {
        final String header = getHeader(sims);
        final String trailer = getTrailer();
        final List<String> data = getSimData(sims);
        zygoFileWriter.generateZygoFile(header, trailer, data, requestPath + fileName);

    }

    @Override
    public void writePukData(List<LoadSim> sims, String fileName) {
        final List<String> data = getPukSimData(sims);
        zygoFileWriter.generateZygoPukFile(data, requestPath + fileName);
    }

    private List<String> getSimData(List<LoadSim> loadSimsForZygo) {
        final SimpleDateFormat dt2 = new SimpleDateFormat("yyyyMMdd");
        final List<String> simData = new ArrayList<>();
        for (final LoadSim sim : loadSimsForZygo) {
            String lineForZygo = "";
            lineForZygo = lineForZygo + sim.getSp();
            lineForZygo = lineForZygo + "  " + dt2.format(sim.getCrDate());
            lineForZygo = lineForZygo + "  " + sim.getSimNr();
            lineForZygo = lineForZygo + "  " + sim.getLongSimNr();
            lineForZygo = lineForZygo + "  " + sim.getImSimNr();
            lineForZygo = lineForZygo + "     " + "PLUG-IN S";
            lineForZygo = lineForZygo + "   " + getType2ForZygo(sim.getType2());
            lineForZygo = lineForZygo + "    " + "00000000";
            simData.add(lineForZygo);
        }
        return simData;
    }

    private List<String> getPukSimData(List<LoadSim> loadSimsForZygo) {
        final List<String> simData = new ArrayList<>();
        for (final LoadSim sim : loadSimsForZygo) {
            String lineForZygo = "";
            lineForZygo = lineForZygo + "  " + sim.getImSimNr();
            lineForZygo = lineForZygo + "|" + sim.getPuk1();
            lineForZygo = lineForZygo + "|" + null;
            lineForZygo = lineForZygo + "|" + null;
            lineForZygo = lineForZygo + "|" + null;
            simData.add(lineForZygo);
        }
        return simData;
    }

    private String getType2ForZygo(String fileType2) {
        String type2 = "YES USIM";

        if ("YES USIM DUO".equals(fileType2) || "3 IN 1 DUO".equals(type2)) {
            type2 = "YES DUO U";

        } else if ("Y32K".equals(type2)) {
            type2 = "Y32K";

        } else if ("MICRO USIM".equals(type2) || "NANO USIM".equals(type2) || YESUSIM.equals(type2)) {
            type2 = YESUSIM;

        } else if ("Y32KDUO".equals(type2)) {
            type2 = "Y32KDUO";

        }
        return type2;
    }

    private String getTrailer() {
        return TRAILERTEXT;
    }

    private String getHeader(List<LoadSim> loadSimsForZygo) {
        final Date date = new Date();
        final SimpleDateFormat dt = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
        String header = "\n" + "__ mcp156ai __________________________Gegenereerd op :";
        header = header + " " + dt.format(date);
        header = header + "\n" + "ICC-nr vanaf  : " + loadSimsForZygo.get(0).getSimNr();
        header = header + "\n" + "ICC-nr tot    : " + loadSimsForZygo.get(loadSimsForZygo.size() - 1).getSimNr();
        header = header + "\n" + "__________________________________________";
        return header;
    }

}
