package com.itl.pns.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.util.ObjectUtils;

public class GlobalPropertyReader {

	@Autowired
	private Environment environment;

	@Value("${envirnoment}")
	private String activeProfile;

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalPropertyReader.class);

	private static GlobalPropertyReader instance = null;
	private Properties appProperties;
	InputStream appInput = null;

	//  UAT/SIT/PROD RUN
//	private GlobalPropertyReader() throws IOException {
//		LOGGER.info("Reading property file");
//		appProperties = new Properties();
//		String profileName = "spring.profiles.active";
//		String serverBaseDir = System.getProperty(profileName);
//		LOGGER.info("JBOSS System Property: " + serverBaseDir);
//		
//		if (!ObjectUtils.isEmpty(serverBaseDir) && serverBaseDir.equalsIgnoreCase("local")) {
//			profileName = "application-local.properties";
//			LOGGER.info("Active Profile Name:" + profileName);
//		} else if (!ObjectUtils.isEmpty(serverBaseDir) && serverBaseDir.equalsIgnoreCase("infradev")) {
//			profileName = "application-infradev.properties";
//			LOGGER.info("Active Profile Name:" + profileName);
//		} else if (!ObjectUtils.isEmpty(serverBaseDir) && serverBaseDir.equalsIgnoreCase("dev")) {
//			profileName = "application-dev.properties";
//			LOGGER.info("Active Profile Name:" + profileName);
//		} else if (!ObjectUtils.isEmpty(serverBaseDir) && serverBaseDir.equalsIgnoreCase("sit")) {
//			profileName = "application-sit.properties";
//			LOGGER.info("Active Profile Name:" + profileName);
//		} else if (!ObjectUtils.isEmpty(serverBaseDir) && serverBaseDir.equalsIgnoreCase("uat")) {
//			profileName = "application-uat.properties";
//			LOGGER.info("Active Profile Name:" + profileName);
//		} else if (!ObjectUtils.isEmpty(serverBaseDir) && serverBaseDir.equalsIgnoreCase("prod")) {
//			profileName = "application-prod.properties";
//			LOGGER.info("Active Profile Name:" + profileName);
//		}
//
//		appInput = getClass().getClassLoader().getResourceAsStream(profileName);
//		if (appInput != null) {
//			appProperties.load(appInput);
//		}
//
//		LOGGER.info("Property file read successfully");
//	}

	// LOCAL RUN
	private GlobalPropertyReader() throws IOException {
		LOGGER.info("Reading property file");
		appProperties = new Properties();
		String profileName = "spring.profiles.active";

		appInput = getClass().getClassLoader().getResourceAsStream("application.properties");
		if (appInput != null) {
			appProperties.load(appInput);
		}

		if (!ObjectUtils.isEmpty(appProperties.get(profileName))
				&& appProperties.get(profileName).toString().equalsIgnoreCase("local")) {
			profileName = "application-local.properties";
			LOGGER.info("Active Profile Name:" + profileName);
		} else if (!ObjectUtils.isEmpty(appProperties.get(profileName))
				&& appProperties.get(profileName).toString().equalsIgnoreCase("infradev")) {
			profileName = "application-infradev.properties";
			LOGGER.info("Active Profile Name:" + profileName);
		} else if (!ObjectUtils.isEmpty(appProperties.get(profileName))
				&& appProperties.get(profileName).toString().equalsIgnoreCase("dev")) {
			profileName = "application-dev.properties";
			LOGGER.info("Active Profile Name:" + profileName);
		} else if (!ObjectUtils.isEmpty(appProperties.get(profileName))
				&& appProperties.get(profileName).toString().equalsIgnoreCase("sit")) {
			profileName = "application-sit.properties";
			LOGGER.info("Active Profile Name:" + profileName);
		} else if (!ObjectUtils.isEmpty(appProperties.get(profileName))
				&& appProperties.get(profileName).toString().equalsIgnoreCase("uat")) {
			profileName = "application-uat.properties";
			LOGGER.info("Active Profile Name:" + profileName);
		} else if (!ObjectUtils.isEmpty(appProperties.get(profileName))
				&& appProperties.get(profileName).toString().equalsIgnoreCase("prod")) {
			profileName = "application-prod.properties";
			LOGGER.info("Active Profile Name:" + profileName);
		}

		appInput = getClass().getClassLoader().getResourceAsStream(profileName);
		if (appInput != null) {
			appProperties.load(appInput);
		}

		LOGGER.info("Property file read successfully");
	}

	public static GlobalPropertyReader getInstance() {
		if (null == instance) {
			try {
				instance = new GlobalPropertyReader();
			} catch (Exception e) {
				LOGGER.error("Error while reading property file", e);
			}
		}
		return instance;
	}

	public static boolean refreshInstance() {
		boolean isRefreshed = false;
		try {
			instance = new GlobalPropertyReader();
			isRefreshed = true;
		} catch (IOException e) {
			LOGGER.error("Error while reading property file", e);
		}
		return isRefreshed;
	}

	public String getValue(String key) {
		return appProperties.getProperty(key);
	}

	public Properties getProperties() {
		return appProperties;
	}

}
