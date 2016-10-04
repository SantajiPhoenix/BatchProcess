package nl.yestelecom.phoenix.batch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.yestelecom.phoenix.batch.job.creditcontrol.CreditControlProcess;

@RestController
@RequestMapping("/")
public class BGProcessController {

	@Autowired
	CreditControlProcess controlProcess;

	@RequestMapping(value = "/creditcontrol", method = RequestMethod.GET)
	public void run() {
		controlProcess.execute();
	}

}
