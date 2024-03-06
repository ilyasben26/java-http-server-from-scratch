package com.ilyasben.httpserver;

import com.ilyasben.httpserver.config.Configuration;
import com.ilyasben.httpserver.config.ConfigurationManager;
import com.ilyasben.httpserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Entry point class for the HTTP server.
 */
public class HttpServer {

    // Logger for logging server events
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    /**
     * Main method to start the HTTP server.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {

        LOGGER.info("Server starting...");

        // Load configuration using ConfigurationManager singleton
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");

        // Get the loaded configuration
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
        LOGGER.info("Using Port: " + conf.getPort());
        LOGGER.info("Using WebRoot: " + conf.getWebroot());

        // Create and start a ServerListenerThread to listen for incoming connections
        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
            // Handle any exceptions that occur during server startup
            e.printStackTrace();
        }
    }
}
