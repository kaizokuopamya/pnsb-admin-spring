package com.itl.pns.jdbc;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
public class MySqlJDBCConnection {
	private static Logger LOGGER = Logger.getLogger(MySqlJDBCConnection.class);

	/*
	private static final String MY_SQL_DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String MY_SQL_DB_CONNECTION = "jdbc:mysql://172.25.1.11:3306/mjbl_replica_uat";
	private static final String MY_SQL_DB_USER = "bots";
	private static final String MY_SQL_DB_PASSWORD = "infra#2017";

	private static final String MY_SQL_DB_CONNECTION_TD = "jdbc:mysql://172.25.1.11:3306/mjbl_replica_uat";
	private static final String MY_SQL_DB_USER_TD = "bots";
	private static final String MY_SQL_DB_PASSWORD_TD = "infra#2017";*/
	
	
	
	//Dev Connections
	
	private static final String MY_SQL_DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String MY_SQL_DB_CONNECTION = "jdbc:oracle:thin:@172.25.0.201:1521/orclsez";
	private static final String MY_SQL_DB_USER = "omni";
	private static final String MY_SQL_DB_PASSWORD = "seepz#123";

	private static final String MY_SQL_DB_CONNECTION_TD = "jdbc:oracle:thin:@172.25.0.201:1521/orclsez";
	private static final String MY_SQL_DB_USER_TD = "omni";
	private static final String MY_SQL_DB_PASSWORD_TD = "seepz#123";
	
	
	//UAT Connections
	
	/*private static final String MY_SQL_DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String MY_SQL_DB_CONNECTION = "jdbc:oracle:thin:@172.24.144.34:1521/PSBUAT";
	private static final String MY_SQL_DB_USER = "omni";
	private static final String MY_SQL_DB_PASSWORD = "omni123";

	private static final String MY_SQL_DB_CONNECTION_TD = "jdbc:oracle:thin:@172.24.144.34:1521/PSBUAT";
	private static final String MY_SQL_DB_USER_TD = "omni";
	private static final String MY_SQL_DB_PASSWORD_TD = "omni123";*/

	public static Connection getMysqlDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(MY_SQL_DB_DRIVER);
		} catch (ClassNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
		}

		try {
			dbConnection = DriverManager.getConnection(MY_SQL_DB_CONNECTION, MY_SQL_DB_USER, MY_SQL_DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return dbConnection;
	}

	public static Connection getTDDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(MY_SQL_DB_DRIVER);
		} catch (ClassNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
		}
		try {
			dbConnection = DriverManager.getConnection(MY_SQL_DB_CONNECTION_TD, MY_SQL_DB_USER_TD, MY_SQL_DB_PASSWORD_TD);
			return dbConnection;
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return dbConnection;
	}
}



