package com.harman.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

public class MariaModel implements MariaStructure {

	static MariaModel mariaModel;

	public static MariaModel getInstance() {
		if (mariaModel == null)
			mariaModel = new MariaModel();
		return mariaModel;
	}

	public String getDeviceInformation(String device_id) {
		Connection conn = null;
		Statement stmt = null;
		JSONArray jsonArray = new JSONArray();
		try {
			// STEP 2: Register JDBC driver
			Class.forName("org.mariadb.jdbc.Driver");
			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost/DEVICE_INFO_STORE", "root", "");
			System.out.println("Connected database successfully...");
			stmt = conn.createStatement();
			ResultSet rs;
			String query;
			if (device_id != null && !device_id.equalsIgnoreCase("")) {
				query = "SELECT * FROM device_registration WHERE device_id=" + device_id;
			} else
				query = "SELECT * FROM device_registration";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("device_id", rs.getString("device_id"));
				jsonObject.put("device_model", rs.getString("device_model"));
				jsonObject.put("operations_name", rs.getString("operations_name"));
				jsonObject.put("operations_params", rs.getString("operations_params"));
				jsonObject.put("fw_version", rs.getString("fw_version"));
				jsonObject.put("sw_version", rs.getString("sw_version"));
				jsonObject.put("connection", rs.getString("connection"));
				jsonArray.put(jsonObject);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					conn.close();
				}
			} catch (SQLException se) {
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		if (jsonArray.length() == 0) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("message", "Record are not found in tables.");
			return jsonObject.toString();
		} else
			return jsonArray.toString();
	}

	public String insertDeviceInformation(String device_id, String device_model, String operations_name,
			String operations_params, String fw_version, String sw_version, String connection) {
		Connection conn = null;
		Statement stmt = null;
		int response = 0;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("org.mariadb.jdbc.Driver");
			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost/DEVICE_INFO_STORE", "root", "");
			System.out.println("Connected database successfully...");
			// STEP 4: Execute a query
			System.out.println("Creating table in given database...");
			stmt = conn.createStatement();

			Statement statement = conn.createStatement();
			String query = "INSERT INTO `device_info_table`(device_id,device_model,operations_name,operations_params,fw_version,sw_version,connection) VALUE ('"
					+ device_id + "','" + device_model + "','" + operations_name + "','" + operations_params + "','"
					+ fw_version + "','" + sw_version + "','" + connection + "')";
			response = statement.executeUpdate(query);

		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("SQLException " + se.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SQLException " + e.getMessage());
		} finally {
			try {
				if (stmt != null) {
					conn.close();
				}
			} catch (SQLException se) {
				System.out.println("SQLException while closing data");
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return String.valueOf(response);
	}

}
