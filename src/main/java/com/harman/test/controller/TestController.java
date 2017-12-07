package com.harman.test.controller;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cloudApp")
public class TestController {

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String displayLogin() {
		System.out.println("******************************");
		// String returnStr = new Calculater().getDataFromMongo();
		return "Success";
	}

	/*
	 * @RequestMapping(value = "/seeData", method = RequestMethod.POST)
	 * public @ResponseBody String getNewElement(){
	 * 
	 * String retVal = "ok"; return "success"; }
	 */
	@RequestMapping(value = "/saveData", method = RequestMethod.POST)
	public @ResponseBody String saveData(@RequestBody String test) {
		JSONObject retunResponse = new JSONObject();
		try {
			JSONObject jsonObject = new JSONObject(test);
			String device_id = jsonObject.getString("device_id");
			String device_model = jsonObject.getString("device_model");
			String operations_name = jsonObject.getString("operations_name");
			String operations_params = jsonObject.getString("operations_params");
			String fw_version = jsonObject.getString("fw_version");
			String sw_version = jsonObject.getString("sw_version");
			String connection = jsonObject.getString("connection");
			Calculater calculater = new Calculater();
			String response = calculater.updateRealTimeMariaDB(device_id, device_model, operations_name,
					operations_params, fw_version, sw_version, connection);
			System.out.println(response);
			retunResponse.put("status", response);
			if (response.equals("1"))
				retunResponse.put("message", "Data record inserted successfully!");
			else
				retunResponse.put("message", "Data record insertion failed!");
		} catch (Exception e) {
			retunResponse.put("status", "0");
			retunResponse.put("message", "Invalid json format received.");
			System.out.println("fail to parse");
		}
		return retunResponse.toString();
	}
}

/*
 * public String saveData(String device_id, String device_model, String
 * operations_name, String operations_params, String fw_version, String
 * sw_version, String connection) {
 * 
 * Calculater calculater = new Calculater(); String response =
 * calculater.updateRealTimeMariaDB(device_id, device_model, operations_name,
 * operations_params, fw_version, sw_version, connection);
 * System.out.println(response); return response; }
 * 
 * }
 */