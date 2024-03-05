package com.ilyasben.httpserver;

import com.ilyasben.httpserver.config.ConfigurationManager;

/**
 *
 * Driver Class for the Http Server
 *
 */
public class HttpServer {
    public static void main(String[] args) {

        System.out.println("Server starting...");

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
    }
}
