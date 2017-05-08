package nl.yestelecom.phoenix.batch.simupload.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import nl.yestelecom.phoenix.batch.simupload.configuration.impl.ZygoFtpConfiguration;
import nl.yestelecom.phoenix.batch.simupload.io.impl.M2MFileCreatorImpl;
import nl.yestelecom.phoenix.batch.simupload.io.impl.M2MSimCreator;
import nl.yestelecom.phoenix.batch.simupload.model.LoadSim;
import nl.yestelecom.phoenix.batch.simupload.repo.SimLoadRepository;
import nl.yestelecom.phoenix.batch.simupload.util.FileSender;
import nl.yestelecom.phoenix.batch.simupload.util.SimUploadUtil;
import nl.yestelecom.phoenix.connection.repo.GsmNumberTypeRepo;
import nl.yestelecom.phoenix.connection.repo.GsmStatusRepository;
import nl.yestelecom.phoenix.connection.repo.GsmTypeRepo;
import nl.yestelecom.phoenix.connection.repo.SimStatusRepository;
import nl.yestelecom.phoenix.deal.model.Dealer;
import nl.yestelecom.phoenix.deal.repo.DealerRepository;
import nl.yestelecom.phoenix.sim.model.GsmNumber;
import nl.yestelecom.phoenix.sim.model.GsmNumberType;
import nl.yestelecom.phoenix.sim.model.GsmStatus;
import nl.yestelecom.phoenix.sim.model.GsmType;
import nl.yestelecom.phoenix.sim.model.Sim;
import nl.yestelecom.phoenix.sim.model.SimStatus;
import nl.yestelecom.phoenix.sim.repository.GsmNumberRepository;
import nl.yestelecom.phoenix.sim.repository.SimRepository;

@Service
@Configuration
public class SimLoadM2MService {

    @Value("${simUpload.m2m.response.file}")
    private String responseFileName;

    @Value("${simupload.requestfile.path}")
    private String requestPath;

    @Autowired
    SimLoadRepository simLoadRepository;
    @Autowired
    SimRepository simRepository;
    @Autowired
    SimStatusRepository simStatusRepository;
    @Autowired
    GsmStatusRepository gsmStatusRepository;
    @Autowired
    GsmNumberTypeRepo gsmNumberTypeRepo;
    @Autowired
    GsmTypeRepo gsmTypeRepo;
    @Autowired
    GsmNumberRepository gsmNumberRepository;

    @Autowired
    M2MSimCreator m2MSimCreator;
    @Autowired
    M2MFileCreatorImpl m2MFileCreatorImpl;
    @Autowired
    ZygoFtpConfiguration zygoFtpConfiguration;
    @Autowired
    FileSender fileSender;
    @Autowired
    SimUploadUtil simUploadUtil;

    @Autowired
    DealerRepository dealerRepository;

    public void process(String simDetails) {
        List<LoadSim> simList = new ArrayList<>();
        List<Sim> simListToSave = new ArrayList<>();
        List<GsmNumber> gsmNumberToSave = new ArrayList<>();
        simList = m2MSimCreator.createSimFromFile();
        System.out.println("Size is >> " + simList.size());

        simLoadRepository.deleteAll();
        simLoadRepository.save(simList);

        simListToSave = getSimsToLoad(simList);
        gsmNumberToSave = getGsmNumberToSave(simList);

        simRepository.save(simListToSave);
        gsmNumberRepository.save(gsmNumberToSave);
        extractSimsForZygo(simList);

        System.out.println("Process Completed");

    }

    private void extractSimsForZygo(List<LoadSim> simList) {
        final String filename = simUploadUtil.getFileName(responseFileName);
        m2MFileCreatorImpl.writeData(simList, filename);
        fileSender.send(zygoFtpConfiguration, requestPath + responseFileName);
    }

    private List<GsmNumber> getGsmNumberToSave(List<LoadSim> simList) {
        final GsmStatus gsmStatus = gsmStatusRepository.findByCode("FREE");
        final GsmNumberType gsmNumberType = gsmNumberTypeRepo.findByCode("DATAONLY");
        final GsmType gsmType = gsmTypeRepo.findByCode("YESTEL");
        final List<GsmNumber> gsmNumberToSave = new ArrayList<>();
        for (final LoadSim sim : simList) {
            final GsmNumber gsmNumber = GsmNumber.builder().portingData(null).gsmStatus(gsmStatus).customer(null).gsmNo(sim.getGsmNumber()).gsmType(gsmType).isNice(false).gsmNumberType(gsmNumberType)
                    .build();
            gsmNumberToSave.add(gsmNumber);
        }

        return gsmNumberToSave;
    }

    private List<Sim> getSimsToLoad(List<LoadSim> simList) {
        final Dealer dealer = dealerRepository.findById(new Long(1));
        final SimStatus unassigned = simStatusRepository.findByCode("UNASSIGNED");
        final List<Sim> simListToSave = new ArrayList<>();
        for (final LoadSim loadSim : simList) {
            final Sim sim = Sim.builder().imsinr(loadSim.getImSimNr()).simNo(loadSim.getSimNr().toString()).longSimNr(loadSim.getLongSimNr().trim()).dealer(dealer).customer(null)
                    .puk1(loadSim.getPuk1()).type2("M2M").type1(loadSim.getType1().trim()).simStatus(unassigned).sp(new Integer(14)).createdDate(loadSim.getCrDate()).build();
            simListToSave.add(sim);

        }
        return simListToSave;
    }

}
