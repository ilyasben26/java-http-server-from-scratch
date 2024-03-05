package com.ilyasben.httpserver;

import com.ilyasben.httpserver.config.Configuration;
import com.ilyasben.httpserver.config.ConfigurationManager;

/**
 *
 * Driver Class for the Http Server
 *
 */
public class HttpServer {
    public static void main(String[] args) {

        System.out.println("Server starting...");

        // using the Configuration Manager singleton to load a configuration file
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");

        // fetching the loaded configuration
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
        System.out.println("Using Port: " + conf.getPort());
        System.out.println("Using WebRoot: " + conf.getWebroot());
    }
}
