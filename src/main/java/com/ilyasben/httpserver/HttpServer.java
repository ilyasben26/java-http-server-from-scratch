package com.ilyasben.httpserver;

import com.ilyasben.httpserver.config.Configuration;
import com.ilyasben.httpserver.config.ConfigurationManager;
import com.ilyasben.httpserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 *
 * Driver Class for the Http Server
 *
 */
public class HttpServer {


    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);  // Logger

    public static void main(String[] args) {

        LOGGER.info("Server starting...");
        // using the Configuration Manager singleton to load a configuration file
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");

        // fetching the loaded configuration
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
        LOGGER.info("Using Port: " + conf.getPort());
        LOGGER.info("Using WebRoot: " + conf.getWebroot());

        // Creating and running a ServerListener Thread
        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
            // TODO handle the error properly
        }

    }
}
