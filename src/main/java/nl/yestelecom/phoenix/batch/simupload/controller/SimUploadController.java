package nl.yestelecom.phoenix.batch.simupload.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.yestelecom.phoenix.batch.simupload.service.SimLoadM2MService;
import nl.yestelecom.phoenix.batch.simupload.service.SimLoadService;

@RestController
@RequestMapping(value = "/simupload")
// @CrossOrigin(origins = "http://localhost:8080")
public class SimUploadController {
    @Autowired
    SimLoadService simLoadService;

    @Autowired
    SimLoadM2MService simLoadM2MService;

    @RequestMapping(value = "/loadSim", method = RequestMethod.GET)
    @Scheduled(fixedDelay = 3_00_000)
    public void getSimList() {
        simLoadService.processSimDetails();
    }

    @RequestMapping(value = "/loadpuk", method = RequestMethod.POST)
    public void getpukSimlist() {
        simLoadService.processPukDetails();
    }

    @RequestMapping(value = "/loadM2MSim", method = RequestMethod.POST)
    public void getM2MSimList() {
        simLoadM2MService.process();
    }

}
