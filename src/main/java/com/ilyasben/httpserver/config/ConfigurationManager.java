package com.ilyasben.httpserver.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.ilyasben.httpserver.util.Json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {
    // Singleton instance
    private static ConfigurationManager myConfigurationManager;
    private static Configuration myCurrentConfiguration;

    private ConfigurationManager() {
    }

    /**
     * Get an instance of the ConfigurationManager (Singleton pattern).
     * @return The singleton instance of ConfigurationManager.
     */
    public static ConfigurationManager getInstance() {
        if (myConfigurationManager == null)
            myConfigurationManager = new ConfigurationManager();
        return myConfigurationManager;
    }

    /**
     * Loads a configuration file from the provided filePath.
     * @param filePath The path to the configuration file.
     * @throws HttpConfigurationException If there's an issue loading or parsing the configuration file.
     */
    public void loadConfigurationFile(String filePath) {

        FileReader fileReader = null;
        try {
            // Attempt to open the configuration file for reading
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            // Throw an exception if the file is not found
            throw new HttpConfigurationException(e);
        }
        StringBuffer sb = new StringBuffer();
        int i;
        try {
            // Read the contents of the file into a StringBuffer
            while ((i = fileReader.read()) != -1) {
                sb.append((char) i);
            }
        } catch (IOException e) {
            // Throw an exception if there's an issue reading the file
            throw new HttpConfigurationException(e);
        }

        JsonNode conf = null;
        try {
            // Parse the configuration file contents into a JsonNode
            conf = Json.parse(sb.toString());
        } catch (JsonProcessingException e) {
            // Throw an exception if there's an error parsing the JSON
            throw new HttpConfigurationException("Error parsing the Configuration file.", e);
        }
        try {
            // Convert the JsonNode into a Configuration object
            myCurrentConfiguration = Json.fromJson(conf, Configuration.class);
        } catch (JsonProcessingException e) {
            // Throw an exception if there's an error converting the JSON into a Configuration object
            throw new HttpConfigurationException("Error parsing the Configuration file, internal.", e);
        }
    }

    /**
     * Returns the currently loaded configuration.
     * @return The current configuration.
     * @throws HttpConfigurationException If no configuration has been loaded.
     */
    public Configuration getCurrentConfiguration() {
        if (myCurrentConfiguration == null) {
            // Throw an exception if there's no configuration loaded
            throw new HttpConfigurationException("No Current Configuration Set.");
        }
        return myCurrentConfiguration;
    }
}
