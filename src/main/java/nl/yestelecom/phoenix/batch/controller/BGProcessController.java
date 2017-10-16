package nl.yestelecom.phoenix.batch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.yestelecom.phoenix.batch.job.scheduler.BatchJobRunner;

@RestController
@RequestMapping("/")
public class BGProcessController {

    @Autowired
    private BatchJobRunner jobRunner;

    @RequestMapping(value = "/creditcontrol", method = RequestMethod.GET)
    public void runCreditControlProcess() {
        jobRunner.runCreditControlProcess();
    }

    @RequestMapping(value = "/ciot", method = RequestMethod.GET)
    public void runCiotProcess() {
        jobRunner.runCiotProcess();
    }

    @RequestMapping(value = "/simoverview", method = RequestMethod.GET)
    public void runSimOverviewProcess() {
        jobRunner.runSimOverviewProcess();
    }

    @RequestMapping(value = "/marketpoints", method = RequestMethod.GET)
    public void runMarketPointsProcess() {
        jobRunner.runMarketPointsProcess();

    }

    @RequestMapping(value = "/preventel", method = RequestMethod.GET)
    public void runPreventelProcess() {
        jobRunner.runPreventelProcess();
    }

    @RequestMapping(value = "/vasrecon", method = RequestMethod.GET)
    public void runVasReconProcess() {
        jobRunner.runVasRecon();
    }

    @RequestMapping(value = "/runAllJobs", method = RequestMethod.GET)
    public void runAllJobs() {
        jobRunner.runJobs();
    }

}
