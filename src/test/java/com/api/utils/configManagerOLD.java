package com.api.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class configManagerOLD {
	
	private configManagerOLD() {
		//private constructor so that no one will be able to create object for configManager outside the class
	}

	private static Properties prop = new Properties();// create the object of properties class

	static {    // operation of loading the properties file in the memory
		// static block will be executed once during Class Loading time

		// write a program to read the Properties file from
		// src/test/resources/config/config.properties

		File configFile = new File(System.getProperty("user.dir") + File.separator +"src"+ File.separator +"test"+ File.separator +"resources"
				+ File.separator +"config"+ File.separator +"config.properties");

		FileReader fileReader = null;
		try {
			fileReader = new FileReader(configFile);
			prop.load(fileReader);// load the properties file using load()
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

		catch (IOException e) {

			e.printStackTrace();
		}

	}

	public static String getProperty(String key) throws IOException {

		return prop.getProperty(key);
	}

}
