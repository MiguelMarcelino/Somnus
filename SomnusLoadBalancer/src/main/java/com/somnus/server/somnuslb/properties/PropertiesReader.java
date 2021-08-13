package com.somnus.server.somnuslb.properties;

import com.somnus.server.somnuslb.exception.SomnusException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertiesReader {

    private static Logger logger = Logger.getLogger("Properties_Reader");
    private static final String propertiesFileName = "configValues.properties";
    private static Properties properties;


    public static void loadProperties() {
        Properties prop = null;
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(propertiesFileName);
            prop = new Properties();
            prop.load(fis);
        } catch (FileNotFoundException e) {
            logger.severe("Error while retrieving properties file: " + e.getMessage());
        } catch (IOException e) {
            logger.severe("Error loading properties file: " + e.getMessage());
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                throw new SomnusException("Unable to close FileInputStream while reading properties file");
            }
        }
    }

    public static String getPropertiesField(String propName) {
        String propValue = properties.getProperty(propName);
        if(propValue == null)
            throw new SomnusException("Property not found. Property name: " + propName);

        return propValue;
    }
}
