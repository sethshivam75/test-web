package com.harman.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String displayLogin() {
		System.out.println("******************************");
		String returnStr = new Calculater().getDataFromMongo();
		return returnStr;
	}

	@RequestMapping(value = "/saveData", method = RequestMethod.GET)
	public String saveData(String device_id, String device_model, String operations_name, String operations_params,
			String fw_version, String sw_version, String connection) {

		Calculater calculater = new Calculater();
		String response = calculater.updateRealTimeMariaDB(device_id, device_model, operations_name, operations_params,
				fw_version, sw_version, connection);
		System.out.println(response);
		return response;
	}

}
