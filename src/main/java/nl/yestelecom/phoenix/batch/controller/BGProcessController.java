package nl.yestelecom.phoenix.batch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.yestelecom.phoenix.batch.job.ciot.CiotProcess;
import nl.yestelecom.phoenix.batch.job.creditcontrol.CreditControlProcess;
import nl.yestelecom.phoenix.batch.job.marketpoints.MarketPointsProcess;
import nl.yestelecom.phoenix.batch.job.preventel.PreventelProcess;
import nl.yestelecom.phoenix.batch.job.scheduler.JobRunner;
import nl.yestelecom.phoenix.batch.job.simoverview.SimOverviewProcess;

@RestController
@RequestMapping("/")
public class BGProcessController {

	@Autowired
	CreditControlProcess controlProcess;
	@Autowired
	CiotProcess ciotProcess;
	@Autowired
	SimOverviewProcess simOverviewProcess;
	@Autowired
	MarketPointsProcess marketPointsProcess;
	@Autowired
	PreventelProcess preventelProcess;
	@Autowired
	JobRunner jobRunner;

	@RequestMapping(value = "/creditcontrol", method = RequestMethod.GET)
	public void runCreditControlProcess() {
		controlProcess.execute();
	}

	@RequestMapping(value = "/ciot", method = RequestMethod.GET)
	public void runCiotProcess() {
		ciotProcess.execute();
	}

	@RequestMapping(value = "/simoverview", method = RequestMethod.GET)
	public void runSimOverviewProcess() {
		simOverviewProcess.execute();
	}

	@RequestMapping(value = "/marketpoints", method = RequestMethod.GET)
	public void runMarketPointsProcess() {
		marketPointsProcess.execute();
	}

	@RequestMapping(value = "/preventel", method = RequestMethod.GET)
	public void runPreventelProcess() {
		preventelProcess.execute();
	}

	@RequestMapping(value = "/runAllJobs", method = RequestMethod.GET)
	public void runAllJobs() {
		jobRunner.runJobs();
	}
}
