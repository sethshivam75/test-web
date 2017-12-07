package com.harman.test.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Calculater {

	public String getDataFromMongo() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("org.mariadb.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost/hawkbit", "root", "abcd123");
			System.out.println("Connected database successfully...");

			// STEP 4: Execute a query
			System.out.println("Creating table in given database...");
			stmt = conn.createStatement();
			ResultSet rs;
			String query = "SELECT * FROM device_registration";
			rs = stmt.executeQuery(query);
			String result = "";
			while (rs.next()) {
				// 'col1' refers to the value in each row in a column called
				// col1 in you table
				result += "Mac Id :-" + rs.getString("MACID") + ", Last seen Time:-" + rs.getString("LastSeenTime")
						+ "\n";
			}
			return result;
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
		return "Failed to load";

	}

	public String updateRealTimeMariaDB(String device_id, String device_model, String operations_name,
			String operations_params, String fw_version, String sw_version, String connection) {
		Connection conn = null;
		Statement stmt = null;
		int response=0;
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
			System.out.println("SQLException "+se.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SQLException "+e.getMessage());
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
		return String.valueOf(response) ;

	}

}
