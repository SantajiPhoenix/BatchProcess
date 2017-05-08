package nl.yestelecom.phoenix.batch.simupload.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.yestelecom.phoenix.batch.simupload.service.SimLoadM2MService;
import nl.yestelecom.phoenix.batch.simupload.service.SimLoadService;

@RestController
@RequestMapping(value = "/simupload")
@CrossOrigin(origins = "http://localhost:8080")
public class SimUploadController {
    @Autowired
    SimLoadService simLoadService;

    @Autowired
    SimLoadM2MService simLoadM2MService;

    @RequestMapping(value = "/loadSim", method = RequestMethod.POST)
    public void getSimList(@RequestBody String inputData) {
        System.out.println("Data coming in >> " + inputData);
        simLoadService.processSimDetails(inputData);
    }

    @RequestMapping(value = "/loadM2MSim", method = RequestMethod.POST)
    public void getM2MSimList(@RequestBody String inputData) {
        System.out.println("Data coming in >> " + inputData);
        simLoadM2MService.process(inputData);
    }

}
