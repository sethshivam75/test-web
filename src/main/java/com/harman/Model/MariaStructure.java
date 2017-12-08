package com.harman.Model;

public interface MariaStructure {

	public String getDeviceInformation(String macID);

	public String insertDeviceInformation(String device_id, String device_model, String operations_name,
			String operations_params, String fw_version, String sw_version, String connection);

}
