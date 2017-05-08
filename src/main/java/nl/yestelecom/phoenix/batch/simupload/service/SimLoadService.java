package nl.yestelecom.phoenix.batch.simupload.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.simupload.configuration.impl.ZygoFtpConfiguration;
import nl.yestelecom.phoenix.batch.simupload.io.impl.SimCreatorImpl;
import nl.yestelecom.phoenix.batch.simupload.io.impl.SimFileCreatorImpl;
import nl.yestelecom.phoenix.batch.simupload.model.LoadSim;
import nl.yestelecom.phoenix.batch.simupload.repo.SimLoadRepository;
import nl.yestelecom.phoenix.batch.simupload.util.FileSender;
import nl.yestelecom.phoenix.batch.simupload.util.JsonToObjectConverter;
import nl.yestelecom.phoenix.batch.simupload.util.SimUploadUtil;
import nl.yestelecom.phoenix.connection.repo.SimStatusRepository;
import nl.yestelecom.phoenix.sim.model.Sim;
import nl.yestelecom.phoenix.sim.model.SimStatus;
import nl.yestelecom.phoenix.sim.repository.SimRepository;

@Service
@Transactional
@Configuration
public class SimLoadService {

    @Value("${simUpload.response.file}")
    private String responseFileName;

    @Value("${simupload.requestfile.path}")
    private String requestPath;

    @Autowired
    private JsonToObjectConverter convertor;

    @Autowired
    SimLoadRepository simLoadRepository;

    @Autowired
    SimRepository simRepository;

    @Autowired
    SimStatusRepository simStatusRepository;

    @Autowired
    SimCreatorImpl simCreatorImpl;

    @Autowired
    SimFileCreatorImpl simFileCreatorImpl;

    @Autowired
    ZygoFtpConfiguration zygoFtpConfiguration;

    @Autowired
    FileSender fileSender;

    @Autowired
    SimUploadUtil simUploadUtil;

    static public final String fileName = "mcp.txt";

    public void processSimDetails(String simDetails) {
        List<LoadSim> simList = new ArrayList<>();
        final List<LoadSim> simListToSaveInLoad = new ArrayList<>();
        final List<Sim> simListToSave = new ArrayList<>();
        final SimStatus unassigned = simStatusRepository.findByCode("UNASSIGNED");
        try {
            simList = simCreatorImpl.createSimFromFile();
            System.out.println("Data is >> " + simList.get(0).getCrDate());
            simLoadRepository.deleteAll();

            for (final LoadSim loadSim : simList) {
                final String kpnType = simLoadRepository.getKpnType(loadSim.getType2().trim());
                if (null != kpnType && !"".equals(kpnType)) {
                    loadSim.setType2(kpnType);
                }
                final Sim sim = buildSim(loadSim, unassigned);
                simListToSave.add(sim);
                simListToSaveInLoad.add(loadSim);

            }

            simLoadRepository.save(simListToSaveInLoad);

            simRepository.save(simListToSave);

            extractSimsForZygo(simListToSaveInLoad);

        } catch (final Exception e) {
            System.out.println("Error is >> " + e);
        }
    }

    private void extractSimsForZygo(List<LoadSim> simListToSaveInLoad) {
        final String filename = simUploadUtil.getFileName(responseFileName);
        simFileCreatorImpl.writeData(simListToSaveInLoad, filename);
        fileSender.send(zygoFtpConfiguration, requestPath + responseFileName);

    }

    private Sim buildSim(LoadSim loadSim, SimStatus unassigned) {
        // @formatter:off
		return  Sim.builder()
				.imsinr(loadSim.getImSimNr())
				.simNo(loadSim.getSimNr().toString())
				.longSimNr(loadSim.getLongSimNr().trim())
				.dealer(null)
				.customer(null)
				.puk1(loadSim.getPuk1())
				.type2(loadSim.getType2().trim())
				.type1(loadSim.getType1().trim())
				.simStatus(unassigned)
				.sp(new Integer(loadSim.getSp()))
				.createdDate(loadSim.getCrDate())
				.build();
		   // @formatter:on

    }

}
